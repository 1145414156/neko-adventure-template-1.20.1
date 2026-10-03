package com.nekoadventure.block.other;

import com.nekoadventure.block.blockentity.other.SlotMachineBlockEntity;
import com.nekoadventure.item.ModItems;
import com.nekoadventure.other.itemApart.SpawnRandomNekoItems;
import com.nekoadventure.other.itemApart.SpawnRandomSoulItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class SlotMachineBlock extends Block implements BlockEntityProvider {
    public SlotMachineBlock(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (world.isClient) {
            return ActionResult.SUCCESS;
        }

        ItemStack heldItem = player.getStackInHand(hand);

        if (heldItem.getItem() == ModItems.COIN) {
            int useCount = getUseCount(world, pos);

            heldItem.decrement(1);

            int roll = world.random.nextInt(100);

            if (roll < 60-useCount) {
                // 基础60%-useCount：什么都不生成
                world.addBlockBreakParticles(pos, state);
            }
           else if (roll < 80-useCount) {
                SpawnRandomSoulItems spawnRandomSoulItems=new SpawnRandomSoulItems();
                // 20%概率：生成1魂石
                player.getInventory().offerOrDrop(spawnRandomSoulItems.summonRandomSoulItem(world));
                player.sendMessage(Text.literal("§a你获得了 1 个魂石！"), true);
            } else if (roll < 85-useCount) {
               int z=world.random.nextInt(4)+1;
                // 5%概率：生成1~4缠魂物质
                player.getInventory().offerOrDrop(new ItemStack(ModItems.BINDING_SOUL_SUBSTANCE, z));
                player.sendMessage(Text.literal("§a你获得了 "+z+ "枚金币！"), true);
            } else if (roll<90-useCount){
                SpawnRandomNekoItems spawnRandomNekoItems=new SpawnRandomNekoItems();
                // 5%概率：生成1道具
                player.getInventory().offerOrDrop(spawnRandomNekoItems.summonRandomItemFromPool(
                        (ServerWorld) world,
                        null,
                        spawnRandomNekoItems.ALL_POOL_PROBABILITIES_HEIGHT[0],
                        spawnRandomNekoItems.ALL_POOL_PROBABILITIES_HEIGHT[1],
                        spawnRandomNekoItems.ALL_POOL_PROBABILITIES_HEIGHT[2],
                        spawnRandomNekoItems.ALL_POOL_PROBABILITIES_HEIGHT[3]));
                player.sendMessage(Text.literal("§a你获得了 6 枚金币！"), true);
            }
           else if (roll<91-useCount){
                // 9-useCount%概率：随机生成1~10金币
               giveCoins(player, world.random.nextInt(10)+1);
                player.sendMessage(Text.literal("§a你获得了 §c-error- §a枚金币！"), true);
            }
            else {
                world.breakBlock(pos, false);
                world.addBlockBreakParticles(pos, state);
                player.sendMessage(Text.literal("§c老虎机故障爆炸了！"), true);
            }
            incrementUseCount(world, pos);

            return ActionResult.SUCCESS;
        }

        return ActionResult.PASS;
    }

    private void giveCoins(PlayerEntity player, int count) {
        player.getInventory().offerOrDrop(new ItemStack(ModItems.COIN, count));
    }
    private int getUseCount(World world, BlockPos pos) {
        if (world.getBlockEntity(pos) instanceof SlotMachineBlockEntity blockEntity) {
            return blockEntity.getUseCount();
        }
        return 0;
    }
    private void incrementUseCount(World world, BlockPos pos) {
        if (world.getBlockEntity(pos) instanceof SlotMachineBlockEntity blockEntity) {
            blockEntity.incrementUseCount();
            blockEntity.markDirty();
        }
    }

    @Override
    public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new SlotMachineBlockEntity(pos,state);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return VoxelShapes.cuboid(0.0, 0.0, 0.0, 1.0, 1.6, 1.0);
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return VoxelShapes.cuboid(0.0, 0.0, 0.0, 1.0, 1.6, 1.0);
    }
}
