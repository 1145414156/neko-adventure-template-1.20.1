package com.nekoadventure.item.nekoItem.attackTypeItem.specificItems;

import com.nekoadventure.client.ShiftKeyHelper;
import com.nekoadventure.item.nekoItem.attackTypeItem.AbstractNekoSpecificItem;
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

public class SandwormFang extends AbstractNekoSpecificItem {
    private static final String STATE_KEY = "sand_worm_fang";
    private static final int MAX_STRENGTH_STATE = 1000;
    private static final int STRENGTH_PER_HIT_STATE = 50;

    public SandwormFang(Settings settings, double health, double strength, double speed, double attackSpeed, double attackRange, double attackMultiplier, double attackSpeedMultiplier, boolean isSpecific) {
        super(settings, health, strength, speed, attackSpeed, attackRange, attackMultiplier, attackSpeedMultiplier, isSpecific);
    }

    @Override
    public void applySpecificItem(PlayerEntity player, LivingEntity target) {
        if (player.getWorld().isClient) return;
        if (player.getOffHandStack().getItem() instanceof NekoPackageItem nekoPackageItem){
            int current = NekoPackageDataManager.getItemIntState(player, STATE_KEY);
            int gained = Math.min(STRENGTH_PER_HIT_STATE, MAX_STRENGTH_STATE - current);
            if (gained <= 0) return;
            NekoPackageDataManager.setItemIntState(player, STATE_KEY, current + gained);
            nekoPackageItem.addTickData(player, new double[]{0, 0, gained / 100.0, 0, 0, 0, 0});
        }
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);
        Text moreText=Text.of("    造成伤害时，增加自身0.5\"力量\"(上限为10)");
        if (ShiftKeyHelper.isShiftDown()) {
            tooltip.add(moreText);
        }
    }

    @Override
    public void calculateOnTick(PlayerEntity player) {
        if (player.getWorld().isClient) return;
        int current = NekoPackageDataManager.getItemIntState(player, STATE_KEY);
        if (current <= 0) return;
        NekoPackageDataManager.setItemIntState(player, STATE_KEY, Math.max(current - 1, 0));
    }
}
