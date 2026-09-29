package com.nekoadventure.item.nekoItem.functionTypeItem.functionItems.tick;

import com.nekoadventure.item.nekoItem.functionTypeItem.functionItems.NekoFunctionItem;
import net.minecraft.text.Text;

public class CloudBoots extends NekoFunctionItem {
    public CloudBoots(Settings settings) {
        super(settings);
        text= Text.of("允许玩家二段跳");
    }

}
