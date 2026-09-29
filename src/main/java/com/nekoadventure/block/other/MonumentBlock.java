package com.nekoadventure.block.other;

import com.nekoadventure.network.maze.OpenMonumentScreenPacket;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class MonumentBlock extends Block {
    public MonumentBlock(Settings settings) {
        super(settings);
    }

    // 右键纪念碑时向客户端发送打开感谢名单 UI 的请求
    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (world.isClient) {
            return ActionResult.SUCCESS;
        }
        if (player instanceof ServerPlayerEntity serverPlayer) {
            OpenMonumentScreenPacket.send(serverPlayer);
        }
        return ActionResult.SUCCESS;
    }
}
