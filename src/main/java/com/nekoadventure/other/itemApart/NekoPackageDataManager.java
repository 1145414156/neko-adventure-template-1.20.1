package com.nekoadventure.other.itemApart;

import net.minecraft.entity.player.PlayerEntity;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class NekoPackageDataManager {
    public static final double MAX_ATTACK_SPEED=24;
    public static final double MAX_ATTACK_RANGE=36;
    public static final double MAX_SPEED=24.0;
    private static final ConcurrentHashMap<UUID, PlayerNekoData> SERVER_DATA = new ConcurrentHashMap<>();
    private static final ConcurrentHashMap<UUID, Set<String>> ITEM_STATE = new ConcurrentHashMap<>();
    private static final ConcurrentHashMap<UUID, ConcurrentHashMap<String, Integer>> ITEM_INT_STATE = new ConcurrentHashMap<>();
    private static final ConcurrentHashMap<UUID, ConcurrentHashMap<UUID, Long>> ENTITY_RECORD = new ConcurrentHashMap<>();
    private static volatile double[] clientFinalData;
    private static volatile int clientLevel = 1;

    public static class PlayerNekoData {
        public boolean recycle = true;
        public double[] nekoData = new double[7];
        public double[] otherData = new double[7];
        // 每tick自动朝零衰减的临时数据（每tick靠近0.05，<=0.05时归零）
        public double[] tickData = new double[7];
        public double[] finalData = new double[7];
        // [0]=health, [1]=speed, [2]=strength，[3]=attackSpeed,
        // [4]=attackRange,[5]=attackMultiplier,[6]=attackSpeedMultiplier
        public int recycleTime = 0;
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
        ENTITY_RECORD.remove(playerUuid);
    }

    public static boolean getItemState(PlayerEntity player, String key) {
        Set<String> states = ITEM_STATE.get(player.getUuid());
        return states != null && states.contains(key);
    }

    public static void setItemState(PlayerEntity player, String key, boolean value) {
        Set<String> states = ITEM_STATE.computeIfAbsent(player.getUuid(), uuid -> ConcurrentHashMap.newKeySet());
        if (value) {
            states.add(key);
        } else {
            states.remove(key);
        }
    }

    public static int getItemIntState(PlayerEntity player, String key) {
        ConcurrentHashMap<String, Integer> states = ITEM_INT_STATE.get(player.getUuid());
        return states == null ? 0 : states.getOrDefault(key, 0);
    }

    public static void setItemIntState(PlayerEntity player, String key, int value) {
        ConcurrentHashMap<String, Integer> states = ITEM_INT_STATE.computeIfAbsent(player.getUuid(), uuid -> new ConcurrentHashMap<>());
        states.put(key, value);
    }

    // 记录一次实体处理，有效期内重复调用返回false；过期记录在写入时顺带清理
    public static boolean markEntityProcessed(PlayerEntity player, UUID entityUuid, int expireTicks) {
        ConcurrentHashMap<UUID, Long> records = ENTITY_RECORD.computeIfAbsent(player.getUuid(), uuid -> new ConcurrentHashMap<>());
        long now = player.getWorld().getTime();
        records.entrySet().removeIf(entry -> entry.getValue() <= now);
        if (records.containsKey(entityUuid)) {
            return true;
        }
        records.put(entityUuid, now + expireTicks);
        return false;
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
