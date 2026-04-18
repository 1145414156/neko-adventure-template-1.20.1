package com.nekoadventure.item.nekoItem.attackTypeItem.attackItems;

import com.nekoadventure.client.ShiftKeyHelper;
import com.nekoadventure.entity.ModEntities;
import com.nekoadventure.entity.missile.MissileEntity;
import com.nekoadventure.entity.missile.MissileModelType;
import com.nekoadventure.item.nekoItem.attackTypeItem.AttackTypeItem;
import com.nekoadventure.item.other.NekoPackageItem;
import com.nekoadventure.network.NekoPackageDataManager;
import com.nekoadventure.network.ScreenShakeNetworking;
import com.nekoadventure.other.attackApart.AttackTypes;
import com.nekoadventure.sound.ModSoundEvents;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.Text;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BrimStone extends AttackTypeItem {
    public BrimStone(Settings settings, double health, double strength, double speed, double attackSpeed, double attackRange, double attackMultiplier, double attackSpeedMultiplier, boolean isSpecific) {
        super(settings, health, strength, speed, attackSpeed, attackRange, attackMultiplier, attackSpeedMultiplier, isSpecific);
    }

    @Override
    public void changeMainAttackType(PlayerEntity player) {
        super.changeMainAttackType(player);
        World world=player.getWorld();
        int range=0;
        double damage=0;
        double attackRange = 3;
        if (player.getOffHandStack().getItem() instanceof NekoPackageItem nekoPackage){
            damage=nekoPackage.getStrength(player);
            range= (int) nekoPackage.getAttackRange(player);
            attackRange = nekoPackage.getAttackRange(player);
        }
        List<MissileEntity> brimStones = world.getEntitiesByClass(
                MissileEntity.class,
                player.getBoundingBox().expand(attackRange*4),
                entity -> entity.getOwner().equals(player)&&entity.getAttackType().equals(AttackTypes.AttackType.BRIMSTONE));


        if (!world.isClient) {
            if (brimStones.isEmpty()) {
                double spacing = 1.0;
                double cubeSize = 0.5;

                for (int length = 1; length < range; length++) {
                    Vec3d playerPos = player.getPos().add(0,1.25f,0);
                    Vec3d lookDirection = player.getRotationVec(1.0F);

                    AttackTypes brimstoneType = new AttackTypes(AttackTypes.AttackType.BRIMSTONE);
                    MissileEntity missile = new MissileEntity(ModEntities.MISSILE,
                            player.getWorld(),
                            player,
                            brimstoneType,
                            true,
                            range,
                            60,
                            damage/2);
                    missile.setMissileModelType(MissileModelType.BRIMSTONE);
                    Vec3d spawnPos = playerPos.add(lookDirection.multiply(length * spacing - cubeSize / 2));
                    missile.setPos(spawnPos.x, spawnPos.y, spawnPos.z);

                    missile.setYaw(player.getYaw());
                    missile.setPitch(player.getPitch());

                    world.spawnEntity(missile);
                }
                player.getWorld().playSound(null, player.getBlockPos(), ModSoundEvents.BRIMSTONE_ATTACK,
                        SoundCategory.PLAYERS, 0.8F, 1.0F);
                if (damage>30){
                        player.getWorld().playSound(null, player.getBlockPos(), ModSoundEvents.BRIMSTONE_FIERCE_ATTACK,
                                SoundCategory.PLAYERS, 20.0F, 1.0F);
                        if (player instanceof ServerPlayerEntity) {
                            ScreenShakeNetworking.sendToPlayer((ServerPlayerEntity) player,60,0.1f);
                        }
                }
            }
        }
        double[] finalData = NekoPackageDataManager.getFinalData(player);
        double attackSpeed = finalData != null ? finalData[3] : 0;
        int cooldown = (int) (200 - (attackSpeed / 12.0) * 200);
        cooldown = Math.max(60, Math.min(200, cooldown));
        player.getItemCooldownManager().set(player.getOffHandStack().getItem(), cooldown);
    }

    @Override
    public void changeOffAttackType(PlayerEntity player) {
        super.changeOffAttackType(player);
        World world=player.getWorld();
        double damage=1;
        double attackRange = 3;
        if (player.getOffHandStack().getItem() instanceof NekoPackageItem nekoPackage){
            damage=nekoPackage.getStrength(player);
            attackRange = nekoPackage.getAttackRange(player);
        }
        List<MissileEntity> missileEntities = world.getEntitiesByClass(
                MissileEntity.class,
                player.getBoundingBox().expand(attackRange*8),
                entity -> entity.getOwner().equals(player)
        );
            int count = 16;
            double radius = 2.0;
            if (!missileEntities.isEmpty()){
                for (MissileEntity missile : missileEntities) {
                    List<MissileEntity> brimStones = world.getEntitiesByClass(
                            MissileEntity.class,
                            missile.getBoundingBox().expand(attackRange*4),
                            entity->entity.getOwner().equals(missile)&&entity.getAttackType().equals(AttackTypes.AttackType.BRIMSTONE)
                    );
                    if (brimStones.isEmpty()) {
                        player.getWorld().playSound(null, player.getBlockPos(), ModSoundEvents.BRIMSTONE_ATTACK,
                                SoundCategory.PLAYERS, 0.3F, 1.0F);
                        Vec3d centerPos = missile.getPos();
                        for (int i = 0; i < count; i++) {
                            double angle = (2 * Math.PI / count) * i;
                            double x = centerPos.x + radius * Math.cos(angle);
                            double z = centerPos.z + radius * Math.sin(angle);
                            double y = centerPos.y;
                            MissileEntity brimStone = getMissileEntity(missile, damage);
                            brimStone.setPosition(x, y, z);
                            brimStone.setPitch(missile.getPitch());
                            brimStone.setYaw(missile.getYaw());
                            world.spawnEntity(brimStone);
                        }
                    }
                }
            }
    }

    private @NotNull MissileEntity getMissileEntity(MissileEntity missile, double damage) {
        int time=missile.getTime();
        if (time<60){
            time=60;
        }
        AttackTypes brimstoneType = new AttackTypes(AttackTypes.AttackType.BRIMSTONE);
        MissileEntity brimStone = new MissileEntity(ModEntities.MISSILE,
                missile.getWorld(),
                missile,
                brimstoneType,
                true,
                3,
                time,
                damage/2);
        brimStone.setMissileModelType(MissileModelType.BRIMSTONE);
        return brimStone;
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
