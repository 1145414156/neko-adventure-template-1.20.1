package com.nekoadventure.item.nekoItem.attackTypeItem.specificItems;

import com.nekoadventure.client.ShiftKeyHelper;
import com.nekoadventure.item.nekoItem.attackTypeItem.NekoAttackTypeItem;
import com.nekoadventure.item.other.NekoPackageItem;
import com.nekoadventure.other.itemApart.NekoPackageDataManager;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BrokenCrown extends NekoAttackTypeItem {


    public BrokenCrown(Settings settings, double health, double strength, double speed, double attackSpeed, double attackRange, double attackMultiplier, double attackSpeedMultiplier, boolean isSpecific) {
        super(settings, health, strength, speed, attackSpeed, attackRange, attackMultiplier, attackSpeedMultiplier, isSpecific);

    }

    @Override
    public void applySpecificItem(PlayerEntity player, LivingEntity target) {
        if (player.getWorld().isClient()) return;
        if (!(player.getOffHandStack().getItem() instanceof NekoPackageItem nekoPackage)) return;
        int age=player.age;
        int age1=NekoPackageDataManager.getItemIntState(player,"broken_crown");
        if (age-age1>=100){
            nekoPackage.addTickData(player,new double[]{0, 0, 0, 0, 0, 0.5, 0.5});
            NekoPackageDataManager.setItemIntState(player,"broken_crown",age);
        }
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);
        Text moreText=Text.of("造成伤害时，增加自身0.5力量乘区与0.5射速乘区(每5秒触发一次)");
        if (ShiftKeyHelper.isShiftDown()) {
            tooltip.add(moreText);
        }
    }
}
