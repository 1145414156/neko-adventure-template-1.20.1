package com.nekoadventure.datagen;

import com.nekoadventure.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.Models;

public class ModModelProvider extends FabricModelProvider {
    public ModModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {

    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        itemModelGenerator.register(ModItems.EMERGENCY_FOOD, Models.GENERATED);
        itemModelGenerator.register(ModItems.NEKO_PACKAGE, Models.GENERATED);
        itemModelGenerator.register(ModItems.NEKO_FOOD_CAN, Models.GENERATED);
        itemModelGenerator.register(ModItems.HANGER, Models.GENERATED);
    }
}
