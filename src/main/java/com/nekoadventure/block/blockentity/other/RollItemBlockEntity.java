package com.nekoadventure.block.blockentity.other;

import com.nekoadventure.block.blockentity.ModBlockEntityTypes;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;

public class RollItemBlockEntity extends BlockEntity {
    private int useCount = 0;

    public RollItemBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntityTypes.ROLL_ITEM_BLOCK_ENTITY, pos, state);
    }

    public int getUseCount() {
        return useCount;
    }

    public void setUseCount(int useCount) {
        this.useCount = useCount;
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        this.useCount = nbt.getInt("UseCount");
    }

    @Override
    public void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        nbt.putInt("UseCount", this.useCount);
    }
}