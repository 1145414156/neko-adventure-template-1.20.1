package com.nekoadventure.item.nekoItem.attackTypeItem.specificItems;

import com.nekoadventure.client.ShiftKeyHelper;
import com.nekoadventure.item.nekoItem.attackTypeItem.AttackTypeItem;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BloodSacrifice extends AttackTypeItem {
    public BloodSacrifice(Settings settings, double health, double strength, double speed, double attackSpeed, double attackRange, double attackMultiplier, double attackSpeedMultiplier, boolean isSpecific) {
        super(settings, health, strength, speed, attackSpeed, attackRange, attackMultiplier, attackSpeedMultiplier, isSpecific);
    }

    @Override
    public void applySpecificItem(PlayerEntity player, LivingEntity target) {
        float currentHealth = player.getHealth();
        float maxHealth = player.getMaxHealth();
        float lostHealth = maxHealth - currentHealth;
        if (currentHealth > 5.0f) {
            player.damage(player.getDamageSources().magic(), 1.0f);
            float extraDamage = lostHealth * 0.5f;
            if (extraDamage > 0) {
                target.damage(player.getDamageSources().magic(), extraDamage);
            }
        }
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);
        Text moreText=Text.of("造成伤害时，使自身受伤1格血量(如果可以)，并使自身本次攻击额外造成一次自身已损生命值*0.5的伤害");
        if (ShiftKeyHelper.isShiftDown()) {
            tooltip.add(moreText);
        }
    }
}
