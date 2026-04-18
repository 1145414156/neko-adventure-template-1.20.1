package com.nekoadventure.block.specialroomblock;

import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.BlockPos;

public class StartRoomBlock extends AbstractRoomBlock {
    public StartRoomBlock(Settings settings) {
        super(settings);
    }

    @Override
    public void placeOn(BlockPos pos, LivingEntity player) {
        if (player != null) {
            player.teleport(pos.getX(), pos.getY(), pos.getZ());
            player.fallDistance=0;
        }
    }

}
