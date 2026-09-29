package com.nekoadventure.block.specialroomblock;

import com.nekoadventure.block.ModBlocks;
import com.nekoadventure.block.blockentity.maze.MazeBlockEntity;
import com.nekoadventure.block.blockentity.other.ItemBaseBlockEntity;
import com.nekoadventure.item.ModItems;
import com.nekoadventure.item.nekoItem.AbstractNekoItem;
import com.nekoadventure.other.itemApart.SpawnRandomSoulItems;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ShopRoomBlock extends AbstractRoomBlock {
    private final int[] PROBABILITIES_HEIGHT = {40, 35, 17, 8};
    public ShopRoomBlock(Settings settings) {
        super(settings);
    }

    @Override
    public void placeOn(BlockPos pos, LivingEntity player) {
        World world=player.getWorld();
        if (!world.isClient) {
            putPriceOnWhichItemBaseCount(world, pos);
        }
    }

    //扫描全部的ItemBaseBlock
    private List<BlockPos> scanItemBase(World world,BlockPos pos){
        int roomDistance= MazeBlockEntity.detectRoomDistance(world,pos);
        Box roomBox=new Box(
                pos.getX()+roomDistance,
                pos.getY()+15,
                pos.getZ()+roomDistance,
                pos.getX()-roomDistance,pos.getY(),
                pos.getZ()-roomDistance
        );

        List<BlockPos> itemBases=new ArrayList<>();
        for (int x = (int) roomBox.minX; x <= (int) roomBox.maxX; x++) {
            for (int y = (int) roomBox.minY; y <= (int) roomBox.maxY; y++) {
                for (int z = (int) roomBox.minZ; z <= (int) roomBox.maxZ; z++) {
                    BlockPos checkPos = new BlockPos(x, y, z);
                    BlockState state = world.getBlockState(checkPos);
                    if (state.getBlock() == ModBlocks.ITEM_BASE_BLOCK) {
                        itemBases.add(checkPos);
                    }
                }
            }
        }
        return itemBases;
    }

    //这个是算钱的方法
    private void putPriceOnWhichItemBaseCount(World world, BlockPos pos){
        List<BlockPos> itemBases=scanItemBase(world,pos);
        int itemBaseCount=itemBases.size();
        if (itemBaseCount!=0){
            putNekoItem(itemBases,world);
            putOtherItem(itemBases,world);
        }
        for (BlockPos base : itemBases) {
            BlockEntity itemBase = world.getBlockEntity(base);
            if (itemBase instanceof ItemBaseBlockEntity itemBaseBlockEntity) {
                if (itemBaseBlockEntity.getItem().getItem() instanceof AbstractNekoItem) {
                    itemBaseBlockEntity.setRequiredCoins(world.getRandom().nextInt(10)+7);
                } else {
                    itemBaseBlockEntity.setRequiredCoins(3);
                }
            }
        }
    }
    private void putNekoItem(List<BlockPos> itemBases,World world){
        if (itemBases.size()<=3){
            BlockEntity itemBase=world.getBlockEntity(itemBases.get(0));
            if (itemBase instanceof ItemBaseBlockEntity itemBaseBlockEntity){
                itemBaseBlockEntity.generateAndSaveItem((ServerWorld) world,this,
                        PROBABILITIES_HEIGHT[0],PROBABILITIES_HEIGHT[1],PROBABILITIES_HEIGHT[2],PROBABILITIES_HEIGHT[3]);
            }
        }
        else {
            for (int i=0;i<=(Math.min(itemBases.size()-2,3));i++){
                BlockEntity itemBase=world.getBlockEntity(itemBases.get(i));
                if (itemBase instanceof ItemBaseBlockEntity itemBaseBlockEntity){
                    itemBaseBlockEntity.generateAndSaveItem((ServerWorld) world,this,
                            PROBABILITIES_HEIGHT[0],PROBABILITIES_HEIGHT[1],PROBABILITIES_HEIGHT[2],PROBABILITIES_HEIGHT[3]);
                }
            }
        }
    }
    private void putOtherItem(List<BlockPos> itemBases,World world){
        for (BlockPos base : itemBases) {
            BlockEntity itemBase = world.getBlockEntity(base);
            if (itemBase instanceof ItemBaseBlockEntity itemBaseBlockEntity) {
                if (!(itemBaseBlockEntity.getItem().getItem() instanceof  AbstractNekoItem)) {
                    SpawnRandomSoulItems spawnRandomSoulItems=new SpawnRandomSoulItems();
                    Random rand = new Random();
                    switch (rand.nextInt(3)) {
                        case 0-> itemBaseBlockEntity.setItem(spawnRandomSoulItems.summonRandomSoulItem(world));
                        case 1-> itemBaseBlockEntity.setItem(giveRandomOreItem(world));
                        case 2-> itemBaseBlockEntity.setItem(ModItems.BINDING_SOUL_SUBSTANCE.getDefaultStack());
                    }
                }
            }
        }
    }

    private ItemStack giveRandomOreItem(World world) {
        List<Item> oreItems = List.of(
                Items.COAL,
                Items.IRON_INGOT,
                Items.GOLD_INGOT,
                Items.COPPER_INGOT,
                Items.LAPIS_LAZULI,
                Items.REDSTONE,
                Items.EMERALD,
                Items.DIAMOND,
                Items.NETHERITE_SCRAP
        );
        return new ItemStack(oreItems.get(world.getRandom().nextInt(oreItems.size())));
    }
}
