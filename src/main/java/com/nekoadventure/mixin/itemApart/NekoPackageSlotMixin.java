package com.nekoadventure.mixin.itemApart;

import com.nekoadventure.item.other.NekoPackageItem;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

//纸盒无法放入除玩家背包(含盔甲栏/副手)以外的任何容器（箱子、潜影盒、末影箱等）
@Mixin(Slot.class)
public class NekoPackageSlotMixin {
    @Shadow
    @Final
    public Inventory inventory;
    @Inject(method = "canInsert", at = @At("HEAD"), cancellable = true)
    private void preventNekoPackageInsert(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
        if (!(this.inventory instanceof PlayerInventory) && stack.getItem() instanceof NekoPackageItem) {
            cir.setReturnValue(false);
        }
    }
}
