package com.nekoadventure.datagen;

import com.nekoadventure.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;

public class ModENLanguageProvider extends FabricLanguageProvider {
    public ModENLanguageProvider(FabricDataOutput dataOutput) {
        super(dataOutput,"en_us");
    }

    @Override
    public void generateTranslations(TranslationBuilder translationBuilder) {
        translationBuilder.add(ModItems.EMERGENCY_FOOD,"emergency_food");
        translationBuilder.add(ModItems.NEKO_PACKAGE,"neko_package");
        translationBuilder.add(ModItems.NEKO_FOOD_CAN,"neko_food_can");
        translationBuilder.add(ModItems.HANGER,"hanger");
    }
}
