package com.nekoadventure.mixin.mazeApart;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.nekoadventure.NekoAdventure;
import net.minecraft.command.argument.BlockArgumentParser;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.structure.StructurePlacementData;
import net.minecraft.structure.StructureTemplate;
import net.minecraft.structure.processor.JigsawReplacementStructureProcessor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

//用自定义的字段来实现可储存式的墙壁方块储存
@Mixin(JigsawReplacementStructureProcessor.class)
public class JigsawReplacementStructureProcessorMixin {

    @Inject(
            method = "process(Lnet/minecraft/world/WorldView;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/structure/StructureTemplate$StructureBlockInfo;Lnet/minecraft/structure/StructureTemplate$StructureBlockInfo;Lnet/minecraft/structure/StructurePlacementData;)Lnet/minecraft/structure/StructureTemplate$StructureBlockInfo;",
            at = @At("RETURN"),
            cancellable = true
    )
    private void nekoRestoreFinalStateNbt(WorldView world, BlockPos pos, BlockPos pivot,
                                          StructureTemplate.StructureBlockInfo originalBlockInfo,
                                          StructureTemplate.StructureBlockInfo currentBlockInfo,
                                          StructurePlacementData data,
                                          CallbackInfoReturnable<StructureTemplate.StructureBlockInfo> cir) {
        StructureTemplate.StructureBlockInfo replaced = cir.getReturnValue();
        if (replaced == null || replaced.nbt() != null) {
            return;
        }
        NbtCompound jigsawNbt = originalBlockInfo.nbt();
        if (jigsawNbt == null) {
            return;
        }
        NbtCompound finalStateNbt = nekoParseFinalStateNbt(world, jigsawNbt.getString("final_state"));
        if (finalStateNbt == null || finalStateNbt.isEmpty()) {
            return;
        }
        cir.setReturnValue(new StructureTemplate.StructureBlockInfo(replaced.pos(), replaced.state(), finalStateNbt));
    }
    @Unique
    private NbtCompound nekoParseFinalStateNbt(WorldView world, String finalState) {
        if (finalState.isBlank()) {
            return null;
        }
        try {
            return BlockArgumentParser.block(
                    world.createCommandRegistryWrapper(RegistryKeys.BLOCK), finalState, true).nbt();
        } catch (CommandSyntaxException e) {
            NekoAdventure.LOGGER.warn("解析拼图final_state失败: {}", finalState, e);
            return null;
        }
    }
}
