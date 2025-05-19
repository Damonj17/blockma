package net.damonj17.blockma.item;

import net.damonj17.blockma.BlockmaMod;
import net.damonj17.blockma.block.ModBlocks;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModItemGroups {

    public static final ItemGroup BLOCKMA_GROUP = Registry.register(Registries.ITEM_GROUP,
            Identifier.of(BlockmaMod.MOD_ID, "blockma_group"),
            FabricItemGroup.builder().icon(() -> new ItemStack(ModItems.CHERRY))
                    .displayName(Text.translatable("itemgroup.blockma.blockma_group"))
                    .entries((displayContext, entries) -> {
                        entries.add(ModItems.CHERRY);
                        entries.add(ModItems.GOLDEN_CHERRY);
                        entries.add(ModItems.PEACH);
                        entries.add(ModItems.GOLDEN_PEACH);
                        entries.add(ModItems.CANDY_APPLE);
                        entries.add(ModItems.GOLDEN_CANDY_APPLE);
                        entries.add(ModBlocks.PERMAFROST.asItem());
                        entries.add(ModBlocks.PERMAFROST_WALL.asItem());
                        entries.add(ModBlocks.PERMAFROST_SLAB.asItem());
                        entries.add(ModBlocks.PERMAFROST_STAIRS.asItem());
                        entries.add(ModBlocks.COBBLED_PERMAFROST.asItem());
                        entries.add(ModBlocks.COBBLED_PERMAFROST_WALL.asItem());
                        entries.add(ModBlocks.COBBLED_PERMAFROST_SLAB.asItem());
                        entries.add(ModBlocks.COBBLED_PERMAFROST_STAIRS.asItem());
                        entries.add(ModBlocks.POINTED_PERMAFROST.asItem());
                        entries.add(ModBlocks.PERMAFROST_BRICKS.asItem());
                        entries.add(ModBlocks.PERMAFROST_BRICK_WALL.asItem());
                        entries.add(ModBlocks.PERMAFROST_BRICK_SLAB.asItem());
                        entries.add(ModBlocks.PERMAFROST_BRICK_STAIRS.asItem());
                        entries.add(ModBlocks.POLISHED_PERMAFROST.asItem());
                        entries.add(ModBlocks.POLISHED_PERMAFROST_WALL.asItem());
                        entries.add(ModBlocks.POLISHED_PERMAFROST_SLAB.asItem());
                        entries.add(ModBlocks.POLISHED_PERMAFROST_STAIRS.asItem());
                        entries.add(ModBlocks.PERMAFROST_GOLD_ORE.asItem());
                        entries.add(ModBlocks.PERMAFROST_IRON_ORE.asItem());
                        entries.add(ModBlocks.PERMAFROST_COAL_ORE.asItem());
                        entries.add(ModBlocks.PERMAFROST_LAPIS_ORE.asItem());
                        entries.add(ModBlocks.PERMAFROST_DIAMOND_ORE.asItem());
                        entries.add(ModBlocks.PERMAFROST_REDSTONE_ORE.asItem());
                        entries.add(ModBlocks.PERMAFROST_EMERALD_ORE.asItem());
                        entries.add(ModBlocks.PERMAFROST_COPPER_ORE.asItem());
                    }).build());



    public static void registerItemGroups() {
        //BlockmaMod.LOGGER.info("Registering Item Groups for " + BlockmaMod.MOD_ID);


    }
}
