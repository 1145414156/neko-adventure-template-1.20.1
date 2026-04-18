package com.nekoadventure.block;

import com.nekoadventure.NekoAdventure;
import com.nekoadventure.block.maze.*;
import com.nekoadventure.block.other.*;
import com.nekoadventure.block.specialroomblock.*;
import com.nekoadventure.item.ModItems;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModBlocks {
    //迷宫构造方块
    public static final GateBlock GATE_BLOCK=register(
            "gate_block",new GateBlock(AbstractBlock.Settings.copy(Blocks.BEDROCK))
    );
    public static final MazeBlock MAZE_BLOCK=register(
            "maze_block",new MazeBlock(AbstractBlock.Settings.copy(Blocks.BEDROCK))
    );
    public static final SpawnMobBlock SPAWN_MOB_BLOCK=register(
            "spawn_mob_block",new SpawnMobBlock(AbstractBlock.Settings.copy(Blocks.BEDROCK).nonOpaque())
    );
    public static final MazeStructureBlock MAZE_STRUCTURE_BLOCK=register(
            "maze_structure_block",new MazeStructureBlock(AbstractBlock.Settings.copy(Blocks.BEDROCK))
    );
    public static final TPNextLevelBlock TP_NEXT_LEVEL_BLOCK=register(
            "tp_next_level_block",new TPNextLevelBlock(AbstractBlock.Settings.copy(Blocks.BEDROCK).nonOpaque())
    );
    public static final MazeRoomStageBlock MAZE_ROOM_STAGE_BLOCK=register(
            "maze_room_stage_block",new MazeRoomStageBlock(AbstractBlock.Settings.copy(Blocks.BEDROCK))
    );

    //房间方块
    public static final TreasureRoomBlock TREASURE_ROOM_BLOCK=register(
            "treasure_room_block",new TreasureRoomBlock(AbstractBlock.Settings.copy(Blocks.BEDROCK))
    );
    public static final StartRoomBlock START_ROOM_BLOCK=register(
            "start_room_block",new StartRoomBlock(AbstractBlock.Settings.copy(Blocks.BEDROCK))
    );
    public static final ShopRoomBlock SHOP_ROOM_BLOCK=register(
            "shop_room_block",new ShopRoomBlock(AbstractBlock.Settings.copy(Blocks.BEDROCK))
    );
    public static final GambleRoomBlock GAMBLE_ROOM_BLOCK=register(
            "gamble_room_block", new GambleRoomBlock(AbstractBlock.Settings.copy(Blocks.BEDROCK))
    );
    public static final BossRoomBlock BOSS_ROOM_BLOCK=register(
            "boss_room_block",new BossRoomBlock(AbstractBlock.Settings.copy(Blocks.BEDROCK))
    );
    public static final ForgeRoomBlock FORGE_ROOM_BLOCK=register(
            "forge_room_block",new ForgeRoomBlock(AbstractBlock.Settings.copy(Blocks.BEDROCK))
    );
    public static final SoulRoomBlock SOUL_ROOM_BLOCK=register(
            "soul_room_block",new SoulRoomBlock(AbstractBlock.Settings.copy(Blocks.BEDROCK))
    );
    public static final FoodRoomBlock FOOD_ROOM_BLOCK=register(
            "food_room_block",new FoodRoomBlock(AbstractBlock.Settings.copy(Blocks.BEDROCK))
    );
    public static final ConcealRoomBlock CONCEAL_ROOM_BLOCK=register(
            "conceal_room_block",new ConcealRoomBlock(AbstractBlock.Settings.copy(Blocks.BEDROCK))
    );



    //迷宫内功能方块
    public static final ItemBaseBlock ITEM_BASE_BLOCK=register(
            "item_base_block",new ItemBaseBlock(AbstractBlock.Settings.copy(Blocks.BEDROCK).nonOpaque()));
    public static final WishPoolBlock WISH_POOL_BLOCK=register(
            "wish_pool_block",new WishPoolBlock(AbstractBlock.Settings.copy(Blocks.BEDROCK).nonOpaque())
    );
    public static final SlotMachineBlock SLOT_MACHINE_BLOCK=register(
            "slot_machine_block",new SlotMachineBlock(AbstractBlock.Settings.copy(Blocks.BEDROCK).nonOpaque())
    );
    public static final RollItemBlock ROLL_ITEM_BLOCK=register(
            "roll_item_block",new RollItemBlock(AbstractBlock.Settings.copy(Blocks.BEDROCK).nonOpaque())
    );
    public static final ResetFurnaceBlock RESET_FURNACE_BLOCK=register(
            "reset_furnace_block",new ResetFurnaceBlock(AbstractBlock.Settings.copy(Blocks.BEDROCK).nonOpaque())
    );
    public static final RerollFurnaceBlock REROLL_FURNACE_BLOCK=register(
            "reroll_furnace_block",new RerollFurnaceBlock(AbstractBlock.Settings.copy(Blocks.BEDROCK).nonOpaque())
    );

    //纪念碑（右键查看感谢名单）
    public static final MonumentBlock MONUMENT_BLOCK=register(
            "monument_block",new MonumentBlock(AbstractBlock.Settings.copy(Blocks.BEDROCK).nonOpaque())
    );

    //传送迷宫方块
    public static final CommonTPMazeBlock TP_DUNGEON_BLOCK =register(
            "tp_dungeon_block",new CommonTPMazeBlock(AbstractBlock.Settings.copy(Blocks.BEDROCK).nonOpaque(),"dungeon", ModItems.BROKEN_SWORD)
    );


    private static <T extends Block> T register(String path, T block) {
        Registry.register(Registries.BLOCK, Identifier.of(NekoAdventure.MOD_ID, path), block);
        Registry.register(Registries.ITEM, Identifier.of(NekoAdventure.MOD_ID, path), new BlockItem(block, new Item.Settings()));
        return block;
    }
    public static void initialize() {}
}
