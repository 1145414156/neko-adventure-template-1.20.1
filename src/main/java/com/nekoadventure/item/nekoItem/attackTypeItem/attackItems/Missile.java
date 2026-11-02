package com.nekoadventure.item.nekoItem.attackTypeItem.attackItems;

import com.nekoadventure.item.nekoItem.attackTypeItem.AbstractNekoAttackItem;
import net.minecraft.entity.player.PlayerEntity;

public class Missile extends AbstractNekoAttackItem {
    public Missile(Settings settings, double health, double strength, double speed, double attackSpeed, double attackRange, double attackMultiplier, double attackSpeedMultiplier) {
        super(settings, health, strength, speed, attackSpeed, attackRange, attackMultiplier, attackSpeedMultiplier);
    }

    @Override
    public void changeMainAttackType(PlayerEntity player, float yaw) {

    }

    @Override
    public void changeOffAttackType(PlayerEntity player, float yaw) {
        super.changeOffAttackType(player, yaw);
    }
}
