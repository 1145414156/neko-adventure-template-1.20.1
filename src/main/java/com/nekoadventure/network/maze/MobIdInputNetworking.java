package com.nekoadventure.network.maze;

import com.nekoadventure.NekoAdventure;
import com.nekoadventure.block.blockentity.maze.SpawnMobBlockEntity;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

//创造模式玩家为刷怪方块设置boss生物ID的网络通信
public class MobIdInputNetworking {
    // S2C：请求客户端打开生物ID输入界面
    public static final Identifier OPEN_INPUT_CHANNEL = new Identifier(NekoAdventure.MOD_ID, "open_mob_id_input");
    // C2S：客户端提交玩家输入的生物ID
    public static final Identifier SUBMIT_INPUT_CHANNEL = new Identifier(NekoAdventure.MOD_ID, "submit_mob_id_input");

    //给单个玩家发送打开输入界面的请求（附带方块位置与当前已设置的生物ID）
    public static void sendOpenInput(ServerPlayerEntity player, BlockPos pos, String currentId) {
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeBlockPos(pos);
        buf.writeString(currentId);
        ServerPlayNetworking.send(player, OPEN_INPUT_CHANNEL, buf);
    }

    //服务端注册（主类调用）
    public static void registerServerReceivers() {
        //客户端提交输入的生物ID
        ServerPlayNetworking.registerGlobalReceiver(SUBMIT_INPUT_CHANNEL,
                (server, player, handler, buf, responseSender) -> {
                    BlockPos pos = buf.readBlockPos();
                    String input = buf.readString();
                    server.execute(() -> applyInput(player, pos, input));
                });
    }

    //校验玩家与目标方块后应用输入
    private static void applyInput(ServerPlayerEntity player, BlockPos pos, String input) {
        if (!player.isCreative() || player.isSpectator()) return;
        if (!(player.getWorld() instanceof ServerWorld serverWorld)) return;
        BlockEntity blockEntity = serverWorld.getBlockEntity(pos);
        if (blockEntity instanceof SpawnMobBlockEntity spawnMobBlockEntity) {
            spawnMobBlockEntity.applyBossEntityIdInput(player, input);
        }
    }
}
