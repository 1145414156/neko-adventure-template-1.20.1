package com.nekoadventure.item.nekoItem.attackTypeItem;

import com.nekoadventure.item.nekoItem.AbstractNekoItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;

public abstract class AbstractNekoSpecificItem extends AbstractNekoItem {
    public AbstractNekoSpecificItem(Settings settings, double health, double strength, double speed, double attackSpeed,
                                    double attackRange, double attackMultiplier, double attackSpeedMultiplier, boolean isSpecific) {
        super(settings);
        this.health=health;
        this.attackRange=attackRange;
        this.attackSpeed=attackSpeed;
        this.strength=strength;
        this.speed=speed;
        this.attackSpeedMultiplier=attackSpeedMultiplier;
        this.attackMultiplier=attackMultiplier;
    }
    public abstract void applySpecificItem(PlayerEntity player, LivingEntity target);
    //这个方法是为了让一些数据可以在tick中自行计算用的
    public void calculateOnTick(PlayerEntity player){}
}
