package com.nekoadventure.event.maze;

import com.nekoadventure.NekoAdventure;
import com.nekoadventure.other.mazeApart.MazeDataManager;
import com.nekoadventure.other.mazeApart.MazePosNBTCompound;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;
import java.util.ArrayList;
import java.util.List;

public class MazeRemoveHandler {
    private static final int APPLY_TICK=100;
    private static int tick=0;
    public static void register() {
        ServerTickEvents.START_WORLD_TICK.register((ServerWorld world) -> {
            MinecraftServer server = world.getServer();
            List<ServerPlayerEntity> allPlayers = server.getPlayerManager().getPlayerList();
            MazeDataManager mazeDataManager=MazeDataManager.get(world);
            List<MazePosNBTCompound> mazeData = new ArrayList<>();
            if (mazeDataManager != null) {
                 mazeData = mazeDataManager.getRoomData();
            }
            if (!isMazeDimension(world)) {return;}
            if (mazeData.isEmpty()){return;}

            if (world.getPlayers().isEmpty()) {

                if (tick==1){
                    for (ServerPlayerEntity player : allPlayers) {
                        System.out.println(mazeData.size());
                        player.sendMessage(Text.literal("§e正在移除迷宫中，请勿退出游戏"), false);
                    }
                }
                if (tick<=APPLY_TICK) {
                    tick++;
                }
                if (tick>APPLY_TICK) {
                    removeAllMaze(world,allPlayers);
                    tick=0;
                }
            }
                else {
                    tick=0;
                }
        });
    }

    //这个是玩家在迷宫里面四了的方法
    public static void removeAllMaze(ServerWorld world,List<ServerPlayerEntity> allPlayers) {
        MazeDataManager mazeDataManager=MazeDataManager.get(world);
        if (mazeDataManager != null) {
            List<MazePosNBTCompound> mazeData = mazeDataManager.getRoomData();
            if (!mazeData.isEmpty()) {

                for (MazePosNBTCompound mazePosNBTCompound : mazeData) {
                    BlockPos center = mazePosNBTCompound.roomCenter();
                    clearRooms(world, center);
                }
                if (mazeDataManager.getLevelData()>1){
                    removeBossRoom(world,new BlockPos(0,0,0));
                }
                mazeDataManager.clearAllData();
                for (ServerPlayerEntity player : allPlayers) {
                    player.sendMessage(Text.literal("§e已移除迷宫"), false);
                }
            }

        }
        PlayerFirstEnterMazeHandler.dimensionHasBeenVisited.remove(world.getRegistryKey().getValue());
    }
    //这个是去下一层用的方法
    public static void removeAllMaze(ServerWorld world, List<MazePosNBTCompound> mazeData,List<ServerPlayerEntity> allPlayers) {
        MazeDataManager mazeDataManager=MazeDataManager.get(world);
        if (mazeDataManager != null) {
            for (ServerPlayerEntity player : allPlayers) {
                player.sendMessage(Text.literal("§a正在移除迷宫中，请勿退出游戏"), false);
            }
            for (MazePosNBTCompound mazePosNBTCompound : mazeData) {
                BlockPos center = mazePosNBTCompound.roomCenter();
                clearRooms(world, center);
            }

            if (mazeDataManager.getLevelData()>1){
                removeBossRoom(world,new BlockPos(0,0,0));
            }

            mazeDataManager.clearRoomData();
            for (ServerPlayerEntity player : allPlayers) {
                player.sendMessage(Text.literal("§a已移除迷宫"), false);
            }
        }
    }

    //这个是实际开始清除房间的方法
    private static void clearRooms(ServerWorld world, BlockPos center) {
        // 计算范围：中心点向各个方向延伸 24 格（总共 50 格）
        int radius = 24;
        int startX = center.getX() - radius;
        int endX = center.getX() + radius;
        int startY = center.getY() - radius;
        int endY = center.getY() + radius;
        int startZ = center.getZ() - radius;
        int endZ = center.getZ() + radius;
        startY = Math.max(startY, world.getBottomY());
        endY = Math.min(endY, world.getTopY() - 1);

        Box box=new Box(
                startX,startY,startZ,
                endX,endY,endZ
        );
        for (int x = startX; x <= endX; x++) {
            for (int y = world.getBottomY(); y < world.getTopY(); y++) {
                for (int z = startZ; z <= endZ; z++) {
                    BlockPos pos = new BlockPos(x, y, z);
                    BlockState blockState = world.getBlockState(pos);
                    if (!blockState.isAir()) {
                        world.setBlockState(pos, Blocks.AIR.getDefaultState(), 3);
                    }
                }
            }
        }
        List<Entity> entities=world.getEntitiesByClass(
                Entity.class,
                box,
                entity -> true

        );
        for (Entity entity : entities) {
            entity.kill();
        }

        System.out.println("清除范围: x=" + startX + "~" + endX +
                ", y=" + startY + "~" + endY +
                ", z=" + startZ + "~" + endZ);
    }

    private static boolean isMazeDimension(ServerWorld world) {
        TagKey<DimensionType> isMazeTag = TagKey.of(
                RegistryKeys.DIMENSION_TYPE,
                new Identifier(NekoAdventure.MOD_ID, "is_maze")
        );
        return world.getDimensionEntry().isIn(isMazeTag);
    }
    private static void removeBossRoom(World world, BlockPos center) {
        if (world.isClient) return;
        int minX = center.getX() - 32;
        int maxX = center.getX() + 32;
        int minY = center.getY();
        int maxY = center.getY() + 64;
        int minZ = center.getZ() - 32;
        int maxZ = center.getZ() + 32;
        minY = Math.max(minY, world.getBottomY());
        for (int x = minX; x <= maxX; x++) {
            for (int y = minY; y <= maxY; y++) {
                for (int z = minZ; z <= maxZ; z++) {
                    BlockPos pos = new BlockPos(x, y, z);
                    BlockState blockState = world.getBlockState(pos);
                    if (!blockState.isAir()) {
                        world.setBlockState(pos, Blocks.AIR.getDefaultState(), 3);
                    }
                }
            }
        }
        Box box = new Box(
                minX, minY, minZ,
                maxX + 1.0, maxY + 1.0, maxZ + 1.0
        );
        List<Entity> entities = world.getEntitiesByClass(Entity.class, box, entity -> true);
        for (Entity entity : entities) {
            if (!(entity instanceof ServerPlayerEntity)) {
                entity.kill();
            }
        }
    }
}