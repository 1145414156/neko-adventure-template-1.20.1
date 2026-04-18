package com.nekoadventure.datagen.recipe;

import com.nekoadventure.NekoAdventure;
import com.nekoadventure.item.ModItems;
import com.nekoadventure.other.itemApart.SpawnRandomNekoItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.util.Identifier;

import java.util.function.Consumer;

public class ModRecipeProvider extends FabricRecipeProvider {
    public ModRecipeProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generate(Consumer<RecipeJsonProvider> exporter) {
        // 任意 all_item_pool 标签道具 -> 2 个绑定灵魂物质
        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.BINDING_SOUL_SUBSTANCE, 2)
                .input(SpawnRandomNekoItems.ALL_POOL)
                .criterion("has_all_pool_item", conditionsFromTag(SpawnRandomNekoItems.ALL_POOL))
                .offerTo(exporter, new Identifier(NekoAdventure.MOD_ID, "binding_soul_substance_from_pool"));

        // 4 个绑定灵魂物质 -> 1 个道具原型
        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.PROP_PROTOTYPE, 1)
                .pattern("SS")
                .pattern("SS")
                .input('S', ModItems.BINDING_SOUL_SUBSTANCE)
                .criterion("has_binding_soul_substance", conditionsFromItem(ModItems.BINDING_SOUL_SUBSTANCE))
                .offerTo(exporter, new Identifier(NekoAdventure.MOD_ID, "prop_prototype_from_soul_substance"));
    }
}
