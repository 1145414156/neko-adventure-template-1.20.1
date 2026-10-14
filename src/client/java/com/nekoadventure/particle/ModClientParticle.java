package com.nekoadventure.particle;

import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.minecraft.client.particle.*;

public class ModClientParticle {
    public static void init() {
        ParticleFactoryRegistry.getInstance().register(
                ModParticleTypes.LANCE_ATTACK,
                SweepAttackParticle.Factory::new
        );
        ParticleFactoryRegistry.getInstance().register(
                ModParticleTypes.BRIMSTONE_ATTACK1,
                FlameParticle.Factory::new
        );
        ParticleFactoryRegistry.getInstance().register(
                ModParticleTypes.BRIMSTONE_ATTACK2,
                FlameParticle.Factory::new
        );
    }
}
