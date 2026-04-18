package com.nekoadventure.block.other;

import com.nekoadventure.block.ModBlocks;
import com.nekoadventure.block.blockentity.other.ItemBaseBlockEntity;
import com.nekoadventure.item.ModItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class ItemBaseBlock extends Block implements BlockEntityProvider {
    public static final BooleanProperty INDEPENDENCE = BooleanProperty.of("independence");
    public ItemBaseBlock(Settings settings) {
        super(settings);
        //默认放置为连锁状态
        setDefaultState(getStateManager().getDefaultState().with(INDEPENDENCE, false));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(INDEPENDENCE);
    }

    @Override
    public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new ItemBaseBlockEntity(pos, state);
    }

    // 空手右键的时候取出存储的物品
    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (world instanceof ServerWorld) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (!(blockEntity instanceof ItemBaseBlockEntity itemBaseBlockEntity)) {
                return ActionResult.PASS;
            }

            ItemStack heldItem = player.getStackInHand(hand);
            ItemStack storedItem = itemBaseBlockEntity.getItem();

            if (heldItem.isEmpty()) {
                if (!storedItem.isEmpty()) {
                    // 从方块实体获取动态需要的硬币数量
                    int requiredCoins = itemBaseBlockEntity.getRequiredCoins();

                    // 检查玩家是否有足够的硬币
                    if (!hasEnoughCoins(player, requiredCoins)) {
                        player.sendMessage(Text.literal("§c你需要 " + requiredCoins + " 个金币"), true);
                        return ActionResult.FAIL;
                    }

                    // 扣除硬币
                    removeCoins(player, requiredCoins);

                    // 取出物品
                    player.setStackInHand(hand, storedItem.copy());
                    itemBaseBlockEntity.setItem(ItemStack.EMPTY);
                    itemBaseBlockEntity.markDirty();
                    itemBaseBlockEntity.syncToClient();

                    textAndRemoveOtherItemBaseBlock(world, pos);

                    return ActionResult.SUCCESS;
                }
                return ActionResult.PASS;
            }
        }
        return ActionResult.PASS;
    }

    private boolean hasEnoughCoins(PlayerEntity player, int requiredAmount) {
        int coinCount = 0;

        for (int i = 0; i < player.getInventory().size(); i++) {
            ItemStack stack = player.getInventory().getStack(i);

            if (stack.getItem() == ModItems.COIN) {
                coinCount += stack.getCount();

                if (coinCount >= requiredAmount) {
                    return true;
                }
            }
        }

        return coinCount >= requiredAmount;
    }

    // 扣除指定数量的硬币
    private void removeCoins(PlayerEntity player, int amount) {
        int remaining = amount;

        for (int i = 0; i < player.getInventory().size(); i++) {
            ItemStack stack = player.getInventory().getStack(i);

            if (stack.getItem() == ModItems.COIN) {
                int stackCount = stack.getCount();

                if (stackCount >= remaining) {
                    // 当前物品栏足够扣除
                    stack.decrement(remaining);
                    break;
                } else {
                    // 当前物品栏不够，继续查找
                    remaining -= stackCount;
                    stack.setCount(0);
                }
            }
        }

        // 更新玩家物品栏
        player.getInventory().markDirty();
    }

    private void textAndRemoveOtherItemBaseBlock(World world, BlockPos pos) {
        if (!world.getBlockState(pos).get(INDEPENDENCE)) {
            for (int x=-10;x<10;x++){
                for (int z=-10;z<10;z++){
                    if (world.getBlockState(pos.add(x,0,z)).getBlock().equals(ModBlocks.ITEM_BASE_BLOCK)) {
                        if (!world.getBlockState(pos.add(x,0,z)).get(INDEPENDENCE)) {
                            world.removeBlock(pos.add(x,0,z),false);
                            world.addBlockBreakParticles(pos,ModBlocks.ITEM_BASE_BLOCK.getDefaultState());
                        }
                    }
                }
            }
        }
        else {
            world.removeBlock(pos,false);
        }
    }
}
