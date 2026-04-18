package com.nekoadventure.item.soulItem;

import com.nekoadventure.block.ModBlocks;
import com.nekoadventure.block.maze.MazeRoomStageBlock;
import com.nekoadventure.block.specialroomblock.StartRoomBlock;
import com.nekoadventure.effect.ModStatusEffects;
import com.nekoadventure.item.ModItems;
import com.nekoadventure.other.mazeApart.MazeDataManager;
import com.nekoadventure.other.mazeApart.MazePosNBTCompound;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;

public class TPStartRoomSoul extends AbstractSoulItem{
    public TPStartRoomSoul(Settings settings, int maxCharge) {
        super(settings, maxCharge);
        text= Text.of("使用时，如果玩家没有“迷宫诅咒”和“boss战”效果，则传送回该层房间开始点(如果可以)");
    }

    @Override
    public void onUseEffect(World world, PlayerEntity player, ItemStack stack) {
        MazeDataManager dataManager=MazeDataManager.get(world);
        if (dataManager != null) {
            List<MazePosNBTCompound> roomData=dataManager.getRoomData();
            for (MazePosNBTCompound mazePosNBTCompound : roomData) {
                BlockPos blockPos=mazePosNBTCompound.roomCenter();
                if (world.getBlockState(blockPos).equals(ModBlocks.MAZE_ROOM_STAGE_BLOCK.getDefaultState().with(MazeRoomStageBlock.MAZE_STAGE,0))){
                    for (int i=0;i<=30;i++){
                        if (world.getBlockState(blockPos.up(i)).getBlock() instanceof StartRoomBlock startRoomBlock){
                            if (!player.hasStatusEffect(ModStatusEffects.MAZE_CURSE)) {
                                startRoomBlock.placeOn(blockPos.up(3),player);
                            }
                            else {
                                player.giveItemStack(ModItems.TP_START_ROOM_SOUL.getDefaultStack());
                            }
                        }
                    }
                }
            }
        }
    }
}
