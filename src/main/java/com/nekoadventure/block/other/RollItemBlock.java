package com.nekoadventure.block.other;

import com.nekoadventure.block.blockentity.other.RollItemBlockEntity;
import com.nekoadventure.item.ModItems;
import com.nekoadventure.item.nekoItem.AbstractNekoItem;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class RollItemBlock extends Block implements BlockEntityProvider {
    public RollItemBlock(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (world.isClient) {
            return ActionResult.SUCCESS;
        }

        ItemStack heldItem = player.getStackInHand(hand);
        ItemStack item=new ItemStack(heldItem.getItem());

        if (heldItem.getItem() instanceof AbstractNekoItem) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (!(blockEntity instanceof RollItemBlockEntity rollItemEntity)) {
                return ActionResult.PASS;
            }

            heldItem.decrement(1);

            int useCount = rollItemEntity.getUseCount();
            int baseProbability = 25 - useCount;

            int roll = world.random.nextInt(100);

            if (roll < baseProbability) {
                world.addBlockBreakParticles(pos, state);
                player.sendMessage(Text.literal("§7机器没有反应..."), true);
            } else if (roll < baseProbability + 25) {
                // 25% 概率：返还投入的道具
                player.getInventory().offerOrDrop(item);
                player.sendMessage(Text.literal("§a机器返还了你的道具！"), true);
            } else if (roll < baseProbability + 75) {
                // 50% 概率：生成随机金币（10~20）
                int coinCount = world.random.nextInt(16)+11;
                player.getInventory().offerOrDrop(new ItemStack(ModItems.COIN, coinCount));
                player.sendMessage(Text.literal("§6机器吐出了 " + coinCount + " 个金币！"), true);
            } else {
                // 剩余概率：机器爆炸并删除方块
                world.removeBlock(pos, false);
                player.sendMessage(Text.literal("§c机器爆炸了！"), true);
                return ActionResult.SUCCESS;
            }

            rollItemEntity.setUseCount(useCount + 10);
            rollItemEntity.markDirty();

            return ActionResult.SUCCESS;
        }

        return ActionResult.PASS;
    }

    @Override
    public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new RollItemBlockEntity(pos, state);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return VoxelShapes.cuboid(0.0, 0.0, 0.0, 1.0, 1.4, 1.0);
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return VoxelShapes.cuboid(0.0, 0.0, 0.0, 1.0, 1.4, 1.0);
    }
}
