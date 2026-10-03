package com.nekoadventure.item.soulItem;

import com.nekoadventure.item.ModItems;
import com.nekoadventure.item.other.NekoPackageItem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.world.World;

public class StrengthSoul extends AbstractSoulItem{
    public StrengthSoul(Settings settings, int maxCharge) {
        super(settings, maxCharge);
        text= Text.of("使用时，给予玩家7点\"力量\"(随时间衰减)");
    }

    @Override
    public void onUseEffect(World world, PlayerEntity player, ItemStack stack) {
        if (player.getOffHandStack().getItem() instanceof NekoPackageItem nekoPackageItem){
            nekoPackageItem.addTickData(player,new double[]{0,0,7,0,0,0,0});
        }
    }
}
