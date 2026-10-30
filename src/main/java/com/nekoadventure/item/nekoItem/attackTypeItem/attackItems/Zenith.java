package com.nekoadventure.item.nekoItem.attackTypeItem.attackItems;

import com.nekoadventure.client.ShiftKeyHelper;
import com.nekoadventure.entity.ModEntities;
import com.nekoadventure.entity.missile.MissileEntity;
import com.nekoadventure.item.ModItems;
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
import net.minecraft.text.Text;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.stream.Collectors;

public class Zenith extends AbstractNekoAttackItem {
    public Zenith(Settings settings, double health, double strength, double speed, double attackSpeed, double attackRange, double attackMultiplier, double attackSpeedMultiplier) {
        super(settings, health, strength, speed, attackSpeed, attackRange, attackMultiplier, attackSpeedMultiplier);
    }

    @Override
    public void changeMainAttackType(PlayerEntity player, float yaw) {
        super.changeMainAttackType(player, yaw);
        double range = 2;
        double damage = 2;
        if (player.getOffHandStack().getItem() instanceof NekoPackageItem nekoPackage){
            range=nekoPackage.getAttackRange(player);
            damage=nekoPackage.getStrength(player);
        }
        Vec3d look = getRotationVector(player.getPitch(),yaw);
        Vec3d horizontalLook = new Vec3d(look.x, 0, look.z).normalize();
        List<LivingEntity> entities=getEntitiesOnLine(player,player.getEyePos(),horizontalLook,range);
        if (!entities.isEmpty()){
           double distance=entities.size()>1? entities.get(player.getWorld().random.nextInt(entities.size()-1)).
                   distanceTo(player):entities.get(0).distanceTo(player)-0.5;
           range=Math.min(range,distance);
        }
        range=Math.min(15,range)+(!entities.isEmpty()?0.0F:player.getWorld().getRandom().nextInt(10)-4);
        MissileEntity missileEntity = getMissileEntity(player, range, damage);
        missileEntity.setYaw(yaw+(!entities.isEmpty()?0.0F:player.getWorld().random.nextInt(40)-19));
        missileEntity.setPitch(0);
        player.getWorld().spawnEntity(missileEntity);

        if (player.getOffHandStack().getItem() instanceof NekoPackageItem nekoPackage) {
            if(nekoPackage.isHaveNekoItem(player.getOffHandStack(), ModItems.EXPOSED_WIRE)){
                specialAttackWithExportWire(player, range, damage,yaw);
            }
        }

        double[] finalData = NekoPackageDataManager.getFinalData(player);
        double attackSpeed = finalData != null ? finalData[3] : 0;
        int cooldown = (int) (60 - (attackSpeed / NekoPackageDataManager.MAX_ATTACK_SPEED) * 120);
        cooldown = Math.max(4, Math.min(60, cooldown));
        player.getItemCooldownManager().set(player.getOffHandStack().getItem(), cooldown);
    }

    private void specialAttackWithExportWire(PlayerEntity player, double range, double damage,float yaw) {
        for (int i=-1; i<=1; i+=2) {
            Vec3d look = player.getRotationVec(1.0F);
            Vec3d horizontalLook = new Vec3d(look.x, 0, look.z).normalize();
            List<LivingEntity> entities=getEntitiesOnLine(player,player.getEyePos(),horizontalLook,range);
            if (!entities.isEmpty()){
                double distance=entities.size()>1? entities.get(player.getWorld().random.nextInt(entities.size()-1)).
                        distanceTo(player):entities.get(0).distanceTo(player)-0.5;
                range=Math.min(range,distance);
            }
            range=Math.min(15,range)+(!entities.isEmpty()?0.0F:player.getWorld().getRandom().nextInt(10)-4);
            MissileEntity missileEntity = getMissileEntity(player, range, damage);
            missileEntity.setYaw(yaw+(!entities.isEmpty()?0.0F:player.getWorld().random.nextInt(40)-19+20*i));
            missileEntity.setPitch(0);
            player.getWorld().spawnEntity(missileEntity);
        }
    }

