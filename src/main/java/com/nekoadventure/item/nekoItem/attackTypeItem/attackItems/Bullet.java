package com.nekoadventure.item.nekoItem.attackTypeItem.attackItems;

import com.nekoadventure.client.ShiftKeyHelper;
import com.nekoadventure.entity.ModEntities;
import com.nekoadventure.entity.missile.MissileEntity;
import com.nekoadventure.item.nekoItem.attackTypeItem.AbstractNekoAttackItem;
import com.nekoadventure.item.other.NekoPackageItem;
import com.nekoadventure.other.itemApart.NekoPackageDataManager;
import com.nekoadventure.other.mobApart.AttackTypes;
import com.nekoadventure.other.mobApart.MissileModelType;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class Bullet extends AbstractNekoAttackItem {

    public Bullet(Settings settings, double health, double strength, double speed, double attackSpeed, double attackRange, double attackMultiplier, double attackSpeedMultiplier) {
        super(settings, health, strength, speed, attackSpeed, attackRange, attackMultiplier, attackSpeedMultiplier);
    }

    @Override
    public void changeMainAttackType(PlayerEntity player, float yaw) {
        super.changeMainAttackType(player, yaw);
        double range = 2;
        double damage = 2;
        if (player.getOffHandStack().getItem() instanceof NekoPackageItem nekoPackage){
            range= nekoPackage.getAttackRange(player);
            damage=nekoPackage.getStrength(player);
        }
        MissileEntity missileEntity = getMissileEntity(player, range, damage);
        missileEntity.setYaw(yaw);
        player.getWorld().playSound(null, player.getBlockPos(), SoundEvents.BLOCK_DISPENSER_LAUNCH,
                SoundCategory.PLAYERS, 1.0F, 1.0F);
        player.getWorld().spawnEntity(missileEntity);

        double[] finalData = NekoPackageDataManager.getFinalData(player);
        double attackSpeed = finalData != null ? finalData[3] : 0;
        int cooldown = (int) (60 - (attackSpeed / NekoPackageDataManager.MAX_ATTACK_SPEED) * 60);
        cooldown = Math.max(1, Math.min(60, cooldown));
        player.getItemCooldownManager().set(player.getOffHandStack().getItem(), cooldown);
    }




    private @NotNull MissileEntity getMissileEntity(PlayerEntity player, double range, double damage) {
        AttackTypes bulletType = new AttackTypes(AttackTypes.AttackType.BULLET);
        MissileEntity missileEntity=new MissileEntity(ModEntities.MISSILE,
                player.getWorld(),
                player,
                bulletType,
                false,
                range,
                60,
                damage);
        missileEntity.setMissileModelType(MissileModelType.BULLET);
        missileEntity.setPos(player.getX(), player.getEyeY(), player.getZ());
        missileEntity.setPitch(player.getPitch());
        return missileEntity;
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);
        Text moreText=Text.of("主攻击：向前方发射一枚\"力量\"伤害的弹幕");
        if (ShiftKeyHelper.isShiftDown()) {
            tooltip.add(moreText);
        }
    }


}
