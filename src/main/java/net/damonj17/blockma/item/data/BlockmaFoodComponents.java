package net.damonj17.blockma.item.data;

import net.minecraft.component.type.FoodComponent;

public class BlockmaFoodComponents {
    public static final FoodComponent APPLE = new FoodComponent.Builder().nutrition(4).saturationModifier(0.3F).build();
    public static final FoodComponent GOLDEN_APPLE = new FoodComponent.Builder().nutrition(4).saturationModifier(1.2F).alwaysEdible().build();
}