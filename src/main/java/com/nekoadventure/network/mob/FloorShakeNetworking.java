package com.nekoadventure.network.mob;

import com.nekoadventure.NekoAdventure;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.block.BlockState;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

public class FloorShakeNetworking {

    public static final Identifier CHANNEL = new Identifier(NekoAdventure.MOD_ID, "floor_rotate");

    /** 默认的最小/最大弹跳高度 */
    public static final float DEFAULT_MIN_HEIGHT = 0.8F;
    public static final float DEFAULT_MAX_HEIGHT = 2.0F;

    /**
     * 给单个玩家发送地板旋转效果
     */
    public static void sendToPlayer(ServerPlayerEntity player, BlockPos center, double radius,
                                    float minHeight, float maxHeight) {
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeBlockPos(center);
        buf.writeDouble(radius);
        buf.writeFloat(minHeight);
        buf.writeFloat(maxHeight);
        ServerPlayNetworking.send(player, CHANNEL, buf);
    }

    /**
     * 给一个世界里的所有在线玩家发送地板效果
     * 使用默认高度范围
     */
    public static void sendToAll(ServerWorld world, BlockPos center, double radius) {
        sendToAll(world, center, radius, false, DEFAULT_MIN_HEIGHT, DEFAULT_MAX_HEIGHT);
    }

    /**
     * 给一个世界里的所有在线玩家发送地板效果
     */
    public static void sendToAll(ServerWorld world, BlockPos center, double radius,
                                 float minHeight, float maxHeight) {
        sendToAll(world, center, radius, false, minHeight, maxHeight);
    }

    /**
     * 给一个世界里的所有在线玩家发送地板旋转效果
     *
     * @param destroyBlocks 是否真实践踏：true 时受影响方块被破坏（不掉落物，基岩等不可破坏方块除外）
     */
    public static void sendToAll(ServerWorld world, BlockPos center, double radius,
                                 boolean destroyBlocks, float minHeight, float maxHeight) {
        for (ServerPlayerEntity player : world.getPlayers()) {
            sendToPlayer(player, center, radius, minHeight, maxHeight);
        }
        if (destroyBlocks) {
            destroyFloorBlocks(world, center, radius);
        }
    }

    /**
     * 破坏影响范围内的地板方块（与客户端收集旋转方块的条件保持一致：跳过空气和不可破坏方块）
     */
    private static void destroyFloorBlocks(ServerWorld world, BlockPos center, double radius) {
        int r = (int) Math.ceil(radius);
        for (int dx = -r; dx <= r; dx++) {
            for (int dz = -r; dz <= r; dz++) {
                if (dx * dx + dz * dz > radius * radius) {
                    continue;
                }
                BlockPos pos = center.add(dx, 0, dz);
                BlockState state = world.getBlockState(pos);
                if (state.isAir() || state.getHardness(world, pos) < 0) {
                    continue;
                }
                world.breakBlock(pos, false);
            }
        }
    }
}

