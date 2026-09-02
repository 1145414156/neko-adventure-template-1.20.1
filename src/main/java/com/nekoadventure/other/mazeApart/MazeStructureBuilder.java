package com.nekoadventure.other.mazeApart;

import com.nekoadventure.NekoAdventure;
import com.nekoadventure.block.ModBlocks;
import com.nekoadventure.block.blockentity.maze.MazeBlockEntity;
import com.nekoadventure.block.specialroomblock.AbstractRoomBlock;
import com.nekoadventure.block.specialroomblock.BossRoomBlock;
import com.nekoadventure.block.specialroomblock.ShopRoomBlock;
import com.nekoadventure.block.specialroomblock.TreasureRoomBlock;
import com.nekoadventure.item.ModItems;
import com.nekoadventure.item.nekoItem.AbstractNekoItem;
import com.nekoadventure.item.soulItem.AbstractSoulItem;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.RegistryKey;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.structure.StructurePlacementData;
import net.minecraft.structure.StructureTemplate;
import net.minecraft.structure.StructureTemplateManager;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

//这个类是用来专门放置特殊房间的类（例如藏宝房和商店就在这里）
public class MazeStructureBuilder {
    public final int MIN_IMPASSE_COUNT = 3;
    private List<MazePosNBTCompound> calculateRoomType(World world, int gateCount) {
        List<MazePosNBTCompound> impasse = new ArrayList<>();
        MazeDataManager data = MazeDataManager.get(world);
        if (data != null) {
            List<MazePosNBTCompound> allData = data.getInitialData();
            if (!allData.isEmpty()) {
                for (MazePosNBTCompound roomData : allData) {
                    if (roomData.gateCount()== gateCount) {
                        impasse.add(roomData);
                    }
                }
            }
        }
        return impasse;
    }

    //这个方法是执行放置的入口
    public void placeAllSpecialRoom(World world) {
        if (!world.isClient) {
            List<MazePosNBTCompound> impasse = calculateRoomType(world, 1);
            int impasseCount;
            if (!impasse.isEmpty()) {impasseCount=impasse.size();}
            else {impasseCount=0;}

            //这个是处理当死胡同不够的时候的情况
            if (impasseCount < MIN_IMPASSE_COUNT) {
                List<MazePosNBTCompound> furcation = Stream.concat(calculateRoomType(world, 3).stream(), calculateRoomType(world, 2).stream()).toList();
                int needRoomCount = MIN_IMPASSE_COUNT - impasseCount;
                if (furcation.isEmpty()) {
                    throw new IllegalStateException(String.format("IllegalRoomData:because Impasse count is %d,but need MIN_IMPASSE_COUNT is 3", needRoomCount));
                }
                for (int i = 0; i < needRoomCount; i++) {
                    MazePosNBTCompound roomData = furcation.get(i);
                    impasse.add(roomData);
                }
                placeBaseSpecialRooms(impasse,world);
            }
            //这个是当死胡同刚好的时候
            else if (impasseCount== MIN_IMPASSE_COUNT) {
                placeBaseSpecialRooms(impasse, world);
            }
            //这个是死胡同多于正常数的时候（就是正常情况）
            else {
                placeBaseSpecialRooms(impasse, world);
                placeMoreSpecialRooms(impasse, impasseCount - MIN_IMPASSE_COUNT,world);
            }
        }
    }

    public void clearRoomItemEntity(World world) {
        MazeDataManager data = MazeDataManager.get(world);
        if (data != null) {
            List<MazePosNBTCompound> allRoom= data.getInitialData();
            for(MazePosNBTCompound roomData : allRoom) {
                int roomDistance = MazeBlockEntity.detectRoomDistance(world, roomData.roomCenter())*2;
                BlockPos pos=new BlockPos(roomData.roomCenter());
                List<ItemEntity> entities= world.getEntitiesByClass(
                        ItemEntity.class,
                        new Box(pos.getX()+roomDistance,pos.getY()+roomDistance,pos.getZ()+roomDistance
                        ,pos.getX()-roomDistance,pos.getY()-roomDistance,pos.getZ()-roomDistance),
                        entity -> true

                );
                for (Entity entity : entities) {
                    entity.kill();
                }
            }
        }
    }

