package com.nekoadventure.mixin.client;

import com.nekoadventure.effect.ModStatusEffects;
import com.nekoadventure.item.ModItems;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
@Mixin(ClientPlayerInteractionManager.class)
public class ClientPlayerInteractionManagerMixin {
    @Inject(method = "attackBlock", at = @At("HEAD"), cancellable = true)
    private void onAttackBlock(BlockPos pos, Direction direction, CallbackInfoReturnable<Boolean> cir) {
        ClientPlayerEntity player = MinecraftClient.getInstance().player;
        if ((player != null && player.hasStatusEffect(ModStatusEffects.MAZE_CURSE))||(player != null && player.hasStatusEffect(ModStatusEffects.BOSS_FIGHT))) {
            cir.setReturnValue(false);
        }
    }


    @Inject(method = "interactBlock", at = @At("HEAD"), cancellable = true)
    private void onInteractBlock(ClientPlayerEntity player, Hand hand, BlockHitResult hitResult,
                                 CallbackInfoReturnable<ActionResult> cir) {
        if (player.hasStatusEffect(ModStatusEffects.MAZE_CURSE)||player.hasStatusEffect(ModStatusEffects.BOSS_FIGHT)) {
            ItemStack stack = player.getStackInHand(hand);

            // 允许 NEKO_PACKAGE 使用
            if (stack.isOf(ModItems.NEKO_PACKAGE)) {
                return;
            }

            if (stack.getItem() instanceof BlockItem) {
                cir.setReturnValue(ActionResult.FAIL);
            }
        }
    }

    // 阻止客户端开始破坏进度
    @Inject(method = "updateBlockBreakingProgress", at = @At("HEAD"), cancellable = true)
    private void onUpdateBlockBreakingProgress(BlockPos pos, Direction direction,
                                               CallbackInfoReturnable<Boolean> cir) {
        ClientPlayerEntity player = MinecraftClient.getInstance().player;
        if (player != null && (player.hasStatusEffect(ModStatusEffects.MAZE_CURSE) || player.hasStatusEffect(ModStatusEffects.BOSS_FIGHT))) {
            cir.setReturnValue(false);
        }
    }
}