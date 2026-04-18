package com.nekoadventure.mixin.itemApart;

import com.nekoadventure.item.other.NekoPackageItem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ServerPlayerEntity.class)
public class ServerPlayerEntityMixin {

    //纸盒无法被丢弃：Q键/Ctrl+Q丢弃（创造与生存模式都走这里）会在清空槽位前拦截，防止纸盒被丢出
    @Inject(method = "dropSelectedItem", at = @At("HEAD"), cancellable = true)
    private void preventDropSelected(boolean entireStack, CallbackInfoReturnable<Boolean> cir) {
        PlayerEntity player = (PlayerEntity) (Object) this;
        if (player.getInventory().getMainHandStack().getItem() instanceof NekoPackageItem) {
            cir.setReturnValue(false);
        }
    }
}
