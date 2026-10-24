package com.nekoadventure.particle;

import com.nekoadventure.NekoAdventure;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModParticleTypes {
    public static final DefaultParticleType LANCE_ATTACK = FabricParticleTypes.simple();
    public static final DefaultParticleType BRIMSTONE_ATTACK1= FabricParticleTypes.simple();
    public static final DefaultParticleType BRIMSTONE_ATTACK2=FabricParticleTypes.simple();
    public static void initialize() {
        Registry.register(
                Registries.PARTICLE_TYPE,
                new Identifier(NekoAdventure.MOD_ID, "lance_attack"),
                LANCE_ATTACK
        );
        Registry.register(
                Registries.PARTICLE_TYPE,
                new Identifier(NekoAdventure.MOD_ID, "brimstone_attack1"),
                BRIMSTONE_ATTACK1
        );
        Registry.register(
                Registries.PARTICLE_TYPE,
                new Identifier(NekoAdventure.MOD_ID,"brimstone_attack2"),
                BRIMSTONE_ATTACK2
        );
    }
}
