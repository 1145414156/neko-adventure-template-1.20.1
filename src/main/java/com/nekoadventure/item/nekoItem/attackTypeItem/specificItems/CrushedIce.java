package com.nekoadventure.item.nekoItem.attackTypeItem.specificItems;

import com.nekoadventure.client.ShiftKeyHelper;
import com.nekoadventure.entity.ModEntities;
import com.nekoadventure.entity.missile.MissileEntity;
import com.nekoadventure.entity.missile.MissileModelType;
import com.nekoadventure.item.nekoItem.attackTypeItem.NekoAttackTypeItem;
import com.nekoadventure.item.other.NekoPackageItem;
import com.nekoadventure.other.attackApart.AttackTypes;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Random;

public class CrushedIce extends NekoAttackTypeItem {
    public CrushedIce(Settings settings, double health, double strength, double speed, double attackSpeed, double attackRange, double attackMultiplier, double attackSpeedMultiplier, boolean isSpecific) {
        super(settings, health, strength, speed, attackSpeed, attackRange, attackMultiplier, attackSpeedMultiplier, isSpecific);
    }

    @Override
    public void applySpecificItem(PlayerEntity player, LivingEntity target) {
        Random rand = new Random();
        if (rand.nextInt(100)<10){
            double strength = 1;
            if (player.getOffHandStack().getItem() instanceof NekoPackageItem nekoPackage) {
                strength = nekoPackage.getStrength(player);
            }
            for (int i=0;i<4;i++){
                MissileEntity missile=getMissileEntity(player, strength);
                missile.setYaw(90*i);
                missile.setPitch(0);
                missile.setPosition(player.getEyePos());
                player.getWorld().spawnEntity(missile);
            }
        }
    }

    private @NotNull MissileEntity getMissileEntity(PlayerEntity player, double damage) {
        AttackTypes bulletType = new AttackTypes(AttackTypes.AttackType.BULLET);
        MissileEntity missileEntity=new MissileEntity(ModEntities.MISSILE,
                player.getWorld(),
                player,
                bulletType,
                false,
                16,
                60,
                damage);
        missileEntity.setMissileModelType(MissileModelType.BULLET);
        missileEntity.setPos(player.getX(), player.getEyeY(), player.getZ());
        missileEntity.setYaw(player.getYaw());
        missileEntity.setPitch(player.getPitch());
        return missileEntity;
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);
        Text moreText=Text.of("造成伤害时，有1/10概率在自身4个方向生成弹幕");
        if (ShiftKeyHelper.isShiftDown()) {
            tooltip.add(moreText);
        }
    }
}
