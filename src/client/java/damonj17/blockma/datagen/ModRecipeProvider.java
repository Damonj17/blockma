package damonj17.blockma.datagen;

import net.damonj17.blockma.block.ModBlocks;
import net.damonj17.blockma.item.ModItems;
import net.damonj17.blockma.BlockmaMod;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.recipe.RecipeExporter;
import net.minecraft.data.recipe.RecipeGenerator;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.Identifier;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends FabricRecipeProvider {
    public ModRecipeProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected RecipeGenerator getRecipeGenerator(RegistryWrapper.WrapperLookup wrapperLookup, RecipeExporter recipeExporter) {
        return new RecipeGenerator(wrapperLookup, recipeExporter) {
            @Override
            public void generate() {
                List<ItemConvertible> BLOCKMA_IRON_SMELTABLES = List.of(ModBlocks.PERMAFROST_IRON_ORE);

                offerSmelting(BLOCKMA_IRON_SMELTABLES, RecipeCategory.MISC, Items.IRON_INGOT, 0.7f, 200, "iron_ingot");
                offerBlasting(BLOCKMA_IRON_SMELTABLES, RecipeCategory.MISC, Items.IRON_INGOT, 0.7f, 100, "iron_ingot");

                List<ItemConvertible> BLOCKMA_GOLD_SMELTABLES = List.of(ModBlocks.PERMAFROST_GOLD_ORE);

                offerSmelting(BLOCKMA_GOLD_SMELTABLES, RecipeCategory.MISC, Items.GOLD_INGOT, 1.0f, 200, "gold_ingot");
                offerBlasting(BLOCKMA_GOLD_SMELTABLES, RecipeCategory.MISC, Items.GOLD_INGOT, 1.0f, 100, "gold_ingot");

                List<ItemConvertible> BLOCKMA_COPPER_SMELTABLES = List.of(ModBlocks.PERMAFROST_COPPER_ORE);

                offerSmelting(BLOCKMA_COPPER_SMELTABLES, RecipeCategory.MISC, Items.COPPER_INGOT, 0.7f, 200, "copper_ingot");
                offerBlasting(BLOCKMA_COPPER_SMELTABLES, RecipeCategory.MISC, Items.COPPER_INGOT, 0.7f, 100, "copper_ingot");

                List<ItemConvertible> BLOCKMA_COAL_SMELTABLES = List.of(ModBlocks.PERMAFROST_COAL_ORE);

                offerSmelting(BLOCKMA_COAL_SMELTABLES, RecipeCategory.MISC, Items.COAL, 0.1f, 200, "coal");
                offerBlasting(BLOCKMA_COAL_SMELTABLES, RecipeCategory.MISC, Items.COAL, 0.1f, 100, "coal");

                List<ItemConvertible> BLOCKMA_REDSTONE_SMELTABLES = List.of(ModBlocks.PERMAFROST_REDSTONE_ORE);

                offerSmelting(BLOCKMA_REDSTONE_SMELTABLES, RecipeCategory.MISC, Items.REDSTONE, 0.7f, 200, "redstone");
                offerBlasting(BLOCKMA_REDSTONE_SMELTABLES, RecipeCategory.MISC, Items.REDSTONE, 0.7f, 100, "redstone");

                List<ItemConvertible> BLOCKMA_LAPIS_SMELTABLES = List.of(ModBlocks.PERMAFROST_LAPIS_ORE);

                offerSmelting(BLOCKMA_LAPIS_SMELTABLES, RecipeCategory.MISC, Items.LAPIS_LAZULI, 0.2f, 200, "lapis_lazuli");
                offerBlasting(BLOCKMA_LAPIS_SMELTABLES, RecipeCategory.MISC, Items.LAPIS_LAZULI, 0.2f, 100, "lapis_lazuli");

                List<ItemConvertible> BLOCKMA_DIAMOND_SMELTABLES = List.of(ModBlocks.PERMAFROST_DIAMOND_ORE);

                offerSmelting(BLOCKMA_DIAMOND_SMELTABLES, RecipeCategory.MISC, Items.DIAMOND, 0.2f, 200, "diamond");
                offerBlasting(BLOCKMA_DIAMOND_SMELTABLES, RecipeCategory.MISC, Items.DIAMOND, 0.2f, 100, "diamond");

                List<ItemConvertible> BLOCKMA_EMERALD_SMELTABLES = List.of(ModBlocks.PERMAFROST_EMERALD_ORE);

                offerSmelting(BLOCKMA_EMERALD_SMELTABLES, RecipeCategory.MISC, Items.EMERALD, 1.0f, 200, "emerald");
                offerBlasting(BLOCKMA_EMERALD_SMELTABLES, RecipeCategory.MISC, Items.EMERALD, 1.0f, 100, "emerald");


            }
        };
    }


    @Override
    public String getName() {
        return "Blockma Recipes";
    }
}