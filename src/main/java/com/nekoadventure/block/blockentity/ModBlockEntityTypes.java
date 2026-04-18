package com.nekoadventure.block.blockentity;

import com.nekoadventure.NekoAdventure;
import com.nekoadventure.block.ModBlocks;
import com.nekoadventure.block.blockentity.maze.GateBlockEntity;
import com.nekoadventure.block.blockentity.maze.MazeBlockEntity;
import com.nekoadventure.block.blockentity.maze.MazeStructureBlockEntity;
import com.nekoadventure.block.blockentity.maze.SpawnMobBlockEntity;
import com.nekoadventure.block.blockentity.other.*;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModBlockEntityTypes {
    public static <T extends BlockEntityType<?>> T register(String path, T blockEntityType) {return Registry.register(Registries.BLOCK_ENTITY_TYPE, Identifier.of(NekoAdventure.MOD_ID, path), blockEntityType);}

    public static final BlockEntityType<GateBlockEntity> GATE_BLOCK=register("gate_block", FabricBlockEntityTypeBuilder.create(GateBlockEntity::new, ModBlocks.GATE_BLOCK).build());
    public static final BlockEntityType<MazeBlockEntity> MAZE_BLOCK=register("maze_block", FabricBlockEntityTypeBuilder.create(MazeBlockEntity::new, ModBlocks.MAZE_BLOCK).build());
    public static final BlockEntityType<SpawnMobBlockEntity> SPAWN_MOB_ENTITY_BLOCK_ENTITY=register("spawn_mob_entity_block_entity",FabricBlockEntityTypeBuilder.create(SpawnMobBlockEntity::new,ModBlocks.SPAWN_MOB_BLOCK).build());
    public static final BlockEntityType<MazeStructureBlockEntity> MAZE_STRUCTURE_BLOCK_ENTITY=register("maze_structure_block_entity",FabricBlockEntityTypeBuilder.create(MazeStructureBlockEntity::new,ModBlocks.MAZE_STRUCTURE_BLOCK).build());
    public static final BlockEntityType<ItemBaseBlockEntity> ITEM_BASE_BLOCK_ENTITY =register("item_base_block_entity",FabricBlockEntityTypeBuilder.create(ItemBaseBlockEntity::new, ModBlocks.ITEM_BASE_BLOCK).build());
    public static final BlockEntityType<SlotMachineBlockEntity> SLOT_MACHINE_BLOCK_ENTITY=register("slot_machine_block_entity",FabricBlockEntityTypeBuilder.create(SlotMachineBlockEntity::new,ModBlocks.SLOT_MACHINE_BLOCK).build());
    public static final BlockEntityType<RollItemBlockEntity> ROLL_ITEM_BLOCK_ENTITY=register("roll_item_block_entity",FabricBlockEntityTypeBuilder.create(RollItemBlockEntity::new,ModBlocks.ROLL_ITEM_BLOCK).build());
    public static final BlockEntityType<ResetFurnaceBlockEntity> RESET_FURNACE_BLOCK_ENTITY=register("reset_furnace_block_entity",FabricBlockEntityTypeBuilder.create(ResetFurnaceBlockEntity::new,ModBlocks.RESET_FURNACE_BLOCK).build());
    public static final BlockEntityType<RerollFurnaceBlockEntity> REROLL_FURNACE_BLOCK_ENTITY=register("reroll_furnace_block",FabricBlockEntityTypeBuilder.create(RerollFurnaceBlockEntity::new,ModBlocks.REROLL_FURNACE_BLOCK).build());
    public static void initialize() {}
}
