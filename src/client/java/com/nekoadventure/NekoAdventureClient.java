package com.nekoadventure;


import com.nekoadventure.block.ModBlocks;
import com.nekoadventure.block.blockentity.ItemBaseBlockEntityRenderer;
import com.nekoadventure.block.blockentity.ModBlockEntityTypes;
import com.nekoadventure.block.blockentity.RerollFurnaceBlockEntityRenderer;
import com.nekoadventure.block.blockentity.ResetFurnaceBlockEntityRenderer;
import com.nekoadventure.client.ShiftKeyHelper;
import com.nekoadventure.entity.ModEntityRenderer;
import com.nekoadventure.item.nekoItem.AbstractNekoItem;
import com.nekoadventure.keyboard.ModKeyBoard;
import com.nekoadventure.network.item.ClientNekoPackageDataHandler;
import com.nekoadventure.network.maze.ClientMobIdInputHandler;
import com.nekoadventure.network.maze.ClientMonumentCreditsHandler;
import com.nekoadventure.network.maze.ClientScreenEffectHandler;
import com.nekoadventure.network.mob.ClientFloorShakeHandler;
import com.nekoadventure.network.mob.ClientScreenFlashHandler;
import com.nekoadventure.network.mob.ClientScreenShakeHandler;
import com.nekoadventure.network.mob.ClientScreenSwayHandler;
import com.nekoadventure.other.itemApart.SpawnRandomNekoItems;
import com.nekoadventure.particle.ModClientParticle;
import com.nekoadventure.sound.BossMusicHandler;
import com.nekoadventure.ui.NekoPackageScreen;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.fabricmc.fabric.api.client.model.loading.v1.ModelLoadingPlugin;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;

import java.util.List;

public class NekoAdventureClient implements ClientModInitializer {

	private record ItemPoolTag(TagKey<Item> tag, String displayName, Formatting color) {
	}

	private static final List<ItemPoolTag> ITEM_POOL_TAGS = List.of(
			new ItemPoolTag(SpawnRandomNekoItems.TREASURE_POOL, "宝箱池", Formatting.GREEN),
			new ItemPoolTag(SpawnRandomNekoItems.SHOP_POOL, "商店池", Formatting.GOLD),
			new ItemPoolTag(SpawnRandomNekoItems.BOSS_POOL, "首领池", Formatting.BLUE),
			new ItemPoolTag(SpawnRandomNekoItems.FORGE_POOL, "锻造池", Formatting.WHITE),
			new ItemPoolTag(SpawnRandomNekoItems.FOOD_POOL, "食物池", Formatting.RED),
			new ItemPoolTag(SpawnRandomNekoItems.GAMBLE_POOL, "赌博池", Formatting.DARK_AQUA),
			new ItemPoolTag(SpawnRandomNekoItems.SOUL_POOL, "魂石池", Formatting.LIGHT_PURPLE),
			new ItemPoolTag(SpawnRandomNekoItems.CONCEAL_POOL, "隐藏池", Formatting.YELLOW)
	);

	@Override
	public void onInitializeClient() {
		ShiftKeyHelper.setShiftDownSupplier(Screen::hasShiftDown);
		BlockEntityRendererFactories.register(ModBlockEntityTypes.ITEM_BASE_BLOCK_ENTITY, ctx -> new ItemBaseBlockEntityRenderer());
		BlockEntityRendererFactories.register(ModBlockEntityTypes.REROLL_FURNACE_BLOCK_ENTITY,ctx -> new RerollFurnaceBlockEntityRenderer());
		BlockEntityRendererFactories.register(ModBlockEntityTypes.RESET_FURNACE_BLOCK_ENTITY,ctx -> new ResetFurnaceBlockEntityRenderer());

		HudRenderCallback.EVENT.register(new NekoPackageScreen());

		ModKeyBoard.register();

		ItemTooltipCallback.EVENT.register((stack, context, lines) -> {
			if (!(stack.getItem() instanceof AbstractNekoItem)) {
				return;
			}
			if (ModKeyBoard.isShowItemPoolKeyDown()) {
				appendItemPoolTooltip(stack, lines);
			} else {
				lines.add(Text.literal("按住").formatted(Formatting.DARK_GRAY)
						.append(ModKeyBoard.getShowItemPoolKeyName())
						.append(Text.literal("查看道具池")).formatted(Formatting.DARK_GRAY));
			}
		});

		ClientMonumentCreditsHandler.init();
		ClientMobIdInputHandler.init();
		ClientScreenEffectHandler.register();
		ClientScreenShakeHandler.init();
		ClientScreenFlashHandler.init();
		ClientScreenSwayHandler.init();
		ClientFloorShakeHandler.init();
		ClientNekoPackageDataHandler.init();
		ModClientParticle.init();
		BossMusicHandler.init();

		ModEntityRenderer.register();
		ModelLoadingPlugin.register(context -> {for (int i = 0; i <= 20; i++) {context.addModels(new Identifier(NekoAdventure.MOD_ID, "item/zenith_sword_" + i));}});

		BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getTranslucent(),
				ModBlocks.ROLL_ITEM_BLOCK, ModBlocks.SLOT_MACHINE_BLOCK,
				ModBlocks.WISH_POOL_BLOCK,ModBlocks.SPAWN_MOB_BLOCK,
				ModBlocks.RESET_FURNACE_BLOCK,ModBlocks.REROLL_FURNACE_BLOCK,
				ModBlocks.TP_DUNGEON_BLOCK,ModBlocks.ITEM_BASE_BLOCK,
				ModBlocks.MONUMENT_BLOCK,ModBlocks.TP_NEXT_LEVEL_BLOCK);

	}

	private static void appendItemPoolTooltip(ItemStack stack, List<Text> tooltip) {
		tooltip.add(Text.literal("所属道具池：").formatted(Formatting.GRAY));
		boolean hasPool = false;
		for (ItemPoolTag pool : ITEM_POOL_TAGS) {
			if (stack.isIn(pool.tag())) {
				tooltip.add(Text.literal(" - " + pool.displayName()).formatted(pool.color()));
				hasPool = true;
			}
		}
		if (!hasPool) {
			tooltip.add(Text.literal(" - 无").formatted(Formatting.DARK_GRAY));
		}
	}
}
