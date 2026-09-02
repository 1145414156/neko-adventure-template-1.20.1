package com.nekoadventure.item.soulItem;

import com.nekoadventure.client.ShiftKeyHelper;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

//这个是全部筛选道具池的魂石总类
public abstract class AbstractSoulPoolItem extends AbstractSoulItem {
    public AbstractSoulPoolItem(Settings settings, int maxCharge) {
        super(settings, maxCharge);
    }

    @Override
    public void onUseEffect(World world, PlayerEntity player, ItemStack stack) {
        if (!player.isCreative()&&!player.isSpectator()) {
            player.giveItemStack(stack);
        }
    }
    public abstract List<Item> getStoragePoolItems(List<Item> inputPools, PlayerEntity player);

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);
        if (ShiftKeyHelper.isShiftDown()) {
            tooltip.add(Text.of("一种特殊的魂石，可以筛选“道具原胚”的道具池，如果使用时按下shift则会反转道具池"));
        }
    }
}