    private void placeBaseSpecialRooms(List<MazePosNBTCompound> roomData, World world) {
        //生成boss房
        int roomDistance=MazeBlockEntity.detectRoomDistance(world,roomData.get(0).roomCenter());
        BlockPos bossRoomCenter = roomData.get(0).roomCenter();
        placeStructure(world, bossRoomCenter, "boss_room");
        placeGateForRoom(world, bossRoomCenter, roomDistance, false);
        Block bossBlock = world.getBlockState(bossRoomCenter).getBlock();
        if (bossBlock instanceof BossRoomBlock bossRoomBlock) {
            bossRoomBlock.placeOn(bossRoomCenter, world.getPlayers().get(0));
        }

        //生成宝箱房
        BlockPos treasureRoomCenter = roomData.get(1).roomCenter();
        placeStructure(world, treasureRoomCenter, "treasure_room");
        placeGateForRoom(world, treasureRoomCenter, roomDistance, false);
        Block treasureBlock = world.getBlockState(treasureRoomCenter).getBlock();
        if (treasureBlock instanceof TreasureRoomBlock treasureRoomBlock) {
            treasureRoomBlock.placeOn(treasureRoomCenter, world.getPlayers().get(0));
        }

        //生成商店
        BlockPos shopRoomCenter = roomData.get(2).roomCenter();
        placeStructure(world, shopRoomCenter, "shop_room");
        placeGateForRoom(world, shopRoomCenter, roomDistance, false);
        Block shopBlock=world.getBlockState(shopRoomCenter).getBlock();
        if (shopBlock instanceof ShopRoomBlock shopRoomBlock){
            shopRoomBlock.placeOn(shopRoomCenter, world.getPlayers().get(0));
        }
    }

    //这个是用来额外再生成更多的特殊房间（例如隐藏房，重铸房）
    private void placeMoreSpecialRooms(List<MazePosNBTCompound> roomData, int roomCount, World world) {
        int roomDistance = MazeBlockEntity.detectRoomDistance(world, roomData.get(0).roomCenter());
        MazeDataManager mazeDataManager=MazeDataManager.get(world);
        int level=1;
        if (mazeDataManager != null) {
            level=mazeDataManager.getLevelData();
        }
        //第一层不会刷新其他全部特殊房间
        if (level>1) {
            record RoomConfig(java.util.function.Function<World, String> provider, int probability) {}

            List<RoomConfig> roomConfigs = new ArrayList<>(List.of(
                    new RoomConfig(this::placeStrangeRoomOrGambleRoom, 75),
                    new RoomConfig(this::placeConcealRoomOrSoulRoom, 75),
                    new RoomConfig(this::placeResetRoomOrSmeltRoom, 75),
                    new RoomConfig(this::placeForgeRoomOrFoodRoom, 75),
                    new RoomConfig(this::placeMineralRoom, 75)
            ));
            Collections.shuffle(roomConfigs);
            int maxRooms = Math.min(roomCount, roomConfigs.size());

            for (int i = 0; i < maxRooms; i++) {
                int roomIndex = 3 + i;
                RoomConfig config = roomConfigs.get(i);

                if (Random.create().nextInt(100) + 1 <= config.probability()) {
                    BlockPos roomPos = roomData.get(roomIndex).roomCenter();
                    String roomType = config.provider().apply(world);
                    placeStructure(world, roomPos, roomType);
                    placeGateForRoom(world, roomPos, roomDistance, roomType.equals("conceal_room"));
                    if (world.getBlockState(roomPos).getBlock() instanceof AbstractRoomBlock roomBlock){
                        roomBlock.placeOn(roomPos, world.getPlayers().get(0));
                    }
                }
            }

        }
    }

