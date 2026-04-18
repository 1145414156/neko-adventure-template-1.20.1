package com.nekoadventure.block.blockentity.maze;

import com.nekoadventure.NekoAdventure;
import com.nekoadventure.block.blockentity.ModBlockEntityTypes;
import com.nekoadventure.effect.ModStatusEffects;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;
import org.jetbrains.annotations.NotNull;

import java.util.List;

//这个类是专门用来做在获得战斗效果时，关门（生成对应的方块）
public class GateBlockEntity extends BlockEntity implements BlockEntityTicker<GateBlockEntity> {


    public GateBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntityTypes.GATE_BLOCK,pos,state);

    }

    private List<PlayerEntity> getPlayersInRange() {
        if (world == null || pos == null) return List.of();
        Box detectionBox = getBox();
        return world.getEntitiesByClass(
                PlayerEntity.class,
                detectionBox,
                player -> player.isAlive() && !player.isSpectator()&&!player.isCreative()
        );
    }

    private @NotNull Box getBox() {
        int range = 48;
        double minX = pos.getX() - range;
        double minY = pos.getY() - 3;
        double minZ = pos.getZ() - range;
        double maxX = pos.getX() + range;
        double maxY = pos.getY() + 15;
        double maxZ = pos.getZ() + range;
        return new Box(minX, minY, minZ, maxX, maxY, maxZ);
    }

    @Override
    public void tick(World world, BlockPos pos, BlockState state, GateBlockEntity blockEntity) {
        if (world.isClient) return;
        if (world instanceof ServerWorld serverWorld) {
            if (isMazeDimension(serverWorld)) {
                placeGate(world, pos);
                placeWall(world, pos);
            }
        }
    }

    private void placeGate(World world, BlockPos pos) {
        if (!getPlayersInRange().isEmpty()) {
            for (PlayerEntity player : getPlayersInRange()) {
                if (!player.isCreative()||hasMonstersInRange()) {
                        if (player.hasStatusEffect(ModStatusEffects.MAZE_CURSE)||hasMonstersInRange()) {
                            for (int i=4;i>0;i--){
                                if (world.getBlockState(new BlockPos(pos.getX(), pos.getY()+i, pos.getZ())).equals(Blocks.AIR.getDefaultState())) {
                                    world.setBlockState(new BlockPos(pos.getX(), pos.getY()+i, pos.getZ()),Blocks.BEDROCK.getDefaultState());
                                }
                            }
                        }
                        else {
                            for (int i=4;i>0;i--){
                                world.setBlockState(new BlockPos(pos.getX(), pos.getY()+i, pos.getZ()),Blocks.AIR.getDefaultState());
                            }
                        }
                }
            }
        }
        else {
            for (int i=4;i>0;i--){
                if (world.getBlockState(new BlockPos(pos.getX(), pos.getY()+i, pos.getZ())).equals(Blocks.AIR.getDefaultState())) {
                    world.setBlockState(new BlockPos(pos.getX(), pos.getY()+i, pos.getZ()),Blocks.BEDROCK.getDefaultState());
                }
            }
        }
    }
    //这个方法是用来把那些无用的大门给变成墙壁
    private void placeWall(World world, BlockPos pos){
        if (world.getBlockState(pos.down(1)).equals(Blocks.BEDROCK.getDefaultState())||world.getBlockState(pos.down(1)).equals(Blocks.AIR.getDefaultState())) {
            for (int a=-1;a<=1;a++){
                for (int c=-1;c<=1;c++){
                    if (world.getBlockState(new BlockPos(pos.getX()+a,pos.getY(),pos.getZ()+c)).equals(Blocks.AIR.getDefaultState())) {
                        BlockState blockState=world.getBlockState(new BlockPos(pos.getX(),pos.getY()+5,pos.getZ()));
                        for (int d=4;d>=0;d--){
                            world.setBlockState(new BlockPos(pos.getX(),pos.getY()+d,pos.getZ()),
                                    blockState);
                        }
                    }
                }
            }
        }
    }

    private boolean hasMonstersInRange() {
        Box detectionBox = getBox();
        if (world != null) {
            List<HostileEntity> monsters = world.getEntitiesByClass(
                    HostileEntity.class,
                    detectionBox,
                    monster -> monster.isAlive() && !monster.isRemoved()
            );
            return !monsters.isEmpty();
        }
        return false;
    }

    private boolean isMazeDimension(ServerWorld world) {
        TagKey<DimensionType> isMazeTag = TagKey.of(
                RegistryKeys.DIMENSION_TYPE,
                new Identifier(NekoAdventure.MOD_ID, "is_maze")
        );
        return world.getDimensionEntry().isIn(isMazeTag);
    }
}
