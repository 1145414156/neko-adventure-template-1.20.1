package com.nekoadventure.item.nekoItem.functionTypeItem.functionItems.tick;

import com.nekoadventure.item.ModItems;
import com.nekoadventure.item.nekoItem.functionTypeItem.functionItems.AbstractNekoFunctionItem;
import com.nekoadventure.other.itemApart.NekoPackageDataManager;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import java.util.List;

public class SoulJar extends AbstractNekoFunctionItem {
    private static final int DEATH_RECORD_TICKS = 100;

    public SoulJar(Settings settings) {
        super(settings);
        text= Text.of("每击杀100只生物时，生成道具（纯净灵魂）");
    }

    @Override
    public void applyTickFunction(PlayerEntity player) {
        if (player.getWorld().isClient()) return;
        int soul = NekoPackageDataManager.getItemIntState(player, "soulJar");
        List<LivingEntity> livingEntities=player.getWorld().getEntitiesByClass(
                LivingEntity.class,
                player.getBoundingBox().expand((double) 16 +3),
                entity -> entity != player && entity instanceof LivingEntity && !(entity instanceof ArmorStandEntity)
        );
        if (!livingEntities.isEmpty()) {
            for (LivingEntity livingEntity : livingEntities) {
                if (livingEntity != null && livingEntity.isDead()) {
                    if (NekoPackageDataManager.markEntityProcessed(player, livingEntity.getUuid(), DEATH_RECORD_TICKS)) {
                        continue;
                    }
                    livingEntity.getWorld().addParticle(ParticleTypes.SOUL,livingEntity.getX(),livingEntity.getY()+0.5,livingEntity.getZ(),0.0,0.01,0.0);
                    if (soul<100) {
                        soul++;
                    }
                    if (player.getWorld() instanceof ServerWorld serverWorld){
                        serverWorld.spawnParticles(ParticleTypes.SOUL,
                                livingEntity.getX(), livingEntity.getY()+1, livingEntity.getZ(), soul, 0.5, 0.5, 0.5, 0.01);
                    }
                    if (soul==100){
                        if (player.getWorld() instanceof ServerWorld serverWorld){
                            serverWorld.spawnParticles(ParticleTypes.SOUL,
                                    player.getX(), player.getY()+1, player.getZ(), soul, 0.1, 0.1, 0.1, 0.1);
                        }
                        player.getWorld().playSound(null, player.getBlockPos(), SoundEvents.PARTICLE_SOUL_ESCAPE, SoundCategory.PLAYERS, 1.0F, 1.0F);
                        player.giveItemStack(ModItems.PURENESS_SOUL.getDefaultStack());
                        soul=0;
                    }
                }
            }
        }
        NekoPackageDataManager.setItemIntState(player, "soulJar", soul);
    }
}
