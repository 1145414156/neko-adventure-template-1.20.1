package com.nekoadventure.item.nekoItem.functionTypeItem.functionItems;

import com.nekoadventure.item.nekoItem.AbstractNekoItem;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class AbstractNekoFunctionItem extends AbstractNekoItem {

    public AbstractNekoFunctionItem(Settings settings) {
        super(settings);
    }
    public boolean applyDamagedFunction(PlayerEntity player){return false;}
    public void applyTickFunction(PlayerEntity player){}

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);
    }
}
