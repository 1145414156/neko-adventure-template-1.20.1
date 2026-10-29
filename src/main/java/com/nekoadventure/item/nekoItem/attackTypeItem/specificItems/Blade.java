package com.nekoadventure.item.nekoItem.attackTypeItem.specificItems;

import com.nekoadventure.client.ShiftKeyHelper;
import com.nekoadventure.item.nekoItem.attackTypeItem.AbstractNekoSpecificItem;
import com.nekoadventure.item.other.NekoPackageItem;
import com.nekoadventure.sound.ModSoundEvents;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Random;

public class Blade extends AbstractNekoSpecificItem {
    public Blade(Settings settings, double health, double strength, double speed, double attackSpeed, double attackRange, double attackMultiplier, double attackSpeedMultiplier, boolean isSpecific) {
        super(settings, health, strength, speed, attackSpeed, attackRange, attackMultiplier, attackSpeedMultiplier, isSpecific);
    }
    @Override
    public void applySpecificItem(PlayerEntity player, LivingEntity target) {
        Random random=new Random();
        if (random.nextInt(99) <10) {
            double strength = 1;
            if (player.getOffHandStack().getItem() instanceof NekoPackageItem nekoPackage) {
                strength = nekoPackage.getStrength(player);
            }
            target.damage(player.getDamageSources().mobAttack(player),
                    (float) strength);
            player.getWorld().playSound(null, player.getBlockPos(), ModSoundEvents.BLADE_ATTACK,
                    SoundCategory.PLAYERS, 1.0F, 1.0F);
            if (player.getWorld() instanceof ServerWorld serverWorld) {
                serverWorld.spawnParticles(
                        ParticleTypes.CRIT,
                        target.getX(), target.getEyeY(), target.getZ(),
                        30,
                        0.4, 0.4, 0.4,
                        0.5
                );
            }
        }
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);
        Text moreText=Text.of("造成伤害时，有1/10概率额外造成一次伤害(不会触发特效)");
        if (ShiftKeyHelper.isShiftDown()) {
            tooltip.add(moreText);
        }
    }
}
