package com.nekoadventure.mixin.itemApart;

import com.nekoadventure.effect.ModStatusEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.network.ServerPlayerInteractionManager;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ServerPlayerInteractionManager.class)
public class ServerPlayerInteractionManagerMixin {
    @Final
    @Shadow
    protected ServerPlayerEntity player;

    // 阻止破坏
    @Inject(method = "tryBreakBlock", at = @At("HEAD"), cancellable = true)
    private void onTryBreakBlock(BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        if (player.hasStatusEffect(ModStatusEffects.MAZE_CURSE)||player.hasStatusEffect(ModStatusEffects.BOSS_FIGHT)) {
            cir.setReturnValue(false);
        }
    }

    // 阻止放置/交互
    @Inject(method = "interactBlock", at = @At("HEAD"), cancellable = true)
    private void onInteractBlock(ServerPlayerEntity player2, World world, ItemStack stack, Hand hand,
                                 BlockHitResult hitResult, CallbackInfoReturnable<ActionResult> cir) {
        if (player.hasStatusEffect(ModStatusEffects.MAZE_CURSE)||player.hasStatusEffect(ModStatusEffects.BOSS_FIGHT)) {
            cir.setReturnValue(ActionResult.FAIL);
        }
    }
}