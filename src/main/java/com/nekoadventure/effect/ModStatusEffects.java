package com.nekoadventure.effect;

import com.nekoadventure.NekoAdventure;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModStatusEffects {
    public static final StatusEffect MAZE_CURSE = Registry.register(Registries.STATUS_EFFECT, new Identifier(NekoAdventure.MOD_ID, "maze_curse"), new MazeCurseStatusEffect());
    public static final StatusEffect BOSS_FIGHT=Registry.register(Registries.STATUS_EFFECT,new Identifier(NekoAdventure.MOD_ID,"boss_fight"), new BossFightStatusEffect());
    public static void initialize() {}

}
