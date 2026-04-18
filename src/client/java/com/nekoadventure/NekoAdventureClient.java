package com.nekoadventure;


import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class NekoAdventureClient implements ClientModInitializer {
	public static KeyBinding RESET_KEY;

	@Override
	public void onInitializeClient() {

		RESET_KEY = KeyBindingHelper.registerKeyBinding(new KeyBinding(
				"key.nekoadventure.reset",
				InputUtil.Type.KEYSYM,
				GLFW.GLFW_KEY_C,                    // 默认X键
				"category.nekoadventure.main"
		));
	}
}