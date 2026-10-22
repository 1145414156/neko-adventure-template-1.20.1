package com.nekoadventure.item.nekoItem.attackTypeItem;

import com.nekoadventure.item.nekoItem.AbstractNekoItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public abstract class AbstractNekoAttackItem extends AbstractNekoItem {
    private final Map<UUID, Long> lastOffAttackTimes = new HashMap<>();
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


    public void changeMainAttackType(PlayerEntity player){}

    public void changeOffAttackType(PlayerEntity player){}

    public boolean canExecute(PlayerEntity player,int tick) {
        long now = player.getWorld().getTime();
        Long last = lastOffAttackTimes.get(player.getUuid());
        if (last == null || now - last >= tick) {
            lastOffAttackTimes.put(player.getUuid(), now);
            return true;
        }
        return false;
    }
}
