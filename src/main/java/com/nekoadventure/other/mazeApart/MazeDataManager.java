package com.nekoadventure.other.mazeApart;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.PersistentState;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


//这个类是储存和处理关于地牢的所有数据(level,roomData)
public class MazeDataManager extends PersistentState {
    private final List<MazePosNBTCompound> roomData = new ArrayList<>();
    private final List<MazePosNBTCompound> initialData = new ArrayList<>();
    private int level = 1;
    private MazeCenterNBTCompound mazeCenter = null;
    private int mazeRange = 0;

    private int mazeHeight = 0;

    @Override
    public NbtCompound writeNbt(NbtCompound nbt) {
        NbtList initialList = new NbtList();
        for (MazePosNBTCompound marker : initialData) {
            initialList.add(marker.toNbt());
        }
        nbt.put("initialData", initialList);

        NbtList roomList = new NbtList();
        for (MazePosNBTCompound marker : roomData) {
            roomList.add(marker.toNbt());
        }
        nbt.put("roomData", roomList);

        nbt.putInt("Level", level);

        if (mazeCenter != null) {
            nbt.put("MazeCenter", mazeCenter.toNBT());
        }
        nbt.putInt("MazeRange", mazeRange);
        nbt.putInt("MazeHeight", mazeHeight);

        return nbt;
    }

    public static MazeDataManager fromNbt(NbtCompound nbt) {
        MazeDataManager data = new MazeDataManager();

        data.level = nbt.getInt("Level");

        NbtList initialList = nbt.getList("initialData", NbtCompound.COMPOUND_TYPE);
        for (int i = 0; i < initialList.size(); i++) {
            MazePosNBTCompound marker = MazePosNBTCompound.fromNbt(initialList.getCompound(i));
            data.initialData.add(marker);
        }
        NbtList roomList = nbt.getList("roomData", NbtCompound.COMPOUND_TYPE);
        for (int i = 0; i < roomList.size(); i++) {
            MazePosNBTCompound marker = MazePosNBTCompound.fromNbt(roomList.getCompound(i));
            data.roomData.add(marker);
        }
        if (nbt.contains("MazeCenter", NbtCompound.COMPOUND_TYPE)) {
            data.mazeCenter = MazeCenterNBTCompound.fromNBT(nbt.getCompound("MazeCenter"));
        }
        data.mazeRange = nbt.getInt("MazeRange");
        data.mazeHeight = nbt.getInt("MazeHeight");

        return data;
    }

    public static MazeDataManager get(World world) {
        String dimensionName = world.getDimensionKey().getValue().toString()
                .replace(":", "_");
        if (world instanceof ServerWorld serverWorld) {
            return serverWorld.getPersistentStateManager().getOrCreate(
                    MazeDataManager::fromNbt,
                    MazeDataManager::new,
                    dimensionName + "_dimension_data"
            );
        }
        return null;
    }

    public void addToInitialData(MazePosNBTCompound marker) {
        initialData.add(marker);
        markDirty();
    }

    public void addToRoomData(MazePosNBTCompound marker) {
        roomData.add(marker);
        markDirty();
    }

    public List<MazePosNBTCompound> getInitialData() {
        List<MazePosNBTCompound> arrayList = new ArrayList<>(initialData);
        Collections.shuffle(arrayList);
        return arrayList;
    }

    public List<MazePosNBTCompound> getRoomData() {
        return new ArrayList<>(roomData);
    }

    public void clearInitialData() {
        initialData.clear();
        markDirty();
    }

    public void clearRoomData() {
        roomData.clear();
        markDirty();
    }

    public void clearAllData() {
        clearInitialData();
        clearRoomData();
        resetLevelData();
        clearMazeCenter();
        resetMazeRange();
    }

    public int getLevelData() {
        return level;
    }

    public void addLevelData() {
        level += 1;
        markDirty();
    }

    public void resetLevelData() {
        level = 1;
        markDirty();
    }
    public MazeCenterNBTCompound getMazeCenter() {
        return mazeCenter;
    }

    public void setMazeCenter(BlockPos centerPos) {
        this.mazeCenter = new MazeCenterNBTCompound(centerPos);
        markDirty();
    }
    public void clearMazeCenter() {
        this.mazeCenter = null;
        markDirty();
    }
    public int getMazeRange() {
        return mazeRange;
    }
    public void setMazeRange(int range) {
        this.mazeRange = range;
        markDirty();
    }
    public void resetMazeRange() {
        this.mazeRange = 0;
        markDirty();
    }

    public int getMazeHeight() {
        return mazeHeight;
    }
    public void setMazeHeight(int height) {
        this.mazeHeight = height;
        markDirty();
    }
}
