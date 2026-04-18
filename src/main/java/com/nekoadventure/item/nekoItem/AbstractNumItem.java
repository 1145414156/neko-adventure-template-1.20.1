package com.nekoadventure.item.nekoItem;

import net.minecraft.item.Item;

public class AbstractNumItem extends Item {
    double health;
    double strength;
    double speed;
    double attackSpeed;

    public AbstractNumItem(Settings settings,double health,double strength,double speed,double attackSpeed) {
        super(settings);
        this.health=health;
        this.strength=strength;
        this.speed=speed;
        this.attackSpeed=attackSpeed;
    }
    public double getHealth(){
        return health;
    }
    public double getStrength(){
        return strength;
    }
    public double getSpeed(){
        return speed;
    }
    public double getAttackSpeed() {
        return attackSpeed;
    }
}
