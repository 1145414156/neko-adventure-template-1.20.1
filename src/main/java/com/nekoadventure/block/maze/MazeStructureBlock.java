package com.nekoadventure.block.maze;

import com.nekoadventure.NekoAdventure;
import com.nekoadventure.block.ModBlocks;
import com.nekoadventure.block.blockentity.maze.GateBlockEntity;
import com.nekoadventure.other.mazeApart.MazeDataManager;
import com.nekoadventure.other.mazeApart.MazePosNBTCompound;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;

import java.util.List;

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
                            clearRoomEntity(world,dataManager);
                            NekoAdventure.LOGGER.info("detect room type success in{}type{}", pos, data.gateCount());
                            int offset = roomRange * 2 + 1;
                            for (Direction dir : Direction.Type.HORIZONTAL) {
                                BlockPos neighborPos = pos.offset(dir, offset);
                                ChunkPos neighborChunk = new ChunkPos(neighborPos);
                                if (!serverWorld.getChunkManager().isChunkLoaded(neighborChunk.x, neighborChunk.z)) {
                                    NekoAdventure.LOGGER.info("infect skip {} {} chunk not loaded", dir, neighborPos.toShortString());
                                    continue;
                                }
                                BlockState neighborState = world.getBlockState(neighborPos);
                                if (neighborState.isOf(Blocks.BEDROCK) && world.getBlockState(neighborPos.down()).isOf(ModBlocks.MAZE_BLOCK)) {
                                    world.setBlockState(neighborPos, ModBlocks.MAZE_STRUCTURE_BLOCK.getDefaultState());
                                    neighborState = world.getBlockState(neighborPos);
                                    NekoAdventure.LOGGER.info("infect inline convert {} {}", dir, neighborPos.toShortString());
                                }
                                if (neighborState.getBlock() instanceof MazeStructureBlock mazeBlock) {
                                    mazeBlock.apply(world, neighborPos);
                                    NekoAdventure.LOGGER.info("infect detect room type success {} {}", dir, neighborPos.toShortString());
                                } else {
                                    NekoAdventure.LOGGER.info("infect skip {} {} block={}", dir, neighborPos.toShortString(), neighborState.getBlock());
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
                GateBlockEntity.resolveUselessGate(world, checkPos);
                if (world.getBlockState(checkPos).isOf(ModBlocks.GATE_BLOCK)) {
                    count++;
                }

        }
        return count;
    }

    private void clearRoomEntity(World world,MazeDataManager data) {

        List<LivingEntity> entities=world.getEntitiesByClass(LivingEntity.class,
                new Box(data.getMazeRange(),data.getMazeHeight(),data.getMazeRange(),-data.getMazeRange(),0,-data.getMazeRange()),
                e-> !(e instanceof PlayerEntity)&&!(e instanceof ArmorStandEntity)&&e.isAlive());
        List<ItemEntity> itemEntities=world.getEntitiesByClass(ItemEntity.class,
                new Box(data.getMazeRange(),data.getMazeHeight(),data.getMazeRange(),-data.getMazeRange(),0,-data.getMazeRange()),
                item -> item instanceof ItemEntity);
        if (!entities.isEmpty()){
            for (LivingEntity livingEntity : entities) {
                livingEntity.kill();
            }
        }
        if (!itemEntities.isEmpty()){
            for (ItemEntity livingEntity : itemEntities) {
                livingEntity.kill();
            }
        }
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
