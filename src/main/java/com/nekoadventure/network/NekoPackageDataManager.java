package com.nekoadventure.network;

import net.minecraft.entity.player.PlayerEntity;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

// 猫咪纸盒的数据存储层：服务端按玩家UUID隔离存储，客户端只缓存本地玩家的最终数据(由网络包同步)
public class NekoPackageDataManager {
    private static final ConcurrentHashMap<UUID, PlayerNekoData> SERVER_DATA = new ConcurrentHashMap<>();
    private static volatile double[] clientFinalData;

    // 单个玩家的纸盒数据
    public static class PlayerNekoData {
        public boolean recycle = true;
        public double[] nekoData = new double[7];
        public double[] otherData = new double[7];
        public double[] finalData = new double[7];
        // [0]=health, [1]=speed, [2]=strength，[3]=attackSpeed,
        // [4]=attackRange,[5]=attackMultiplier,[6]=attackSpeedMultiplier
        public int recycleTime = 0;
    }

    public static PlayerNekoData getOrCreate(PlayerEntity player) {
        return SERVER_DATA.computeIfAbsent(player.getUuid(), uuid -> new PlayerNekoData());
    }

    public static PlayerNekoData get(PlayerEntity player) {
        return SERVER_DATA.get(player.getUuid());
    }

    public static void remove(UUID playerUuid) {
        SERVER_DATA.remove(playerUuid);
    }

    public static double[] getFinalData(PlayerEntity player) {
        if (player.getWorld().isClient) {
            return clientFinalData;
        }
        PlayerNekoData data = SERVER_DATA.get(player.getUuid());
        return data == null ? null : data.finalData;
    }

    public static void addOtherData(PlayerEntity player, double[] otherData1) {
        if (player.getWorld().isClient) {
            return;
        }
        PlayerNekoData data = getOrCreate(player);
        if (otherData1 == null || otherData1.length != data.otherData.length) {
            return;
        }
        for (int i = 0; i < data.otherData.length; i++) {
            data.otherData[i] += otherData1[i];
        }
    }

    public static void reset(PlayerEntity player) {
        if (player.getWorld().isClient) {
            return;
        }
        PlayerNekoData data = getOrCreate(player);
        data.recycle = true;
        data.recycleTime = 0;
    }

    public static void setClientFinalData(double[] finalData) {
        clientFinalData = finalData;
    }
}
