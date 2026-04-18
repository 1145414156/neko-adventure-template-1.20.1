package com.nekoadventure;

import com.nekoadventure.datagen.language.ModCNLanguageProvider;
import com.nekoadventure.datagen.language.ModENLanguageProvider;
import com.nekoadventure.datagen.model.ModModelProvider;
import com.nekoadventure.datagen.recipe.ModRecipeProvider;
import com.nekoadventure.datagen.tags.ModDimensionTagProvider;
import com.nekoadventure.datagen.tags.ModItemTagsProvider;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class NekoAdventureDataGenerator implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
        pack.addProvider(ModModelProvider::new);
        pack.addProvider(ModCNLanguageProvider::new);
        pack.addProvider(ModENLanguageProvider::new);
        pack.addProvider(ModDimensionTagProvider::new);
        pack.addProvider(ModItemTagsProvider::new);
        pack.addProvider(ModRecipeProvider::new);
    }
}
