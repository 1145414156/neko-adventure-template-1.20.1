package com.nekoadventure.block.blockentity.other;

import com.nekoadventure.block.blockentity.ModBlockEntityTypes;
import com.nekoadventure.block.specialroomblock.AbstractRoomBlock;
import com.nekoadventure.other.itemApart.SpawnRandomNekoItems;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;

public class ItemBaseBlockEntity extends BlockEntity {
    private ItemStack item =ItemStack.EMPTY;
    private int requiredCoins = 0;
    public ItemBaseBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntityTypes.ITEM_BASE_BLOCK_ENTITY, pos, state);
    }

    public void generateAndSaveItem(ServerWorld world, AbstractRoomBlock roomBlock,
                                    int common,int uncommon,int rare,int epic) {
        SpawnRandomNekoItems spawnRandomNekoItems = new SpawnRandomNekoItems();

        this.item = spawnRandomNekoItems.summonRandomItemFromPool(world,roomBlock,common,uncommon,rare,epic);

        markDirty();
        world.updateListeners(pos, getCachedState(), getCachedState(), 3);
    }

    public ItemStack getItem() {
        return item;
    }

    public void setItem(ItemStack item) {
        this.item = item;
        markDirty();
    }

    public int getRequiredCoins() {
        return requiredCoins;
    }

    public void setRequiredCoins(int requiredCoins) {
        this.requiredCoins = requiredCoins;
        markDirty();
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        NbtCompound itemNbt = new NbtCompound();
        item.writeNbt(itemNbt);
        nbt.putInt("RequiredCoins", requiredCoins);
        nbt.put("item", itemNbt);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        requiredCoins = nbt.getInt("RequiredCoins");
        if (nbt.contains("item", NbtCompound.COMPOUND_TYPE)) {
            item = ItemStack.fromNbt(nbt.getCompound("item"));
        } else {
            item = ItemStack.EMPTY;
        }
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
