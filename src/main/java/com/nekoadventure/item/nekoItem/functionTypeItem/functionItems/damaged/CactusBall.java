package com.nekoadventure.item.nekoItem.functionTypeItem.functionItems.damaged;

import com.nekoadventure.item.nekoItem.functionTypeItem.functionItems.NekoFunctionItem;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.world.World;

import java.util.Comparator;
import java.util.List;

public class CactusBall extends NekoFunctionItem {
    public CactusBall(Settings settings) {
        super(settings);
        text= Text.of("受到伤害时，给予最近生物3点伤害");
    }

    @Override
    public boolean applyDamagedFunctionItem(PlayerEntity player) {
        World world = player.getWorld();
        List<LivingEntity> entities = world.getEntitiesByClass(
                LivingEntity.class,
                player.getBoundingBox().expand(4.0),
                entity -> entity != player && !(entity instanceof PlayerEntity)
        );
        if (!entities.isEmpty()) {
            Entity nearest = entities.stream()
                    .min(Comparator.comparingDouble(e -> e.squaredDistanceTo(player)))
                    .orElse(null);

            if (nearest instanceof LivingEntity target) {
                target.damage(player.getDamageSources().playerAttack(player), 3.0f);
                world.playSound(null, target.getBlockPos(),
                        SoundEvents.ENTITY_PLAYER_ATTACK_STRONG,
                        SoundCategory.PLAYERS, 1.0F, 1.0F);
            }
        }
        return false;
    }
}
