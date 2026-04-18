package com.nekoadventure.item;

import com.nekoadventure.item.nekoItem.AbstractNumItem;
import net.minecraft.entity.Entity;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.screen.slot.Slot;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ClickType;
import net.minecraft.util.Hand;
import net.minecraft.util.Rarity;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import java.util.UUID;

public class NekoPackageItem extends Item {
    boolean recycle = true;
    double[] finalData = new double[4]; // [0]=health, [1]=speed, [2]=strength，[3]=attackSpeed
    int recycleTime = 0;

    public NekoPackageItem(Settings settings) {
        super(settings.maxCount(1).rarity(Rarity.EPIC));
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);
        if (!world.isClient) {
            // 使用 Bundle 的存储逻辑
            NbtCompound nbt = stack.getOrCreateNbt();
            if (!nbt.contains("Items")) {
                nbt.put("Items", new NbtList());
            }

            // 打开简单的物品选择界面
            user.sendMessage(Text.literal("背包内容:"), false);
            NbtList items = nbt.getList("Items", NbtCompound.COMPOUND_TYPE);
            for (int i = 0; i < items.size(); i++) {
                ItemStack itemStack = ItemStack.fromNbt(items.getCompound(i));
                user.sendMessage(Text.literal("- " + itemStack.getName().getString()
                        + " x" + itemStack.getCount()), false);
            }
        }

