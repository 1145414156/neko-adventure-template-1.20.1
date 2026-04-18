package com.nekoadventure.mixin.mazeApart;

import com.nekoadventure.NekoAdventure;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

//在is_maze的维度中，禁用僵尸增援机制
@Mixin(ZombieEntity.class)
public class ZombieEntityMixin {

    @Redirect(
            method = "damage(Lnet/minecraft/entity/damage/DamageSource;F)Z",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/mob/ZombieEntity;getAttributeValue(Lnet/minecraft/entity/attribute/EntityAttribute;)D"
            )
    )
    private double nekoDisableReinforcementsInMaze(ZombieEntity zombie, EntityAttribute attribute) {
        if (attribute == EntityAttributes.ZOMBIE_SPAWN_REINFORCEMENTS && nekoIsMazeDimension(zombie.getWorld())) {
            return 0.0;
        }
        return zombie.getAttributeValue(attribute);
    }

    @Unique
    private boolean nekoIsMazeDimension(World world) {
        if (!(world instanceof ServerWorld serverWorld)) {
            return false;
        }
        TagKey<DimensionType> isMazeTag = TagKey.of(
                RegistryKeys.DIMENSION_TYPE,
                new Identifier(NekoAdventure.MOD_ID, "is_maze")
        );
        return serverWorld.getDimensionEntry().isIn(isMazeTag);
    }
}
