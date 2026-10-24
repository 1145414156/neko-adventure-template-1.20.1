package com.nekoadventure.item.nekoItem.functionTypeItem.functionItems.tick;

import com.nekoadventure.item.nekoItem.functionTypeItem.functionItems.AbstractNekoFunctionItem;
import net.minecraft.text.Text;

public class Twist extends AbstractNekoFunctionItem {
    public Twist(Settings settings) {
        super(settings);
        text=Text.of("发射的弹幕随时间获得向左或向右的加速度");
    }

    @Override
    public float getCalculateYaw(int t) {
        return (float) Math.sin(t*0.5)*40;
    }
}