        return TypedActionResult.success(stack);
    }

    @Override
    public boolean onStackClicked(ItemStack stack, Slot slot, ClickType clickType, PlayerEntity player) {
        if (clickType == ClickType.RIGHT && !player.isSneaking()) {
            // 右键点击物品栏中的物品放入背包
            ItemStack slotStack = slot.getStack();
            if (!slotStack.isEmpty()) {
                //只允许模组的道具进入背包
                //不用做输出物品：毕竟这个道具之后也拿不出来
                if (slotStack.getItem() instanceof AbstractNumItem) {
                    {
                        this.addToBag(stack, slotStack);
                        slotStack.decrement(1);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    //添加物品
    private void addToBag(ItemStack bag, ItemStack item) {
        NbtCompound nbt = bag.getOrCreateNbt();
        NbtList items = nbt.getList("Items", NbtCompound.COMPOUND_TYPE);

        NbtCompound itemNbt = new NbtCompound();
        item.writeNbt(itemNbt);
        items.add(itemNbt);

        nbt.put("Items", items);
        bag.setNbt(nbt);
    }

    //所有数值类道具的入口（我知道这里的代码很烂，但是目前的我没招）
    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        super.inventoryTick(stack, world, entity, slot, selected);
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
            if (player.isHolding(stack.getItem())) {
                if (recycleTime < 100) {
                    recycleTime++;
                }
                //时间自增
                if (recycle) {
                    if (!world.isClient) {
                        NbtCompound nbt = stack.getOrCreateNbt();
                        if (!nbt.contains("Items")) {
                            nbt.put("Items", new NbtList());
                        }
                        //遍历来读取数值
                        player.sendMessage(Text.of("一级测试"), false);
                        NbtList items = nbt.getList("Items", NbtCompound.COMPOUND_TYPE);
                        for (int i = 0; i < items.size(); i++) {
                            ItemStack itemStack = ItemStack.fromNbt(items.getCompound(i));
                            //强转
                            AbstractNumItem numItem = (AbstractNumItem) itemStack.getItem();
                            // [0]=health, [1]=speed, [2]=strength，[3]=attackSpeed
                            //开始实际给数值了
                            finalData[0] += numItem.getHealth();
                            finalData[1] += numItem.getSpeed();
                            finalData[2] += numItem.getStrength();
                            finalData[3] += numItem.getAttackSpeed();
                        }
                        recycle = false;
                        if (damageAttr != null) {
                            damageAttr.removeModifier(UUID.fromString("069a79f4-44e9-4726-a5be-fca90e38aaf2"));
                        }
                        if (speedAttr != null) {
                            speedAttr.removeModifier(UUID.fromString("069a79f4-44e9-4726-a5be-fca90e38aaf1"));
                        }
                        if (healthAttr != null) {
                            healthAttr.removeModifier(UUID.fromString("069a79f4-44e9-4726-a5be-fca90e38aaf0"));
                        }
                        if (attackSpeedAttr != null) {
                            attackSpeedAttr.removeModifier(UUID.fromString("069a79f4-44e9-4726-a5be-fca90e38aaf3"));
                        }
                        player.sendMessage(Text.of("最终的生命是" + finalData[0] + "_" + "最终的速度是" + finalData[1] + "_" + "最终的力量是" + finalData[2] + "_" + "最终的攻速是" + finalData[3]));
                        //应用数值
                        if (finalData[0] > 0) {
                            EntityAttributeModifier healthModifier = new EntityAttributeModifier(
                                    UUID.fromString("069a79f4-44e9-4726-a5be-fca90e38aaf0"),
                                    "Neko Package Health",
                                    finalData[0],  // 生命值
                                    EntityAttributeModifier.Operation.ADDITION
                            );
                            if (healthAttr != null) {
                                healthAttr.addTemporaryModifier(healthModifier);
                            }
                        }
                        if (finalData[1] > 0) {
                            if (finalData[1] > 10) {
                                finalData[1] = 10;//卡上限，防止飞起来
                            }
                            EntityAttributeModifier speedModifier = new EntityAttributeModifier(
                                    UUID.fromString("069a79f4-44e9-4726-a5be-fca90e38aaf1"),
                                    "Neko Package Speed",
                                    finalData[1] * 0.02,  // 速度值（太快了得乘0.02）
                                    EntityAttributeModifier.Operation.ADDITION
                            );
                            if (speedAttr != null) {
                                speedAttr.addTemporaryModifier(speedModifier);
                            }
                        }
                        if (finalData[2] > 0) {
                            EntityAttributeModifier strengthModifier = new EntityAttributeModifier(
                                    UUID.fromString("069a79f4-44e9-4726-a5be-fca90e38aaf2"),
                                    "Neko Package Strength",
                                    finalData[2],  // 力量值
                                    EntityAttributeModifier.Operation.ADDITION
                            );
                            if (damageAttr != null) {
                                damageAttr.addTemporaryModifier(strengthModifier);
                            }
                        }
                        if (finalData[3] > 0) {
                            EntityAttributeModifier attackSpeedModifier = new EntityAttributeModifier(
                                    UUID.fromString("069a79f4-44e9-4726-a5be-fca90e38aaf3"),
                                    "Neko Package AttackSpeed",
                                    finalData[3],//攻速值
                                    EntityAttributeModifier.Operation.ADDITION
                            );
                            if (attackSpeedAttr != null) {
                                attackSpeedAttr.addTemporaryModifier(attackSpeedModifier);
                            }
                        }
                    }
                }
                //如果按下特定按键则则更新数据
                if (player.isSneaking()&&!recycle && recycleTime > 99 && !player.isSpectator()) {
                    finalData[0] = 0;
                    finalData[1] = 0;
                    finalData[2] = 0;
                    finalData[3] = 0;
                    player.sendMessage(Text.of("重置！"));
                    player.playSound(SoundEvents.ENTITY_CAT_AMBIENT, 1.0F, 1.0F);
                    //重置数据
                    recycle = true;
                    recycleTime = 0;
                }
            }
            else {
                if (damageAttr != null) {
                    damageAttr.removeModifier(UUID.fromString("069a79f4-44e9-4726-a5be-fca90e38aaf2"));
                }
                if (speedAttr != null) {
                    speedAttr.removeModifier(UUID.fromString("069a79f4-44e9-4726-a5be-fca90e38aaf1"));
                }
                if (healthAttr != null) {
                    healthAttr.removeModifier(UUID.fromString("069a79f4-44e9-4726-a5be-fca90e38aaf0"));
                }
                if (attackSpeedAttr != null) {
                    attackSpeedAttr.removeModifier(UUID.fromString("069a79f4-44e9-4726-a5be-fca90e38aaf3"));
                }
            }
        }
    }
}
