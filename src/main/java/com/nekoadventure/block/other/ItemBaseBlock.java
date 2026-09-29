package com.nekoadventure.block.other;

import com.nekoadventure.block.ModBlocks;
import com.nekoadventure.block.blockentity.other.ItemBaseBlockEntity;
import com.nekoadventure.block.specialroomblock.AbstractRoomBlock;
import com.nekoadventure.item.ModItems;
import com.nekoadventure.item.other.NekoPackageItem;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
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
    //这里的冷却系统用AI做的
    private final int[] PROBABILITIES_HEIGHT = {40, 35, 17, 8};
    public static final BooleanProperty INDEPENDENCE = BooleanProperty.of("independence");
    public ItemBaseBlock(Settings settings) {
        super(settings);
        //默认放置为连锁状态（拿出一个物品，全部物品都会被删除）
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
                    //仍在冷却中:直接忽略这次右键,避免同一次点击被重复处理
                    if (itemBaseBlockEntity.isInUseCooldown()) {
                        return ActionResult.FAIL;
                    }
                    //记录本次交互,使接下来的一小段时间内的重复右键全部失效
                    itemBaseBlockEntity.markUsed();

                    int requiredCoins = itemBaseBlockEntity.getRequiredCoins();
                    if (!hasEnoughCoins(player, requiredCoins)) {
                        player.sendMessage(Text.literal("§c你需要 " + requiredCoins + " 个金币"), true);
                        return ActionResult.FAIL;
                    }
                    removeCoins(player, requiredCoins);
                    player.giveItemStack(storedItem);
                    textAndRemoveOtherItemBaseBlock(world, pos,player);
                    itemBaseBlockEntity.markDirty();
                    itemBaseBlockEntity.syncToClient();
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
        player.getInventory().markDirty();
    }

    private void textAndRemoveOtherItemBaseBlock(World world, BlockPos pos,PlayerEntity player) {
        if (!world.getBlockState(pos).get(INDEPENDENCE)) {
            ItemStack nekoPackageItemStack =findNekoPackage(player);
            if (nekoPackageItemStack!=null&&nekoPackageItemStack.getItem() instanceof NekoPackageItem packageItem) {
                if (packageItem.isHaveNekoItem(nekoPackageItemStack,ModItems.ABUNDANT_ITEM_BASE)){
                    world.removeBlock(pos, false);
                    return;
                }
            }
            for (int x=-10;x<10;x++){
                for (int z=-10;z<10;z++){
                    if (world.getBlockState(pos.add(x,0,z)).getBlock().equals(ModBlocks.ITEM_BASE_BLOCK)) {
                        if (!world.getBlockState(pos.add(x,0,z)).get(INDEPENDENCE)) {
                            world.removeBlock(pos.add(x,0,z),false);
                        }
                    }
                }
            }
        }
        else {
            ItemBaseBlockEntity itemBaseBlockEntity = (ItemBaseBlockEntity)world.getBlockEntity(pos);
            if (itemBaseBlockEntity != null&&itemBaseBlockEntity.getRequiredCoins()>0) {
                ItemStack nekoPackageItemStack =findNekoPackage(player);
                if (nekoPackageItemStack !=null&& nekoPackageItemStack.getItem() instanceof NekoPackageItem packageItem){
                    if (packageItem.isHaveNekoItem(nekoPackageItemStack,ModItems.RESTOCK)){
                        for (int i=-10;i<10;i++){
                            for (int j=-10;j<10;j++){
                                for (int k=-2;k<3;k++){
                                    if (world.getBlockState(new BlockPos(pos.getX()+i,pos.getY()+k,pos.getZ()+j)).getBlock() instanceof AbstractRoomBlock abstractRoomBlock){
                                        itemBaseBlockEntity.setRequiredCoins(itemBaseBlockEntity.getRequiredCoins()+10);
                                        itemBaseBlockEntity.generateAndSaveItem((ServerWorld) world,abstractRoomBlock,
                                                PROBABILITIES_HEIGHT[0],PROBABILITIES_HEIGHT[1],PROBABILITIES_HEIGHT[2],PROBABILITIES_HEIGHT[3]);
                                        return;
                                    }
                                }
                            }
                        }
                    }
                }
            }
            world.removeBlock(pos,false);
        }
    }

    private static @Nullable ItemStack findNekoPackage(PlayerEntity player) {
        ItemStack offhand = player.getOffHandStack();
        if (offhand.getItem() == ModItems.NEKO_PACKAGE) {
            return offhand;
        }
        PlayerInventory inventory = player.getInventory();
        for (int i = 0; i < inventory.main.size(); i++) {
            ItemStack stack = inventory.main.get(i);
            if (stack.getItem() == ModItems.NEKO_PACKAGE) {
                return stack;
            }
        }
        for (ItemStack armorStack : inventory.armor) {
            if (armorStack.getItem() == ModItems.NEKO_PACKAGE) {
                return armorStack;
            }
        }
        return null;
    }
}
