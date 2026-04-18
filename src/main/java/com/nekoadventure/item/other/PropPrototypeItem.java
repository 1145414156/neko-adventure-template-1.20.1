package com.nekoadventure.item.other;

import com.nekoadventure.block.specialroomblock.AbstractRoomBlock;
import com.nekoadventure.client.ShiftKeyHelper;
import com.nekoadventure.item.nekoItem.AbstractNekoItem;
import com.nekoadventure.item.soulItem.AbstractSoulPoolItem;
import com.nekoadventure.other.itemApart.SpawnRandomNekoItems;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtString;
import net.minecraft.registry.Registries;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.*;

/**
 * 道具原型：一个可读取房间道具池并抽取道具的物品。
 * <p>
 * 使用逻辑（状态机，以 NBT 中是否已储存道具池来区分）：
 * <ul>
 *   <li>第一次使用（NBT 中无道具池）：扫描以玩家为中心的 7×7×7 范围，
 *       找到第一个 {@link AbstractRoomBlock}，读取其房间道具池，打乱后储存进 NBT。</li>
 *   <li>已储存道具池且副手为 {@link AbstractSoulPoolItem}：用该魂石的
 *       {@code getStoragePoolItems} 取出两边道具池都拥有的道具（交集）作为新的道具池，
 *       并消耗副手魂石。可反复用多个魂石逐步过滤，直到副手为空。</li>
 *   <li>已储存道具池且副手为空（或非魂石）：从当前道具池中随机抽取一个道具生成到世界，
 *       并将该道具从池中移除（不放回抽取）。</li>
 * </ul>
 */
public class PropPrototypeItem extends Item {
    // NBT 中储存道具池的键
    private static final String POOL_KEY = "neko_stored_pool";
    // 扫描房间方块的范围半径（7×7×7 => 每轴 ±3）
    private static final int SCAN_RADIUS = 3;

    private final SpawnRandomNekoItems spawnRandomNekoItems = new SpawnRandomNekoItems();

    public PropPrototypeItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        ItemStack stack = player.getStackInHand(hand);
        if (world.isClient) {
            return TypedActionResult.pass(stack);
        }
        ServerWorld serverWorld = (ServerWorld) world;

        List<Item> pool = readStoredPool(stack);

        // 第一次使用：尚未储存道具池，扫描附近房间方块并储存打乱后的道具池
        if (pool.isEmpty()) {
            AbstractRoomBlock roomBlock = findNearbyRoomBlock(serverWorld, player);
            if (roomBlock == null) {
                player.sendMessage(Text.literal("§c附近 7×7×7 范围内没有找到房间方块"), true);
                return TypedActionResult.fail(stack);
            }
            Set<Item> roomPool = spawnRandomNekoItems.getRoomPool(serverWorld, roomBlock);
            List<Item> shuffled = new ArrayList<>(roomPool);
            Collections.shuffle(shuffled);
            writeStoredPool(stack, shuffled);
            player.sendMessage(Text.literal("§e已读取房间道具池，共 " + shuffled.size() + " 个道具"), true);
            return TypedActionResult.success(stack);
        }

        // 已储存道具池：副手为魂石池道具时进行过滤（取交集），否则直接抽取
        ItemStack offhand = player.getOffHandStack();
        if (offhand.getItem() instanceof AbstractSoulPoolItem soulPoolItem) {
            List<Item> filtered = filterPoolBySoul(pool, soulPoolItem,player);
            writeStoredPool(stack, filtered);
            offhand.decrement(1);
            player.sendMessage(Text.literal("§b道具池已过滤，剩余 " + filtered.size() + " 个道具"), true);
            return TypedActionResult.success(stack);
        }

