package com.nekoadventure.other.mazeApart;

import com.nekoadventure.NekoAdventure;
import com.nekoadventure.block.ModBlocks;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.structure.StructureTemplate;
import net.minecraft.structure.StructureTemplateManager;
import net.minecraft.structure.pool.StructurePool;
import net.minecraft.structure.pool.StructurePoolBasedGenerator;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;

import java.util.Optional;
import java.util.Random;

//这个类由AI辅助完成
//这个类是用来专门放置地牢的（避免歧义，地牢在代码中叫做maze）
public class MazeBuilder {
    Random random=new Random();
    private int getLevelData(World world) {
        MazeDataManager data = MazeDataManager.get(world);
        if (data != null) {
            return data.getLevelData();
        }
        return 1;
    }
    //true=还有迷宫;false=没有迷宫
    public boolean placeMaze(ServerWorld world) {
        MazeDataManager data = MazeDataManager.get(world);
        int levelData = getLevelData(world);
        BlockPos placePos = new BlockPos((levelData - 1) * 10000-random.nextInt(40)-20, 1, random.nextInt(40)-20);

        Identifier poolId = getPoolPath(world);

        Identifier jigsawName = new Identifier("minecraft", "start");

        Registry<StructurePool> poolRegistry = world.getRegistryManager()
                .get(RegistryKeys.TEMPLATE_POOL);

        if (!poolRegistry.containsId(poolId)){
            System.err.println("pool not found");
            return false;
        }

        RegistryKey<StructurePool> poolKey = RegistryKey.of(RegistryKeys.TEMPLATE_POOL, poolId);
        RegistryEntry<StructurePool> poolEntry = poolRegistry.entryOf(poolKey);
        int size;
        if (getLevelData(world)%2==0){
            size=5;
        }
        else {
            size=4;
        }
        StructurePoolBasedGenerator.generate(world, poolEntry, jigsawName, size, placePos, false);
        int mazeRange=detectRoomDistanceByStructureName(world);
        BlockPos roomCenter = findStartRoomCenter(world, placePos, mazeRange,data);
        if (roomCenter == null) {
            roomCenter = placePos;
            System.err.println("maze block not found");
        }
        else {
            System.out.println("find maze center success in"+roomCenter);
        }
        if (data != null) {
            data.setMazeRange(mazeRange);
            data.setMazeCenter(roomCenter);
            System.out.println("write maze center data success");
        }
        BlockPos teleportCenter = roomCenter;
        world.getPlayers().forEach(player -> player.teleport(teleportCenter.getX(), placePos.getY()+2, teleportCenter.getZ()));
        System.out.println("place maze success");
        return true;
    }

    //返回房间中心(标记方块所在位置)：maze_block上方1格就是转换后的maze_structure_block(力大砖飞了属于是)
    private BlockPos findStartRoomCenter(ServerWorld world, BlockPos placePos, int mazeRange,MazeDataManager data) {
        int horizontal = mazeRange * 2;
        for (int dy = 48; dy >=0; dy--) {
            for (int dx = -horizontal; dx <= horizontal; dx++) {
                for (int dz = -horizontal; dz <= horizontal; dz++) {
                    BlockPos checkPos = placePos.add(dx, dy, dz);
                    if (world.getBlockState(checkPos).getBlock()==ModBlocks.START_ROOM_BLOCK){
                        System.out.println("find start room in"+checkPos);
                        for (int ay=1;ay<=48;ay++){
                            if (world.getBlockState(checkPos.down(ay)).getBlock() == ModBlocks.MAZE_BLOCK) {
                                data.setMazeHeight(ay+2);
                                return checkPos.down(ay-1);
                            }
                        }
                    }
                }
            }
        }
        return null;
    }

    public int detectRoomDistanceByStructureName(World world) {
        String DATA_NAMESPACE = NekoAdventure.MOD_ID;
        RegistryKey<World> worldKey = world.getRegistryKey();
        String dimensionPath = worldKey.getValue().getPath();
        Identifier structureId = new Identifier(DATA_NAMESPACE, dimensionPath + "/" + "level/" + "1-2/" + "start_room/" + "start1");
        if (!(world instanceof ServerWorld serverWorld)) {
            return 8;
        }
        StructureTemplateManager templateManager = serverWorld.getStructureTemplateManager();
        Optional<StructureTemplate> optional = templateManager.getTemplate(structureId);
        if (optional.isEmpty()) {
            System.err.println("detect room distance by structure failed, structure not found: " + structureId);
            return 8;
        }
        Vec3i size = optional.get().getSize();
        int width = Math.min(size.getX(), size.getZ());
        if (width <= 1) {
            System.err.println("detect room distance by structure failed, structure too short: " + structureId);
            return 8;
        }
        return (width - 1) / 2;
    }

    private Identifier getPoolPath(World world) {
        int levelData = getLevelData(world);
        String DATA_NAMESPACE = NekoAdventure.MOD_ID;
        RegistryKey<World> worldKey = world.getRegistryKey();
        String dimensionPath = worldKey.getValue().getPath();
        //偶
        if (levelData % 2 == 0) {
            int levelData1 = levelData - 1;
            return new Identifier(DATA_NAMESPACE, dimensionPath+"/level/" + levelData1 + "-" + levelData + "/start_room/setting");
        }
        //奇
        else {
            int levelData1 = levelData + 1;
            return new Identifier(DATA_NAMESPACE, dimensionPath+"/level/" + levelData + "-" + levelData1 + "/start_room/setting");
        }
    }
}
