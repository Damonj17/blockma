package damonj17.blockma.datagen;

import net.damonj17.blockma.block.ModBlocks;
import net.damonj17.blockma.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.entry.LeafEntry;
import net.minecraft.loot.function.ApplyBonusLootFunction;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class ModLootTableProvider extends FabricBlockLootTableProvider {
    public ModLootTableProvider(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(dataOutput, registryLookup);
    }

    @Override
    public void generate() {
        //addDrop(ModBlocks.PERMAFROST);
        addDrop(ModBlocks.PERMAFROST_WALL);
        addDrop(ModBlocks.PERMAFROST_SLAB, slabDrops(ModBlocks.PERMAFROST_SLAB));
        addDrop(ModBlocks.PERMAFROST_STAIRS);
        addDrop(ModBlocks.COBBLED_PERMAFROST);
        addDrop(ModBlocks.COBBLED_PERMAFROST_WALL);
        addDrop(ModBlocks.COBBLED_PERMAFROST_SLAB, slabDrops(ModBlocks.COBBLED_PERMAFROST_SLAB));
        addDrop(ModBlocks.COBBLED_PERMAFROST_STAIRS);
        addDrop(ModBlocks.POINTED_PERMAFROST);
        addDrop(ModBlocks.PERMAFROST_BRICKS);
        addDrop(ModBlocks.PERMAFROST_BRICK_WALL);
        addDrop(ModBlocks.PERMAFROST_BRICK_SLAB, slabDrops(ModBlocks.PERMAFROST_BRICK_SLAB));
        addDrop(ModBlocks.PERMAFROST_BRICK_STAIRS);
        addDrop(ModBlocks.POLISHED_PERMAFROST);
        addDrop(ModBlocks.POLISHED_PERMAFROST_WALL);
        addDrop(ModBlocks.POLISHED_PERMAFROST_SLAB, slabDrops(ModBlocks.POLISHED_PERMAFROST_SLAB));
        addDrop(ModBlocks.POLISHED_PERMAFROST_STAIRS);

    }
}