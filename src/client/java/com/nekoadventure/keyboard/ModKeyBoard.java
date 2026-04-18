package com.nekoadventure.keyboard;

import com.nekoadventure.item.ModItems;
import com.nekoadventure.screen.ClientNekoPackageDataHandler;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvents;
import org.lwjgl.glfw.GLFW;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ModKeyBoard {
    private static KeyBinding RESET_NEKO_ITEMS_KEY;

    public static void register() {

        RESET_NEKO_ITEMS_KEY = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.neko-adventure.reset_items",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_O,
                "category.neko-adventure"
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (RESET_NEKO_ITEMS_KEY.wasPressed()) {
                resetNekoItems(client);
            }
        });
    }

    private static void resetNekoItems(MinecraftClient client) {
        if (client.player != null) {
            PlayerEntity player = client.player;
            ItemStack offHand = player.getOffHandStack();
            if (offHand.getItem().equals(ModItems.NEKO_PACKAGE)) {
                ClientNekoPackageDataHandler.requestReset();
                player.playSound(SoundEvents.ENTITY_CAT_AMBIENT, 1.5F, 1.0F);
            }
        }
    }
}
