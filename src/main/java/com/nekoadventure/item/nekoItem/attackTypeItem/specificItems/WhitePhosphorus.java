package com.nekoadventure.item.nekoItem.attackTypeItem.specificItems;

import com.nekoadventure.client.ShiftKeyHelper;
import com.nekoadventure.item.nekoItem.attackTypeItem.NekoAttackTypeItem;
import com.nekoadventure.item.other.NekoPackageItem;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class WhitePhosphorus extends NekoAttackTypeItem {
    public WhitePhosphorus(Settings settings, double health, double strength, double speed, double attackSpeed, double attackRange, double attackMultiplier, double attackSpeedMultiplier, boolean isSpecific) {
        super(settings, health, strength, speed, attackSpeed, attackRange, attackMultiplier, attackSpeedMultiplier, isSpecific);
    }

    @Override
    public void applySpecificItem(PlayerEntity player, LivingEntity target) {
        double strength = 1;
        if (player.getOffHandStack().getItem() instanceof NekoPackageItem nekoPackage) {
            strength = nekoPackage.getStrength(player);
        }
        float strengthValue = (float) strength;
        int fireTicks = (int) (strengthValue * 20);
        fireTicks = Math.min(fireTicks, 200);
        target.setFireTicks(fireTicks);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);
        Text moreText=Text.of("造成伤害时，点燃目标");
        if (ShiftKeyHelper.isShiftDown()) {
            tooltip.add(moreText);
        }
    }
}
