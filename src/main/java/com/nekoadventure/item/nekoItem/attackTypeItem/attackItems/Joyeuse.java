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
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Joyeuse extends NekoAttackTypeItem {
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

        Set<Integer> existingFlags = player.getWorld()
                .getEntitiesByClass(
                        MissileEntity.class,
                        player.getBoundingBox().expand(2.0),
                        e -> e.getAliveDuration() <= e.getTime() / 2
                                && e.getOwner() != null
                                && e.getOwner().equals(player)
                )
                .stream()
                .map(MissileEntity::getFlag)
                .collect(Collectors.toSet());

        List<Integer> available = new ArrayList<>();
        for (int i = 0; i <= 5; i++) {
            if (!existingFlags.contains(i)) {
                available.add(i);
            }
        }
        int flag = available.isEmpty()
                ? -1
                : available.get(player.getWorld().random.nextInt(available.size()));

        if (flag>-1) {
            MissileEntity missileEntity = getMissileEntity(player, range, damage,bulletType,flag);
            missileEntity.setPos(player.getX(), player.getEyeY(), player.getZ());
            missileEntity.setYaw(player.getYaw());
            missileEntity.setPitch(player.getPitch());
            player.getWorld().spawnEntity(missileEntity);
            double[] finalData = NekoPackageDataManager.getFinalData(player);
            System.out.println("flag="+flag);
            double attackSpeed = finalData != null ? finalData[3] : 0;
            int cooldown = (int) (60 - (attackSpeed / NekoPackageDataManager.MAX_ATTACK_SPEED) * 60);
            cooldown = Math.max(4, Math.min(60, cooldown));
            player.getItemCooldownManager().set(player.getOffHandStack().getItem(), cooldown);
        }
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
                    MissileEntity missileEntity = getMissileEntity(missile, range, damage,bulletType);
                    missileEntity.setPos(player.getX(), player.getEyeY(), player.getZ());
                    missileEntity.setYaw(player.getYaw());
                    missileEntity.setPitch(player.getPitch());
                    player.getWorld().spawnEntity(missileEntity);
            }
        }
    }

    private @NotNull MissileEntity getMissileEntity(Entity owner, double range, double damage, AttackTypes attackTypes) {
        MissileEntity missileEntity=new MissileEntity(ModEntities.MISSILE,
                owner.getWorld(),
                owner,
                attackTypes,
                true,
                range,
                30,
                damage);
        missileEntity.setMissileModelType(MissileModelType.JOYEUSE);
        return missileEntity;
    }

    private @NotNull MissileEntity getMissileEntity(Entity owner, double range, double damage, AttackTypes attackTypes, int flag) {
        MissileEntity missileEntity=new MissileEntity(ModEntities.MISSILE,
                owner.getWorld(),
                owner,
                attackTypes,
                true,
                range,
                120,
                damage,
                flag);
        missileEntity.setMissileModelType(MissileModelType.JOYEUSE);
        return missileEntity;
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);
        Text moreText=Text.of("主攻击：生成一个会跟随玩家移动的弹幕，过一段时间后会自动朝向最近的生物发射，接触会造成\"力量\"伤害的弹幕");
        Text moreText1=Text.of("次攻击：当检测到周围有自身发射的弹幕时，在视野前持续生成不会被实体阻拦的\"力量\"伤害的弹幕");
        if (ShiftKeyHelper.isShiftDown()) {
            tooltip.add(moreText);
            tooltip.add(moreText1);
        }
    }

}
