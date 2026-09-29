package com.nekoadventure.block.maze;

import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class AbstractMazeBlock extends Block implements BlockEntityProvider {
    public AbstractMazeBlock(Settings settings) {
        super(settings);
    }

    public int detectRoomDistance(World world, BlockPos pos) {
        for (int i=0;i<=48;i++){
            if (world.getBlockState(new BlockPos(pos.getX()+i,pos.getY(),pos.getZ())).getBlock() instanceof GateBlock) {
                return i;
            }
        }
        return 8;
    }
}
