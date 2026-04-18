package com.nekoadventure.item.nekoItem;

public class NumItem extends AbstractNekoItem {
    public NumItem(Settings settings, double health, double strength, double speed, double attackSpeed,double attackRange,double attackMultiplier,double attackSpeedMultiplier) {
        super(settings.maxCount(1));
        this.health=health;
        this.strength=strength;
        this.speed=speed;
        this.attackSpeed=attackSpeed;
        this.attackRange=attackRange;
        this.attackMultiplier=attackMultiplier;
        this.attackSpeedMultiplier=attackSpeedMultiplier;
    }

}
