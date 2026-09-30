package com.nekoadventure.item.nekoItem.attackTypeItem.attackItems;

import com.nekoadventure.client.ShiftKeyHelper;
import com.nekoadventure.entity.ModEntities;
import com.nekoadventure.entity.missile.MissileEntity;
import com.nekoadventure.entity.missile.MissileModelType;
import com.nekoadventure.item.nekoItem.attackTypeItem.NekoAttackTypeItem;
import com.nekoadventure.item.other.NekoPackageItem;
import com.nekoadventure.other.attackApart.AttackTypes;
import com.nekoadventure.other.itemApart.NekoPackageDataManager;
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

import java.util.Comparator;
import java.util.List;

public class Boomerang extends NekoAttackTypeItem {
    public Boomerang(Settings settings, double health, double strength, double speed, double attackSpeed, double attackRange, double attackMultiplier, double attackSpeedMultiplier, boolean isSpecific) {
        super(settings, health, strength, speed, attackSpeed, attackRange, attackMultiplier, attackSpeedMultiplier, isSpecific);
    }

    @Override
    public void changeMainAttackType(PlayerEntity player) {
        super.changeMainAttackType(player);
        double range = 2;
        double damage = 2;
        if (player.getOffHandStack().getItem() instanceof NekoPackageItem nekoPackage){
            range= nekoPackage.getAttackRange(player);
            damage=nekoPackage.getStrength(player);
        }
        MissileEntity missileEntity = getMissileEntity(player, range, damage);
        player.getWorld().playSound(null, player.getBlockPos(), SoundEvents.BLOCK_DISPENSER_LAUNCH,
                SoundCategory.PLAYERS, 1.0F, 1.0F);
        player.getWorld().spawnEntity(missileEntity);
        double[] finalData = NekoPackageDataManager.getFinalData(player);
        double attackSpeed = finalData != null ? finalData[3] : 0;
        int cooldown = (int) (100 - (attackSpeed / NekoPackageDataManager.MAX_ATTACK_SPEED) * 100);
        cooldown = Math.max(10, Math.min(100, cooldown));
        player.getItemCooldownManager().set(player.getOffHandStack().getItem(), cooldown);
    }

    @Override
    public void changeOffAttackType(PlayerEntity player) {
        super.changeOffAttackType(player);
        if (canExecute(player,10)){
            double range = 2;
            double damage = 2;
            if (player.getOffHandStack().getItem() instanceof NekoPackageItem nekoPackage) {
                range = nekoPackage.getAttackRange(player);
                damage = nekoPackage.getStrength(player);
            }
            List<MissileEntity> missileEntities = player.getWorld().getEntitiesByClass(
                    MissileEntity.class,
                    player.getBoundingBox().expand(range*4),
                    entity -> entity.getOwner()!=null&&entity.getOwner().equals(player)
            );
            if (!missileEntities.isEmpty()) {
                for (MissileEntity missileEntity : missileEntities) {
                    LivingEntity target = missileEntity.getWorld().getEntitiesByClass(
                            LivingEntity.class,
                            player.getBoundingBox().expand(range*4),
                            entity -> entity.isAlive() && entity != player
                    ).stream().min(Comparator.comparingDouble(entity -> entity.distanceTo(missileEntity))).orElse(null);
                    if (target != null) {
                        Vec3d pos = missileEntity.getPos();
                        MissileEntity offMissile = getMissileEntity(missileEntity, range, damage);
                        Vec3d direction = target.getEyePos().subtract(pos).normalize();
                        double yaw = Math.toDegrees(Math.atan2(-direction.x, direction.z));
                        double pitch = Math.toDegrees(Math.atan2(-direction.y, Math.sqrt(direction.x * direction.x + direction.z * direction.z)));
                        offMissile.setPosition(pos);
                        offMissile.setYaw((float) yaw);
                        offMissile.setPitch((float) pitch);
                        missileEntity.getWorld().spawnEntity(offMissile);
                    }
                }
            }
        }
    }

    private @NotNull MissileEntity getMissileEntity(Entity owner, double range, double damage) {
        AttackTypes bulletType = new AttackTypes(AttackTypes.AttackType.BOOMERANG);
        MissileEntity missileEntity=new MissileEntity(ModEntities.MISSILE,
                owner.getWorld(),
                owner,
                bulletType,
                true,
                range,
                40,
                damage);
        missileEntity.setMissileModelType(MissileModelType.BOOMERANG);
        missileEntity.setPos(owner.getX(), owner.getEyeY(), owner.getZ());
        missileEntity.setYaw(owner.getYaw());
        missileEntity.setPitch(owner.getPitch());
        return missileEntity;
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);
        Text moreText=Text.of("主攻击：发射一枚会回旋的可以穿透生物的\"力量\"伤害的弹幕");
        Text moreText1=Text.of("次攻击：发射的弹幕持续向距离最近的目标发射会回旋的\"力量\"伤害的弹幕");
        if (ShiftKeyHelper.isShiftDown()) {
            tooltip.add(moreText);
            tooltip.add(moreText1);
        }
    }
}
