package com.nekoadventure.item.nekoItem.functionTypeItem.functionItems.damaged;

import com.nekoadventure.item.nekoItem.functionTypeItem.functionItems.FunctionItem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;

public class SpiritingAway extends FunctionItem {
    int tick=0;
    public SpiritingAway(Settings settings) {
        super(settings);
        text= Text.of("每100刻的游戏中(世界刻)，有13刻会处于无敌状态");
    }

    @Override
    public boolean applyDamagedFunctionItem(PlayerEntity player) {
        if (tick<100){
            tick++;
        }
        else {tick=0;}
        return tick < 13;
    }
}
