package com.nekoadventure.event.maze;

import com.nekoadventure.NekoAdventure;
import com.nekoadventure.block.ModBlocks;
import com.nekoadventure.block.maze.MazeStructureBlock;
import com.nekoadventure.network.maze.ScreenBlackEffectPacket;
import com.nekoadventure.other.mazeApart.MazeBuilder;
import com.nekoadventure.other.mazeApart.MazeDataManager;
import com.nekoadventure.other.mazeApart.MazePosNBTCompound;
import com.nekoadventure.other.mazeApart.MazeStructureBuilder;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.dimension.DimensionType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

//该类由AI辅助完成
//主要作用是在玩家第一次进入tag=is_maze的维度的时候生成一个迷宫
public class PlayerFirstEnterMazeHandler {
    public static final Set<Identifier> dimensionHasBeenVisited = ConcurrentHashMap.newKeySet();

    public static final Map<Identifier, Integer> delayedTasks = new ConcurrentHashMap<>();
    public static final Map<Identifier, Runnable> delayedActions = new ConcurrentHashMap<>();

    /**感染扫描的间隔tick数与最大轮次(兜底防止个别区块始终不就绪时卡住整个流程)*/
    private static final int INFECTION_SCAN_INTERVAL = 10;
    private static final int MAX_INFECTION_SCAN_ROUNDS = 15;

    public static void register() {
        ServerTickEvents.START_WORLD_TICK.register((ServerWorld world) -> {
            if (!isMazeDimension(world)) return;
            Identifier dimensionId = world.getRegistryKey().getValue();
            MinecraftServer server = world.getServer();
            List<ServerPlayerEntity> allPlayers = server.getPlayerManager().getPlayerList();
            if (delayedTasks.containsKey(dimensionId)) {
                int remaining = delayedTasks.get(dimensionId) - 1;
                if (remaining <= 0) {
                    Runnable action = delayedActions.remove(dimensionId);
                    delayedTasks.remove(dimensionId);
                    if (action != null) {
                        action.run();
                    }
                } else {
                    delayedTasks.put(dimensionId, remaining);
                }
                return;
            }

            if (dimensionHasBeenVisited.contains(dimensionId)) return;

            if (!world.getPlayers().isEmpty()) {
                MazeDataManager mazeDataManager = MazeDataManager.get(world);
                if (mazeDataManager != null && mazeDataManager.getRoomData().isEmpty()) {
                    dimensionHasBeenVisited.add(dimensionId);
                    for (ServerPlayerEntity player : allPlayers) {
                        player.sendMessage(Text.literal("§e正在加载迷宫中，请勿退出游戏"), false);
                        ScreenBlackEffectPacket.send(player, true);
                    }

                    new MazeBuilder().placeMaze(world);
                    MazeStructureBuilder builder = new MazeStructureBuilder();

                    //收敛式感染扫描：反复扩散直到无新增房间且区块全部就绪
                    startInfectionScan(world, dimensionId, () -> {
                        delayedTasks.put(dimensionId, 60);
                    delayedActions.put(dimensionId, () -> {
                        builder.placeAllSpecialRoom(world);
                        delayedTasks.put(dimensionId, 20);
                        delayedActions.put(dimensionId, () -> {
                            MazeDataManager data = MazeDataManager.get(world);
                            List<MazePosNBTCompound> initialRoomData=new ArrayList<>();
                            if (data != null) {
                                initialRoomData = data.getInitialData();
                            }
                            /*这些是AI写的
                            作用是:计算，处理全部的房间以及对应的区块光照，防止出现影响游玩体验的情况
                            */
                            builder.recalculateRoomLight(world, initialRoomData);
                            //光照标记完成后，再把房间所在区块的完整区块数据重发给观看玩家，强制客户端整列刷新
                            builder.resendRoomChunks(world, initialRoomData);

                            builder.clearRoomItemEntity(world);
                            mazeDataManager.clearInitialData();
                            for (ServerPlayerEntity player : allPlayers) {
                                player.sendMessage(Text.literal("§e加载迷宫完成"), false);
                                ScreenBlackEffectPacket.send(player, false);
                            }
                        });
                    });
                    });
                }
            }
        });
    }

    public static void getMazeStructureBlockAndApply(ServerWorld world) {
        MazeDataManager data = MazeDataManager.get(world);
        if (data == null || data.getMazeCenter() == null) {
            System.out.println("center not found");
            return;
        }
        BlockPos center = data.getMazeCenter().mazeCenter();
        for (int dy=-2;dy<=2;dy++) {
            BlockPos checkPos =center.up(dy);
            BlockState state = world.getBlockState(checkPos);
            if (state.getBlock() instanceof MazeStructureBlock mazeStructureBlock) {
                mazeStructureBlock.apply(world, checkPos);
            }
        }

    }

