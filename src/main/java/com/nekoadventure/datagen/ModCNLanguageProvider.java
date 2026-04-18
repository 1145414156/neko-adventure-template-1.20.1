package com.nekoadventure.datagen;

import com.nekoadventure.item.ModItems;
import com.nekoadventure.item.itemGroup.ModItemGroups;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;

public class ModCNLanguageProvider extends FabricLanguageProvider {
    public ModCNLanguageProvider(FabricDataOutput dataOutput) {
        super(dataOutput,"zh_cn");
    }

    @Override
    public void generateTranslations(TranslationBuilder translationBuilder) {
        translationBuilder.add(ModItems.EMERGENCY_FOOD,"应急食物");
        translationBuilder.add(ModItems.NEKO_PACKAGE,"猫咪纸箱");
        translationBuilder.add(ModItems.NEKO_FOOD_CAN,"猫罐头");
        translationBuilder.add(ModItems.HANGER,"衣架");
        translationBuilder.add(ModItemGroups.NEKO_GROUP,"猫猫旅途：其他物品");
        translationBuilder.add(ModItemGroups.NUM_ITEM_GROUP,"猫猫旅途：数值类道具");
    }
}
