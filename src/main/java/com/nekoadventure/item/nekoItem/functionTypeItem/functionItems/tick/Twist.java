package com.nekoadventure.item.nekoItem.functionTypeItem.functionItems.tick;

import com.nekoadventure.item.nekoItem.functionTypeItem.functionItems.AbstractNekoFunctionItem;
import net.minecraft.text.Text;

public class Twist extends AbstractNekoFunctionItem {
    public Twist(Settings settings) {
        super(settings);
        text=Text.of("发射的弹幕随时间获得向左或向右的加速度");
    }

    @Override
    public float getCalculateYaw(int t,int  maxTime) {
        return (float) (Math.sin(8.0 * Math.PI * t / maxTime) * 40.0);
    }
}
