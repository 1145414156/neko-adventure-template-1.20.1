package com.nekoadventure.block.maze;

import com.nekoadventure.NekoAdventure;
import com.nekoadventure.item.ModItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class CommonTPMazeBlock extends Block {
    private final String MAZE_NAME;
    private final Item NEED_ITEM;
    public CommonTPMazeBlock(Settings settings, String mazeName, Item needItem) {
        super(settings);
        MAZE_NAME = mazeName;
        NEED_ITEM = needItem;
    }
    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (!world.isClient && player instanceof ServerPlayerEntity serverPlayer) {
            if (!hasItemInInventory(serverPlayer, NEED_ITEM)) {
                return ActionResult.FAIL;
            }
            if (findSlot(serverPlayer) == null) {
                serverPlayer.sendMessage(Text.of("请保证背包内有一格空位！"), true);
                return ActionResult.FAIL;
            }

            MinecraftServer server = world.getServer();
            if (server == null) {
                return ActionResult.FAIL;
            }
            List<ServerPlayerEntity> allPlayers = server.getPlayerManager().getPlayerList();
            for (ServerPlayerEntity p : allPlayers) {
                BlockPos pPos = p.getBlockPos();
                int dx = Math.abs(pPos.getX() - pos.getX());
                int dy = Math.abs(pPos.getY() - pos.getY());
                int dz = Math.abs(pPos.getZ() - pos.getZ());
                if (dx > 12 || dy > 12 || dz > 12) {
                    serverPlayer.sendMessage(Text.of("玩家 " + p.getName().getString() + " 不在传送范围内！"), true);
                    return ActionResult.FAIL;
                }
                if (findSlot(p) == null) {
                    serverPlayer.sendMessage(Text.of("玩家 " + p.getName().getString() + " 背包没有空位！"), true);
                    return ActionResult.FAIL;
                }
            }
            removeItemFromInventory(serverPlayer, NEED_ITEM);
            for (ServerPlayerEntity p : allPlayers) {
                p.giveItemStack(ModItems.NEKO_PACKAGE.getDefaultStack());
            }
            RegistryKey<World> dungeonDimension = RegistryKey.of(
                    RegistryKeys.WORLD,
                    new Identifier(NekoAdventure.MOD_ID, MAZE_NAME)
            );
            ServerWorld dungeonWorld = server.getWorld(dungeonDimension);
            if (dungeonWorld == null) {
                return ActionResult.FAIL;
            }

            world.removeBlock(pos, false);
            BlockPos spawnPos = new BlockPos(0, 100, 0);

            for (ServerPlayerEntity serverPlayerEntity : allPlayers) {
                Entity newEntity = serverPlayerEntity.moveToWorld(dungeonWorld);
                if (newEntity instanceof ServerPlayerEntity newServerPlayer) {
                    newServerPlayer.teleport(
                            dungeonWorld,
                            spawnPos.getX(),
                            spawnPos.getY(),
                            spawnPos.getZ(),
                            newServerPlayer.getYaw(),
                            newServerPlayer.getPitch()
                    );
                }
            }
            return ActionResult.SUCCESS;
        }
        return ActionResult.PASS;
    }

    private boolean hasItemInInventory(ServerPlayerEntity player, Item item) {
        if (player.isCreative()){return true;}
        else{
            for (ItemStack itemStack : player.getInventory().main) {
                if (itemStack.isOf(item)) {
                    return true;
                }
            }
            return false;
        }
    }

    private void removeItemFromInventory(ServerPlayerEntity player, Item item) {
        if (!player.isCreative()) {
            for (ItemStack itemStack : player.getInventory().main) {
                if (itemStack.isOf(item)) {
                    itemStack.decrement(1);
                    break;
                }
            }
        }
    }

    private static @Nullable ItemStack findSlot(PlayerEntity player) {
        ItemStack offhand = player.getOffHandStack();
        if (offhand.isEmpty()) {
            return offhand;
        }
        PlayerInventory inventory = player.getInventory();
        for (int i = 0; i < inventory.main.size(); i++) {
            ItemStack stack = inventory.main.get(i);
            if (stack.getItem().getDefaultStack().isEmpty()) {
                return stack;
            }
        }
        return null;
    }
}