    @Override
    public void changeOffAttackType(PlayerEntity player, float yaw) {
        super.changeOffAttackType(player, yaw);
        if (canExecute(player, 10)) {
            double range = 2;
            double damage = 2;
            List<MissileEntity> missileEntities = player.getWorld().getEntitiesByClass(
                    MissileEntity.class,
                    player.getBoundingBox().expand(range*2),
                    entity -> entity.getOwner()!=null&&entity.getOwner().equals(player)
            );
            if (!missileEntities.isEmpty()) {
                MissileEntity missile=missileEntities.get(player.getWorld().random.nextInt(missileEntities.size()));
                if (player.getOffHandStack().getItem() instanceof NekoPackageItem nekoPackage){
                    range=nekoPackage.getAttackRange(player);
                    damage=nekoPackage.getStrength(player);
                }
                Vec3d look = missile.getRotationVec(1.0F);
                Vec3d horizontalLook = new Vec3d(look.x, 0, look.z).normalize();
                List<LivingEntity> entities=getEntitiesOnLine(missile,missile.getEyePos(),horizontalLook,range);
                if (!entities.isEmpty()){
                    double distance=entities.size()>1? entities.get(missile.getWorld().random.nextInt(entities.size()-1)).
                            distanceTo(missile):entities.get(0).distanceTo(missile)-0.5;
                    range=Math.min(range,distance);
                }
                range=Math.min(15,range)+(!entities.isEmpty()?0.0F:player.getWorld().getRandom().nextInt(10)-4);
                MissileEntity missileEntity = getMissileEntity(missile, range, damage);
                missileEntity.setYaw(missile.getYaw()+(!entities.isEmpty()?0.0F:player.getWorld().random.nextInt(40)-19));
                missileEntity.setPitch(0);
                player.getWorld().spawnEntity(missileEntity);
            }
        }
    }

    private @NotNull MissileEntity getMissileEntity(Entity owner, double range, double damage) {
        AttackTypes bulletType = new AttackTypes(owner instanceof PlayerEntity?AttackTypes.AttackType.ZENITH:AttackTypes.AttackType.ELLIPSE);
        MissileEntity missileEntity = new MissileEntity(ModEntities.MISSILE,
                owner.getWorld(),
                owner,
                bulletType,
                true,
                range,
                15+owner.getWorld().getRandom().nextInt(10),
                damage,
                owner.getWorld().random.nextInt(21)
        );
        missileEntity.setMissileModelType(MissileModelType.ZENITH);
        missileEntity.setPos(owner.getX(), owner.getEyeY()-0.3, owner.getZ());
        return missileEntity;
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);
        Text moreText=Text.of("主攻击：朝前方发射一枚回形旋转的剑，剑会对接触到的生物造成一次\"力量/2\"伤害");
        Text moreText1=Text.of("次攻击：弹幕会朝面前发射一次主攻击对应的弹幕");
        if (ShiftKeyHelper.isShiftDown()) {
            tooltip.add(moreText);
            tooltip.add(moreText1);
        }
    }

    private List<LivingEntity> getEntitiesOnLine(Entity e,
                                                 Vec3d start,
                                                 Vec3d horizontalDir,
                                                 double maxDistance) {
        Vec3d startXZ = new Vec3d(start.x, 0, start.z);
        Vec3d dir = horizontalDir.normalize();
        Box searchBox = new Box(
                startXZ.x - maxDistance, start.y - 1, startXZ.z - maxDistance,
                startXZ.x + maxDistance, start.y + 2, startXZ.z + maxDistance
        );

        return e.getWorld().getEntitiesByClass(
                LivingEntity.class,
                searchBox,
                entity -> entity != e && entity.isAlive()
        ).stream().filter(entity -> {
            Vec3d entityPos = entity.getPos();
            Vec3d entityXZ = new Vec3d(entityPos.x, 0, entityPos.z);
            Vec3d relative = entityXZ.subtract(startXZ);
            double projection = relative.dotProduct(dir);
            if (projection < 0 || projection > maxDistance) {
                return false;
            }
            Vec3d closestPoint = startXZ.add(dir.multiply(projection));
            return entityXZ.distanceTo(closestPoint) <= 1.2;
        }).collect(Collectors.toList());
    }

    protected final Vec3d getRotationVector(float pitch, float yaw) {
        float f = pitch * (float) (Math.PI / 180.0);
        float g = -yaw * (float) (Math.PI / 180.0);
        float h = MathHelper.cos(g);
        float i = MathHelper.sin(g);
        float j = MathHelper.cos(f);
        float k = MathHelper.sin(f);
        return new Vec3d(i * j, -k, h * j);
    }
}
