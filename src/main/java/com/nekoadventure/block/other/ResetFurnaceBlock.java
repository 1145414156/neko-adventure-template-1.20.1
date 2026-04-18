package com.nekoadventure.block.other;

import com.nekoadventure.block.blockentity.other.ResetFurnaceBlockEntity;
import com.nekoadventure.item.ModItems;
import com.nekoadventure.item.nekoItem.AbstractNekoItem;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class ResetFurnaceBlock extends Block implements BlockEntityProvider {
    public ResetFurnaceBlock(Settings settings) {
        super(settings);
    }
    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (world.isClient) {
            return ActionResult.SUCCESS;
        }

        // 获取手持物品
        ItemStack heldStack = player.getStackInHand(hand);
        if (!(heldStack.getItem() instanceof AbstractNekoItem)) {
            return ActionResult.FAIL;
        }

        // 获取方块实体
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (!(blockEntity instanceof ResetFurnaceBlockEntity furnace)) {
            return ActionResult.FAIL;
        }

        int useCount = furnace.getUseCount();

        int successRate = 100 - (useCount * 10);
        int requiredCoins = 5*(useCount+1)-5;

        int random = world.getRandom().nextInt(100);
        if (removeCoinsFromPlayer(player, requiredCoins)) {
            if (random < successRate) {
                heldStack.decrement(1);
                for (int r=4;r>0;r--) {
                    player.giveItemStack(ModItems.BINDING_SOUL_SUBSTANCE.getDefaultStack());
                }
                furnace.addUseCount();

                furnace.syncToClient();
                player.sendMessage(Text.literal("成功！消耗了 " + requiredCoins + " 个金币，物品已还原"), true);

            }
            else {
                // 失败：机器爆炸，物品和金币不变
                world.addBlockBreakParticles(pos,state);
                world.removeBlock(pos, false);
                player.sendMessage(Text.literal("机器发生了爆炸！"), true);
            }
        }
        return ActionResult.SUCCESS;
    }

    private boolean removeCoinsFromPlayer(PlayerEntity player, int amount) {
        int totalCoins = 0;
        for (int i = 0; i < player.getInventory().size(); i++) {
            ItemStack stack = player.getInventory().getStack(i);
            if (stack.isOf(ModItems.COIN)) {
                totalCoins += stack.getCount();
            }
        }

        if (totalCoins < amount) {
            return false;
        }

        int remaining = amount;
        for (int i = 0; i < player.getInventory().size(); i++) {
            ItemStack stack = player.getInventory().getStack(i);
            if (stack.isOf(ModItems.COIN)) {
                int stackCount = stack.getCount();
                if (stackCount >= remaining) {
                    stack.decrement(remaining);
                    break;
                } else {
                    remaining -= stackCount;
                    stack.setCount(0);
                }
            }
        }

        return true;
    }


    @Override
    public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new ResetFurnaceBlockEntity(pos, state);
    }
}
