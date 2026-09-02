package com.nekoadventure.block.maze;

import com.nekoadventure.NekoAdventure;
import com.nekoadventure.event.maze.MazeRemoveHandler;
import com.nekoadventure.event.maze.PlayerFirstEnterMazeHandler;
import com.nekoadventure.item.ModItems;
import com.nekoadventure.item.other.NekoPackageItem;
import com.nekoadventure.network.ScreenBlackEffectPacket;
import com.nekoadventure.other.mazeApart.MazeBuilder;
import com.nekoadventure.other.mazeApart.MazeDataManager;
import com.nekoadventure.other.mazeApart.MazePosNBTCompound;
import com.nekoadventure.other.mazeApart.MazeStructureBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
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
import net.minecraft.world.dimension.DimensionType;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;

public class TPNextLevelBlock extends Block {

    public TPNextLevelBlock(Settings settings) {
        super(settings);
    }
    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (findNekoPackage(player)!=null) {
            world.setBlockState(player.getBlockPos(), Blocks.AIR.getDefaultState());
            apply(player, world);
            return ActionResult.SUCCESS;
        }
        return ActionResult.PASS;
    }

    private void apply(PlayerEntity player, World world) {
        if (!world.isClient) {
            if (world instanceof ServerWorld serverWorld) {
                if (!isMazeDimension(serverWorld)){return;}
                MinecraftServer server = world.getServer();
                List<ServerPlayerEntity> allPlayers;
                allPlayers = server.getPlayerManager().getPlayerList();
                MazeBuilder mazeBuilder = new MazeBuilder();
                MazeDataManager mazeDataManager = MazeDataManager.get(world);

                if (mazeDataManager != null) {
                    player.teleport(mazeDataManager.getLevelData() * 10000, 100, 0);
                    mazeDataManager.addLevelData();
                    List<MazePosNBTCompound> mazeData = mazeDataManager.getRoomData();
                    MazeRemoveHandler.removeAllMaze(serverWorld, mazeData,allPlayers);
                }

                ItemStack nekoPackage=findNekoPackage(player);
                if (!mazeBuilder.placeMaze(serverWorld)) {
                    ServerWorld overworld = Objects.requireNonNull(world.getServer()).getWorld(World.OVERWORLD);
                    if (nekoPackage != null && nekoPackage.getItem() instanceof NekoPackageItem nekoPackageItem) {
                        nekoPackageItem.setFinished(nekoPackage, 2);
                    }
                    if (overworld != null) {
                        allPlayers = server.getPlayerManager().getPlayerList();
                        for (ServerPlayerEntity serverPlayer:allPlayers) {
                            BlockPos spawnPos = overworld.getSpawnPos();
                            serverPlayer.teleport(
                                    overworld,
                                    spawnPos.getX(),
                                    spawnPos.getY(),
                                    spawnPos.getZ(),
                                    player.getYaw(),
                                    player.getPitch()
                            );

                        }
                        if (mazeDataManager != null) {
                            mazeDataManager.clearAllData();
                        }
                        PlayerFirstEnterMazeHandler.dimensionHasBeenVisited.remove(world.getRegistryKey().getValue());
                        player.sendMessage(Text.of("玩家"+player.getName().getString()+"已通关地牢"),false);
                    }
                }
                else {
                    Identifier dimensionId = serverWorld.getRegistryKey().getValue();
                    MazeStructureBuilder builder = new MazeStructureBuilder();
                    player.sendMessage(Text.literal("§e正在加载迷宫中，请勿退出游戏"), false);
                    if (player instanceof ServerPlayerEntity) {
                        ScreenBlackEffectPacket.send((ServerPlayerEntity) player, true);
                    }
                    //这里因为这个方块本身并没有tick方法所以用了服务器那里的计时器（人话：我偷懒了）
                    PlayerFirstEnterMazeHandler.delayedTasks.put(dimensionId, 80);
                    PlayerFirstEnterMazeHandler.delayedActions.put(dimensionId, () -> {
                        builder.placeAllSpecialRoom(serverWorld);
                        PlayerFirstEnterMazeHandler.delayedTasks.put(dimensionId, 20);
                        PlayerFirstEnterMazeHandler.delayedActions.put(dimensionId, () -> {
                            builder.clearRoomItemEntity(world);
                            if (mazeDataManager != null) {
                                mazeDataManager.clearInitialData();
                                player.sendMessage(Text.literal("§e加载迷宫成功"), false);
                                if (player instanceof ServerPlayerEntity) {
                                    ScreenBlackEffectPacket.send((ServerPlayerEntity) player, false);
                                }
                            }});
                    });
                }
            }
        }
    }

    private static @Nullable ItemStack findNekoPackage(PlayerEntity player) {
        ItemStack offhand = player.getOffHandStack();
        if (offhand.getItem() == ModItems.NEKO_PACKAGE) {
            return offhand;
        }
        PlayerInventory inventory = player.getInventory();
        for (int i = 0; i < inventory.main.size(); i++) {
            ItemStack stack = inventory.main.get(i);
            if (stack.getItem() == ModItems.NEKO_PACKAGE) {
                return stack;
            }
        }
        for (ItemStack armorStack : inventory.armor) {
            if (armorStack.getItem() == ModItems.NEKO_PACKAGE) {
                return armorStack;
            }
        }
        return null;
    }

    private static boolean isMazeDimension(ServerWorld world) {
        TagKey<DimensionType> isMazeTag = TagKey.of(
                RegistryKeys.DIMENSION_TYPE,
                new Identifier(NekoAdventure.MOD_ID, "is_maze")
        );
        return world.getDimensionEntry().isIn(isMazeTag);
    }
}
