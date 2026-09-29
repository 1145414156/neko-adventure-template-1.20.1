package com.nekoadventure.item.nekoItem.functionTypeItem.functionItems.tick;

import com.nekoadventure.item.nekoItem.functionTypeItem.functionItems.NekoFunctionItem;
import net.minecraft.text.Text;

public class Restock extends NekoFunctionItem {
    public Restock(Settings settings) {
        super(settings);
        text= Text.of("获得道具时，如果该道具底座需要金币购买，则会重新随机抽取并上架一个新的道具，新的道具价格为原价格+5~15");
    }
}
