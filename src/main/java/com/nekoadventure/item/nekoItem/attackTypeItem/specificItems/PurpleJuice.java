package com.nekoadventure.item.nekoItem.attackTypeItem.specificItems;

import com.nekoadventure.client.ShiftKeyHelper;
import com.nekoadventure.item.nekoItem.attackTypeItem.AttackTypeItem;
import com.nekoadventure.item.other.NekoPackageItem;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class PurpleJuice extends AttackTypeItem {
    double strength;
    public PurpleJuice(Settings settings, double health, double strength, double speed, double attackSpeed, double attackRange, double attackMultiplier, double attackSpeedMultiplier, boolean isSpecific) {
        super(settings, health, strength, speed, attackSpeed, attackRange, attackMultiplier, attackSpeedMultiplier, isSpecific);
    }

    @Override
    public void applySpecificItem(PlayerEntity player, LivingEntity target) {
        if (player.getOffHandStack().getItem() instanceof NekoPackageItem nekoPackage) {
            double randomStrength = 0.1 + player.getRandom().nextDouble() * 1.9;
            if (strength!=0) {
                nekoPackage.addOtherData(player, new double[]{0, 0, -strength, 0, 0, 0, 0});
                strength = randomStrength;
                nekoPackage.addOtherData(player, new double[]{0, 0, randomStrength, 0, 0, 0, 0});
            }
            else {
                nekoPackage.addOtherData(player, new double[]{0, 0, randomStrength, 0, 0, 0, 0});
                strength = randomStrength;
            }
        }
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);
        Text moreText=Text.of("造成伤害时，使自身额外随机获得0.1~2.0的\"力量\"");
        if (ShiftKeyHelper.isShiftDown()) {
            tooltip.add(moreText);
        }
    }
}
