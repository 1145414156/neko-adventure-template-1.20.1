package com.nekoadventure.item.soulItem;

import com.nekoadventure.item.ModItems;
import com.nekoadventure.item.nekoItem.AbstractNekoItem;
import com.nekoadventure.other.itemApart.SpawnRandomNekoItems;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class ResetSoul extends AbstractSoulItem{
    public ResetSoul(Settings settings, int maxCharge) {
        super(settings, maxCharge);
        text= Text.of("使用时，重置背包内的全部道具，道具池为全部道具");

    }

    @Override
    public void onUseEffect(World world, PlayerEntity player, ItemStack stack) {
        SpawnRandomNekoItems spawnRandomNekoItems=new SpawnRandomNekoItems();
        List<ItemStack> stacksToRemove = new ArrayList<>();
        int totalItems = 0;
        collectAbstractNekoStacks(player, stacksToRemove);
        for (ItemStack itemStack : stacksToRemove) {
            totalItems += itemStack.getCount();
        }

        if (totalItems > 0&&!world.isClient) {
            for (int i = 0; i < totalItems; i++) {
                player.giveItemStack(spawnRandomNekoItems.summonRandomItemFromPool((ServerWorld) world,null,spawnRandomNekoItems.ALL_POOL_PROBABILITIES_HEIGHT[0],spawnRandomNekoItems.ALL_POOL_PROBABILITIES_HEIGHT[1],
                        spawnRandomNekoItems.ALL_POOL_PROBABILITIES_HEIGHT[2],spawnRandomNekoItems.ALL_POOL_PROBABILITIES_HEIGHT[3]));
            }
            for (ItemStack itemStack : stacksToRemove) {
                itemStack.setCount(0);
            }
        } else {
            player.giveItemStack(ModItems.RESET_SOUL.getDefaultStack());
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
