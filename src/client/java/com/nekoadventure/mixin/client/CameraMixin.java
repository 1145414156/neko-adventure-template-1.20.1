package com.nekoadventure.mixin.client;

import com.nekoadventure.network.mob.ClientScreenSwayHandler;
import net.minecraft.client.render.Camera;
import net.minecraft.entity.Entity;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Camera.class)
public class CameraMixin {
    @Inject(method = "update", at = @At("TAIL"))
    private void nekoApplyScreenSway(BlockView area, Entity focusedEntity, boolean thirdPerson,
                                     boolean inverseView, float tickDelta, CallbackInfo ci) {
        ClientScreenSwayHandler.applySway((Camera) (Object) this, tickDelta);
    }
}
