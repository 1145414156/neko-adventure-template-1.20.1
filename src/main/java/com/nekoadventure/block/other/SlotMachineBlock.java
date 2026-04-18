package com.nekoadventure.block.other;

import com.nekoadventure.block.blockentity.other.SlotMachineBlockEntity;
import com.nekoadventure.item.ModItems;
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

public class SlotMachineBlock extends Block implements BlockEntityProvider {
    public SlotMachineBlock(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (world.isClient) {
            return ActionResult.SUCCESS;
        }

        ItemStack heldItem = player.getStackInHand(hand);

        if (heldItem.getItem() == ModItems.COIN) {
            int useCount = getUseCount(world, pos);

            heldItem.decrement(1);

            int roll = world.random.nextInt(100);

            if (roll < 60-useCount) {
                // 基础60%-useCount：什么都不生成
                world.addBlockBreakParticles(pos, state);
            }
           else if (roll < 80-useCount) {
                // 20%概率：生成1金币
                giveCoins(player, 1);
                player.sendMessage(Text.literal("§a你获得了 1 枚金币！"), true);
            } else if (roll < 85-useCount) {
                // 5%概率：生成2金币
                giveCoins(player, 2);
                player.sendMessage(Text.literal("§a你获得了 2 枚金币！"), true);
            } else if (roll<90-useCount){
                // 5%概率：生成3金币
                giveCoins(player, 3);
                player.sendMessage(Text.literal("§a你获得了 3 枚金币！"), true);
            }
           else if (roll<91-useCount){
                // 1%概率：随机生成1~5金币
               giveCoins(player, world.random.nextInt(5));
                player.sendMessage(Text.literal("§a你获得了 §c-error- §a枚金币！"), true);
            }
            else {
                world.breakBlock(pos, false);
                world.addBlockBreakParticles(pos, state);
                player.sendMessage(Text.literal("§c老虎机故障爆炸了！"), true);
            }

            // 增加使用次数
            incrementUseCount(world, pos);

            return ActionResult.SUCCESS;
        }

        return ActionResult.PASS;
    }

    private void giveCoins(PlayerEntity player, int count) {
        player.getInventory().offerOrDrop(new ItemStack(ModItems.COIN, count));
    }

    // 获取方块的使用次数（存储在方块实体中）
    private int getUseCount(World world, BlockPos pos) {
        if (world.getBlockEntity(pos) instanceof SlotMachineBlockEntity blockEntity) {
            return blockEntity.getUseCount();
        }
        return 0;
    }

    // 增加使用次数
    private void incrementUseCount(World world, BlockPos pos) {
        if (world.getBlockEntity(pos) instanceof SlotMachineBlockEntity blockEntity) {
            blockEntity.incrementUseCount();
            blockEntity.markDirty();
        }
    }

    @Override
    public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new SlotMachineBlockEntity(pos,state);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return VoxelShapes.cuboid(0.0, 0.0, 0.0, 1.0, 1.6, 1.0);
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return VoxelShapes.cuboid(0.0, 0.0, 0.0, 1.0, 1.6, 1.0);
    }
}
