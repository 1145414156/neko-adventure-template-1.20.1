package com.nekoadventure.item.soulItem;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.world.World;

public class InstanceHealthSoul extends AbstractSoulItem{
    public InstanceHealthSoul(Settings settings, int maxCharge) {
        super(settings,maxCharge);
        text=Text.of("使用时，回复全部生命值");
    }

    @Override
    public void onUseEffect(World world, PlayerEntity player, ItemStack stack) {
        player.heal(player.getMaxHealth()-player.getHealth());
    }

}
