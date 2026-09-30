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
import java.util.Random;

public class SkeletonPriestJewel extends NekoAttackTypeItem {
    public SkeletonPriestJewel(Settings settings, double health, double strength, double speed, double attackSpeed, double attackRange, double attackMultiplier, double attackSpeedMultiplier, boolean isSpecific) {
        super(settings, health, strength, speed, attackSpeed, attackRange, attackMultiplier, attackSpeedMultiplier, isSpecific);
    }

    @Override
    public void applySpecificItem(PlayerEntity player, LivingEntity target) {
        if (target.getHealth() < target.getMaxHealth()/2) {
            Random rand = new Random();
            double strength = 1;
            if (player.getOffHandStack().getItem() instanceof NekoPackageItem nekoPackage) {
                strength = nekoPackage.getStrength(player);
            }
            double randomNum = Math.min(10,rand.nextDouble(strength)/10);
            if (rand.nextInt(100)<=randomNum) {
                target.damage(target.getDamageSources().mobAttack(player),target.getHealth());
            }
        }
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);
        Text moreText=Text.of("造成伤害时，有\"力量\"*0.1/100(上限1/10)的概率直接将目标(血量低于1/2时)斩杀");
        if (ShiftKeyHelper.isShiftDown()) {
            tooltip.add(moreText);
        }
    }
}
