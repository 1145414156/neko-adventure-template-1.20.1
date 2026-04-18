package com.nekoadventure.block.blockentity.maze;

import com.nekoadventure.NekoAdventure;
import com.nekoadventure.block.ModBlocks;
import com.nekoadventure.block.blockentity.ModBlockEntityTypes;
import com.nekoadventure.block.maze.MazeRoomStageBlock;
import com.nekoadventure.block.specialroomblock.StartRoomBlock;
import com.nekoadventure.other.mazeApart.MazeDataManager;
import com.nekoadventure.other.mazeApart.MazePosNBTCompound;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;

//这个类是用来读取，处理，上传房间数据到存档文件夹的
//同时把玩家tp到出生点
public class MazeStructureBlockEntity extends AbstractMazeBlockEntity {
    private int tick;
    public MazeStructureBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntityTypes.MAZE_STRUCTURE_BLOCK_ENTITY, pos, state);
    }


    //这个方法是用来计算门的数量的
private int calculateGateCount(World world, BlockPos pos) {
        int a=0;
        int detect=MazeBlockEntity.detectRoomDistance(world,pos);
        if (world.getBlockState(new BlockPos(pos.getX()+detect,pos.getY(),pos.getZ())).getBlock() == ModBlocks.GATE_BLOCK) {a++;}
        if (world.getBlockState(new BlockPos(pos.getX()-detect,pos.getY(),pos.getZ())).getBlock() == ModBlocks.GATE_BLOCK) {a++;}
        if (world.getBlockState(new BlockPos(pos.getX(),pos.getY(),pos.getZ()+detect)).getBlock() == ModBlocks.GATE_BLOCK) {a++;}
        if (world.getBlockState(new BlockPos(pos.getX(),pos.getY(),pos.getZ()-detect)).getBlock() == ModBlocks.GATE_BLOCK) {a++;}
        //当门是数量为0时，直接移除这个方块

        return a;
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

    @Override
    public void tick(World world, BlockPos pos, BlockState state, BlockEntity blockEntity) {
        if (world.isClient) return;

        if (world instanceof ServerWorld serverWorld) {
            int PREPARE_TICK = 5;
            if (tick< PREPARE_TICK) {
                tick++;
            }
            if(tick>= PREPARE_TICK) {
                if (isMazeDimension(serverWorld)) {
                    if (calculateGateCount(world,pos)==0) {
                        world.setBlockState(pos, Blocks.AIR.getDefaultState());
                    }
                    else {
                        MazeDataManager dataManager= MazeDataManager.get(world);
                        MazePosNBTCompound data=settleBlockPos(pos,calculateGateCount(world,pos));
                        if (dataManager != null) {
                            dataManager.addToInitialData(data);
                            dataManager.addToRoomData(data);
                            for (int i=0;i<=30;i++){
                                if (world.getBlockState(pos.up(i)).getBlock() instanceof StartRoomBlock startRoomBlock){
                                    for (PlayerEntity player:world.getPlayers()) {
                                        startRoomBlock.placeOn(pos.up(3),player);
                                    }
                                    break;
                                }
                            }
                            world.setBlockState(pos,ModBlocks.MAZE_ROOM_STAGE_BLOCK.getDefaultState().with(MazeRoomStageBlock.MAZE_STAGE,0));
                            tick=0;
                            dataManager.markDirty();
                        }
                    }
                }
            }
        }
    }
}
