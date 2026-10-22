package com.nekoadventure.item.nekoItem.functionTypeItem.functionItems.tick;

import com.nekoadventure.item.nekoItem.functionTypeItem.functionItems.AbstractNekoFunctionItem;
import net.minecraft.text.Text;

public class AbundantItemBase extends AbstractNekoFunctionItem {
    public AbundantItemBase(Settings settings) {
        super(settings);
        text= Text.of("移除道具多选一的可能");
    }
}
