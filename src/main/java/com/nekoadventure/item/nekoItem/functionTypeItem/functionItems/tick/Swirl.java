package com.nekoadventure.item.nekoItem.functionTypeItem.functionItems.tick;

import com.nekoadventure.item.nekoItem.functionTypeItem.functionItems.AbstractNekoFunctionItem;
import net.minecraft.text.Text;

public class Swirl extends AbstractNekoFunctionItem {
    public Swirl(Settings settings) {
        super(settings);
        text=Text.of("发射的弹幕持续获得向左方向的加速度");

    }

    @Override
    public float getCalculateYaw(int t) {
        return 90+t*-10;
    }
}
