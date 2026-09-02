package com.nekoadventure.block.other;

import com.nekoadventure.item.ModItems;
import com.nekoadventure.item.nekoItem.AbstractNekoItem;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class WishPoolBlock extends Block {
    public WishPoolBlock(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (world.isClient) {
            return ActionResult.SUCCESS;
        }
        ItemStack heldItem = player.getStackInHand(hand);
        if (heldItem.getItem() == ModItems.COIN) {
            heldItem.decrement(1);
            int roll = world.random.nextInt(100);

            if (roll < 50) {
                world.addBlockBreakParticles(pos, state);
            } else if (roll < 80) {
                // 30%概率：随机杂物（大概）
                giveRandomJunkItem(world, player);
            } else if (roll < 90) {
                // 10%概率：随机原版矿物
                giveRandomOreItem(world, player);
            } else if (roll < 98) {
               // 8%概率：生成怪物
                spawnRandomMob(world, pos);
            } else {
                // 2%概率：随机道具
                giveRandomAbstractNekoItem(world, player);
            }

            return ActionResult.SUCCESS;
        }

        return ActionResult.PASS;
    }

    // 辅助方法
    private void giveRandomJunkItem(World world, PlayerEntity player) {
        List<Item> junkItems = List.of(
                Items.DIRT,
                Items.COBBLESTONE,
                Items.SAND,
                Items.GRAVEL,
                Items.CLAY,
                Items.BONE,
                Items.STRING,
                Items.FEATHER,
                Items.PAPER,
                Items.SUGAR_CANE,
                Items.ROTTEN_FLESH,
                Items.SPIDER_EYE,
                Items.INK_SAC,
                Items.GLASS_BOTTLE,
                Items.LEATHER,
                Items.GUNPOWDER
        );

        Item randomJunk = junkItems.get(world.random.nextInt(junkItems.size()));
        player.getInventory().offerOrDrop(new ItemStack(randomJunk, 1));
    }

    private void giveRandomOreItem(World world, PlayerEntity player) {
        List<Item> oreItems = List.of(
                Items.COAL,
                Items.IRON_INGOT,
                Items.GOLD_INGOT,
                Items.COPPER_INGOT,
                Items.LAPIS_LAZULI,
                Items.REDSTONE,
                Items.EMERALD,
                Items.DIAMOND,
                Items.NETHERITE_SCRAP
        );
        ItemStack itemStack=new ItemStack(oreItems.get(world.getRandom().nextInt(oreItems.size())));
        player.getInventory().offerOrDrop(itemStack);
    }

    private void giveRandomAbstractNekoItem(World world, PlayerEntity player) {
        List<Item> nekoItems = new ArrayList<>();
        for (Item item : Registries.ITEM) {
            if (item instanceof AbstractNekoItem) {
                nekoItems.add(item);
            }
        }

        if (!nekoItems.isEmpty()) {
            Item randomNekoItem = nekoItems.get(world.random.nextInt(nekoItems.size()));
            player.getInventory().offerOrDrop(new ItemStack(randomNekoItem, 1));
        }
    }

    private void spawnRandomMob(World world, BlockPos pos) {
        List<EntityType<?>> mobs = List.of(
                EntityType.ZOMBIE,
                EntityType.SKELETON,
                EntityType.SPIDER,
                EntityType.CAVE_SPIDER,
                EntityType.CREEPER,
                EntityType.WITCH,
                EntityType.HUSK,
                EntityType.DROWNED,
                EntityType.STRAY,
                EntityType.PHANTOM,
                EntityType.SLIME,
                EntityType.MAGMA_CUBE,
                EntityType.GUARDIAN,
                EntityType.ELDER_GUARDIAN,
                EntityType.RAVAGER,
                EntityType.PILLAGER,
                EntityType.HOGLIN,
                EntityType.ZOGLIN
        );

        EntityType<?> randomMob = mobs.get(world.random.nextInt(mobs.size()));

        BlockPos spawnPos = pos.up(2);
        Entity entity = randomMob.create(world);
        if (entity != null) {
            entity.setPosition(spawnPos.getX() + 0.5, spawnPos.getY(), spawnPos.getZ() + 0.5);
            world.spawnEntity(entity);
        }
    }
}
