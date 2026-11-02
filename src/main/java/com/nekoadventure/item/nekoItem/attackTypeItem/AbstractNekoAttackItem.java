package com.nekoadventure.item.nekoItem.attackTypeItem;

import com.nekoadventure.entity.missile.MissileEntity;
import com.nekoadventure.item.nekoItem.AbstractNekoItem;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;

import java.util.Map;
import java.util.WeakHashMap;

public abstract class AbstractNekoAttackItem extends AbstractNekoItem {
    private final Map<Entity, Long> lastOffAttackTimes = new WeakHashMap<>();
    public AbstractNekoAttackItem(Settings settings, double health, double strength, double speed, double attackSpeed,
                                  double attackRange, double attackMultiplier, double attackSpeedMultiplier) {
        super(settings);
        this.health=health;
        this.attackRange=attackRange;
        this.attackSpeed=attackSpeed;
        this.strength=strength;
        this.speed=speed;
        this.attackSpeedMultiplier=attackSpeedMultiplier;
        this.attackMultiplier=attackMultiplier;
    }


    public void changeMainAttackType(PlayerEntity player,float yaw){}

    public void changeOffAttackType(PlayerEntity player, MissileEntity missileEntity, float yaw){}
    public boolean canExecute(PlayerEntity player, Entity missile, int tick) {
        long now = player.getWorld().getTime();
        Long last = lastOffAttackTimes.get(missile);
        if (last == null || now - last >= tick) {
            lastOffAttackTimes.put(missile, now);
            return true;
        }
        return false;
    }
}
