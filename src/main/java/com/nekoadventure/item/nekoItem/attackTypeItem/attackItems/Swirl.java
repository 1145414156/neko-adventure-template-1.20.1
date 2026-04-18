package com.nekoadventure.item.nekoItem.attackTypeItem.attackItems;

import com.nekoadventure.client.ShiftKeyHelper;
import com.nekoadventure.entity.missile.MissileEntity;
import com.nekoadventure.item.nekoItem.attackTypeItem.AttackTypeItem;
import com.nekoadventure.item.other.NekoPackageItem;
import com.nekoadventure.other.attackApart.AttackTypes;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class Swirl extends AttackTypeItem {
    public Swirl(Settings settings, double health, double strength, double speed, double attackSpeed, double attackRange, double attackMultiplier, double attackSpeedMultiplier, boolean isSpecific) {
        super(settings, health, strength, speed, attackSpeed, attackRange, attackMultiplier, attackSpeedMultiplier, isSpecific);
    }

    @Override
    public void changeOffAttackType(PlayerEntity player) {
        super.changeOffAttackType(player);
        double attackRange = 3;
        if (player.getOffHandStack().getItem() instanceof NekoPackageItem nekoPackage) {
            attackRange = nekoPackage.getAttackRange(player);
        }
        List<MissileEntity> missileEntities = player.getWorld().getEntitiesByClass(
                MissileEntity.class,
                player.getBoundingBox().expand(attackRange*2),
                entity -> entity.getOwner()!=null&&entity.getAttackType().equals(AttackTypes.AttackType.BULLET)
        );
        if (!missileEntities.isEmpty()) {
            for (MissileEntity missileEntity : missileEntities) {
                if (!missileEntity.getAttackType().equals(AttackTypes.AttackType.BRIMSTONE)
                        &&missileEntity.getVelocity().length()>0.1) {
                    double lateralAcceleration = 0.2;
                    Vec3d leftDir = Vec3d.fromPolar(0F, missileEntity.getYaw() + 90F).normalize();
                    missileEntity.addVelocity(
                            leftDir.x * lateralAcceleration,
                            0,
                            leftDir.z * lateralAcceleration
                    );
                    missileEntity.setYaw(missileEntity.getYaw()+10F);
                }
            }
        }
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);
        Text moreText=Text.of("次攻击：自身发射的弹幕持续获得向左方向的加速度");
        if (ShiftKeyHelper.isShiftDown()) {
            tooltip.add(moreText);
        }
    }
}
