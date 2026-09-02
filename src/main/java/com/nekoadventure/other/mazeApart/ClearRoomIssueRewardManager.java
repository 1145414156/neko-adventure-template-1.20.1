package com.nekoadventure.other.mazeApart;

import com.nekoadventure.item.ModItems;
import com.nekoadventure.other.itemApart.SpawnRandomNekoItems;
import com.nekoadventure.other.itemApart.SpawnRandomSoulItems;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;

import java.util.Random;


//清理房间后的奖励发放
public class ClearRoomIssueRewardManager {
    private final Random random = new Random();

    public void apply(PlayerEntity player, World world) {
        SpawnRandomNekoItems spawnRandomNekoItems = new SpawnRandomNekoItems();
        player.addStatusEffect(new StatusEffectInstance(StatusEffects.INSTANT_HEALTH, 1, 10));
        int num = random.nextInt(100) + 1;
        //10%:回满饱食度
        if (num <=10){
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.SATURATION, 1, 10));
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
