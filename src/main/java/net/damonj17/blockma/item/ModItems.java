package net.damonj17.blockma.item;

import net.damonj17.blockma.BlockmaMod;
import net.damonj17.blockma.item.data.BlockmaConsumableComponents;
import net.damonj17.blockma.item.data.BlockmaFoodComponents;
import net.minecraft.item.Item;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

import java.util.function.Function;

public class ModItems {
    public static Item register(String name, Function<Item.Settings, Item> itemFactory, Item.Settings settings) {
        // Create the item key.
        RegistryKey<Item> itemKey = RegistryKey.of(RegistryKeys.ITEM, Identifier.of(BlockmaMod.MOD_ID, name));

        // Create the item instance.
        Item item = itemFactory.apply(settings.registryKey(itemKey));

        // Register the item.
        Registry.register(Registries.ITEM, itemKey, item);

        return item;


    }
    public static final Item CHERRY = register("cherry", Item::new,
            new Item.Settings().food(BlockmaFoodComponents.APPLE)
    );
    public static final Item GOLDEN_CHERRY = register("golden_cherry", Item::new, new Item.Settings()
            .food(BlockmaFoodComponents.GOLDEN_APPLE, BlockmaConsumableComponents.GOLDEN_APPLE)
    );
    public static final Item PEACH = register("peach", Item::new,
            new Item.Settings().food(BlockmaFoodComponents.APPLE)
    );
    public static final Item GOLDEN_PEACH = register("golden_peach", Item::new, new Item.Settings()
            .food(BlockmaFoodComponents.GOLDEN_APPLE, BlockmaConsumableComponents.GOLDEN_APPLE)
    );
    public static final Item CANDY_APPLE = register("candy_apple", Item::new,
            new Item.Settings().food(BlockmaFoodComponents.APPLE)
    );
    public static final Item GOLDEN_CANDY_APPLE = register("golden_candy_apple", Item::new, new Item.Settings()
            .food(BlockmaFoodComponents.GOLDEN_APPLE, BlockmaConsumableComponents.GOLDEN_APPLE)
    );

    public static void initialize() {


    }



}
