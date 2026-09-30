package com.nekoadventure.block.specialroomblock;

import com.nekoadventure.block.ModBlocks;
import com.nekoadventure.block.blockentity.other.ItemBaseBlockEntity;
import com.nekoadventure.other.itemApart.SpawnRandomNekoItems;
import com.nekoadventure.other.mazeApart.MazeDataManager;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class ConcealRoomBlock extends AbstractRoomBlock {
    private final int[] PROBABILITIES_HEIGHT = {35, 30, 20, 15};
    private int roomRange;

    public ConcealRoomBlock(Settings settings) {
        super(settings);
    }

    @Override
    public void placeOn(BlockPos pos, LivingEntity player) {
        List<BlockPos> itemBases=scanItemBase(player.getWorld(),pos);
        putSoulItem(itemBases,player.getWorld());
    }

    private List<BlockPos> scanItemBase(World world, BlockPos center){
        if (world==null)return new ArrayList<>();
        if (roomRange<=0){
            MazeDataManager mazeDataManager=MazeDataManager.get(world);
            if (mazeDataManager!=null){
                roomRange=mazeDataManager.getMazeRange();
            }
        }
        int roomDistance= roomRange;
        Box roomBox=new Box(
                center.getX()+roomDistance,
                center.getY()+15,
                center.getZ()+roomDistance,
                center.getX()-roomDistance, center.getY(),
                center.getZ()-roomDistance
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
    private void putSoulItem(List<BlockPos> itemBases,World world){
        for (BlockPos pos : itemBases) {
            BlockEntity itemBase=world.getBlockEntity(pos);
            if (itemBase instanceof ItemBaseBlockEntity itemBaseBlockEntity&&world instanceof ServerWorld){
                SpawnRandomNekoItems randomNekoItems=new SpawnRandomNekoItems();
                itemBaseBlockEntity.setItem(randomNekoItems.summonRandomItemFromPool((ServerWorld) world,this,PROBABILITIES_HEIGHT[0],PROBABILITIES_HEIGHT[1],PROBABILITIES_HEIGHT[2],PROBABILITIES_HEIGHT[3]));
            }
        }
    }
}