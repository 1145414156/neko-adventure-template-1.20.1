package com.nekoadventure.keyboard;

import com.nekoadventure.item.ModItems;
import com.nekoadventure.item.other.PropPrototypeItem;
import com.nekoadventure.mixin.client.HandledScreenAccessor;
import com.nekoadventure.network.item.ClientNekoPackageDataHandler;
import com.nekoadventure.ui.PropPrototypePoolScreen;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

import java.util.List;

public class ModKeyBoard {
    private static KeyBinding RESET_NEKO_ITEMS_KEY;
    private static KeyBinding MAIN_ATTACK_TYPE_KEY;
    private static KeyBinding SHOW_ITEM_POOL_KEY;
    private static boolean showItemPoolKeyWasDown;

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

        SHOW_ITEM_POOL_KEY = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.neko-adventure.show_item_pool",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_LEFT_ALT,
                "category.neko-adventure"
        ));

        ClientTickEvents.START_CLIENT_TICK.register(client -> {
            if (RESET_NEKO_ITEMS_KEY.wasPressed()) {
                resetNekoItems(client);
            }
            if (MAIN_ATTACK_TYPE_KEY.isPressed()) {
                applyMainAttackType(client);
            }
            boolean keyDown = isShowItemPoolKeyDown();
            if (keyDown && !showItemPoolKeyWasDown) {
                openPropPrototypePoolScreen(client);
            }
            showItemPoolKeyWasDown = keyDown;
        });
    }

    /**
     * 在容器界面中悬停道具原胚并按下查看道具池的按键时，打开只读的道具池预览界面
     */
    private static void openPropPrototypePoolScreen(MinecraftClient client) {
        if (!(client.currentScreen instanceof HandledScreen<?> handledScreen)) {
            return;
        }
        Slot slot = ((HandledScreenAccessor) handledScreen).nekoGetFocusedSlot();
        if (slot == null || slot.getStack().isEmpty()) {
            return;
        }
        if (!(slot.getStack().getItem() instanceof PropPrototypeItem propPrototype)) {
            return;
        }
        List<Item> pool = propPrototype.getPoolItems(slot.getStack());
        if (pool.isEmpty()) {
            return;
        }
        client.setScreen(new PropPrototypePoolScreen(pool));
    }

    public static boolean isShowItemPoolKeyDown() {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.getWindow() == null) {
            return false;
        }
        InputUtil.Key boundKey = KeyBindingHelper.getBoundKeyOf(SHOW_ITEM_POOL_KEY);
        if (boundKey.getCategory() == InputUtil.Type.MOUSE) {
            return GLFW.glfwGetMouseButton(client.getWindow().getHandle(), boundKey.getCode()) == GLFW.GLFW_PRESS;
        }
        return InputUtil.isKeyPressed(client.getWindow().getHandle(), boundKey.getCode());
    }

    public static Text getShowItemPoolKeyName() {
        return SHOW_ITEM_POOL_KEY.getBoundKeyLocalizedText();
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
    private static void applyMainAttackType(MinecraftClient client) {
        if (client.player != null) {
            ItemStack offHand = client.player.getOffHandStack();
            if (offHand.getItem().equals(ModItems.NEKO_PACKAGE)) {
                ClientNekoPackageDataHandler.requestMainAttackType();
            }
        }
    }
}
