package com.nekoadventure.item.nekoItem.functionTypeItem.functionItems.tick;

import com.nekoadventure.item.nekoItem.functionTypeItem.functionItems.NekoFunctionItem;
import net.minecraft.text.Text;

public class Battery extends NekoFunctionItem {
    public Battery(Settings settings) {
        super(settings);
        text= Text.of("清理房间时，有1/2几率会使魂石额外获得一次充能");
    }
}
