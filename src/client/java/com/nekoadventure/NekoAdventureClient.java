package com.nekoadventure;


import com.nekoadventure.block.ModBlocks;
import com.nekoadventure.block.blockentity.ItemBaseBlockEntityRenderer;
import com.nekoadventure.block.blockentity.ModBlockEntityTypes;
import com.nekoadventure.block.blockentity.RerollFurnaceBlockEntityRenderer;
import com.nekoadventure.block.blockentity.ResetFurnaceBlockEntityRenderer;
import com.nekoadventure.client.ShiftKeyHelper;
import com.nekoadventure.entity.ModEntityRenderer;
import com.nekoadventure.keyboard.ModKeyBoard;
import com.nekoadventure.screen.ClientFloorShakeHandler;
import com.nekoadventure.screen.ClientMonumentCreditsHandler;
import com.nekoadventure.screen.ClientNekoPackageDataHandler;
import com.nekoadventure.screen.ClientScreenEffectHandler;
import com.nekoadventure.screen.ClientScreenShakeHandler;
import com.nekoadventure.ui.NekoPackageScreen;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;

public class NekoAdventureClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		ShiftKeyHelper.setShiftDownSupplier(Screen::hasShiftDown);
		BlockEntityRendererFactories.register(ModBlockEntityTypes.ITEM_BASE_BLOCK_ENTITY, ctx -> new ItemBaseBlockEntityRenderer());
		BlockEntityRendererFactories.register(ModBlockEntityTypes.REROLL_FURNACE_BLOCK_ENTITY,ctx -> new RerollFurnaceBlockEntityRenderer());
		BlockEntityRendererFactories.register(ModBlockEntityTypes.RESET_FURNACE_BLOCK_ENTITY,ctx -> new ResetFurnaceBlockEntityRenderer());

		HudRenderCallback.EVENT.register(new NekoPackageScreen());

		ModKeyBoard.register();

		ClientMonumentCreditsHandler.init();
		ClientScreenEffectHandler.register();
		ClientScreenShakeHandler.init();
		ClientFloorShakeHandler.init();
		ClientNekoPackageDataHandler.init();

		ModEntityRenderer.register();
		BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getTranslucent(),
				ModBlocks.ROLL_ITEM_BLOCK, ModBlocks.SLOT_MACHINE_BLOCK,
				ModBlocks.WISH_POOL_BLOCK,ModBlocks.SPAWN_MOB_BLOCK,
				ModBlocks.RESET_FURNACE_BLOCK,ModBlocks.REROLL_FURNACE_BLOCK,
				ModBlocks.TP_DUNGEON_BLOCK,ModBlocks.ITEM_BASE_BLOCK,
				ModBlocks.MONUMENT_BLOCK,ModBlocks.TP_NEXT_LEVEL_BLOCK);

	}
}
