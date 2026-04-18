package com.nekoadventure.event;

import com.nekoadventure.event.maze.MazeRemoveHandler;
import com.nekoadventure.event.maze.PlayerFirstEnterMazeHandler;

public class ModEvents {
    public static void initialize(){
        MazeRemoveHandler.register();
        PlayerFirstEnterMazeHandler.register();
    }
}
