package com.nekoadventure.item.nekoItem.functionTypeItem.functionItems.tick;

import com.nekoadventure.entity.missile.MissileEntity;
import com.nekoadventure.item.nekoItem.functionTypeItem.functionItems.AbstractNekoFunctionItem;
import com.nekoadventure.item.other.NekoPackageItem;
import com.nekoadventure.other.mobApart.AttackTypes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.Vec3d;

import java.util.List;
import java.util.Objects;

public class Acceleration extends AbstractNekoFunctionItem {

    public Acceleration(Settings settings) {
        super(settings);
        text=Text.of("尝试将攻击弹道修正为直线");
    }

    @Override
    public float getCalculateYaw(int t,int maxTime) {
        return 0;
    }
}
