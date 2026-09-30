package com.nekoadventure.other.mazeApart;

import net.minecraft.block.Block;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.NotNull;

//  这个是用来详细处理和作为参考：特殊数据roomData<BlockPos,GateCount>
//                                         房间中心   房间门数量
public record MazePosNBTCompound(BlockPos roomCenter, int gateCount) {
    public NbtCompound toNbt() {
        NbtCompound nbt = new NbtCompound();
        nbt.putInt("roomX", roomCenter.getX());
        nbt.putInt("roomY", roomCenter.getY());
        nbt.putInt("roomZ", roomCenter.getZ());
        nbt.putInt("gateCount", gateCount);
        return nbt;
    }

    public static MazePosNBTCompound fromNbt(NbtCompound nbt) {
        BlockPos pos = new BlockPos(
                nbt.getInt("roomX"),
                nbt.getInt("roomY"),
                nbt.getInt("roomZ")
        );
        int gateCount = nbt.getInt("gateCount");
        return new MazePosNBTCompound(pos, gateCount);
    }

    @Override
    public @NotNull String toString() {
        return "MazePosNBTCompound{" +
                "位置=" + roomCenter.toShortString() +
                ", 门数=" + gateCount +
                '}';
    }
}
