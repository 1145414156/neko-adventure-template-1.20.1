package com.nekoadventure.item.nekoItem.attackTypeItem.attackItems;

import com.nekoadventure.client.ShiftKeyHelper;
import com.nekoadventure.entity.ModEntities;
import com.nekoadventure.entity.missile.MissileEntity;
import com.nekoadventure.entity.missile.MissileModelType;
import com.nekoadventure.item.nekoItem.attackTypeItem.AttackTypeItem;
import com.nekoadventure.item.other.NekoPackageItem;
import com.nekoadventure.network.NekoPackageDataManager;
import com.nekoadventure.other.attackApart.AttackTypes;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class Joyeuse extends AttackTypeItem {
    public Joyeuse(Settings settings, double health, double strength, double speed, double attackSpeed, double attackRange, double attackMultiplier, double attackSpeedMultiplier, boolean isSpecific) {
        super(settings, health, strength, speed, attackSpeed, attackRange, attackMultiplier, attackSpeedMultiplier, isSpecific);
    }

    @Override
    public void changeMainAttackType(PlayerEntity player) {
        super.changeMainAttackType(player);
        double range = 1;
        double damage = 1;
        if (player.getOffHandStack().getItem() instanceof NekoPackageItem nekoPackage){
            range= nekoPackage.getAttackRange(player);
            damage=nekoPackage.getStrength(player);
        }
        AttackTypes bulletType = new AttackTypes(AttackTypes.AttackType.JOYEUSE);
        MissileEntity missileEntity = getMissileEntity(player, range,60,damage,bulletType);
        missileEntity.setPos(player.getX(), player.getEyeY(), player.getZ());
        missileEntity.setYaw(player.getYaw());
        missileEntity.setPitch(player.getPitch());
        player.getWorld().spawnEntity(missileEntity);
        double[] finalData = NekoPackageDataManager.getFinalData(player);
        double attackSpeed = finalData != null ? finalData[3] : 0;
        int cooldown = (int) (60 - (attackSpeed / 12.0) * 60);
        cooldown = Math.max(4, Math.min(60, cooldown));
        player.getItemCooldownManager().set(player.getOffHandStack().getItem(), cooldown);
    }

    @Override
    public void changeOffAttackType(PlayerEntity player) {
        super.changeOffAttackType(player);
        if (canExecute(player,10)){
            double range = 1;
            double damage = 1;
            if (player.getOffHandStack().getItem() instanceof NekoPackageItem nekoPackage){
                range= nekoPackage.getAttackRange(player);
                damage=nekoPackage.getStrength(player);
            }
            MissileEntity missile=player.getWorld().getEntitiesByClass(
                    MissileEntity.class,
                    player.getBoundingBox().expand(range*4),
                    e -> e.getOwner()!=null&&e.getOwner().equals(player)
            ).stream().limit(1).findFirst().orElse(null);
            if (missile!=null) {
                AttackTypes bulletType = new AttackTypes(AttackTypes.AttackType.BULLET);
                    MissileEntity missileEntity = getMissileEntity(missile, range,30,damage,bulletType);
                    missileEntity.setPos(player.getX(), player.getEyeY(), player.getZ());
                    missileEntity.setYaw(player.getYaw());
                    missileEntity.setPitch(player.getPitch());
                    player.getWorld().spawnEntity(missileEntity);
            }
        }
    }

    private @NotNull MissileEntity getMissileEntity(Entity owner, double range, int time , double damage, AttackTypes attackTypes) {
        MissileEntity missileEntity=new MissileEntity(ModEntities.MISSILE,
                owner.getWorld(),
                owner,
                attackTypes,
                true,
                range,
                time,
                damage);
        missileEntity.setMissileModelType(MissileModelType.JOYEUSE);
        return missileEntity;
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);
        Text moreText=Text.of("主攻击：生成一个持续前进的弹幕，撞到生物时消失并在停留在原地生成一个接触会造成\"力量\"x2伤害的弹幕");
        Text moreText1=Text.of("次攻击：当检测到周围有自身发射的弹幕时，在视野前持续生成不会被实体阻拦的\"力量\"伤害的弹幕");
        if (ShiftKeyHelper.isShiftDown()) {
            tooltip.add(moreText);
            tooltip.add(moreText1);
        }
    }

}
