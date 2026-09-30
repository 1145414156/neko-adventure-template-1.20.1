package com.nekoadventure.other.mazeApart;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.NotNull;

public record MazeCenterNBTCompound(BlockPos mazeCenter){
    public NbtCompound toNBT(){
        NbtCompound nbt = new NbtCompound();
        nbt.putInt("roomX", mazeCenter.getX());
        nbt.putInt("roomY", mazeCenter.getY());
        nbt.putInt("roomZ", mazeCenter.getZ());
        return nbt;
    }
    public static MazeCenterNBTCompound fromNBT(NbtCompound nbt) {
        BlockPos mazeCenter =new BlockPos(nbt.getInt("roomX"),nbt.getInt("roomY"),nbt.getInt("roomZ"));
        return new MazeCenterNBTCompound(mazeCenter);
    }
    @Override
    public @NotNull String toString(){
        return "MazeCenterNBTCompound{"+
                mazeCenter.toShortString()+"}";
    }
}
