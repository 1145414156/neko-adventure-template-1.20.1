package com.nekoadventure.other.mazeApart;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

//处理玩家黑屏状态
public class PlayerBlackScreenState {
    public static final ConcurrentHashMap<UUID, Boolean> blackScreenPlayers = new ConcurrentHashMap<>();

    public static void setBlackScreen(UUID playerUuid, boolean isBlack) {
        if (isBlack) {
            blackScreenPlayers.put(playerUuid, true);
        } else {
            blackScreenPlayers.remove(playerUuid);
        }
    }

    public static boolean isBlackScreen(UUID playerUuid) {
        return blackScreenPlayers.getOrDefault(playerUuid, false);
    }
}