    //这一部分是用来生成更多特殊房间的
    //奇异房或者赌博房
    private String placeStrangeRoomOrGambleRoom(World world){
        String room = "strange_room";
        if (world instanceof ServerWorld serverWorld) {
            for (PlayerEntity player : serverWorld.getPlayers()) {
                int coinCount=0;
                for (ItemStack itemStack : player.getInventory().main) {
                    if (itemStack.getItem() == ModItems.COIN) {
                        coinCount += itemStack.getCount();
                    }
                }
                if (coinCount <= 10) {
                    if (Random.create().nextInt(100)+1<50-(coinCount-8)*5){
                    room = "gamble_room";
                    break;
                    }
                }
            }
        }
        return room;
    }
    //隐藏房或者魂石房
    private String placeConcealRoomOrSoulRoom(World world) {
        String room = "conceal_room";
        boolean haveSoul =false;
        if (world instanceof ServerWorld serverWorld) {
            for (PlayerEntity player : serverWorld.getPlayers()) {
                for (ItemStack itemStack : player.getInventory().main) {
                    if (itemStack.getItem() instanceof AbstractSoulItem) {
                        if (Random.create().nextInt(100) + 1 <= 50) {
                            haveSoul =true;
                        }
                        break;
                    }
                }
            }
            if (!haveSoul){room="soul_room";}
        }
        return room;
    }
    //重铸房或者熔炼房
    private String placeResetRoomOrSmeltRoom(World world) {
        String room = "reset_room";
        if (world instanceof ServerWorld serverWorld) {
            for (PlayerEntity player : serverWorld.getPlayers()) {
                int nekoItemCount = 0;
                for (ItemStack itemStack : player.getInventory().main) {
                    if (itemStack.getItem() instanceof AbstractNekoItem) {
                        nekoItemCount += itemStack.getCount();
                    }
                }
                if (nekoItemCount > 5) {
                    room = "smelt_room";
                    break;
                }
                if (Random.create().nextInt(100) + 1 <= 50) {
                    room = "smelt_room";
                    break;
                }
            }
        }
        return room;
    }
    //锻造房或者食物房
    private String placeForgeRoomOrFoodRoom(World world) {
        String room = "forge_room";
        if (world instanceof ServerWorld serverWorld) {
            for (PlayerEntity player : serverWorld.getPlayers()) {
                int foodCount = 0;
                for (ItemStack itemStack : player.getInventory().main) {
                    if (!itemStack.isEmpty() && itemStack.getItem().isFood()) {
                        foodCount += itemStack.getCount();
                    }
                }
                if (foodCount < 16) {
                    int missingFood = 16 - foodCount;
                    int probability = Math.min(25 + missingFood * 5, 80);

                    if (Random.create().nextInt(100) + 1 <= probability) {
                        room = "food_room";
                        break;
                    }
                }
            }
        }
        return room;
    }

    //矿场
    private String placeMineralRoom(World world) {
        return "mineral_room";
    }

    public void placeStructure(World world, BlockPos pos, String roomType) {
        if (!(world instanceof ServerWorld serverWorld)) {
            return;
        }
        Identifier structurePath = getPath(world, roomType);

        StructureTemplateManager structureManager = serverWorld.getStructureTemplateManager();

        Optional<StructureTemplate> optional = structureManager.getTemplate(structurePath);
        int distance = MazeBlockEntity.detectRoomDistance(world, pos.up(1));
        BlockPos placePos = new BlockPos(pos.getX() - distance, pos.getY() - 1, pos.getZ() - distance);
        if (optional.isPresent()) {
            StructureTemplate template = optional.get();
            StructurePlacementData placementData = new StructurePlacementData()
                    .setMirror(BlockMirror.NONE)
                    .setRotation(BlockRotation.NONE)
                    .setPosition(placePos)
                    .setUpdateNeighbors(true)
                    .setIgnoreEntities(false)
                    .setInitializeMobs(true);
            template.place(serverWorld, placePos, placePos, placementData, Random.create(), 2);

            //开发辅助
            System.out.println("成功放置结构: " + structurePath + " 在位置: " + pos);
            //开发辅助

        }
    }

