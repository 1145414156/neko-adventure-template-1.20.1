package com.nekoadventure.block.blockentity.other;

import com.nekoadventure.block.blockentity.ModBlockEntityTypes;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;

public class ResetFurnaceBlockEntity extends BlockEntity {
    private int useCount = 0;
    private static final String USE_COUNT_KEY = "use_count";

    public ResetFurnaceBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntityTypes.RESET_FURNACE_BLOCK_ENTITY  , pos, state);
    }
    public int getUseCount() {
        return useCount;
    }

    public void addUseCount() {
        this.useCount++;
        markDirty();
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        this.useCount = nbt.getInt(USE_COUNT_KEY);
    }

    @Override
    public void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        nbt.putInt(USE_COUNT_KEY, this.useCount);
    }
//以下是给网络同步用的

    @Override
    public NbtCompound toInitialChunkDataNbt() {
        NbtCompound nbt = new NbtCompound();
        writeNbt(nbt);
        return nbt;
    }

    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }
    public void syncToClient() {
        if (world instanceof ServerWorld serverWorld) {
            serverWorld.getChunkManager().markForUpdate(pos);
        }
    }
}