    /**
     * 收敛式感染扫描：反复对"已记录房间"的四个方向尝试感染扩散（含BEDROCK标记的内联补转换与区块加载检查），
     * 直到某一轮既没有新房间、也没有未就绪的区块为止，然后执行onConverged进入后续流程。
     * 用于替代原先只调用一次getMazeStructureBlockAndApply的写法：感染依赖方块实体异步tick转换，
     * 一次性快照会因转换/加载时机而随机漏掉房间，因而采用这种反复进行的操作
     */
    public static void startInfectionScan(ServerWorld world, Identifier dimensionId, Runnable onConverged) {
        delayedTasks.put(dimensionId, 30);
        delayedActions.put(dimensionId, () -> infectionScanRound(world, dimensionId, onConverged, 1));
    }

    private static void infectionScanRound(ServerWorld world, Identifier dimensionId, Runnable onConverged, int round) {
        getMazeStructureBlockAndApply(world);
        MazeDataManager data = MazeDataManager.get(world);
        int before = data != null ? data.getRoomData().size() : 0;
        int roomRange = data != null ? data.getMazeRange() : 0;
        boolean pendingChunk = infectRecordedRooms(world, data, roomRange);
        int after = data != null ? data.getRoomData().size() : 0;
        boolean changed = after > before;
        System.out.println("感染扫描第" + round + "轮：房间数 " + before + " -> " + after + "，区块未就绪=" + pendingChunk);

        boolean needRetry = changed || pendingChunk || after == 0;
        if (needRetry && round < MAX_INFECTION_SCAN_ROUNDS) {
            delayedTasks.put(dimensionId, INFECTION_SCAN_INTERVAL);
            delayedActions.put(dimensionId, () -> infectionScanRound(world, dimensionId, onConverged, round + 1));
            return;
        }
        if (after == 0) {
            System.out.println("感染扫描失败：始终没有找到房间标记方块，请检查迷宫生成与mazeRange");
        } else if (needRetry) {
            System.out.println("感染扫描达到最大轮次" + MAX_INFECTION_SCAN_ROUNDS + "，仍有房间未就绪，继续后续流程");
        }
        onConverged.run();
    }

    /**对全部已记录房间的四个方向尝试感染扩散；返回是否仍存在未加载的区块(需要下一轮重试)*/
    private static boolean infectRecordedRooms(ServerWorld world, MazeDataManager data, int roomRange) {
        if (data == null || roomRange <= 0) {
            System.out.println("感染扫描跳过：data=" + (data != null) + "，roomRange=" + roomRange);
            return false;
        }
        int offset = roomRange * 2 + 1;
        boolean pendingChunk = false;
        for (MazePosNBTCompound room : data.getRoomData()) {
            BlockPos center = room.roomCenter();
            for (Direction dir : Direction.Type.HORIZONTAL) {
                BlockPos neighborPos = center.offset(dir, offset);
                ChunkPos neighborChunk = new ChunkPos(neighborPos);
                if (!world.getChunkManager().isChunkLoaded(neighborChunk.x, neighborChunk.z)) {
                    pendingChunk = true;
                    continue;
                }
                BlockState neighborState = world.getBlockState(neighborPos);
                //房间标记方块由方块实体异步tick转换而来，这里内联补一次转换，避免因转换时机过早而漏感染(MazeBlockEntity的转换)
                if (neighborState.isOf(Blocks.BEDROCK) && world.getBlockState(neighborPos.down()).isOf(ModBlocks.MAZE_BLOCK)) {
                    world.setBlockState(neighborPos, ModBlocks.MAZE_STRUCTURE_BLOCK.getDefaultState());
                    neighborState = world.getBlockState(neighborPos);
                    System.out.println("感染扫描内联转换 " + dir + " " + neighborPos.toShortString());
                }
                if (neighborState.getBlock() instanceof MazeStructureBlock mazeStructureBlock) {
                    mazeStructureBlock.apply(world, neighborPos);
                }
            }
        }
        return pendingChunk;
    }

    private static boolean isMazeDimension(ServerWorld world) {
        TagKey<DimensionType> isMazeTag = TagKey.of(
                RegistryKeys.DIMENSION_TYPE,
                new Identifier(NekoAdventure.MOD_ID, "is_maze")
        );
        return world.getDimensionEntry().isIn(isMazeTag);
    }

}