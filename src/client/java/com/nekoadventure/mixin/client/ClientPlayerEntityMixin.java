package com.nekoadventure.mixin.client;

import com.nekoadventure.item.ModItems;
import com.nekoadventure.item.other.NekoPackageItem;
import net.minecraft.client.network.ClientPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

//这个类是给道具：云朵靴二段跳
@Mixin(ClientPlayerEntity.class)
public abstract class ClientPlayerEntityMixin {
    @Unique
    private boolean nekoLastJumping = false;

    @Unique
    private boolean nekoDoubleJumped = false;
    @Inject(method = "tickMovement", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/input/Input;tick(ZF)V", shift = At.Shift.AFTER))
    private void onTickMovement(CallbackInfo ci) {
        ClientPlayerEntity player = (ClientPlayerEntity) (Object) this;
        boolean pressedJump = player.input.jumping && !nekoLastJumping;
        nekoLastJumping = player.input.jumping;
        if (player.isOnGround()) {
            nekoDoubleJumped = false;
            return;
        }
        if (!pressedJump || nekoDoubleJumped || player.getAbilities().allowFlying) {
            return;
        }
        if (!(player.getOffHandStack().getItem() instanceof NekoPackageItem nekoPackageItem)
                || !nekoPackageItem.isHaveNekoItem(player.getOffHandStack(), ModItems.CLOUD_BOOTS)) {
            return;
        }
        nekoDoubleJumped = true;
        player.jump();
    }
}
