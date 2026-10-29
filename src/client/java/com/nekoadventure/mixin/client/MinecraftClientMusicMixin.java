package com.nekoadventure.mixin.client;

import com.nekoadventure.sound.BossMusicHandler;
import net.minecraft.client.MinecraftClient;
import net.minecraft.sound.MusicSound;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MinecraftClient.class)
public class MinecraftClientMusicMixin {
    @Inject(method = "getMusicType", at = @At("HEAD"), cancellable = true)
    private void onGetMusicType(CallbackInfoReturnable<MusicSound> cir) {
        if (BossMusicHandler.isBossMusicActive()) {
            cir.setReturnValue(BossMusicHandler.getBossMusic());
        }
    }
}
