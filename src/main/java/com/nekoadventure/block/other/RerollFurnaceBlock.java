package com.nekoadventure.block.other;

import com.nekoadventure.block.blockentity.other.RerollFurnaceBlockEntity;
import com.nekoadventure.item.ModItems;
import com.nekoadventure.item.nekoItem.AbstractNekoItem;
import com.nekoadventure.item.soulItem.AbstractSoulItem;
import com.nekoadventure.other.itemApart.SpawnRandomNekoItems;
import com.nekoadventure.other.itemApart.SpawnRandomSoulItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class RerollFurnaceBlock extends Block implements BlockEntityProvider {

    public RerollFurnaceBlock(Settings settings) {
        super(settings);
    }
    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (world.isClient) {
            return ActionResult.SUCCESS;
        }
        ItemStack heldStack = player.getStackInHand(hand);
        if (!(heldStack.getItem() instanceof AbstractNekoItem)&&!(heldStack.getItem() instanceof AbstractSoulItem)) {
            return ActionResult.FAIL;
        }
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (!(blockEntity instanceof RerollFurnaceBlockEntity furnace)) {
            return ActionResult.FAIL;
        }

        int useCount = furnace.getUseCount();

        int successRate = 100 - (useCount * 10);
        int requiredCoins = 10 * (useCount + 1) - 5;

        int random = world.getRandom().nextInt(100);
        if (removeCoinsFromPlayer(player, requiredCoins)) {
            if (random < successRate) {
                SpawnRandomSoulItems spawnRandomSoulItems=new SpawnRandomSoulItems();
                SpawnRandomNekoItems spawnRandomNekoItems=new SpawnRandomNekoItems();
                if (world instanceof ServerWorld) {
                    if (heldStack.getItem() instanceof AbstractSoulItem) {
                        player.setStackInHand(hand, spawnRandomSoulItems.summonRandomSoulItem(
                                world));
                    }
                    else {
                        player.setStackInHand(hand, spawnRandomNekoItems.summonRandomItemFromPool(
                                (ServerWorld) world,
                                null,
                                spawnRandomNekoItems.ALL_POOL_PROBABILITIES_HEIGHT[0],
                                spawnRandomNekoItems.ALL_POOL_PROBABILITIES_HEIGHT[1],
                                spawnRandomNekoItems.ALL_POOL_PROBABILITIES_HEIGHT[2],
                                spawnRandomNekoItems.ALL_POOL_PROBABILITIES_HEIGHT[3]));
                    }
                }
                furnace.addUseCount();
                furnace.syncToClient();
                player.sendMessage(Text.literal("物品已重置"), true);

            }
            else {
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
        return new RerollFurnaceBlockEntity(pos, state);
    }
}
