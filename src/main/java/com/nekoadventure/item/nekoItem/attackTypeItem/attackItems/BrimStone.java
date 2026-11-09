package com.nekoadventure.item.nekoItem.attackTypeItem.attackItems;

import com.nekoadventure.client.ShiftKeyHelper;
import com.nekoadventure.entity.ModEntities;
import com.nekoadventure.entity.missile.MissileEntity;
import com.nekoadventure.item.ModItems;
import com.nekoadventure.item.nekoItem.AbstractNekoItem;
import com.nekoadventure.item.nekoItem.attackTypeItem.AbstractNekoAttackItem;
import com.nekoadventure.item.other.NekoPackageItem;
import com.nekoadventure.network.mob.ScreenShakeNetworking;
import com.nekoadventure.other.itemApart.NekoPackageDataManager;
import com.nekoadventure.other.mobApart.AttackTypes;
import com.nekoadventure.other.mobApart.MissileModelType;
import com.nekoadventure.sound.ModSoundEvents;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.Text;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BrimStone extends AbstractNekoAttackItem {
    public BrimStone(Settings settings, double health, double strength, double speed, double attackSpeed, double attackRange, double attackMultiplier, double attackSpeedMultiplier) {
        super(settings, health, strength, speed, attackSpeed, attackRange, attackMultiplier, attackSpeedMultiplier);
    }

    @Override
    public void changeMainAttackType(PlayerEntity player, float yaw) {
        super.changeMainAttackType(player, yaw);
        World world=player.getWorld();
        int range=0;
        double damage=0;
        if (player.getOffHandStack().getItem() instanceof NekoPackageItem nekoPackage){
            damage=nekoPackage.getStrength(player);
            range= (int) nekoPackage.getAttackRange(player);
        }
        List<MissileEntity> brimStones = world.getEntitiesByClass(
                MissileEntity.class,
                player.getBoundingBox().expand(2),
                entity -> Objects.equals(entity.getOwner(), player) && AttackTypes.AttackType.BRIMSTONE.equals(entity.getAttackType()));
        if (!world.isClient) {
            if (brimStones.isEmpty()) {
                double spacing = 1.0;
                double cubeSize = 0.5;
                Vec3d spawnPos = player.getPos().add(0, 1.2, 0);
                float initialYaw = yaw;
                float pitch = player.getPitch();
                float currentYaw = initialYaw;
                MissileEntity missile = getMissileEntity(player, range, damage, 1);
                for (int length = 1; length <= range; length++) {
                    if (length > 1) {
                        if (length < range) {
                            missile = getMissileEntity(missile, range, damage, length);
                        } else {
                            missile = getMissileEntity(player, range, damage, length);
                        }
                    }
                    int t = (int) ((length - 1) / (double) range * missile.getTime());
                    float calculateYaw = getCalculateYawFromNekoItems(player,missile,t);
                    yaw = calculateYaw != 114514 ? initialYaw + calculateYaw : initialYaw;
                    Vec3d direction = getRotationVector(pitch, currentYaw);
                    spawnPos = spawnPos.add(direction.multiply(length == 1 ? spacing - cubeSize / 2 : spacing));
                    missile.setPos(spawnPos.x, spawnPos.y, spawnPos.z);
                    missile.setYaw(yaw);
                    missile.setPitch(pitch);
                    world.spawnEntity(missile);
                    currentYaw = yaw;
                }
                player.getWorld().playSound(null, player.getBlockPos(), ModSoundEvents.BRIMSTONE_ATTACK,
                        SoundCategory.PLAYERS, 0.8F, 1.0F);
                if (damage>100){
                        player.getWorld().playSound(null, player.getBlockPos(), ModSoundEvents.BRIMSTONE_FIERCE_ATTACK,
                                SoundCategory.PLAYERS, 15.0F, 1.0F);
                        if (player instanceof ServerPlayerEntity) {
                            ScreenShakeNetworking.sendToPlayer((ServerPlayerEntity) player,30,0.3f);
                        }
                }
            }
        }
        double[] finalData = NekoPackageDataManager.getFinalData(player);
        double attackSpeed = finalData != null ? finalData[3] : 0;
        int cooldown = (int) (200 - (attackSpeed / NekoPackageDataManager.MAX_ATTACK_SPEED) * 200);
        cooldown = Math.max(60, Math.min(200, cooldown));
        player.getItemCooldownManager().set(player.getOffHandStack().getItem(), cooldown);
    }
    @Override
    public void changeOffAttackType(PlayerEntity player,MissileEntity missile,float yaw) {
            World world=player.getWorld();
            double damage=1;
            double attackRange = 3;
            List<MissileEntity> brimStones = world.getEntitiesByClass(
                    MissileEntity.class,
                    missile.getBoundingBox().expand(8),
                    entity-> Objects.equals(entity.getOwner(), missile) && AttackTypes.AttackType.BRIMSTONE.equals(entity.getAttackType())
            );
        if (player.getOffHandStack().getItem() instanceof NekoPackageItem nekoPackage){
            damage=nekoPackage.getStrength(player);
            attackRange = nekoPackage.getAttackRange(player);
            ArrayList<AbstractNekoItem> attackItems=NekoPackageItem.getNekoItems(player.getOffHandStack(),true,3);
            if (attackItems.get(attackItems.size()-1).equals(ModItems.BRIMSTONE)) return;
            if (attackItems.get(attackItems.size()-1).equals(ModItems.MISSILE)) {
                // changeOffAttackType 在 tick 前段执行，age 已自增，等价于旧 aliveDuration == 62
                if (missile.age == 63&&brimStones.size()<=12) {
                    specialAttackWithMissile(player, missile, world, damage);
                }
                return;
            }
        }
            int count = 12;
            double radius = 2.0;
        if (canExecute(player, missile, 15)){
            if (Objects.equals(missile.getRealOwner(), player)) {
                if (brimStones.size()<=12) {
                    Vec3d centerPos = missile.getPos();
                    for (int i = 0; i < count; i++) {
                        double angle = (2 * Math.PI / count) * i;
                        double x = centerPos.x + radius * Math.cos(angle);
                        double z = centerPos.z + radius * Math.sin(angle);
                        double y = centerPos.y;

                        MissileEntity brimStone = getMissileEntity(missile, attackRange, damage, player);
                        brimStone.setPosition(x, y, z);
                        brimStone.setPitch(0);
                        brimStone.setYaw((float) Math.toDegrees(angle));
                        world.spawnEntity(brimStone);
                        }
                    player.getWorld().playSound(null, player.getBlockPos(),
                            ModSoundEvents.BRIMSTONE_ATTACK, SoundCategory.PLAYERS, 0.3F, 1.5F);
                }
            }
        }
    }

    private void specialAttackWithMissile(PlayerEntity player, MissileEntity missile, World world, double damage) {
        double range= Math.min(missile.getRange(), 12);
        double spacing = 1.0;
        double cubeSize = 0.5;
        Vec3d centerPos = missile.getPos();
        int directionCount = 8;
        for (int directionIndex = 0; directionIndex < directionCount; directionIndex++) {
            float initialYaw = 360.0F / directionCount * directionIndex;
            float currentYaw = initialYaw;
            Vec3d spawnPos = centerPos;
            for (int length = 1; length <= range; length++) {
                MissileEntity missileEntity = getMissileEntity(missile, range, damage, length, player);
                int t = (int) ((length - 1) / range * missileEntity.getTime());
                float calculateYaw = getCalculateYawFromNekoItems(player,missile,t);
                float segmentYaw = calculateYaw != 114514 ? initialYaw + calculateYaw : initialYaw;
                Vec3d direction = getRotationVector(0, currentYaw);
                spawnPos = spawnPos.add(direction.multiply(length == 1 ? spacing - cubeSize / 2 : spacing));
                missileEntity.setPos(spawnPos.x, spawnPos.y, spawnPos.z);
                missileEntity.setYaw(segmentYaw);
                missileEntity.setPitch(0);
                world.spawnEntity(missileEntity);
                currentYaw = segmentYaw;
            }
        }
        player.getWorld().playSound(null, player.getBlockPos(), ModSoundEvents.BRIMSTONE_ATTACK,
                SoundCategory.PLAYERS, 1.0F, 1.0F);
    }

    private float getCalculateYawFromNekoItems(PlayerEntity player,MissileEntity missile,int t) {
        if (player.getOffHandStack().getItem() instanceof NekoPackageItem) {
            List<AbstractNekoItem> attackItems = NekoPackageItem.getNekoItems(player.getOffHandStack(), true, 0);
            int count=0;
            float addYaw=0;
            for(int l=attackItems.size()-1;l>=0;l--){
                AbstractNekoItem attackItem=attackItems.get(l);
                if (attackItem.getCalculateYaw(t,missile.getTime())!=114514){
                    addYaw+=(attackItem.getCalculateYaw(t,missile.getTime()));
                    count++;
                    if (count>=3){
                        return addYaw;
                    }
                }
            }
            if (addYaw==0){
                return 114514;
            }
            return addYaw;
        }
        return 114514;
    }

    private @NotNull MissileEntity getMissileEntity(Entity owner, double range, double damage,int flag) {
        AttackTypes brimstoneType = new AttackTypes(AttackTypes.AttackType.BRIMSTONE);
        MissileEntity missile= new MissileEntity(ModEntities.MISSILE,
                owner.getWorld(),
                owner,
                brimstoneType,
                true,
                range,
                60,
                damage /2,
                flag);
        missile.setMissileModelType(MissileModelType.BRIMSTONE);
        return  missile;
    }
    private @NotNull MissileEntity getMissileEntity(Entity owner, double range, double damage,int flag,Entity realOwner) {
        AttackTypes brimstoneType = new AttackTypes(AttackTypes.AttackType.BRIMSTONE);
        MissileEntity missile= new MissileEntity(ModEntities.MISSILE,
                owner.getWorld(),
                owner,
                brimstoneType,
                true,
                range,
                60,
                damage /2,
                flag,
                realOwner);
        missile.setMissileModelType(MissileModelType.BRIMSTONE);
        return  missile;
    }
    private @NotNull MissileEntity getMissileEntity(MissileEntity owner, double range, double damage, PlayerEntity realOwner) {
        AttackTypes brimstoneType = new AttackTypes(AttackTypes.AttackType.BRIMSTONE);
        int time=owner.getTime()<=10? 3:30;
        MissileEntity missile= new MissileEntity(ModEntities.MISSILE,
                owner.getWorld(),
                owner,
                brimstoneType,
                true,
                range,
                time,
                damage /2,
                -1,
                realOwner);
        missile.setMissileModelType(MissileModelType.BRIMSTONE);
        return  missile;
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

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);
        Text moreText=Text.of("主攻击：发射一条\"射程\"长度的光柱，对接触到光柱的生物造成\"力量\"/2的伤害");
        Text moreText1=Text.of("次攻击：在弹幕周围生成一个光圈，对接触到光圈的生物造成\"力量\"/2的伤害(可触发特效)");
        if (ShiftKeyHelper.isShiftDown()) {
            tooltip.add(moreText);
            tooltip.add(moreText1);
        }
    }
}
