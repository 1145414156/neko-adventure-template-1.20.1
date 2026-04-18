package com.nekoadventure.item;

import net.minecraft.item.FoodComponent;

public class ModFoodComponents {
    public static final FoodComponent EMERGENCY_FOOD =
            new FoodComponent.Builder().hunger(4).saturationModifier(0.3F).snack().build();
}
