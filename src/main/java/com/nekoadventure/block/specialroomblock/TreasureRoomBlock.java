package com.nekoadventure.block.specialroomblock;

import com.nekoadventure.block.ModBlocks;
import com.nekoadventure.block.blockentity.other.ItemBaseBlockEntity;
import com.nekoadventure.block.other.ItemBaseBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

//通过硬编码来随机生成物品底座和物品(其他的是检测物品底座后生成物品)
public class TreasureRoomBlock extends AbstractRoomBlock {
    private final int[] PROBABILITIES_HEIGHT = {50, 30, 17, 3};
    private final Random RANDOM=new Random();
    public TreasureRoomBlock(Settings settings) {
        super(settings);
    }

    @Override
    public void placeOn(BlockPos pos, LivingEntity player) {
        World world=player.getWorld();
        if (!world.isClient) {
            int randomRoomType=RANDOM.nextInt(100)+1;
            if (randomRoomType<=96){
                placeOneBase(world, pos);
            }
            else if (randomRoomType<=98){
                placeTwoChooseBase(world, pos);
            }
            else if (randomRoomType==99){
                placeMoreChooseBase(world, pos);
            }
            else {
                placeTwoBase(world, pos);
            }
            world.setBlockState(pos.down(1),ModBlocks.MAZE_BLOCK.getDefaultState());
        }
    }

    private void placeOneBase(World world, BlockPos pos) {
        BlockState item_base_block=ModBlocks.ITEM_BASE_BLOCK.getDefaultState();
        world.setBlockState(pos.up(1), item_base_block);
        BlockEntity item_base_block_entity= world.getBlockEntity(pos.up(1));
        if (item_base_block_entity instanceof ItemBaseBlockEntity){
            ((ItemBaseBlockEntity) item_base_block_entity).generateAndSaveItem((ServerWorld) world,this,
                    PROBABILITIES_HEIGHT[0],PROBABILITIES_HEIGHT[1],PROBABILITIES_HEIGHT[2],PROBABILITIES_HEIGHT[3]);
        }
    }
    private void placeTwoChooseBase(World world, BlockPos pos) {
        BlockState item_base_block=ModBlocks.ITEM_BASE_BLOCK.getDefaultState().with(ItemBaseBlock.INDEPENDENCE,false);
        BlockPos blockPos=pos.add(3,1,0);
        BlockPos blockPos1=pos.add(-3,1,0);
        world.setBlockState(blockPos, item_base_block);
        world.setBlockState(blockPos1, item_base_block);
        BlockEntity item_base_block_entity= world.getBlockEntity(blockPos);
        BlockEntity item_base_block_entity1= world.getBlockEntity(blockPos1);
        if (item_base_block_entity instanceof ItemBaseBlockEntity){
            ((ItemBaseBlockEntity) item_base_block_entity).generateAndSaveItem((ServerWorld) world,this,
                    PROBABILITIES_HEIGHT[0],PROBABILITIES_HEIGHT[1],PROBABILITIES_HEIGHT[2],PROBABILITIES_HEIGHT[3]);
        }
        if (item_base_block_entity1 instanceof ItemBaseBlockEntity){
            ((ItemBaseBlockEntity) item_base_block_entity1).generateAndSaveItem((ServerWorld) world,this,
                    PROBABILITIES_HEIGHT[0],PROBABILITIES_HEIGHT[1],PROBABILITIES_HEIGHT[2],PROBABILITIES_HEIGHT[3]);
        }
    }
    private void placeMoreChooseBase(World world, BlockPos pos) {
        BlockState item_base_block=ModBlocks.ITEM_BASE_BLOCK.getDefaultState().with(ItemBaseBlock.INDEPENDENCE,false);
        BlockPos blockPos=pos.add(3,1,3);
        BlockPos blockPos1=pos.add(3,1,-3);
        BlockPos blockPos2=pos.add(-3,1,3);
        BlockPos blockPos3=pos.add(-3,1,-3);
        world.setBlockState(blockPos, item_base_block,3);
        world.setBlockState(blockPos1, item_base_block,3);
        world.setBlockState(blockPos2, item_base_block,3);
        world.setBlockState(blockPos3, item_base_block,3);
        BlockEntity item_base_block_entity= world.getBlockEntity(blockPos);
        BlockEntity item_base_block_entity1= world.getBlockEntity(blockPos1);
        BlockEntity item_base_block_entity2= world.getBlockEntity(blockPos2);
        BlockEntity item_base_block_entity3= world.getBlockEntity(blockPos3);
        if (item_base_block_entity instanceof ItemBaseBlockEntity){
            ((ItemBaseBlockEntity) item_base_block_entity).generateAndSaveItem((ServerWorld) world,this,
                    PROBABILITIES_HEIGHT[0],PROBABILITIES_HEIGHT[1],PROBABILITIES_HEIGHT[2],PROBABILITIES_HEIGHT[3]);
        }
        if (item_base_block_entity1 instanceof ItemBaseBlockEntity){
            ((ItemBaseBlockEntity) item_base_block_entity1).generateAndSaveItem((ServerWorld) world,this,
                    PROBABILITIES_HEIGHT[0],PROBABILITIES_HEIGHT[1],PROBABILITIES_HEIGHT[2],PROBABILITIES_HEIGHT[3]);
        }
        if (item_base_block_entity2 instanceof ItemBaseBlockEntity){
            ((ItemBaseBlockEntity) item_base_block_entity2).generateAndSaveItem((ServerWorld) world,this,
                    PROBABILITIES_HEIGHT[0],PROBABILITIES_HEIGHT[1],PROBABILITIES_HEIGHT[2],PROBABILITIES_HEIGHT[3]);
        }
        if (item_base_block_entity3 instanceof ItemBaseBlockEntity){
            ((ItemBaseBlockEntity) item_base_block_entity3).generateAndSaveItem((ServerWorld) world,this,
                    PROBABILITIES_HEIGHT[0],PROBABILITIES_HEIGHT[1],PROBABILITIES_HEIGHT[2],PROBABILITIES_HEIGHT[3]);
        }
    }
    private void placeTwoBase(World world, BlockPos pos) {
        BlockState item_base_block=ModBlocks.ITEM_BASE_BLOCK.getDefaultState().with(ItemBaseBlock.INDEPENDENCE, true);
        BlockPos blockPos=pos.add(3,1,0);
        BlockPos blockPos1=pos.add(-3,1,0);
        world.setBlockState(blockPos, item_base_block);
        world.setBlockState(blockPos1, item_base_block);
        BlockEntity item_base_block_entity= world.getBlockEntity(blockPos);
        BlockEntity item_base_block_entity1= world.getBlockEntity(blockPos1);
        if (item_base_block_entity instanceof ItemBaseBlockEntity){
            ((ItemBaseBlockEntity) item_base_block_entity).generateAndSaveItem((ServerWorld) world,this,
                    PROBABILITIES_HEIGHT[0],PROBABILITIES_HEIGHT[1],PROBABILITIES_HEIGHT[2],PROBABILITIES_HEIGHT[3]);
        }
        if (item_base_block_entity1 instanceof ItemBaseBlockEntity){
            ((ItemBaseBlockEntity) item_base_block_entity1).generateAndSaveItem((ServerWorld) world,this,
                    PROBABILITIES_HEIGHT[0],PROBABILITIES_HEIGHT[1],PROBABILITIES_HEIGHT[2],PROBABILITIES_HEIGHT[3]);
        }
    }
}
