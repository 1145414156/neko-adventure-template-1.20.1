package com.nekoadventure.item.soulItem;

import com.nekoadventure.item.ModItems;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.world.World;

public class OriginSoul extends AbstractSoulItem{
    public OriginSoul(Settings settings, int maxCharge) {
        super(settings, maxCharge);
        text= Text.of("使用时，随机给予玩家1~8个缠魂物质");
    }

    @Override
    public void onUseEffect(World world, PlayerEntity player, ItemStack stack) {
        int c=world.random.nextInt(8)+1;
        for (int z=0;z<c;z++){
            player.giveItemStack(ModItems.BINDING_SOUL_SUBSTANCE.getDefaultStack());
        }
    }
}
