package com.nekoadventure.particle;

import com.mojang.serialization.Codec;
import com.nekoadventure.NekoAdventure;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.particle.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.function.Function;

public class ModParticleTypes {
    public static final DefaultParticleType LANCE_ATTACK = FabricParticleTypes.simple();
    public static void initialize() {
        Registry.register(
                Registries.PARTICLE_TYPE,
                new Identifier(NekoAdventure.MOD_ID, "lance_attack"),
                LANCE_ATTACK
        );
    }
}
