package com.nekoadventure.item.soulItem;

import com.nekoadventure.client.ShiftKeyHelper;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class InstanceHealthSoul extends AbstractSoulItem{
    public InstanceHealthSoul(Settings settings, int maxCharge) {
        super(settings,maxCharge);
        text=Text.of("使用时，回复全部生命值");
    }

    @Override
    public void onUseEffect(World world, PlayerEntity player, ItemStack stack) {
        player.addStatusEffect(new StatusEffectInstance(StatusEffects.INSTANT_HEALTH,1,100));
    }

}
