package com.nekoadventure.item.nekoItem.attackTypeItem.attackItems;

import com.nekoadventure.entity.ModEntities;
import com.nekoadventure.entity.missile.MissileEntity;
import com.nekoadventure.item.nekoItem.attackTypeItem.AbstractNekoAttackItem;
import com.nekoadventure.item.other.NekoPackageItem;
import com.nekoadventure.other.itemApart.NekoPackageDataManager;
import com.nekoadventure.other.mobApart.AttackTypes;
import com.nekoadventure.other.mobApart.MissileModelType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.NotNull;

public class Missile extends AbstractNekoAttackItem {
    public Missile(Settings settings, double health, double strength, double speed, double attackSpeed, double attackRange, double attackMultiplier, double attackSpeedMultiplier) {
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
        missileEntity.setPitch(-90);
        Vec3d look = player.getRotationVec(1.0F);
        Vec3d pos = player.getEyePos().subtract(look.multiply(1.0));
        missileEntity.setPosition(pos.x, player.getEyePos().y, pos.z);
        player.getWorld().playSound(null, player.getBlockPos(), SoundEvents.BLOCK_DISPENSER_LAUNCH,
                SoundCategory.PLAYERS, 1.0F, 1.0F);

        player.getWorld().spawnEntity(missileEntity);
        double[] finalData = NekoPackageDataManager.getFinalData(player);
        double attackSpeed = finalData != null ? finalData[3] : 0;
        int cooldown = (int) (120 - (attackSpeed / NekoPackageDataManager.MAX_ATTACK_SPEED) * 120);
        cooldown = Math.max(20, Math.min(120, cooldown));
        player.getItemCooldownManager().set(player.getOffHandStack().getItem(), cooldown);
    }

    @Override
    public void changeOffAttackType(PlayerEntity player, MissileEntity missileEntity, float yaw) {

    }


    private @NotNull MissileEntity getMissileEntity(Entity owner, double range, double damage) {
        AttackTypes attackType = new AttackTypes(AttackTypes.AttackType.MISSILE);
        MissileEntity missile= new MissileEntity(ModEntities.MISSILE,
                owner.getWorld(),
                owner,
                attackType,
                true,
                range,
                100,
                damage*2
        );
        missile.setMissileModelType(MissileModelType.JOYEUSE);
        return  missile;
    }
}
