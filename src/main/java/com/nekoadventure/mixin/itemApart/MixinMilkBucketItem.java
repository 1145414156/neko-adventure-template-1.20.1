package com.nekoadventure.mixin.itemApart;

import com.nekoadventure.effect.ModStatusEffects;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.MilkBucketItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;


//这里是专门用来禁用牛奶清除效果的
@Mixin(MilkBucketItem.class)
public class MixinMilkBucketItem {
    @Redirect(method = "finishUsing", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;clearStatusEffects()Z"))
    private boolean disableClearStatusEffects(LivingEntity user) {
        return !user.hasStatusEffect(ModStatusEffects.MAZE_CURSE)&&user.hasStatusEffect(ModStatusEffects.BOSS_FIGHT);
    }
}
