package com.nekoadventure.keyboard;

import com.nekoadventure.item.ModItems;
import com.nekoadventure.network.item.ClientNekoPackageDataHandler;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvents;
import org.lwjgl.glfw.GLFW;

public class ModKeyBoard {
    private static KeyBinding RESET_NEKO_ITEMS_KEY;
    private static KeyBinding MAIN_ATTACK_TYPE_KEY;

    public static void register() {

        RESET_NEKO_ITEMS_KEY = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.neko-adventure.reset_items",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_O,
                "category.neko-adventure"
        ));

        MAIN_ATTACK_TYPE_KEY = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.neko-adventure.main_attack_type",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_P,
                "category.neko-adventure"
        ));

        ClientTickEvents.START_CLIENT_TICK.register(client -> {
            if (RESET_NEKO_ITEMS_KEY.wasPressed()) {
                resetNekoItems(client);
            }
            if (MAIN_ATTACK_TYPE_KEY.isPressed()) {
                applyMainAttackType(client);
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

    //主攻击方式由按键触发(按住可持续触发，实际频率由服务端冷却校验控制)
    private static void applyMainAttackType(MinecraftClient client) {
        if (client.player != null) {
            ItemStack offHand = client.player.getOffHandStack();
            if (offHand.getItem().equals(ModItems.NEKO_PACKAGE)) {
                ClientNekoPackageDataHandler.requestMainAttackType();
            }
        }
    }
}
