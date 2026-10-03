package com.nekoadventure.block.blockentity.maze;

import com.nekoadventure.NekoAdventure;
import com.nekoadventure.block.ModBlocks;
import com.nekoadventure.block.blockentity.ModBlockEntityTypes;
import com.nekoadventure.block.maze.MazeRoomStageBlock;
import com.nekoadventure.block.specialroomblock.BossRoomBlock;
import com.nekoadventure.network.maze.MobIdInputNetworking;
import com.nekoadventure.other.mazeApart.MazeDataManager;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.server.network.ServerPlayerEntity;
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
//1.当有玩家接近时，生成怪物/boss
//2.在特殊的摆放条件下，可以以硬编码的形式生成boss

public class SpawnMobBlockEntity extends AbstractMazeBlockEntity {

    //创造模式玩家通过右键设置的boss生物ID（命名空间:生物ID），设置后boss按该ID通过代码生成而不是放置结构
    private String customBossEntityId = null;

    private int roomRange;

    public SpawnMobBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntityTypes.SPAWN_MOB_ENTITY_BLOCK_ENTITY, pos, state);
    }

    //创造模式空手右键时，请求客户端打开生物ID输入界面
    public void requestBossEntityIdInput(ServerPlayerEntity player) {
        MobIdInputNetworking.sendOpenInput(player, pos, customBossEntityId == null ? "" : customBossEntityId);
    }

    //应用玩家输入的boss生物ID：输入为空或没有对应实体时清除设置，恢复原有结构生成逻辑
    public void applyBossEntityIdInput(ServerPlayerEntity player, String input) {
        String trimmed = input == null ? "" : input.trim();
        if (trimmed.isEmpty()) {
            customBossEntityId = null;
            markDirty();
            player.sendMessage(Text.of("§7已清除自定义boss生物，将采用原有结构生成逻辑"), true);
            return;
        }
        Identifier entityId = Identifier.tryParse(trimmed);
        if (entityId == null || Registries.ENTITY_TYPE.getOrEmpty(entityId).isEmpty()) {
            customBossEntityId = null;
            markDirty();
            player.sendMessage(Text.of("§c没有找到生物ID：" + trimmed + "，将采用原有结构生成逻辑"), true);
            return;
        }
        customBossEntityId = entityId.toString();
        markDirty();
        player.sendMessage(Text.of("§a已将boss生成设置为：" + entityId), true);
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
        if (roomRange<=0){
            if (this.getWorld() != null) {
                MazeDataManager mazeDataManager=MazeDataManager.get(this.getWorld());
                if (mazeDataManager!=null){
                    roomRange=mazeDataManager.getMazeRange();
                }
            }

        }
        int range = roomRange-1;
        double minX = pos.getX() - range+0.5;
        double minY = pos.getY() - 2;
        double minZ = pos.getZ() - range+0.5;
        double maxX = pos.getX() + range-0.5;
        double maxY = pos.getY() + 16;
        double maxZ = pos.getZ() + range-0.5;
        return new Box(minX, minY, minZ, maxX, maxY, maxZ);
    }

    private boolean spawnMobInRange() {
        boolean ifSpawner = false;

        World world = this.getWorld();
        BlockPos entityPos = this.getPos();

        // 判断下方方块是否为 BOSS_ROOM_BLOCK
        boolean isBossRoom = world != null
                && world.getBlockState(entityPos.down(1)).isOf(ModBlocks.BOSS_ROOM_BLOCK);

        int minX, maxX, minY, maxY, minZ, maxZ;
        if (isBossRoom) {
            minX = entityPos.getX() - 16;
            maxX = entityPos.getX() + 15;
            minY = entityPos.getY() - 5;
            maxY = entityPos.getY() + 4;
            minZ = entityPos.getZ() - 16;
            maxZ = entityPos.getZ() + 15;
        } else {
            Box detectionBox = getBox();
            minX = (int) Math.floor(detectionBox.minX);
            maxX = (int) Math.ceil(detectionBox.maxX);
            minY = (int) Math.floor(detectionBox.minY);
            maxY = (int) Math.ceil(detectionBox.maxY);
            minZ = (int) Math.floor(detectionBox.minZ);
            maxZ = (int) Math.ceil(detectionBox.maxZ);
        }

        for (int x = minX; x <= maxX; x++) {
            for (int y = minY; y <= maxY; y++) {
                for (int z = minZ; z <= maxZ; z++) {
                    BlockPos checkPos = new BlockPos(x, y, z);
                    if (world == null) {
                        continue;
                    }
                    BlockState blockState = world.getBlockState(checkPos);
                    if (blockState.isOf(Blocks.SPAWNER)) {
                        world.setBlockState(checkPos, Blocks.AIR.getDefaultState());
                        world.syncWorldEvent(2001, checkPos, Block.getRawIdFromState(blockState));

                        if (world.getBlockState(this.getPos().down(1)).equals(
                                ModBlocks.BOSS_ROOM_BLOCK.getDefaultState()
                                        .with(BossRoomBlock.CAN_TELEPORT, false))) {
                            if (!spawnCustomBossEntity(checkPos)) {
                                placeBossStructure(checkPos);
                            }
                        } else {
                            placeMobStructure(checkPos);
                        }

                        if (world.getBlockState(pos.down(1)).isOf(ModBlocks.BOSS_ROOM_BLOCK)) {
                            BossRoomBlock.setCanTeleport(world.getBlockState(pos.down(1)), false);
                        }
                        world.setBlockState(pos, Blocks.AIR.getDefaultState());
                        ifSpawner = true;
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
        System.out.println("spawned boss with structure");
    }

    //当玩家设置的生物ID直接生成boss实体，否则使用原逻辑
    private boolean spawnCustomBossEntity(BlockPos spawnerPos) {
        if (customBossEntityId == null || customBossEntityId.isBlank()) return false;
        if (!(world instanceof ServerWorld serverWorld)) return false;
        Identifier entityId = Identifier.tryParse(customBossEntityId);
        if (entityId == null) return false;
        Optional<EntityType<?>> entityTypeOptional = Registries.ENTITY_TYPE.getOrEmpty(entityId);
        if (entityTypeOptional.isEmpty()) return false;
        Entity entity = entityTypeOptional.get().create(serverWorld);
        if (entity == null) return false;
        entity.setPosition(spawnerPos.getX() + 0.5, spawnerPos.getY(), spawnerPos.getZ() + 0.5);
        serverWorld.spawnEntity(entity);
        System.out.println("Spawned Custom Boss Entity with ID: " + entityId);
        return true;
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        if (customBossEntityId != null) {
            nbt.putString("CustomBossEntityId", customBossEntityId);
        }
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        customBossEntityId = nbt.contains("CustomBossEntityId", NbtCompound.STRING_TYPE) ? nbt.getString("CustomBossEntityId") : null;
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
                    if (world.getBlockState(pos.down(1)).isOf(ModBlocks.MAZE_ROOM_STAGE_BLOCK)) {
                        world.setBlockState(pos.down(1), ModBlocks.MAZE_ROOM_STAGE_BLOCK.getDefaultState().with(MazeRoomStageBlock.MAZE_STAGE,1));
                    }
                    for (PlayerEntity player : getPlayersInRange(0)) {
                        if (!(player.isCreative()&&!player.isSpectator())&&spawnMobInRange()) {
                            player.sendMessage(Text.of("开始战斗"),true);
                        }
                    }
                    List<PlayerEntity> allPlayerInRange = getPlayersInRange(3.5);
                    PlayerEntity player=world.getClosestPlayer(pos.getX(),pos.getY()+1,pos.getZ(),getBox().maxX,true);
                    allPlayerInRange.forEach(p->{
                        if (player != null) {if (!p.equals(player)) {p.teleport(player.getX(),player.getY()+0.1,player.getZ());}}
                    });
                }
            }
        }
    }

}

