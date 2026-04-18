package com.nekoadventure;

import com.nekoadventure.datagen.ModCNLanguageProvider;
import com.nekoadventure.datagen.ModENLanguageProvider;
import com.nekoadventure.datagen.ModModelProvider;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class NekoAdventureDataGenerator implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
        pack.addProvider(ModModelProvider::new);
        pack.addProvider(ModCNLanguageProvider::new);
        pack.addProvider(ModENLanguageProvider::new);
    }
}