    private Identifier getPath(World world, String roomType) {
        RegistryKey<World> worldKey = world.getRegistryKey();
        String dimensionPath = worldKey.getValue().getPath();
        String path = dimensionPath + "/specific_room/" + roomType+"/"+roomType+1;
        if (world instanceof  ServerWorld serverWorld) {
            StructureTemplateManager structureManager = serverWorld.getStructureTemplateManager();
            Optional<StructureTemplate> optional = structureManager.getTemplate(new Identifier(NekoAdventure.MOD_ID,path));
            if (optional.isEmpty()) {

                //开发辅助
                System.out.println("房间只有一个类型");
                //开发辅助

                path=dimensionPath + "/specific_room/" + roomType+"/"+roomType;
            }
            else {
                List<String> allPath=new ArrayList<>();
                for (int a = 1; structureManager.getTemplate(new Identifier(NekoAdventure.MOD_ID,path)).isPresent(); a++){
                    allPath.add(path);
                    path=dimensionPath + "/specific_room/" + roomType+"/"+roomType+a;
                }
                path=allPath.get(Random.create().nextInt(allPath.size()));

                //开发辅助
                System.out.println(path);
                //开发辅助

            }
        }
        return new Identifier(NekoAdventure.MOD_ID, path);
    }


    //这个方法是给特殊房间放置大门的(因为有的大门会有问题)
    private void placeGateForRoom(World world, BlockPos roomCenter, int roomDistance, boolean isConcealRoom) {
        BlockState gateBlockState = ModBlocks.GATE_BLOCK.getDefaultState();
        BlockState disguiseBlockState = null;
        // 获取伪装方块材质
        if (isConcealRoom) {
            Direction[] dirs = {Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST};
            for (Direction dir : dirs) {
                BlockPos checkPos = roomCenter.add(
                        dir.getOffsetX() * (roomDistance + 2), 5, dir.getOffsetZ() * (roomDistance + 2)
                );
                BlockState state = world.getBlockState(checkPos);
                if (!state.isAir() && !state.isOf(ModBlocks.GATE_BLOCK)) {
                    disguiseBlockState = state;
                    break;
                }
            }
            if (disguiseBlockState == null) {
                disguiseBlockState = Blocks.STONE.getDefaultState();
            }
        }

        BlockPos[] directions = {
                new BlockPos(1, 0, 0), new BlockPos(-1, 0, 0),
                new BlockPos(0, 0, 1), new BlockPos(0, 0, -1)
        };

        for (BlockPos dir : directions) {
            int dx = dir.getX(), dz = dir.getZ();
            if (world.getBlockState(roomCenter.add(dx * roomDistance, 0, dz * roomDistance)).isAir()) {
                continue;
            }

            int tz = -dx;
            BlockPos gatePos = roomCenter.add(dx * (roomDistance + 1), 0, dz * (roomDistance + 1));
            BlockPos disguisePos = roomCenter.add(dx * (roomDistance + 2), 0, dz * (roomDistance + 2));
            for (int y = 0; y < 1; y++) {
                for (int w = -1; w <= 1; w++) {
                    world.setBlockState(gatePos.add(dz * w, y, tz * w), gateBlockState);
                }
            }

            // 如果是隐藏房，放置伪装墙并检查支撑
            if (isConcealRoom) {
                for (int y = 0; y <= 5; y++) {
                    for (int w = -1; w <= 1; w++) {
                        world.setBlockState(disguisePos.add(dz * w, y, tz * w), disguiseBlockState);
                    }
                }

                boolean hasSupport = true;
                for (int w = -1; w <= 1; w++) {
                    BlockPos below = disguisePos.add(dz * w, -1, tz * w);
                    if (!world.getBlockState(below).isSolidBlock(world, below)) {
                        hasSupport = false;
                        break;
                    }
                }
                if (!hasSupport) {
                    for (int y = 0; y <= 5; y++) {
                        for (int w = -1; w <= 1; w++) {
                            world.setBlockState(disguisePos.add(dz * w, y, tz * w), Blocks.AIR.getDefaultState());
                        }
                    }
                }
            }
        }
    }

}