package com.nekoadventure.item.nekoItem.functionTypeItem.functionItems.tick;

import com.nekoadventure.item.ModItems;
import com.nekoadventure.item.nekoItem.functionTypeItem.functionItems.NekoFunctionItem;
import com.nekoadventure.other.itemApart.NekoPackageDataManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.text.Text;
import java.util.List;

public class SoulJar extends NekoFunctionItem {
    public SoulJar(Settings settings) {
        super(settings);
        text= Text.of("每击杀100只生物时，生成道具（纯净灵魂）");
    }

    @Override
    public void applyTickFunctionItem(PlayerEntity player) {
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
                    livingEntity.getWorld().addParticle(ParticleTypes.SOUL,livingEntity.getX(),livingEntity.getY()+0.5,livingEntity.getZ(),0.0,0.01,0.0);
                    livingEntity.remove(Entity.RemovalReason.KILLED);
                    if (soul<100) {
                        soul++;
                        player.sendMessage(Text.of("已收集灵魂:"+soul), true);
                    }
                }
                if (soul==100){

                    //开发辅助
                    player.sendMessage(Text.of("已生成纯净灵魂"),true);
                    //开发辅助

                    player.giveItemStack(ModItems.PURENESS_SOUL.getDefaultStack());
                    soul=0;
                }
            }
        }
        NekoPackageDataManager.setItemIntState(player, "soulJar", soul);
    }
}
