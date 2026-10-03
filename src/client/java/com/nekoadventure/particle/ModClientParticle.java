package com.nekoadventure.particle;

import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.minecraft.client.particle.SweepAttackParticle;

public class ModClientParticle {
    public static void init() {
        ParticleFactoryRegistry.getInstance().register(
                ModParticleTypes.LANCE_ATTACK,
                SweepAttackParticle.Factory::new
        );
    }
}
