package com.nekoadventure.block.maze;

import com.nekoadventure.NekoAdventure;
import com.nekoadventure.block.ModBlocks;
import com.nekoadventure.block.blockentity.maze.GateBlockEntity;
import com.nekoadventure.other.mazeApart.MazeDataManager;
import com.nekoadventure.other.mazeApart.MazePosNBTCompound;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;

public class MazeStructureBlock extends AbstractMazeBlock {
    public MazeStructureBlock(Settings settings) {
        super(settings);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return getDefaultState();
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
    }

    public void apply(World world, BlockPos pos) {
        if (world.isClient) return;
        if (world instanceof ServerWorld serverWorld) {
            MazeDataManager mazeDataManager = MazeDataManager.get(world);
            int roomRange = mazeDataManager != null ? mazeDataManager.getMazeRange() : 0;
            if (isMazeDimension(serverWorld)) {
                    int gateCount = calculateGateCount(world, pos, roomRange);
                    if (gateCount == 0) {
                        world.setBlockState(pos, Blocks.AIR.getDefaultState());
                    }
                    else {
                        MazeDataManager dataManager= MazeDataManager.get(world);
                        MazePosNBTCompound data=settleBlockPos(pos,gateCount);
                        if (dataManager != null) {
                            dataManager.addToInitialData(data);
                            dataManager.addToRoomData(data);
                            world.setBlockState(pos, ModBlocks.MAZE_ROOM_STAGE_BLOCK.getDefaultState().with(MazeRoomStageBlock.MAZE_STAGE,0));
                            dataManager.markDirty();
                            System.out.println("detect room type success in"+pos+"type"+data.gateCount());
                            //检测房间四个方向的range*2+1的位置，如果说含有MazeStructureBlock，则再次执行这个方块中的apply()操作
                            int offset = roomRange * 2 + 1;
                            for (Direction dir : Direction.Type.HORIZONTAL) {
                                BlockPos neighborPos = pos.offset(dir, offset);
                                ChunkPos neighborChunk = new ChunkPos(neighborPos);
                                //区块未加载时读到的方块状态不可信(会读到空气/虚空)，跳过并交给收敛式重试扫描
                                if (!serverWorld.getChunkManager().isChunkLoaded(neighborChunk.x, neighborChunk.z)) {
                                    System.out.println("infect skip " + dir + " " + neighborPos.toShortString() + " chunk not loaded");
                                    continue;
                                }
                                BlockState neighborState = world.getBlockState(neighborPos);
                                //房间标记方块由方块实体异步tick转换而来，内联补一次转换，避免因转换时机过早而漏感染
                                if (neighborState.isOf(Blocks.BEDROCK) && world.getBlockState(neighborPos.down()).isOf(ModBlocks.MAZE_BLOCK)) {
                                    world.setBlockState(neighborPos, ModBlocks.MAZE_STRUCTURE_BLOCK.getDefaultState());
                                    neighborState = world.getBlockState(neighborPos);
                                    System.out.println("infect inline convert " + dir + " " + neighborPos.toShortString());
                                }
                                if (neighborState.getBlock() instanceof MazeStructureBlock mazeBlock) {
                                    mazeBlock.apply(world, neighborPos);
                                    System.out.println("infect detect room type success " + dir + " " + neighborPos.toShortString());
                                } else {
                                    System.out.println("infect skip " + dir + " " + neighborPos.toShortString() + " block=" + neighborState.getBlock());
                                }
                            }
                        }
                    }
                }
        }
    }

    private int calculateGateCount(World world, BlockPos pos, int range) {
        if (world == null) return 0;

        int count = 0;
        for (Direction dir :  Direction.Type.HORIZONTAL) {
                BlockPos checkPos = pos.offset(dir, range);
                //无用的大门由GateBlockEntity在方块实体tick中异步替换成墙壁，
                //这里在统计前同步执行一次替换，保证门数统计与大门处理的最终形态一致
                GateBlockEntity.resolveUselessGate(world, checkPos);
                if (world.getBlockState(checkPos).isOf(ModBlocks.GATE_BLOCK)) {
                    count++;
                }

        }
        return count;
    }

    private MazePosNBTCompound settleBlockPos(BlockPos pos,int gateCount){
        return switch (gateCount) {
            case 1, 2, 3, 4 -> new MazePosNBTCompound(pos, gateCount);
            default -> throw new IllegalStateException("Unexpected gateCount: " + gateCount);
        };
    }
    private boolean isMazeDimension(ServerWorld world) {
        TagKey<DimensionType> isMazeTag = TagKey.of(
                RegistryKeys.DIMENSION_TYPE,
                new Identifier(NekoAdventure.MOD_ID, "is_maze")
        );
        return world.getDimensionEntry().isIn(isMazeTag);
    }
}
