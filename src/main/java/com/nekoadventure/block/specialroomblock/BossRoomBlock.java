package com.nekoadventure.block.specialroomblock;

import com.nekoadventure.NekoAdventure;
import com.nekoadventure.block.ModBlocks;
import com.nekoadventure.other.mazeApart.MazeDataManager;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.structure.StructurePlacementData;
import net.minecraft.structure.StructureTemplate;
import net.minecraft.structure.StructureTemplateManager;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BossRoomBlock extends AbstractRoomBlock {
    public static final BooleanProperty CAN_TELEPORT = BooleanProperty.of("can_teleport");

    public BossRoomBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(CAN_TELEPORT, true));
    }
    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(CAN_TELEPORT);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (BossRoomBlock.canTeleport(world.getBlockState(pos))) {
            player.teleport(-3,4,-3);
            return ActionResult.SUCCESS;
        }
        else {
            return ActionResult.PASS;
        }
    }

    @Override
    public void placeOn(BlockPos pos, LivingEntity player) {
    World world=player.getWorld();
    if (world instanceof ServerWorld&&!isMazeDimension((ServerWorld) world)){return;}
        MazeDataManager mazeDataManager = MazeDataManager.get(world);
        int level = 0;
        if (mazeDataManager != null) {
            level = mazeDataManager.getLevelData();
        }
    if (level%2==0){
        placeBossStructure(new BlockPos(-22,1,-22),world);
    }
    else {
        world.setBlockState(pos, ModBlocks.TP_NEXT_LEVEL_BLOCK.getDefaultState());
    }
}





    private void placeBossStructure(BlockPos spawnerPos,World world) {
        if (!(world instanceof ServerWorld serverWorld)) return;
        StructureTemplateManager templateManager = serverWorld.getStructureTemplateManager();
        Optional<StructureTemplate> optional = templateManager.getTemplate(getRandomRoomPath(world));
        optional.ifPresent(structure -> structure.place(
                serverWorld,
                spawnerPos,
                spawnerPos,
                new StructurePlacementData(),
                serverWorld.random,
                2
        ));
    }

    private Identifier getRandomRoomPath(World world){
        List<String> allMobPath=new ArrayList<>();
        if (world != null) {
            MazeDataManager mazeDataManager = MazeDataManager.get(world);
            int level = 1;
            if (mazeDataManager != null) {
                level = mazeDataManager.getLevelData();
            }
            RegistryKey<World> worldKey = world.getRegistryKey();
            String dimensionPath = worldKey.getValue().getPath();
            String path = dimensionPath + "/specific_room" +"/boss_room"+ "/room" + "/level/" + level + "/room"+1;
            if (world instanceof ServerWorld serverWorld) {
                StructureTemplateManager structureManager = serverWorld.getStructureTemplateManager();
                Optional<StructureTemplate> optional = structureManager.getTemplate(new Identifier(NekoAdventure.MOD_ID, path));
                for (int i = 2; optional.isPresent(); i++){
                    allMobPath.add(path);
                    path=dimensionPath + "/specific_room" +"/boss_room"+ "/room" + "/level/" + level + "/room"+i;
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

    public static void setCanTeleport(BlockState state, boolean canTeleport) {
        state.with(CAN_TELEPORT, canTeleport);
    }
    public static boolean canTeleport(BlockState state) {
        return state.get(CAN_TELEPORT);
    }
}
