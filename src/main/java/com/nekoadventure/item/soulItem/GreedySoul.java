package com.nekoadventure.item.soulItem;

import com.nekoadventure.item.ModItems;
import com.nekoadventure.item.nekoItem.AbstractNekoItem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class GreedySoul extends AbstractSoulItem{
    public GreedySoul(Settings settings, int maxCharge) {
        super(settings, maxCharge);
        text= Text.of("使用时，删除背包内的全部道具，每一个道具都会生成0~7个缠魂物质");
    }

    @Override
    public void onUseEffect(World world, PlayerEntity player, ItemStack stack) {
        List<ItemStack> stacksToRemove = new ArrayList<>();
        int totalItems = 0;
        collectAbstractNekoStacks(player, stacksToRemove);
        for (ItemStack itemStack : stacksToRemove) {
            totalItems += itemStack.getCount();
        }

        if (totalItems > 0) {
            for (int i = 0; i < totalItems; i++) {
                int c = world.random.nextInt(8);
                for (int z = 0; z < c; z++) {
                    player.giveItemStack(ModItems.BINDING_SOUL_SUBSTANCE.getDefaultStack());
                }
            }
            for (ItemStack itemStack : stacksToRemove) {
                itemStack.setCount(0);
            }
        } else {
            player.giveItemStack(ModItems.GREEDY_SOUL.getDefaultStack());
        }
    }

    private void collectAbstractNekoStacks(PlayerEntity player, List<ItemStack> output) {
        for (ItemStack itemStack : player.getInventory().main) {
            if (!itemStack.isEmpty() && itemStack.getItem() instanceof AbstractNekoItem) {
                output.add(itemStack);
            }
        }
        ItemStack offhand = player.getOffHandStack();
        if (!offhand.isEmpty() && offhand.getItem() instanceof AbstractNekoItem) {
            output.add(offhand);
        }
        for (ItemStack armorStack : player.getInventory().armor) {
            if (!armorStack.isEmpty() && armorStack.getItem() instanceof AbstractNekoItem) {
                output.add(armorStack);
            }
        }
    }
}
