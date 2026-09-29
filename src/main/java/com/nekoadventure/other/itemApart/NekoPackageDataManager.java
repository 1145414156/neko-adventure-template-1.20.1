package com.nekoadventure.other.itemApart;

import net.minecraft.entity.player.PlayerEntity;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

// 猫咪纸盒的数据存储层：服务端按玩家UUID隔离存储，客户端只缓存本地玩家的最终数据(由网络包同步)
public class NekoPackageDataManager {
    public static final double MAX_ATTACK_SPEED=24;
    public static final double MAX_ATTACK_RANGE=36;
    public static final double MIN_ATTACK_MULTIPLIER=0.5;
    private static final ConcurrentHashMap<UUID, PlayerNekoData> SERVER_DATA = new ConcurrentHashMap<>();
    // 按玩家存储的道具状态标记（key为道具标识，如道具注册名），与SERVER_DATA同生命周期
    private static final ConcurrentHashMap<UUID, Set<String>> ITEM_STATE = new ConcurrentHashMap<>();
    // 按玩家存储的道具整数状态（如数量快照），与SERVER_DATA同生命周期
    private static final ConcurrentHashMap<UUID, ConcurrentHashMap<String, Integer>> ITEM_INT_STATE = new ConcurrentHashMap<>();
    private static volatile double[] clientFinalData;
    private static volatile int clientLevel = 1;

    // 单个玩家的纸盒数据
    public static class PlayerNekoData {
        public boolean recycle = true;
        public double[] nekoData = new double[7];
        public double[] otherData = new double[7];
        // 每tick自动朝零衰减的临时数据（每tick靠近0.05，|值|<=0.05时归零）
        public double[] tickData = new double[7];
        public double[] finalData = new double[7];
        // [0]=health, [1]=speed, [2]=strength，[3]=attackSpeed,
        // [4]=attackRange,[5]=attackMultiplier,[6]=attackSpeedMultiplier
        public int recycleTime = 0;
        // 上次衰减tickData的时间，用于同一tick内去重
        public long lastDecayTick = -1;
    }

    public static PlayerNekoData getOrCreate(PlayerEntity player) {
        return SERVER_DATA.computeIfAbsent(player.getUuid(), uuid -> new PlayerNekoData());
    }

    public static PlayerNekoData get(PlayerEntity player) {
        return SERVER_DATA.get(player.getUuid());
    }

    public static void remove(UUID playerUuid) {
        SERVER_DATA.remove(playerUuid);
        ITEM_STATE.remove(playerUuid);
        ITEM_INT_STATE.remove(playerUuid);
    }

    // 读取按玩家存储的布尔值道具状态标记
    public static boolean getItemState(PlayerEntity player, String key) {
        Set<String> states = ITEM_STATE.get(player.getUuid());
        return states != null && states.contains(key);
    }

    // 写入按玩家存储的布尔值道具状态标记
    public static void setItemState(PlayerEntity player, String key, boolean value) {
        Set<String> states = ITEM_STATE.computeIfAbsent(player.getUuid(), uuid -> ConcurrentHashMap.newKeySet());
        if (value) {
            states.add(key);
        } else {
            states.remove(key);
        }
    }

    // 读取按玩家存储的临时道具数据状态（未设置时返回0）
    public static int getItemIntState(PlayerEntity player, String key) {
        ConcurrentHashMap<String, Integer> states = ITEM_INT_STATE.get(player.getUuid());
        return states == null ? 0 : states.getOrDefault(key, 0);
    }

    // 写入按玩家存储的某个临时数据整数状态
    public static void setItemIntState(PlayerEntity player, String key, int value) {
        ConcurrentHashMap<String, Integer> states = ITEM_INT_STATE.computeIfAbsent(player.getUuid(), uuid -> new ConcurrentHashMap<>());
        states.put(key, value);
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
        data.recycle = true;
    }
    public static double[] getTickData(PlayerEntity player) {
        PlayerNekoData data = SERVER_DATA.get(player.getUuid());
        return data == null ? null : data.tickData;
    }
    public static void addTickData(PlayerEntity player, double[] tickData1) {
        if (player.getWorld().isClient) {
            return;
        }
        PlayerNekoData data = getOrCreate(player);
        if (tickData1 == null || tickData1.length != data.tickData.length) {
            return;
        }
        for (int i = 0; i < data.tickData.length; i++) {
            data.tickData[i] += tickData1[i];
        }
        data.recycle = true;
    }
    public static void decayTickData(PlayerEntity player) {
        if (player.getWorld().isClient) {
            return;
        }
        PlayerNekoData data = getOrCreate(player);
        long now = player.getWorld().getTime();
        if (data.lastDecayTick == now) {
            return;
        }
        data.lastDecayTick = now;
        boolean changed = false;
        for (int i = 0; i < data.tickData.length; i++) {
            double value = data.tickData[i];
            if (value > 0.01) {
                data.tickData[i] = value - 0.01;
                changed = true;
            } else if (value < -0.01) {
                data.tickData[i] = value + 0.01;
                changed = true;
            } else if (value != 0) {
                data.tickData[i] = 0;
                changed = true;
            }
        }
        if (changed) {
            data.recycle = true;
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

    public static void setClientLevel(int level) {
        clientLevel = level;
    }

    public static int getClientLevel() {
        return clientLevel;
    }
}
