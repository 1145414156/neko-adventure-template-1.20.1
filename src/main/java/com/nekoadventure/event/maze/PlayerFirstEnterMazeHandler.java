package com.nekoadventure.event.maze;

import com.nekoadventure.NekoAdventure;
import com.nekoadventure.network.maze.ScreenBlackEffectPacket;
import com.nekoadventure.other.mazeApart.MazeBuilder;
import com.nekoadventure.other.mazeApart.MazeDataManager;
import com.nekoadventure.other.mazeApart.MazePosNBTCompound;
import com.nekoadventure.other.mazeApart.MazeStructureBuilder;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
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
                MazeDataManager finalMazeDataManager = MazeDataManager.get(world);
                if (finalMazeDataManager != null && finalMazeDataManager.getRoomData().isEmpty()) {
                    dimensionHasBeenVisited.add(dimensionId);
                    for (ServerPlayerEntity player : allPlayers) {
                        player.sendMessage(Text.literal("§e正在加载迷宫中，请勿退出游戏"), false);
                        ScreenBlackEffectPacket.send(player, true);
                    }

                    new MazeBuilder().placeMaze(world);
                    MazeStructureBuilder builder = new MazeStructureBuilder();
                    // 4秒延迟后生成特殊房间
                    delayedTasks.put(dimensionId, 80);
                    delayedActions.put(dimensionId, () -> {
                        builder.placeAllSpecialRoom(world);
                        delayedTasks.put(dimensionId, 20);
                        delayedActions.put(dimensionId, () -> {

                            //放置完所有特殊房间后，把每一个房间区域逐格重新标记给光照引擎重算
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
                            finalMazeDataManager.clearInitialData();
                            for (ServerPlayerEntity player : allPlayers) {
                                player.sendMessage(Text.literal("§e加载迷宫完成"), false);
                                ScreenBlackEffectPacket.send(player, false);
                            }
                        });
                    });
                }
            }
        });
    }

    private static boolean isMazeDimension(ServerWorld world) {
        TagKey<DimensionType> isMazeTag = TagKey.of(
                RegistryKeys.DIMENSION_TYPE,
                new Identifier(NekoAdventure.MOD_ID, "is_maze")
        );
        return world.getDimensionEntry().isIn(isMazeTag);
    }

}