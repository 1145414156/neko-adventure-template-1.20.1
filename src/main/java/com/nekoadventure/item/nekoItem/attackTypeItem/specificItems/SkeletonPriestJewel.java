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

public class SkeletonPriestJewel extends AbstractNekoSpecificItem {


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
            double randomNum = Math.min(10,rand.nextDouble(strength)/5);
            if (rand.nextInt(100)<=randomNum) {
                target.damage(target.getDamageSources().mobAttack(player),target.getHealth());
                player.getWorld().playSound(null, player.getBlockPos(), ModSoundEvents.SKELETON_PRIEST_JEWEL,
                        SoundCategory.PLAYERS, 1.0F, 0.9F);
                if (player.getWorld() instanceof ServerWorld serverWorld) {
                    serverWorld.spawnParticles(ParticleTypes.SOUL, target.getX(), target.getEyeY(), target.getZ(), 20, 0.5D, 0.5D, 0.5D, 0.3D);
                    serverWorld.spawnParticles(ParticleTypes.SOUL_FIRE_FLAME, target.getX(), target.getEyeY(), target.getZ(), 20, 0.5D, 0.5D, 0.5D, 0.3D);
                }
            }
        }
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);
        Text moreText=Text.of("造成伤害时，有\"力量\"*0.5/100(上限1/10)的概率直接将目标(血量低于1/2时)斩杀");
        if (ShiftKeyHelper.isShiftDown()) {
            tooltip.add(moreText);
        }
    }
}
