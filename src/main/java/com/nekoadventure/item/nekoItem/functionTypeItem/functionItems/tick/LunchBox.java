package com.nekoadventure.item.nekoItem.functionTypeItem.functionItems.tick;

import com.nekoadventure.item.nekoItem.functionTypeItem.functionItems.AbstractNekoFunctionItem;
import com.nekoadventure.item.other.NekoPackageItem;
import com.nekoadventure.other.itemApart.NekoPackageDataManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.text.Text;

public class LunchBox extends AbstractNekoFunctionItem {
    public LunchBox(Settings settings) {
        super(settings);
        text= Text.of("猫咪纸盒内每拥有一个\"食物类\"道具，额外+2.0生命");
    }

    @Override
    public void applyTickFunction(PlayerEntity player) {
        if (player.getWorld().isClient()) return;
        if (!(player.getOffHandStack().getItem() instanceof NekoPackageItem nekoPackage)) return;
        int haveFoodItem = NekoPackageItem.getNekoItem(player.getOffHandStack(), true, 0).stream()
                .filter(Item::isFood)
                .toList()
                .size();
        int lastFoodItem = NekoPackageDataManager.getItemIntState(player, "lunchBox");
        if (haveFoodItem == lastFoodItem) return;
        NekoPackageDataManager.setItemIntState(player, "lunchBox", haveFoodItem);
        nekoPackage.addOtherData(player, new double[]{(haveFoodItem - lastFoodItem) * 2, 0, 0, 0, 0, 0, 0});
    }
}
