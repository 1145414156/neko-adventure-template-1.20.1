package com.nekoadventure.other.mazeApart;

import com.nekoadventure.item.ModItems;
import com.nekoadventure.item.other.NekoPackageItem;
import com.nekoadventure.item.soulItem.AbstractSoulItem;
import com.nekoadventure.other.itemApart.SpawnRandomNekoItems;
import com.nekoadventure.other.itemApart.SpawnRandomSoulItems;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;

import java.util.Random;


//清理房间后的奖励发放
public class ClearRoomIssueRewardManager {
    private final Random random = new Random();

    public void apply(PlayerEntity player, World world) {

        //魂石充能
        for (int i = 0; i < player.getInventory().size(); i++) {
            ItemStack stack = player.getInventory().getStack(i);
            if (stack.getItem() instanceof AbstractSoulItem soulItem) {
                soulItem.addCharged(stack, 1);
            }
        }

        //电池的特殊充能
        if (player.getOffHandStack().getItem() instanceof NekoPackageItem nekoPackage) {
            if(nekoPackage.isHaveNekoItem(player.getOffHandStack(), ModItems.BATTERY)){
                for (int i = 0; i < player.getInventory().size(); i++) {
                    ItemStack stack = player.getInventory().getStack(i);
                    if (stack.getItem() instanceof AbstractSoulItem soulItem&&random.nextBoolean()) {
                        soulItem.addCharged(stack, 1);
                    }
                }
            }
        }

        SpawnRandomNekoItems spawnRandomNekoItems = new SpawnRandomNekoItems();
        int num = random.nextInt(100) + 1;
        player.addStatusEffect(new StatusEffectInstance(StatusEffects.INSTANT_HEALTH,5,10,true,false));
        //10%:回满饱食度
        if (num <=10){
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.SATURATION, 10, 10,false, false,false));
        }
        //11%:给予玩家缠魂物质
        else if (num <= 41) {
            player.giveItemStack(ModItems.BINDING_SOUL_SUBSTANCE.getDefaultStack());
        }
        //34%:给予玩家随机金币(4~9)
        else if (num <= 75) {
            int coinCount = random.nextInt(5) + 5;
            for (int z = 0; z <= coinCount; z++) {
                player.giveItemStack(ModItems.COIN.getDefaultStack());
            }
        }
        //15%:给予玩家随机魂石
        else if (num <= 90) {
            player.giveItemStack(new SpawnRandomSoulItems().summonRandomSoulItem(world));
        }
        //15%:给予玩家随机道具(从全部池中抽取)
        else if (num <=95) {
            if (world instanceof ServerWorld) {
                spawnRandomNekoItems.summonRandomItemFromPool(
                        (ServerWorld) world,
                        null,
                        spawnRandomNekoItems.ALL_POOL_PROBABILITIES_HEIGHT[0],
                        spawnRandomNekoItems.ALL_POOL_PROBABILITIES_HEIGHT[1],
                        spawnRandomNekoItems.ALL_POOL_PROBABILITIES_HEIGHT[2],
                        spawnRandomNekoItems.ALL_POOL_PROBABILITIES_HEIGHT[3]);
            }
        }
        //1%:给予玩家随机道具(从全部池中抽取)，和一个魂石
        else {
            player.giveItemStack(new SpawnRandomSoulItems().summonRandomSoulItem(world));
            if (world instanceof ServerWorld) {
                spawnRandomNekoItems.summonRandomItemFromPool(
                        (ServerWorld) world,
                        null,
                        spawnRandomNekoItems.ALL_POOL_PROBABILITIES_HEIGHT[0],
                        spawnRandomNekoItems.ALL_POOL_PROBABILITIES_HEIGHT[1],
                        spawnRandomNekoItems.ALL_POOL_PROBABILITIES_HEIGHT[2],
                        spawnRandomNekoItems.ALL_POOL_PROBABILITIES_HEIGHT[3]);
            }
        }
    }
}
