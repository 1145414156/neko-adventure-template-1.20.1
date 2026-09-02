package com.nekoadventure.other.mazeApart;

import com.nekoadventure.NekoAdventure;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.structure.pool.StructurePool;
import net.minecraft.structure.pool.StructurePoolBasedGenerator;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

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
        int levelData = getLevelData(world);
        BlockPos placePos = new BlockPos((levelData - 1) * 10000-random.nextInt(40)-20, random.nextInt(20), random.nextInt(40)-20);

        Identifier poolId = getPoolPath(world);

        Identifier jigsawName = new Identifier("minecraft", "start");

        Registry<StructurePool> poolRegistry = world.getRegistryManager()
                .get(RegistryKeys.TEMPLATE_POOL);

        if (!poolRegistry.containsId(poolId)){

            System.out.println("池子不存在");

            return false;
        }

        RegistryKey<StructurePool> poolKey = RegistryKey.of(RegistryKeys.TEMPLATE_POOL, poolId);
        RegistryEntry<StructurePool> poolEntry = poolRegistry.entryOf(poolKey);
        int size;
        if (getLevelData(world)%2==0){
            size=6;
        }
        else {
            size=5;
        }
        // 5. 生成结构
        StructurePoolBasedGenerator.generate(world, poolEntry, jigsawName, size, placePos, false);
        return true;
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
