package com.nekoadventure.item.nekoItem.functionTypeItem.functionItems.damaged;

import com.nekoadventure.entity.ModEntities;
import com.nekoadventure.entity.missile.MissileEntity;
import com.nekoadventure.item.nekoItem.functionTypeItem.functionItems.NekoFunctionItem;
import com.nekoadventure.item.other.NekoPackageItem;
import com.nekoadventure.other.attackApart.AttackTypes;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

import java.util.Comparator;
import java.util.List;

public class RedScarf extends NekoFunctionItem {
    public RedScarf(Settings settings) {
        super(settings);
        text= Text.of("当附近有生物死亡时，在其位置召唤一个弹幕，锁定最近的生物;同时当玩家受到伤害时，对造成伤害者造成一次\"力量\"/2的伤害");
    }

    @Override
    public boolean applyDamagedFunctionItem(PlayerEntity player) {
        double damage = 2;
        if (player.getOffHandStack().getItem() instanceof NekoPackageItem nekoPackage){
            damage=nekoPackage.getStrength(player)/2;
        }
        LivingEntity attacker=player.getAttacker();
        if (attacker != null) {
                attacker.damage(player.getDamageSources().magic(), (float) damage);
        }
        return false;
    }

    @Override
    public void applyTickFunctionItem(PlayerEntity player) {
        World world=player.getWorld();
        double range = 2;
        double damage = 2;
        if (player.getOffHandStack().getItem() instanceof NekoPackageItem nekoPackage){
            range= nekoPackage.getAttackRange(player);
            damage=nekoPackage.getStrength(player);
        }
        if (world.isClient){return;}
        List<LivingEntity> entities = world.getEntitiesByClass(
                LivingEntity.class,
                player.getBoundingBox().expand(range),
                LivingEntity::isDead
        );
        if (!entities.isEmpty()) {
            for (LivingEntity entity : entities) {
                MissileEntity missile=getMissileEntity(player,range,damage);
                LivingEntity target=getNearestEntity(entity,range);
                if (target!=null) {
                    Vec3d direction = target.getEyePos().subtract(entity.getEyePos()).normalize();
                    double yaw = Math.toDegrees(Math.atan2(-direction.x, direction.z));
                    double pitch = Math.toDegrees(Math.atan2(-direction.y, Math.sqrt(direction.x * direction.x + direction.z * direction.z)));
                    missile.setYaw((float) yaw);
                    missile.setPitch((float) pitch);
                    missile.setPos(entity.getX(), entity.getEyeY(), entity.getZ());
                    world.spawnEntity(missile);
                    entity.remove(Entity.RemovalReason.KILLED);
                }
            }
        }
    }
    private @NotNull MissileEntity getMissileEntity(PlayerEntity player, double range, double damage) {
        AttackTypes bulletType = new AttackTypes(AttackTypes.AttackType.BULLET);
        return new MissileEntity(ModEntities.MISSILE,
                player.getWorld(),
                player,
                bulletType,
                false,
                range,
                60,
                damage);
    }

    private LivingEntity getNearestEntity(LivingEntity entity, double range) {
        return entity.getWorld().getEntitiesByClass(
                LivingEntity.class,
                entity.getBoundingBox().expand(range+3),
                z -> z.isAlive()&&z instanceof LivingEntity && !(z instanceof ArmorStandEntity)&& !(z instanceof PlayerEntity)
        ).stream().min(Comparator.comparingDouble(entity::squaredDistanceTo)).orElse(null);
    }
}
