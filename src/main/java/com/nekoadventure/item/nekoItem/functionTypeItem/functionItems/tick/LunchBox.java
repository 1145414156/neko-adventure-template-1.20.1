package com.nekoadventure.item.nekoItem.functionTypeItem.functionItems.tick;

import com.nekoadventure.item.nekoItem.AbstractNekoItem;
import com.nekoadventure.item.nekoItem.functionTypeItem.functionItems.FunctionItem;
import com.nekoadventure.item.other.NekoPackageItem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.List;

public class LunchBox extends FunctionItem {
    int hadFoodItem;
    int recycleTime=0;

    public LunchBox(Settings settings) {
        super(settings);
        text= Text.of("猫咪纸盒内每拥有一个\"食物类\"道具，额外+2.0生命");
    }

    @Override
    public void applyTickFunctionItem(PlayerEntity player) {
        if (recycleTime>0){
                recycleTime--;
            }
            else {
                ArrayList<AbstractNekoItem> allItems = NekoPackageItem.getNekoItem(player.getOffHandStack(), true, 0);
                List<AbstractNekoItem> edibleItems = allItems.stream()
                        .filter(Item::isFood)
                        .toList();
                int haveFoodItem = edibleItems.size();
                if (haveFoodItem !=hadFoodItem){
                    if (player.getOffHandStack().getItem() instanceof NekoPackageItem nekoPackage) {
                        nekoPackage.addOtherData(player, new double[]{-hadFoodItem*2,0,0,0,0,0,0});
                        nekoPackage.addOtherData(player, new double[]{haveFoodItem *2,0,0,0,0,0,0});
                    }
                    hadFoodItem= haveFoodItem;
                }
                recycleTime=60;
            }
    }

}
