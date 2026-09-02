package com.nekoadventure.item.other;

import com.nekoadventure.NekoAdventure;
import com.nekoadventure.client.ShiftKeyHelper;
import com.nekoadventure.item.ModItems;
import com.nekoadventure.item.nekoItem.AbstractNekoItem;
import com.nekoadventure.item.nekoItem.attackTypeItem.AttackTypeItem;
import com.nekoadventure.item.nekoItem.functionTypeItem.functionItems.FunctionItem;
import com.nekoadventure.network.NekoPackageDataManager;
import com.nekoadventure.network.NekoPackageDataNetworking;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.screen.slot.Slot;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.*;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class NekoPackageItem extends Item {

    //0=调试模式，1=未完成地牢，2=完成地牢
    public static final String FINISHED_KEY = "getIsFinished";

    public NekoPackageItem(Settings settings) {
        super(settings.maxCount(1).rarity(Rarity.EPIC));
    }

    //这里是特殊攻击方式的入口
    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        ItemStack stack = player.getStackInHand(hand);
        if (getIsFinished(stack)==2) {
            return TypedActionResult.fail(stack);
        }
        if (!player.getItemCooldownManager().isCoolingDown(this)) {
            if (!world.isClient) {
                if (!NekoPackageItem.getNekoItem(player.getOffHandStack(), false, 3).isEmpty()) {
                    if (player.getAttackCooldownProgress(0.0f) == 1) {
                        NekoPackageItem.applyMainAttackTypeItem(player);
                    }
                    return TypedActionResult.success(stack);
                }
            }
        }
        return TypedActionResult.fail(stack);
    }

    @Override
    public boolean onStackClicked(ItemStack stack, Slot slot, ClickType clickType, PlayerEntity player) {
        if (getIsFinished(stack)==2) {
            return false;
        }
        if (clickType == ClickType.RIGHT && !player.isSneaking()) {
            ItemStack slotStack = slot.getStack();
            if (!slotStack.isEmpty()) {
                if (slotStack.getItem() instanceof AbstractNekoItem) {
                    {
                        addToBag(stack, slotStack);
                        slotStack.decrement(1);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        super.inventoryTick(stack, world, entity, slot, selected);
        //未通关的纸盒离开迷宫维度时，非创造模式玩家将自动清除（通关后的纸盒保留，但功能被禁用）
        if (world instanceof ServerWorld && getIsFinished(stack)==1 && !isMazeDimension((ServerWorld) world)
                && entity instanceof PlayerEntity player && !player.isCreative()) {
            stack.decrement(1);
            return;
        }
        if (entity instanceof PlayerEntity player) {
                EntityAttributeInstance damageAttr = player.getAttributeInstance(
                        EntityAttributes.GENERIC_ATTACK_DAMAGE
                );
                EntityAttributeInstance speedAttr = player.getAttributeInstance(
                        EntityAttributes.GENERIC_MOVEMENT_SPEED
                );
                EntityAttributeInstance healthAttr = player.getAttributeInstance(
                        EntityAttributes.GENERIC_MAX_HEALTH
                );
                EntityAttributeInstance attackSpeedAttr = player.getAttributeInstance(
                        EntityAttributes.GENERIC_ATTACK_SPEED
                );

                if (player.getOffHandStack().getItem().equals(ModItems.NEKO_PACKAGE)) {
                    if (getIsFinished(player.getOffHandStack())==2) {return;}
                    if (!world.isClient) {
                        NekoPackageDataManager.PlayerNekoData data = NekoPackageDataManager.getOrCreate(player);
                        if (data.recycleTime < 100) {
                            data.recycleTime++;
                        }
                        if (data.recycle || data.recycleTime >= 100) {
                            Arrays.fill(data.nekoData, 0.0);
                            addNekoItemData(player);
                            applyData(player, damageAttr, speedAttr, healthAttr, attackSpeedAttr);
                        }
                    }
                    //这里用来执行FunctionItem的方法
                    NekoPackageItem.applyFunction(NekoPackageItem.getNekoItem(player.getOffHandStack(), false, 4), player, 1);

                }
                else {
                    if (!world.isClient) {
                        NekoPackageDataManager.PlayerNekoData data = NekoPackageDataManager.get(player);
                        if (data != null) {
                            Arrays.fill(data.nekoData, 0.0);
                        }
                    }
                    clearPlayerAttributeInstance(damageAttr, speedAttr, healthAttr, attackSpeedAttr);
                }
        }
    }

    //这个方法是用来添加数值的
    private void addNekoItemData(PlayerEntity player) {
        //遍历来读取物品数值
        {
            NekoPackageDataManager.PlayerNekoData data = NekoPackageDataManager.getOrCreate(player);
            ArrayList items=getNekoItem(player.getOffHandStack(),true,0);
            for (Object item : items) {
                AbstractNekoItem nekoItem = (AbstractNekoItem) item;
                data.nekoData[0] += nekoItem.getHealth();
                data.nekoData[1] += nekoItem.getSpeed();
                data.nekoData[2] += nekoItem.getStrength();
                data.nekoData[3] += nekoItem.getAttackSpeed();
                data.nekoData[4] += nekoItem.getAttackRange();
                data.nekoData[5] += nekoItem.getAttackMultiplier();
                data.nekoData[6] += nekoItem.getAttackSpeedMultiplier();

            }
        }
    }
    //这个方法是用来应用数值到玩家身上
    private void applyData(PlayerEntity player, EntityAttributeInstance damageAttr, EntityAttributeInstance speedAttr,
                           EntityAttributeInstance healthAttr, EntityAttributeInstance attackSpeedAttr) {
        NekoPackageDataManager.PlayerNekoData data = NekoPackageDataManager.getOrCreate(player);
        //先清除上一轮的数据（没有就会跳过）
        data.recycle = false;
        data.finalData[0]=data.nekoData[0]+data.otherData[0];
        data.finalData[1]=data.nekoData[1]+data.otherData[1];
        data.finalData[2]=data.nekoData[2]+data.otherData[2];
        data.finalData[3]=data.nekoData[3]+data.otherData[3];
        data.finalData[4]=data.nekoData[4]+data.otherData[4];
        data.finalData[5]=data.nekoData[5]+data.otherData[5];
        data.finalData[6]=data.nekoData[6]+data.otherData[6];
        calculateData(data.finalData);
        clearPlayerAttributeInstance(damageAttr, speedAttr, healthAttr, attackSpeedAttr);
        // 生命值
        if (data.finalData[0] > 0) {
            EntityAttributeModifier healthModifier = new EntityAttributeModifier(
                    UUID.fromString("550e8400-e29b-41d4-a716-446655440000"),
                    "Neko Package Health",
                    data.finalData[0],
                    EntityAttributeModifier.Operation.ADDITION
            );
            if (healthAttr != null) {
                healthAttr.addTemporaryModifier(healthModifier);
            }
        }
        // 速度值（太快了得乘0.05）
        if (data.finalData[1] > 0) {
            if (data.finalData[1] > 10) {
                data.finalData[1] = 10;//卡上限，防止速度太快
            }
            EntityAttributeModifier speedModifier = new EntityAttributeModifier(
                    UUID.fromString("550e8400-e29b-41d4-a716-446655440001"),
                    "Neko Package Speed",
                   data.finalData[1]*0.05,
                    EntityAttributeModifier.Operation.ADDITION
            );
            if (speedAttr != null) {
                speedAttr.addTemporaryModifier(speedModifier);
            }
        }
        if (data.finalData[2]==0){
            data.finalData[2]=1;
        }
        // 力量值
        EntityAttributeModifier strengthModifier = new EntityAttributeModifier(
                UUID.fromString("550e8400-e29b-41d4-a716-446655440002"),
                "Neko Package Strength",
                data.finalData[2],
                EntityAttributeModifier.Operation.ADDITION
        );
        if (damageAttr != null) {
            damageAttr.addTemporaryModifier(strengthModifier);
        }
        //攻速值（*0.2）
        if (data.finalData[3]>12) {
            data.finalData[3]=12;
        }
        EntityAttributeModifier attackSpeedModifier = new EntityAttributeModifier(
                UUID.fromString("550e8400-e29b-41d4-a716-446655440003"),
                "Neko Package AttackSpeed",
                data.finalData[3]*0.2,
                EntityAttributeModifier.Operation.ADDITION
        );
        if (attackSpeedAttr != null) {
            attackSpeedAttr.addTemporaryModifier(attackSpeedModifier);
        }
        //射程值
        if (data.finalData[4] > 32) {
            data.finalData[4] = 32;//卡上限，防止一刀劈到三百米开外的人
        }
        if (data.finalData[4] < 4) {
            data.finalData[4] = 4;
        }
        if (player instanceof ServerPlayerEntity serverPlayer) {
            NekoPackageDataNetworking.sendToPlayer(serverPlayer, data.finalData);
        }
    }

    private void calculateData(double[] data) {
        data[1]= data[1] * 0.02;
        data[2]= data[2] * data[5] + data[2];
        data[3]= data[3] + data[3] * data[6];
        if (data[3]==0){
            data[3]= data[3]+ data[6];
        }
        if (data[3]<-1){
            data[3]=-1;
        }
        if (data[2]==0){
            data[2]= data[2]+ data[5];
        }
        if (data[2]<0){
            data[2]=1;
        }
    }

    private void clearPlayerAttributeInstance(EntityAttributeInstance damageAttr, EntityAttributeInstance speedAttr, EntityAttributeInstance healthAttr, EntityAttributeInstance attackSpeedAttr) {
        if (damageAttr != null) {
            damageAttr.removeModifier(UUID.fromString("550e8400-e29b-41d4-a716-446655440002"));
        }
        if (speedAttr != null) {
            speedAttr.removeModifier(UUID.fromString("550e8400-e29b-41d4-a716-446655440001"));
        }
        if (healthAttr != null) {
            healthAttr.removeModifier(UUID.fromString("550e8400-e29b-41d4-a716-446655440000"));
        }
        if (attackSpeedAttr != null) {
            attackSpeedAttr.removeModifier(UUID.fromString("550e8400-e29b-41d4-a716-446655440003"));
        }
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);
        if (ShiftKeyHelper.isShiftDown()) {
            tooltip.add(Text.of("用鼠标提起该物品，同时对着一些奇怪的道具按下右键就能装载(参考1.21的收纳袋)"));
            tooltip.add(Text.of("记得将猫咪纸盒放在副手来应用数值"));
        }
        else {
            tooltip.add(Text.of("尝试将一些奇怪的道具塞入这里吧！"));
            tooltip.add(Text.of("按下shift键以了解更多"));
        }

    }

    /*=========================================================================================================
     * 这里是一条分界线，往上是这个物品本身需要的方法，往下是给其他物品使用的方法
     * 因为这个类是要给其他所有道具对应的方法所以就一股脑全部堆到这里了
     * 也不知道对不对，反正这么干我写代码也更方便，毕竟在这里改方法就行了
     ==========================================================================================================*/

    //这个方法是用来应用主攻击方式
    public static void applyMainAttackTypeItem(PlayerEntity player) {
        AttackTypeItem finalAttackMainItem = null;
        ItemStack stack = player.getStackInHand(Hand.OFF_HAND);
        if (getIsFinished(stack)==2) {return;}
        NbtCompound nbt = stack.getOrCreateNbt();
        if (!nbt.contains("Items")) {
            nbt.put("Items", new NbtList());
        }
        NbtList items = nbt.getList("Items", NbtCompound.COMPOUND_TYPE);
        {

            for (int i = 0; i < items.size(); i++) {
                ItemStack itemStack = ItemStack.fromNbt(items.getCompound(i));
                if (itemStack.getItem() instanceof AttackTypeItem attackTypeItem && !attackTypeItem.getIsSpecific()) {
                    finalAttackMainItem=attackTypeItem;
                }
            }
            if (finalAttackMainItem != null) {
                finalAttackMainItem.changeMainAttackType(player);
            }
        }
    }
    public static void applyOffAttackTypeItem(PlayerEntity player) {
        AttackTypeItem finalAttackOffItem=null;
        AttackTypeItem finalAttackMainItem = null;
        ItemStack stack = player.getStackInHand(Hand.OFF_HAND);
        if (getIsFinished(stack)==2) {return;}
        NbtCompound nbt = stack.getOrCreateNbt();
        if (!nbt.contains("Items")) {
            nbt.put("Items", new NbtList());
        }
        NbtList items = nbt.getList("Items", NbtCompound.COMPOUND_TYPE);
        {

            for (int i = 0; i < items.size(); i++) {
                ItemStack itemStack = ItemStack.fromNbt(items.getCompound(i));
                if (itemStack.getItem() instanceof AttackTypeItem attackTypeItem && !attackTypeItem.getIsSpecific()) {
                    finalAttackOffItem=finalAttackMainItem;
                    finalAttackMainItem=attackTypeItem;
                }
            }
            if (finalAttackOffItem != null) {
                finalAttackOffItem.changeOffAttackType(player);
            }

        }
    }

    //这些方法用来取出最后算出来的数值（数据按玩家存储，所以都要传玩家）
    public double getAttackRange(PlayerEntity player) {
        double[] finalData = NekoPackageDataManager.getFinalData(player);
        if (finalData == null || finalData[4] <= 0) {
            return 3;
        }
        return finalData[4];
    }
    public double getStrength(PlayerEntity player){
        double[] finalData = NekoPackageDataManager.getFinalData(player);
        if (finalData == null || finalData[2] <= 0) {
            return 1;
        }
        return finalData[2];
    }

    public double[] getFinalData(PlayerEntity player){
        return NekoPackageDataManager.getFinalData(player);
    }

    public void addOtherData(PlayerEntity player, double[] otherData1) {
        NekoPackageDataManager.addOtherData(player, otherData1);
    }

    //这个方法是用来提取物品的
    //这里的boolean是判断道具是否可重复
    //getType=0:拿全部道具,=1:拿特效类道具,=3:拿改变攻击类道具，=4:拿功能类道具
    public static ArrayList<AbstractNekoItem> getNekoItem(ItemStack stack, boolean isRepeatable,int getType) {
        ArrayList<AbstractNekoItem> nekoItems = new ArrayList<>();
        NbtCompound nbt = stack.getOrCreateNbt();
        if (!nbt.contains("Items")) {nbt.put("Items", new NbtList());}
        NbtList items = nbt.getList("Items", NbtCompound.COMPOUND_TYPE);
        {
            for (int i = 0; i < items.size(); i++) {
                ItemStack itemStack = ItemStack.fromNbt(items.getCompound(i));
                if (getType==0) {
                    if (itemStack.getItem() instanceof AbstractNekoItem abstractNekoItem) {
                        nekoItems.add(abstractNekoItem);
                    }
                }
                else if (getType==1) {
                    if (itemStack.getItem() instanceof AttackTypeItem abstractNekoItem&&abstractNekoItem.getIsSpecific()) {
                        nekoItems.add(abstractNekoItem);
                    }
                }
                else if (getType==3) {
                    if (itemStack.getItem() instanceof AttackTypeItem abstractNekoItem&&!abstractNekoItem.getIsSpecific()) {
                        nekoItems.add(abstractNekoItem);
                    }
                }
                else if (getType==4) {
                    if (itemStack.getItem() instanceof FunctionItem functionItem) {
                        nekoItems.add(functionItem);
                    }
                }
            }
        }
        if (isRepeatable) {
            return nekoItems;
        } else {
            return new ArrayList<>(new LinkedHashSet<>(nekoItems));
        }
    }
    //这个方法主要是用来调用所有的FunctionItem里面的方法，1代表tickFunction方法，2代表HurtFunctionItem方法
    /**@return true=可以执行免伤效果;false=不能执行免伤效果
     **/
    public static boolean applyFunction(ArrayList items, PlayerEntity player, int choose){
        boolean z=false;
        for (int i = 0; i < items.size();) {
            if (choose==1){
                if (items.get(i) instanceof FunctionItem functionItem) {
                    functionItem.applyTickFunctionItem(player);
                }
            }
            else if (choose==2){
                if (items.get(i) instanceof FunctionItem functionItem) {
                    if (functionItem.applyDamagedFunctionItem(player)) {
                        z=true;
                    }
                }
            }
            i++;
        }
        return z;
    }

    //添加物品
    public static void addToBag(ItemStack bag, ItemStack item) {
        NbtCompound nbt = bag.getOrCreateNbt();
        NbtList items = nbt.getList("Items", NbtCompound.COMPOUND_TYPE);
        NbtCompound itemNbt = new NbtCompound();

        item.writeNbt(itemNbt);
        items.add(itemNbt);
        nbt.put("Items", items);
        bag.setNbt(nbt);
    }

    @Override
    public ItemStack getDefaultStack() {
        ItemStack stack = super.getDefaultStack();
        stack.getOrCreateNbt().putInt(FINISHED_KEY, 1);
        return stack;
    }

    public void setFinished(ItemStack stack, int count) {
        stack.getOrCreateNbt().putInt(FINISHED_KEY, count);
    }

    public static int getIsFinished(ItemStack stack) {
        NbtCompound nbt = stack.getNbt();
        if (nbt==null){
            return 2;
        }
        return nbt.getInt(FINISHED_KEY);
    }

    private boolean isMazeDimension(ServerWorld world) {
        TagKey<DimensionType> isMazeTag = TagKey.of(
                RegistryKeys.DIMENSION_TYPE,
                new Identifier(NekoAdventure.MOD_ID, "is_maze")
        );
        return world.getDimensionEntry().isIn(isMazeTag);
    }
}
