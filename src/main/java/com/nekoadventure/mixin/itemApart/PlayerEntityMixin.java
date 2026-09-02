package com.nekoadventure.mixin.itemApart;

import com.nekoadventure.item.nekoItem.attackTypeItem.AttackTypeItem;
import com.nekoadventure.item.other.NekoPackageItem;
import com.nekoadventure.other.mazeApart.PlayerBlackScreenState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.UUID;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity {
	@Shadow
	public abstract boolean damage(DamageSource source, float amount);

    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
		super(entityType, world);
	}

    @Inject(at = @At("RETURN"), method = "tick")
    private void onTick(CallbackInfo ci) {
        PlayerEntity player = (PlayerEntity) (Object) this;
        boolean hasNekoPackAgeItem = isHasNekoPackageItem(player);
        if (!hasNekoPackAgeItem) {
            EntityAttributeInstance damageAttr = player.getAttributeInstance(
                    EntityAttributes.GENERIC_ATTACK_DAMAGE
            );
            EntityAttributeInstance speedAttr = player.getAttributeInstance(
                    EntityAttributes.GENERIC_MOVEMENT_SPEED
            );
            EntityAttributeInstance healthAttr = player.getAttributeInstance(
                    EntityAttributes.GENERIC_MAX_HEALTH
            );
            EntityAttributeInstance attackSpeedAttr = player.getAttributeInstance(
                    EntityAttributes.GENERIC_ATTACK_SPEED
            );
            if (damageAttr != null) {
                damageAttr.removeModifier(UUID.fromString("550e8400-e29b-41d4-a716-446655440002"));
            }
            if (speedAttr != null) {
                speedAttr.removeModifier(UUID.fromString("550e8400-e29b-41d4-a716-446655440001"));
            }
            if (healthAttr != null) {
                healthAttr.removeModifier(UUID.fromString("550e8400-e29b-41d4-a716-446655440000"));
            }
            if (attackSpeedAttr != null) {
                attackSpeedAttr.removeModifier(UUID.fromString("550e8400-e29b-41d4-a716-446655440003"));
            }
        }
    }

    @Unique
    private boolean isHasNekoPackageItem(PlayerEntity player) {
        PlayerInventory inventory= player.getInventory();

        boolean hasNekoPackAgeItem = false;
        for (int i = 0; i < inventory.main.size(); i++) {
            ItemStack stack = inventory.main.get(i);
            if (stack.getItem() instanceof NekoPackageItem) {
                hasNekoPackAgeItem = true;
                break;
            }
        }

        if (!hasNekoPackAgeItem) {
            for (int i = 0; i < inventory.armor.size(); i++) {
                ItemStack stack = inventory.armor.get(i);
                if (stack.getItem() instanceof NekoPackageItem) {
                    hasNekoPackAgeItem = true;
                    break;
                }
            }
        }

        if (!hasNekoPackAgeItem) {
            ItemStack offHandStack = inventory.offHand.get(0);
            if (offHandStack.getItem() instanceof NekoPackageItem) {
                hasNekoPackAgeItem = true;
            }
        }
        return hasNekoPackAgeItem;
    }

    //这里是所有攻击特效注入原版的方法
	@Inject(at = @At(value = "HEAD"), method = "attack")
	private void disableAttack(Entity target, CallbackInfo info) {
        if (target instanceof LivingEntity) {
            PlayerEntity player = (PlayerEntity) (Object) this;
            if (NekoPackageItem.getIsFinished(player.getOffHandStack())!=2 && NekoPackageItem.getNekoItem(player.getOffHandStack(), false, 1)!=null) {
                if (player.getAttackCooldownProgress(0.0f) == 1) {
                    ArrayList specificItems = NekoPackageItem.getNekoItem(player.getOffHandStack(), false, 1);
                    for (int i = specificItems.size(); i >= 1; i--) {
                        AttackTypeItem attackTypeItem = (AttackTypeItem) specificItems.get(i - 1);
                        attackTypeItem.applySpecificItem(player, (LivingEntity) target);
                    }
                }
            }
        }
    }

    //这里是所有受伤类道具注入原版的方法
	@Inject(method = "damage", at = @At(value = "HEAD"), cancellable = true)
	private void onPlayerDamage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
		PlayerEntity player = (PlayerEntity) (Object) this;
        if (player.getOffHandStack().getItem() instanceof NekoPackageItem
                && NekoPackageItem.getIsFinished(player.getOffHandStack())!=2) {
                if (NekoPackageItem.applyFunction(NekoPackageItem.getNekoItem
                        (player.getOffHandStack(), false, 4), player, 2)) {
                    cir.cancel();
            }
        }
    }

    //这个是让玩家黑屏(前往下一层)的时候无法移动的方法
    @Inject(method = "getMovementSpeed", at = @At("RETURN"), cancellable = true)
    private void onGetMovementSpeed(CallbackInfoReturnable<Float> cir) {
        PlayerEntity player = (PlayerEntity) (Object) this;
        if (PlayerBlackScreenState.isBlackScreen(player.getUuid())) {
            cir.setReturnValue(0.0f);
        }
    }
    //这个是让玩家黑屏的时候无法跳跃的方法
    @Inject(method = "jump", at = @At("HEAD"), cancellable = true)
    private void onJump(CallbackInfo ci) {
        PlayerEntity player = (PlayerEntity) (Object) this;
        if (PlayerBlackScreenState.isBlackScreen(player.getUuid())) {
            ci.cancel();
        }
    }
}