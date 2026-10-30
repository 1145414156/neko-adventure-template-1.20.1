package com.nekoadventure.mixin.client;

import com.nekoadventure.item.ModItems;
import com.nekoadventure.item.other.NekoPackageItem;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.ParticleTypes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayerEntity.class)
public abstract class ClientPlayerEntityMixin {
    @Unique
    private boolean nekoLastJumping = false;

    @Unique
    private boolean nekoDoubleJumped = false;
    //给道具：云朵靴二段跳用的
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
        if (player.getWorld() instanceof ClientWorld clientWorld) {
            var random = clientWorld.random;
            for (int i = 0; i < 20; i++) {
                double ox = (random.nextDouble() - 0.5) * 1.2;
                double oy = (random.nextDouble() - 0.5) * 0.6;
                double oz = (random.nextDouble() - 0.5) * 1.2;
                clientWorld.addParticle(
                        ParticleTypes.CLOUD,
                        player.getX() + ox, player.getY() + oy, player.getZ() + oz,
                        ox * 0.02, 0.02, oz * 0.02
                );
            }
        }
        player.jump();
    }
}
