package damonj17.blockma.datagen;

import net.damonj17.blockma.block.ModBlocks;
import net.damonj17.blockma.item.ModItems;
import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.client.data.BlockStateModelGenerator;
import net.minecraft.client.data.ItemModelGenerator;
import net.minecraft.client.data.Models;
import net.minecraft.item.Item;


public class ModModelProvider extends FabricModelProvider {
    public ModModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
        BlockStateModelGenerator.BlockTexturePool polishedPermafrostPool = blockStateModelGenerator.registerCubeAllModelTexturePool(ModBlocks.POLISHED_PERMAFROST);
        polishedPermafrostPool.stairs(ModBlocks.POLISHED_PERMAFROST_STAIRS);
        polishedPermafrostPool.slab(ModBlocks.POLISHED_PERMAFROST_SLAB);
        polishedPermafrostPool.wall(ModBlocks.POLISHED_PERMAFROST_WALL);

        BlockStateModelGenerator.BlockTexturePool permafrostPool = blockStateModelGenerator.registerCubeAllModelTexturePool(ModBlocks.PERMAFROST);
        permafrostPool.stairs(ModBlocks.PERMAFROST_STAIRS);
        permafrostPool.slab(ModBlocks.PERMAFROST_SLAB);
        permafrostPool.wall(ModBlocks.PERMAFROST_WALL);

        BlockStateModelGenerator.BlockTexturePool cobbledPermafrostPool = blockStateModelGenerator.registerCubeAllModelTexturePool(ModBlocks.COBBLED_PERMAFROST);
        cobbledPermafrostPool.stairs(ModBlocks.COBBLED_PERMAFROST_STAIRS);
        cobbledPermafrostPool.slab(ModBlocks.COBBLED_PERMAFROST_SLAB);
        cobbledPermafrostPool.wall(ModBlocks.COBBLED_PERMAFROST_WALL);

        BlockStateModelGenerator.BlockTexturePool permafrostBricksPool = blockStateModelGenerator.registerCubeAllModelTexturePool(ModBlocks.PERMAFROST_BRICKS);
        permafrostBricksPool.stairs(ModBlocks.PERMAFROST_BRICK_STAIRS);
        permafrostBricksPool.slab(ModBlocks.PERMAFROST_BRICK_SLAB);
        permafrostBricksPool.wall(ModBlocks.PERMAFROST_BRICK_WALL);

        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.PERMAFROST_GOLD_ORE);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.PERMAFROST_IRON_ORE);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.PERMAFROST_COAL_ORE);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.PERMAFROST_LAPIS_ORE);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.PERMAFROST_DIAMOND_ORE);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.PERMAFROST_REDSTONE_ORE);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.PERMAFROST_EMERALD_ORE);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.PERMAFROST_COPPER_ORE);
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        itemModelGenerator.register(ModItems.CHERRY, Models.GENERATED);
        itemModelGenerator.register(ModItems.GOLDEN_CHERRY, Models.GENERATED);
        itemModelGenerator.register(ModItems.PEACH, Models.GENERATED);
        itemModelGenerator.register(ModItems.GOLDEN_PEACH, Models.GENERATED);
        itemModelGenerator.register(ModItems.CANDY_APPLE, Models.GENERATED);
        itemModelGenerator.register(ModItems.GOLDEN_CANDY_APPLE, Models.GENERATED);
        //itemModelGenerator.register(Item.fromBlock(ModBlocks.POINTED_PERMAFROST), Models.GENERATED);
    }
}