        // 副手为空（或非魂石池道具）：随机抽取一个道具生成，并将其从池中移除
        if (player.getMainHandStack().getItem().equals(this)&&player.getOffHandStack().isEmpty() ) {
            Item drawn = pool.get(world.getRandom().nextInt(pool.size()));
            pool.remove(drawn);
            writeStoredPool(stack, pool);
            ItemEntity itemEntity = new ItemEntity(serverWorld, player.getX(), player.getY() + 0.5, player.getZ(), new ItemStack(drawn));
            itemEntity.setPickupDelay(0);
            serverWorld.spawnEntity(itemEntity);
            player.sendMessage(Text.literal("§a抽到了：" + drawn.getName().getString()), true);
            player.getMainHandStack().decrement(1);
        }
        return TypedActionResult.success(stack);
    }

    /**
     * 用魂石池道具过滤当前道具池，取两边都拥有的道具（交集）。
     * 魂石的 {@code getStoragePoolItems} 定义了它从输入池中保留哪些道具。
     */
    private List<Item> filterPoolBySoul(List<Item> pool, AbstractSoulPoolItem soulPoolItem, PlayerEntity player) {
        List<Item> currentNekoItems = new ArrayList<>();
        for (Item item : pool) {
            if (item instanceof AbstractNekoItem nekoItem) {
                currentNekoItems.add(nekoItem);
            }
        }
        if (currentNekoItems.isEmpty()) {
            return Collections.emptyList();
        }

        List<Item> soulSelected = soulPoolItem.getStoragePoolItems(currentNekoItems, player);
        if (soulSelected == null) {
            return Collections.emptyList();
        }

        // 如果魂石返回的池子与当前NekoItems内容完全相同，直接返回外部传入的池子
        if (soulSelected.size() == currentNekoItems.size() &&
                new HashSet<>(soulSelected).equals(new HashSet<>(currentNekoItems))) {
            return soulSelected;
        }

        // 取交集：仅保留同时存在于当前道具池中的、被魂石选中的道具
        Set<Item> soulSelectedSet = new HashSet<>(soulSelected);
        List<Item> intersected = new ArrayList<>();
        for (Item item : pool) {
            if (soulSelectedSet.contains(item)) {
                intersected.add(item);
            }
        }
        if (!intersected.isEmpty()) {
            return intersected;
        } else {
            return Collections.emptyList();
        }
    }

    /**
     * 在以玩家为中心的 7×7×7 范围内查找第一个 {@link AbstractRoomBlock}。
     */
    private AbstractRoomBlock findNearbyRoomBlock(ServerWorld world, PlayerEntity player) {
        BlockPos center = player.getBlockPos();
        for (BlockPos pos : BlockPos.iterate(
                center.getX() - SCAN_RADIUS, center.getY() - SCAN_RADIUS, center.getZ() - SCAN_RADIUS,
                center.getX() + SCAN_RADIUS, center.getY() + SCAN_RADIUS, center.getZ() + SCAN_RADIUS)) {
            if (world.getBlockState(pos).getBlock() instanceof AbstractRoomBlock roomBlock) {
                return roomBlock;
            }
        }
        return null;
    }

    /**
     * 对外暴露读取储存道具池的能力（供客户端悬停预览等使用）。
     * 键缺失或数据非法时返回空列表（防御性读取）。
     */
    public List<Item> getPoolItems(ItemStack stack) {
        return readStoredPool(stack);
    }

    /**
     * 从 NBT 读取储存的道具池。键缺失或数据非法时返回空列表（防御性读取）。
     */
    private List<Item> readStoredPool(ItemStack stack) {
        List<Item> result = new ArrayList<>();
        NbtCompound nbt = stack.getNbt();
        if (nbt == null || !nbt.contains(POOL_KEY, NbtElement.LIST_TYPE)) {
            return result;
        }
        NbtList list = nbt.getList(POOL_KEY, NbtElement.STRING_TYPE);
        for (NbtElement element : list) {
            Identifier id = Identifier.tryParse(element.asString());
            if (id != null && Registries.ITEM.containsId(id)) {
                result.add(Registries.ITEM.get(id));
            }
        }
        return result;
    }

    /**
     * 将道具池以注册名（Identifier 字符串）列表的形式写入 NBT。
     */
    private void writeStoredPool(ItemStack stack, List<Item> pool) {
        NbtCompound nbt = stack.getOrCreateNbt();
        NbtList list = new NbtList();
        for (Item item : pool) {
            Identifier id = Registries.ITEM.getId(item);
            list.add(NbtString.of(id.toString()));
        }
        nbt.put(POOL_KEY, list);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);
        int poolSize = readStoredPool(stack).size();
        if (poolSize == 0) {
            tooltip.add(Text.literal("尚未绑定道具池：靠近房间方块使用以读取").formatted(Formatting.GRAY));
        } else {
            tooltip.add(Text.literal("已储存道具池：" + poolSize + " 个道具，副手为空的时候按下右键可随机提取").formatted(Formatting.GOLD));
            if (!ShiftKeyHelper.isShiftDown()) {
                tooltip.add(Text.literal("按下Shift键了解储存的道具").formatted(Formatting.GRAY));
            }
            else {
                tooltip.add(Text.literal("尝试将某些特殊魂石放在副手并按下右键来改变道具池吧").formatted(Formatting.GREEN));
            }
        }
    }

    @Override
    public boolean hasGlint(ItemStack stack) {
        NbtCompound nbt = stack.getNbt();
        return nbt!=null&&nbt.contains(POOL_KEY, NbtElement.LIST_TYPE);
    }
}
