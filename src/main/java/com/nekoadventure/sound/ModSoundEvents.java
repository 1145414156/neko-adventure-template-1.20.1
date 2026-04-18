package com.nekoadventure.sound;

import com.nekoadventure.NekoAdventure;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public class ModSoundEvents {
    public static final SoundEvent ELECTRICITY_RUN_THROUGH=register("electricity_run_through");
    public static final SoundEvent BRIMSTONE_ATTACK=register("brimstone_attack");
    public static final SoundEvent BRIMSTONE_FIERCE_ATTACK =register("brimstone_fierce_attack");
    public static final SoundEvent LANCE_ATTACK=register("lance_attack");
    public static final SoundEvent PARRY_ATTACK=register("parry_attack");

    public static final SoundEvent ENTITY_GRAND_KNIGHT_AMBIENT =register("grand_knight_ambient");
    public static final SoundEvent ENTITY_GRAND_KNIGHT_WALK =register("grand_knight_walk");
    public static final SoundEvent ENTITY_GRAND_KNIGHT_SWORD_ATTACK=register("grand_knight_sword_attack");
    public static final SoundEvent ENTITY_GRAND_KNIGHT_RAPID_ATTACK=register("grand_knight_rapid_attack");
    public static final SoundEvent ENTITY_GRAND_KNIGHT_DELAY_BULLET=register("grand_knight_delay_bullet");
    public static final SoundEvent ENTITY_GRAND_KNIGHT_EARTH_QUAKER=register("grand_knight_earth_quaker");
    public static final SoundEvent ENTITY_GRAND_KNIGHT_HURT=register("grand_knight_hurt");
    public static final SoundEvent ENTITY_GRAND_KNIGHT_DEATH=register("grand_knight_death");

    private static SoundEvent register(String name) {
        Identifier id=new Identifier(NekoAdventure.MOD_ID, name);
        return Registry.register(Registries.SOUND_EVENT,id,SoundEvent.of(id));
    }
    public static void initialize(){};
}
