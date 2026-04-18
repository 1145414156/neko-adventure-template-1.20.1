package com.nekoadventure.mixin.itemApart;

import com.nekoadventure.effect.ModStatusEffects;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.thrown.EnderPearlEntity;
import net.minecraft.util.hit.HitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EnderPearlEntity.class)
public class EnderPearlEntityMixin {
    @Inject(method = "onCollision", at = @At("HEAD"), cancellable = true)
    private void onCollision(HitResult hitResult, CallbackInfo ci) {
        // 获取投掷者
        EnderPearlEntity pearl = (EnderPearlEntity)(Object)this;
        Entity owner = pearl.getOwner();

        if (owner instanceof PlayerEntity player) {
            if (player.hasStatusEffect(ModStatusEffects.MAZE_CURSE)||player.hasStatusEffect(ModStatusEffects.BOSS_FIGHT)) {
                ci.cancel();
                pearl.discard();
            }
        }
    }
}