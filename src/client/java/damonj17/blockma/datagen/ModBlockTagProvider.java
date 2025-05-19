package damonj17.blockma.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BlockTags;
import net.damonj17.blockma.block.ModBlocks;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagProvider extends FabricTagProvider.BlockTagProvider {
    public ModBlockTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
        getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE)
                .add(ModBlocks.COBBLED_PERMAFROST)
                .add(ModBlocks.COBBLED_PERMAFROST_SLAB)
                .add(ModBlocks.COBBLED_PERMAFROST_STAIRS)
                .add(ModBlocks.COBBLED_PERMAFROST_WALL)
                .add(ModBlocks.PERMAFROST)
                .add(ModBlocks.PERMAFROST_SLAB)
                .add(ModBlocks.PERMAFROST_STAIRS)
                .add(ModBlocks.PERMAFROST_WALL)
                .add(ModBlocks.PERMAFROST_BRICKS)
                .add(ModBlocks.PERMAFROST_BRICK_SLAB)
                .add(ModBlocks.PERMAFROST_BRICK_STAIRS)
                .add(ModBlocks.PERMAFROST_BRICK_WALL)
                .add(ModBlocks.POLISHED_PERMAFROST)
                .add(ModBlocks.POLISHED_PERMAFROST_SLAB)
                .add(ModBlocks.POLISHED_PERMAFROST_STAIRS)
                .add(ModBlocks.POLISHED_PERMAFROST_WALL)
                .add(ModBlocks.POINTED_PERMAFROST)
                .add(ModBlocks.PERMAFROST_GOLD_ORE)
                .add(ModBlocks.PERMAFROST_IRON_ORE)
                .add(ModBlocks.PERMAFROST_COAL_ORE)
                .add(ModBlocks.PERMAFROST_LAPIS_ORE)
                .add(ModBlocks.PERMAFROST_DIAMOND_ORE)
                .add(ModBlocks.PERMAFROST_REDSTONE_ORE)
                .add(ModBlocks.PERMAFROST_EMERALD_ORE)
                .add(ModBlocks.PERMAFROST_COPPER_ORE);



        getOrCreateTagBuilder(BlockTags.WALLS)
                .add(ModBlocks.PERMAFROST_WALL)
                .add(ModBlocks.COBBLED_PERMAFROST_WALL)
                .add(ModBlocks.POLISHED_PERMAFROST_WALL)
                .add(ModBlocks.PERMAFROST_BRICK_WALL);
    }
}