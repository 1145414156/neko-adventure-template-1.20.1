package com.nekoadventure.item.nekoItem.attackTypeItem.specificItems;

import com.nekoadventure.client.ShiftKeyHelper;
import com.nekoadventure.item.nekoItem.attackTypeItem.NekoAttackTypeItem;
import com.nekoadventure.item.other.NekoPackageItem;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Random;

public class Cullet extends NekoAttackTypeItem {


    public Cullet(Settings settings, double health, double strength, double speed, double attackSpeed, double attackRange, double attackMultiplier, double attackSpeedMultiplier, boolean isSpecific) {
        super(settings, health, strength, speed, attackSpeed, attackRange, attackMultiplier, attackSpeedMultiplier, isSpecific);
    }

    @Override
    public void applySpecificItem(PlayerEntity owner, LivingEntity target) {
        super.applySpecificItem(owner, target);
        double strength = 1;
        if (owner.getOffHandStack().getItem() instanceof NekoPackageItem nekoPackage) {
            strength = nekoPackage.getStrength(owner);
        }
        int a= (int) strength;
        if (a>45){a=45;}
        int b=50-a;
        Random random=new Random();
        if (random.nextInt(99) <b) {
            target.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS,3*20,2,true,true));
        }
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);
        Text moreText=Text.of("造成伤害时，有50-\"力量\"/100的几率让对方获得3s，效果2的减速效果（下限5/100）");
        if (ShiftKeyHelper.isShiftDown()) {
            tooltip.add(moreText);
        }
    }
}
