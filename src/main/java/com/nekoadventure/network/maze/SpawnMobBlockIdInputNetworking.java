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

public class SpawnMobBlockIdInputNetworking {
    public static final Identifier OPEN_INPUT_CHANNEL = new Identifier(NekoAdventure.MOD_ID, "open_mob_id_input");
    public static final Identifier SUBMIT_INPUT_CHANNEL = new Identifier(NekoAdventure.MOD_ID, "submit_mob_id_input");
    public static void sendOpenInput(ServerPlayerEntity player, BlockPos pos, String currentId) {
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeBlockPos(pos);
        buf.writeString(currentId);
        ServerPlayNetworking.send(player, OPEN_INPUT_CHANNEL, buf);
    }
    public static void registerServerReceivers() {
        ServerPlayNetworking.registerGlobalReceiver(SUBMIT_INPUT_CHANNEL,
                (server, player, handler, buf, responseSender) -> {
                    BlockPos pos = buf.readBlockPos();
                    String input = buf.readString();
                    server.execute(() -> applyInput(player, pos, input));
                });
    }
    private static void applyInput(ServerPlayerEntity player, BlockPos pos, String input) {
        if (!player.isCreative() || player.isSpectator()) return;
        if (!(player.getWorld() instanceof ServerWorld serverWorld)) return;
        BlockEntity blockEntity = serverWorld.getBlockEntity(pos);
        if (blockEntity instanceof SpawnMobBlockEntity spawnMobBlockEntity) {
            spawnMobBlockEntity.applyBossEntityIdInput(player, input);
        }
    }
}
