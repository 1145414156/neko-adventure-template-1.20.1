package com.nekoadventure.item.nekoItem.attackTypeItem.attackItems;

import com.nekoadventure.client.ShiftKeyHelper;
import com.nekoadventure.entity.ModEntities;
import com.nekoadventure.entity.missile.MissileEntity;
import com.nekoadventure.item.nekoItem.attackTypeItem.AbstractNekoAttackItem;
import com.nekoadventure.item.other.NekoPackageItem;
import com.nekoadventure.other.itemApart.NekoPackageDataManager;
import com.nekoadventure.other.mobApart.AttackTypes;
import com.nekoadventure.other.mobApart.MissileModelType;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class Singularity extends AbstractNekoAttackItem {
    public Singularity(Settings settings, double health, double strength, double speed, double attackSpeed, double attackRange, double attackMultiplier, double attackSpeedMultiplier) {
        super(settings, health, strength, speed, attackSpeed, attackRange, attackMultiplier, attackSpeedMultiplier);
    }
    @Override
    public void changeMainAttackType(PlayerEntity player, float yaw) {
        double damage=4;
        double attackRange = 3;
        if (player.getOffHandStack().getItem() instanceof NekoPackageItem nekoPackage){
            damage=nekoPackage.getStrength(player);
            attackRange = nekoPackage.getAttackRange(player);
        }
        MissileEntity missileEntity=getMissileEntity(player,attackRange,damage);
        missileEntity.setYaw(yaw);
        missileEntity.setPitch(player.getPitch());
        missileEntity.setPosition(player.getX(), player.getEyePos().y, player.getZ());
        player.getWorld().playSound(null, player.getBlockPos(), SoundEvents.BLOCK_DISPENSER_LAUNCH,
                SoundCategory.PLAYERS, 1.0F, 1.0F);
        player.getWorld().spawnEntity(missileEntity);
        double[] finalData = NekoPackageDataManager.getFinalData(player);
        double attackSpeed = finalData != null ? finalData[3] : 0;
        int cooldown = (int) (240 - (attackSpeed / NekoPackageDataManager.MAX_ATTACK_SPEED) * 240);
        cooldown = Math.max(60, Math.min(240, cooldown));
        player.getItemCooldownManager().set(player.getOffHandStack().getItem(), cooldown);
    }

    @Override
    public void changeOffAttackType(PlayerEntity player, MissileEntity missileEntity, float yaw) {
        if(canExecute(player,missileEntity,2)){
            singularPull(missileEntity,Math.min(8,missileEntity.getRange()));
        }
    }

    private @NotNull MissileEntity getMissileEntity(Entity owner, double range, double damage) {
        AttackTypes attackType = new AttackTypes(AttackTypes.AttackType.SINGULARITY);
        MissileEntity missile= new MissileEntity(ModEntities.MISSILE,
                owner.getWorld(),
                owner,
                attackType,
                true,
                range,
                120,
                damage*2
        );
        missile.setMissileModelType(MissileModelType.BULLET);
        return  missile;
    }

    private void singularPull(MissileEntity entity, double range) {
        if (range <= 0) {
            return;
        }
        Vec3d center = entity.getBoundingBox().getCenter();
        Entity realOwner = entity.getRealOwner();
        List<LivingEntity> targets = entity.getWorld().getEntitiesByClass(
                LivingEntity.class,
                entity.getBoundingBox().expand(range),
                e -> e != realOwner && e.isAlive()
        );
        for (LivingEntity target : targets) {
            Vec3d diff = center.subtract(target.getBoundingBox().getCenter());
            double distance = diff.length();
            if (distance < 0.01) {
                continue;
            }
            double strength = 0.05 + 0.25 * (1.0 - Math.min(distance / range, 1.0));
            Vec3d pull = diff.multiply(strength / distance);
            target.addVelocity(pull.x, pull.y, pull.z);
            target.velocityModified = true;
        }
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);
        Text moreText=Text.of("主攻击：发射一枚持续吸引周围生物的弹幕，若撞到生物或墙壁后停止");
        Text moreText1=Text.of("次攻击：弹幕会持续吸引周围生物");
        if (ShiftKeyHelper.isShiftDown()) {
            tooltip.add(moreText);
            tooltip.add(moreText1);
        }
    }
}
