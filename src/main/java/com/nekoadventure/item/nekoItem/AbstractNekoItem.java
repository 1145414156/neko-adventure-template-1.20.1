package com.nekoadventure.item.nekoItem;

import com.nekoadventure.client.ShiftKeyHelper;
import com.nekoadventure.item.nekoItem.functionTypeItem.functionItems.AbstractNekoFunctionItem;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public abstract class AbstractNekoItem extends Item {
    public Text text;
    public double health;
    public double strength;
    public double speed;
    public double attackSpeed;
    public double attackRange;
    public double attackMultiplier;
    public double attackSpeedMultiplier;

    public AbstractNekoItem(Settings settings) {
        super(settings.maxCount(1));
    }
    public double getHealth(){
        return health;
    }
    public double getStrength(){
        return strength;
    }
    public double getSpeed(){
        return speed;
    }
    public double getAttackSpeed() {
        return attackSpeed;
    }
    public double getAttackRange(){
        return attackRange;
    }
    public double getAttackMultiplier(){
        return attackMultiplier;
    }
    public double getAttackSpeedMultiplier(){
        return attackSpeedMultiplier;
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);
        //按住Shift才显示道具描述，没按住时只显示提示
        if (!ShiftKeyHelper.isShiftDown()) {
            tooltip.add(Text.of("按住Shift显示描述").copy().formatted(Formatting.GRAY));
            return;
        }
        //这里这一块是为了道具描述只显示有意义的数值
        String textHealth = "";
        String textStrength = "";
        String textSpeed = "";
        String textAttackSpeed = "";
        String textAttackRange = "";
        String textAttackMultiplier = "";
        String textAttackSpeedMultiplier = "";

        if (!(this instanceof AbstractNekoFunctionItem)) {
            if (health!=0){if (health>0){textHealth=" 生命"+"+"+health;} else {textHealth=" 生命"+health;}}
            if (strength!=0){if (strength>0){textStrength=" 力量"+"+"+strength;} else {textStrength=" 力量"+strength;}}
            if (speed!=0){if (speed>0){textSpeed=" 移速"+"+"+speed;} else {textSpeed=" 移速"+speed;}}
            if (attackSpeed!=0){if (attackSpeed>0){textAttackSpeed=" 攻速"+"+"+attackSpeed;} else {textAttackSpeed=" 攻速"+attackSpeed;}}
            if (attackRange!=0){if (attackRange>0){textAttackRange=" 射程"+"+"+attackRange;} else {textAttackRange=" 射程"+attackRange;}}
            if (attackMultiplier!=0){if (attackMultiplier>0){textAttackMultiplier=" 攻击乘区"+"+"+attackMultiplier;} else {textAttackMultiplier=" 攻击乘区"+attackMultiplier;}}
            if (attackSpeedMultiplier!=0){if (attackSpeedMultiplier>0){textAttackSpeedMultiplier=" 攻速乘区"+"+"+attackSpeedMultiplier;} else {textAttackSpeedMultiplier=" 攻速乘区"+attackSpeedMultiplier;}}

            text=Text.of(textHealth+textSpeed+
                    textStrength+textAttackRange+textAttackSpeed+
                    textAttackMultiplier+textAttackSpeedMultiplier);
        }
        if (text==null){
            text = Text.of("null");
        }
        tooltip.add(text);
    }
}
