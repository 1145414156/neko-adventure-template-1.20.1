package com.nekoadventure.block.maze;

import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;

public abstract class AbstractMazeBlock extends Block implements BlockEntityProvider {
    public AbstractMazeBlock(Settings settings) {
        super(settings);
    }

}
