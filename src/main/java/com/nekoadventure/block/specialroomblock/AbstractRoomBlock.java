package com.nekoadventure.block.specialroomblock;

import net.minecraft.block.Block;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.BlockPos;

public abstract class AbstractRoomBlock extends Block {
    public AbstractRoomBlock(Settings settings) {
        super(settings);
    }
    public abstract void placeOn(BlockPos pos, LivingEntity player);
}
