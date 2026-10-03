package com.nekoadventure.item.soulItem;

import com.nekoadventure.effect.ModStatusEffects;
import com.nekoadventure.item.ModItems;
import com.nekoadventure.other.mazeApart.MazeDataManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TPStartRoomSoul extends AbstractSoulItem{
    public TPStartRoomSoul(Settings settings, int maxCharge) {
        super(settings, maxCharge);
        text= Text.of("使用时，如果玩家没有“迷宫诅咒”和“boss战”效果，则传送回该层房间开始点(如果可以)");
    }

    @Override
    public void onUseEffect(World world, PlayerEntity player, ItemStack stack) {
        MazeDataManager dataManager=MazeDataManager.get(world);
        if (dataManager != null) {
            BlockPos roomData=dataManager.getMazeCenter().mazeCenter();
            if (!player.hasStatusEffect(ModStatusEffects.MAZE_CURSE)) {
                player.teleport(roomData.getX(), roomData.getY()+2, roomData.getZ());
            }
            else {
                player.giveItemStack(ModItems.TP_START_ROOM_SOUL.getDefaultStack());
            }
        }
    }
}
