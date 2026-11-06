package com.nekoadventure.item.nekoItem.attackTypeItem.attackItems;

import com.nekoadventure.client.ShiftKeyHelper;
import com.nekoadventure.item.nekoItem.AbstractNekoItem;
import com.nekoadventure.item.nekoItem.attackTypeItem.AbstractNekoAttackItem;
import com.nekoadventure.item.other.NekoPackageItem;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class ThirdAttacks extends AbstractNekoAttackItem {
    public ThirdAttacks(Settings settings, double health, double strength, double speed, double attackSpeed, double attackRange, double attackMultiplier, double attackSpeedMultiplier) {
        super(settings, health, strength, speed, attackSpeed, attackRange, attackMultiplier, attackSpeedMultiplier);
    }

    @Override
    public void changeMainAttackType(PlayerEntity player, float yaw) {
        if (player.getOffHandStack().getItem() instanceof NekoPackageItem){
            ArrayList<AbstractNekoItem> attackItems=NekoPackageItem.getNekoItems(player.getOffHandStack(),true,3);
            if (attackItems.get(attackItems.size()-2) instanceof AbstractNekoAttackItem abstractNekoAttackItem) {
                if (attackItems.size() < 2) {
                    return;
                }
                if (abstractNekoAttackItem != this) {
                    for (int i = -1; i <= 1; i++) {
                        abstractNekoAttackItem.changeMainAttackType(player, player.getYaw() + i * 30);
                    }
                }
            }
        }
    }
    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);
        Text moreText=Text.of("主攻击：复制次攻击道具的主攻击方式并连续释放三次");
        if (ShiftKeyHelper.isShiftDown()) {
            tooltip.add(moreText);
        }
    }
}
