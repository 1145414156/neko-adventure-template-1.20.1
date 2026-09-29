package com.nekoadventure.item.nekoItem.functionTypeItem.functionItems.tick;

import com.nekoadventure.item.nekoItem.functionTypeItem.functionItems.NekoFunctionItem;
import com.nekoadventure.item.other.NekoPackageItem;
import com.nekoadventure.other.itemApart.NekoPackageDataManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;

public class Adrenaline extends NekoFunctionItem {
    public Adrenaline(Settings settings) {
        super(settings);
        text = Text.of("生命低于1/2时，+0.2力量乘区，+0.5速度，+0.3射速乘区");
    }

    @Override
    public void applyTickFunctionItem(PlayerEntity player) {
        if (player.getWorld().isClient()) return;
        if (!(player.getOffHandStack().getItem() instanceof NekoPackageItem nekoPackage)) return;
        boolean lowHealth = player.getHealth() <= player.getMaxHealth() / 2;
        if (lowHealth == NekoPackageDataManager.getItemState(player, "adrenaline")) return;
        NekoPackageDataManager.setItemState(player, "adrenaline", lowHealth);
        nekoPackage.addOtherData(player, lowHealth
                ? new double[]{0, 0.5, 0, 0, 0, 0.2, 0.3}
                : new double[]{0, -0.5, 0, 0, 0, -0.2, -0.3});
    }
}
