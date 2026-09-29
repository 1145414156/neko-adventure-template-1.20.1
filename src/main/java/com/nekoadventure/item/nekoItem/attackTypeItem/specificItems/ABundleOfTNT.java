package com.nekoadventure.item.nekoItem.attackTypeItem.specificItems;

import com.nekoadventure.client.ShiftKeyHelper;
import com.nekoadventure.item.nekoItem.attackTypeItem.NekoAttackTypeItem;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ABundleOfTNT extends NekoAttackTypeItem {
    public ABundleOfTNT(Settings settings, double health, double strength, double speed, double attackSpeed, double attackRange, double attackMultiplier, double attackSpeedMultiplier, boolean isSpecific) {
        super(settings, health, strength, speed, attackSpeed, attackRange, attackMultiplier, attackSpeedMultiplier, isSpecific);
    }

    @Override
    public void applySpecificItem(PlayerEntity player, LivingEntity target) {
        super.applySpecificItem(player, target);
        Explosion explosion = new Explosion(
                target.getWorld(),
                player,
                target.getX(),
                target.getY(),
                target.getZ(),
                0.5F,
                false,
                Explosion.DestructionType.KEEP
        );
        explosion.collectBlocksAndDamageEntities();
        explosion.affectWorld(true);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);
        Text text= Text.of("当攻击到目标时，产生一次爆炸");
        if (ShiftKeyHelper.isShiftDown()) {
            tooltip.add(text);
        }
    }
}
