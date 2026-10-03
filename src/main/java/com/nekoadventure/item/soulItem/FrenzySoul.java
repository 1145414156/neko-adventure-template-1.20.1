package com.nekoadventure.item.soulItem;

import com.nekoadventure.item.other.NekoPackageItem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.world.World;

public class FrenzySoul extends AbstractSoulItem{
    public FrenzySoul(Settings settings, int maxCharge) {
        super(settings, maxCharge);
        text= Text.of("使用时，自身+3\"力量\"+3\"射速\"(随时间衰减)");
    }

    @Override
    public void onUseEffect(World world, PlayerEntity player, ItemStack stack) {
        if (player.getOffHandStack().getItem() instanceof NekoPackageItem nekoPackageItem){
            nekoPackageItem.addTickData(player,new double[]{0,0,3,3,0,0,0});
        }
    }
}
