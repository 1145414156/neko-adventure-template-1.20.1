package com.nekoadventure.block.blockentity.maze;

import com.nekoadventure.NekoAdventure;
import com.nekoadventure.block.ModBlocks;
import com.nekoadventure.block.blockentity.ModBlockEntityTypes;
import com.nekoadventure.block.maze.MazeRoomStageBlock;
import com.nekoadventure.block.specialroomblock.BossRoomBlock;
import com.nekoadventure.effect.ModStatusEffects;
import com.nekoadventure.other.mazeApart.MazeDataManager;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.structure.StructurePlacementData;
import net.minecraft.structure.StructureTemplate;
import net.minecraft.structure.StructureTemplateManager;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//这个类的作用有:
//1.当有玩家接近时，默认生成怪物
//2.在特殊的摆放条件下，可以生成boss

public class SpawnMobBlockEntity extends AbstractMazeBlockEntity {

    public SpawnMobBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntityTypes.SPAWN_MOB_ENTITY_BLOCK_ENTITY, pos, state);
    }

    private List<PlayerEntity> getPlayersInRange(double expand) {
        if (world == null || pos == null) return List.of();
        Box detectionBox = getBox().expand(expand);
        return world.getEntitiesByClass(
                PlayerEntity.class,
                detectionBox,
                player -> player.isAlive() && !player.isSpectator()
        );
    }

    private @NotNull Box getBox() {
        int range = MazeBlockEntity.detectRoomDistance(world,pos)-1;
        double minX = pos.getX() - range+0.5;
        double minY = pos.getY() - 2;
        double minZ = pos.getZ() - range+0.5;
        double maxX = pos.getX() + range-0.5;
        double maxY = pos.getY() + 16;
        double maxZ = pos.getZ() + range-0.5;
        return new Box(minX, minY, minZ, maxX, maxY, maxZ);
    }

    private boolean spawnMobInRange() {
        boolean ifSpawner=false;
        Box detectionBox = getBox();
        for (int x = (int) detectionBox.minX; x <= (int) detectionBox.maxX; x++) {
            for (int y = (int) detectionBox.minY; y <= (int) detectionBox.maxY; y++) {
                for (int z = (int) detectionBox.minZ; z <= (int) detectionBox.maxZ; z++) {
                    BlockPos checkPos = new BlockPos(x, y, z);
                    if (world != null) {
                        BlockState blockState = world.getBlockState(checkPos);
                        if (blockState.isOf(Blocks.SPAWNER)) {
                            world.setBlockState(checkPos, Blocks.AIR.getDefaultState());
                            world.syncWorldEvent(2001, checkPos, Block.getRawIdFromState(blockState));


                            if (this.getWorld() != null && this.getWorld().getBlockState(this.getPos().down(1)).equals(ModBlocks.BOSS_ROOM_BLOCK.getDefaultState().with(BossRoomBlock.CAN_TELEPORT,false))){
                                placeBossStructure(checkPos);
                                List<? extends PlayerEntity> playerEntities=world.getPlayers();
                                if (!playerEntities.isEmpty()){
                                    for (PlayerEntity playerEntity : playerEntities) {
                                        playerEntity.addStatusEffect(new StatusEffectInstance(ModStatusEffects.BOSS_FIGHT,3600*20,0,false,false));
                                    }
                                }
                            }
                            else {
                                placeMobStructure(checkPos);
                            }

                            if (world.getBlockState(pos.down(1)).isOf(ModBlocks.BOSS_ROOM_BLOCK)) {
                                BossRoomBlock.setCanTeleport(world.getBlockState(pos.down(1)), false);
                            }
                            world.setBlockState(pos, Blocks.AIR.getDefaultState());
                            ifSpawner=true;
                        }
                    }
                }
            }
        }
        return ifSpawner;
    }

    private void placeMobStructure(BlockPos spawnerPos) {
        if (!(world instanceof ServerWorld serverWorld)) return;
        StructureTemplateManager templateManager = serverWorld.getStructureTemplateManager();
        Optional<StructureTemplate> optional = templateManager.getTemplate(getRandomMobPath(world));
        optional.ifPresent(structure -> structure.place(
                serverWorld,
                spawnerPos,
                spawnerPos,
                new StructurePlacementData(),
                serverWorld.random,
                2
        ));
    }

    private void placeBossStructure(BlockPos spawnerPos) {
        if (!(world instanceof ServerWorld serverWorld)) return;
        StructureTemplateManager templateManager = serverWorld.getStructureTemplateManager();
        Optional<StructureTemplate> optional = templateManager.getTemplate(getRandomBossPath(world));
        optional.ifPresent(structure -> structure.place(
                serverWorld,
                spawnerPos,
                spawnerPos,
                new StructurePlacementData(),
                serverWorld.random,
                2
        ));
    }

    private Identifier getRandomMobPath(World world){
        List<String> allMobPath=new ArrayList<>();
        if (world != null) {
            MazeDataManager mazeDataManager = MazeDataManager.get(world);
            int level = 1;
            if (mazeDataManager != null) {
                level = mazeDataManager.getLevelData();
            }
            RegistryKey<World> worldKey = world.getRegistryKey();
            String dimensionPath = worldKey.getValue().getPath();
            String path = dimensionPath + "/mob" + "/level" + "/" + level + "/mob"+1;
            if (world instanceof ServerWorld serverWorld) {
                StructureTemplateManager structureManager = serverWorld.getStructureTemplateManager();
                Optional<StructureTemplate> optional = structureManager.getTemplate(new Identifier(NekoAdventure.MOD_ID, path));
                for (int i = 2; optional.isPresent(); i++){
                    allMobPath.add(path);
                    path=dimensionPath + "/mob" + "/level" + "/" + level + "/mob"+i;
                    optional = structureManager.getTemplate(new Identifier(NekoAdventure.MOD_ID, path));
                }
            }
        }
        String finalPath=allMobPath.get(Random.create().nextInt(allMobPath.size()));
        return new Identifier(NekoAdventure.MOD_ID,finalPath);
    }
    private Identifier getRandomBossPath(World world){
        List<String> allMobPath=new ArrayList<>();
        if (world != null) {
            MazeDataManager mazeDataManager = MazeDataManager.get(world);
            int level = 1;
            if (mazeDataManager != null) {
                level = mazeDataManager.getLevelData();
            }
            RegistryKey<World> worldKey = world.getRegistryKey();
            String dimensionPath = worldKey.getValue().getPath();
            String path = dimensionPath + "/mob" +"/boss"+ "/level" + "/" + level + "/mob"+1;
            if (world instanceof ServerWorld serverWorld) {
                StructureTemplateManager structureManager = serverWorld.getStructureTemplateManager();
                Optional<StructureTemplate> optional = structureManager.getTemplate(new Identifier(NekoAdventure.MOD_ID, path));
                for (int i = 2; optional.isPresent(); i++){
                    allMobPath.add(path);
                    path=dimensionPath + "/mob"+"/boss" + "/level" + "/" + level + "/mob"+i;
                    optional = structureManager.getTemplate(new Identifier(NekoAdventure.MOD_ID, path));
                }
            }
        }
        String finalPath=allMobPath.get(Random.create().nextInt(allMobPath.size()));
        return new Identifier(NekoAdventure.MOD_ID,finalPath);
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
            if (isMazeDimension(serverWorld)) {
                if (!getPlayersInRange(0).isEmpty()) {
                    for (PlayerEntity player : getPlayersInRange(0)) {
                        if (!(player.isCreative()&&!player.isSpectator())&&spawnMobInRange()) {
                            player.sendMessage(Text.of("开始战斗"),true);
                            if (world.getBlockState(pos.down(1)).isOf(ModBlocks.MAZE_ROOM_STAGE_BLOCK)) {
                                world.setBlockState(pos.down(1), ModBlocks.MAZE_ROOM_STAGE_BLOCK.getDefaultState().with(MazeRoomStageBlock.MAZE_STAGE,1));
                            }
                        }
                    }
                    List<PlayerEntity> allPlayerInRange = getPlayersInRange(1.5);
                    PlayerEntity player=world.getClosestPlayer(pos.getX(),pos.getY(),pos.getZ(),getBox().maxX,true);
                    allPlayerInRange.forEach(p->{
                        if (player != null) {if (!p.equals(player)) {p.teleport(player.getX(),player.getY(),player.getZ());}}
                    });
                }
            }
        }
    }

}

