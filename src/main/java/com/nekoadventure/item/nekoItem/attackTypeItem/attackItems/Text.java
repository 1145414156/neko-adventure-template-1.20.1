package com.nekoadventure.item.nekoItem.attackTypeItem.attackItems;

import com.nekoadventure.item.nekoItem.AbstractNekoItem;
import com.nekoadventure.item.nekoItem.attackTypeItem.AbstractNekoAttackItem;
import com.nekoadventure.item.other.NekoPackageItem;
import net.minecraft.entity.player.PlayerEntity;

import java.util.List;

public class Text extends AbstractNekoAttackItem {
    public Text(Settings settings, double health, double strength, double speed, double attackSpeed, double attackRange, double attackMultiplier, double attackSpeedMultiplier) {
        super(settings, health, strength, speed, attackSpeed, attackRange, attackMultiplier, attackSpeedMultiplier);
    }

    @Override
    public void changeMainAttackType(PlayerEntity player, float yaw) {
        List<AbstractNekoItem> nekoItems = NekoPackageItem.getNekoItems(player.getOffHandStack(), true, 3);
        AbstractNekoAttackItem attackItem = null;
        for (int i = nekoItems.size() - 1; i >= 0; i--) {
            if (nekoItems.get(i) instanceof AbstractNekoAttackItem item && item != this) {
                attackItem = item;
                break;
            }
        }
        if (attackItem == null) {
            System.out.println("error");
            return;
        }
        for (int i = 0; i < 4; i++) {
            attackItem.changeMainAttackType(player, player.getYaw() + i * 90);
        }
    }
}
