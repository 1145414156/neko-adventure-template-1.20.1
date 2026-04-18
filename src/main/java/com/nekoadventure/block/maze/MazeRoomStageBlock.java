package com.nekoadventure.block.maze;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

public class MazeRoomStageBlock extends AbstractMazeBlock {

    public static final IntProperty MAZE_STAGE = IntProperty.of("maze_stage", 0, 2);
    public MazeRoomStageBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(MAZE_STAGE, 0));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(MAZE_STAGE);
    }

    // 获取阶段
    public static int getMazeStage(BlockState state) {
        return state.get(MAZE_STAGE);
    }

    // 设置阶段（返回新的 BlockState）
    public static BlockState setMazeStage(BlockState state, int stage) {
        return state.with(MAZE_STAGE, stage);
    }

    @Override
    public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return null;
    }
}