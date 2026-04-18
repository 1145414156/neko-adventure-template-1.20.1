package com.nekoadventure.item.soulItem;

import com.nekoadventure.client.ShiftKeyHelper;
import com.nekoadventure.sound.ModSoundEvents;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;


//这个是所有魂石类道具的全部基础
//魂石具有以下特性：
//1.默认耐久为0，需要充能满才能使用
//2.只能使用一次（除非有特别说明）
//3.通过打怪充能（除非有特别说明）
//做个比喻：类似于以撒的结合里面的卡牌+药丸+主动道具的结合体（主要是减少一点点设定，方便新玩家入坑吧....）
public abstract class AbstractSoulItem extends Item {
    Text text;
    private final int maxCharge;

    public  AbstractSoulItem(Settings settings, int maxCharge) {
        super(settings.maxDamage(maxCharge));
        this.maxCharge = maxCharge;
    }

    public abstract void onUseEffect(World world, PlayerEntity player, ItemStack stack);

    // ========== 充能管理方法 ==========

    public boolean isFullyCharged(ItemStack stack) {
        return stack.getDamage() == 0;
    }

    public void addCharged(ItemStack stack, int amount) {
        // 使用原版耐久系统增加充能（即修复耐久）
        if (maxCharge == 0) {return;}
        int currentDamage = stack.getDamage();
        int newDamage = Math.max(0, currentDamage - amount);
        stack.setDamage(newDamage);

        // 如果满充能了，显示提示
        if (newDamage == 0) {
            if (stack.getHolder() != null) {
                stack.getHolder().getWorld().playSound(null,stack.getHolder().getBlockPos(), ModSoundEvents.ELECTRICITY_RUN_THROUGH,
                        SoundCategory.PLAYERS,1.0F,1.0F);
            }
            // 物品已满充能，并且播放音效
        }
    }
    // ========== 物品使用逻辑 ==========

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        ItemStack stack = player.getStackInHand(hand);

        if (!world.isClient) {
            // 检查是否满充能（满耐久）
            if (!isFullyCharged(stack)) {
                return TypedActionResult.fail(stack);
            }

            onUseEffect(world, player, stack);

            stack.decrement(1);

            player.getWorld().playSound(null,player.getBlockPos(), SoundEvents.BLOCK_GLASS_BREAK, SoundCategory.PLAYERS,1.0F,1.0F);

            return TypedActionResult.success(stack);
        }

        return TypedActionResult.pass(stack);
    }

    // ========== 物品显示相关 ==========

    // 满充能时发光
    @Override
    public boolean hasGlint(ItemStack stack) {
        return isFullyCharged(stack);
    }

    @Override
    public boolean isItemBarVisible(ItemStack stack) {return true;}


    @Override
    public ItemStack getDefaultStack() {
        ItemStack stack = new ItemStack(this);
        if (maxCharge==0){return stack;}
        stack.setDamage(maxCharge);
        return stack;
    }
    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);
        if (!ShiftKeyHelper.isShiftDown()) {
            tooltip.add(Text.of("按住Shift显示描述").copy().formatted(Formatting.GRAY));
            return;
        }
        if (text!=null) {
            tooltip.add(text);
        }
    }
}
