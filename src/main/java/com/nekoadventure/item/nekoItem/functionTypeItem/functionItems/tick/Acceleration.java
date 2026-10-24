package com.nekoadventure.item.nekoItem.functionTypeItem.functionItems.tick;

import com.nekoadventure.entity.missile.MissileEntity;
import com.nekoadventure.item.nekoItem.functionTypeItem.functionItems.AbstractNekoFunctionItem;
import com.nekoadventure.item.other.NekoPackageItem;
import com.nekoadventure.other.mobApart.AttackTypes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.Vec3d;

import java.util.List;
import java.util.Objects;

public class Acceleration extends AbstractNekoFunctionItem {

    public Acceleration(Settings settings) {
        super(settings);
        text=Text.of("发射的弹幕持续获得向前方的加速度，同时尝试将攻击弹道修正为直线");
    }

    @Override
    public void applyTickFunction(PlayerEntity player) {
        if (player.getWorld().isClient()) return;
        double range = 4;
        if (player.getOffHandStack().getItem() instanceof NekoPackageItem nekoPackageItem) {
            range = nekoPackageItem.getAttackRange(player);
        }
        double maxSpeed = 3.0;
        double accelPerTick = 0.2;
        List<MissileEntity> missileEntities = player.getWorld().getEntitiesByClass(
                MissileEntity.class,
                player.getBoundingBox().expand(range),
                e -> e.getOwner()!=null
                                && Objects.equals(e.getRealOwner(), player)
                        && e.getAttackType()!=null
                        && e.getAttackType() != AttackTypes.AttackType.BRIMSTONE
                        && e.getAttackType() != AttackTypes.AttackType.ZENITH
        );

        if (!missileEntities.isEmpty()) {
            for (MissileEntity missile : missileEntities) {
                Vec3d vel = missile.getVelocity();
                double speed = vel.length();
                if (speed < 1.0E-6 || speed >= maxSpeed) {
                    continue;
                }
                missile.addVelocity(vel.normalize().multiply(accelPerTick));
                missile.velocityModified = true;
            }
        }
    }

    @Override
    public float getCalculateYaw(int t) {
        return 0;
    }
}
