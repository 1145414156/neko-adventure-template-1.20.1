package com.nekoadventure.block.maze;

import com.nekoadventure.block.blockentity.maze.SpawnMobBlockEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

//开始战斗在另外一个方块实体类
public class SpawnMobBlock extends AbstractMazeBlock {

    public static final IntProperty RANGE_MODE = IntProperty.of("range_mode", 0, 1);

    public SpawnMobBlock(Settings settings) {
        super(settings);
    }
    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(RANGE_MODE);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return getStateManager().getDefaultState().with(RANGE_MODE, 0);
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new SpawnMobBlockEntity(pos,state);
    }
    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        if (!world.isClient) {
            return (world1, pos, state1, blockEntity) -> {
                if (blockEntity instanceof SpawnMobBlockEntity gate) {
                    gate.tick(world1, pos, state1, gate);
                }
            };
        }
        return null;
    }

}
