package net.damonj17.blockma.block;

import net.damonj17.blockma.BlockmaMod;
import net.damonj17.blockma.block.custom.PointedPermafrostBlock;
import net.minecraft.block.*;
import net.minecraft.block.enums.NoteBlockInstrument;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.intprovider.ConstantIntProvider;
import net.minecraft.util.math.intprovider.UniformIntProvider;

import java.util.function.Function;

import static net.minecraft.block.Blocks.createLightLevelFromLitBlockState;

public class ModBlocks {

    public static void initialize() {}

    private static Block register(String name, Function<AbstractBlock.Settings, Block> blockFactory, AbstractBlock.Settings settings, boolean shouldRegisterItem) {
        // Create a registry key for the block
        RegistryKey<Block> blockKey = keyOfBlock(name);
        // Create the block instance
        Block block = blockFactory.apply(settings.registryKey(blockKey));

        // Sometimes, you may not want to register an item for the block.
        // Eg: if it's a technical block like `minecraft:moving_piston` or `minecraft:end_gateway`
        if (shouldRegisterItem) {
            // Items need to be registered with a different type of registry key, but the ID
            // can be the same.
            RegistryKey<Item> itemKey = keyOfItem(name);

            BlockItem blockItem = new BlockItem(block, new Item.Settings().registryKey(itemKey));
            Registry.register(Registries.ITEM, itemKey, blockItem);
        }

        return Registry.register(Registries.BLOCK, blockKey, block);
    }

    private static RegistryKey<Block> keyOfBlock(String name) {
        return RegistryKey.of(RegistryKeys.BLOCK, Identifier.of(BlockmaMod.MOD_ID, name));
    }

    private static RegistryKey<Item> keyOfItem(String name) {
        return RegistryKey.of(RegistryKeys.ITEM, Identifier.of(BlockmaMod.MOD_ID, name));
    }

    public static final Block PERMAFROST = register(
            "permafrost",
            Block::new,
            AbstractBlock.Settings.create().sounds(BlockSoundGroup.STONE).mapColor(MapColor.PALE_PURPLE).instrument(NoteBlockInstrument.BASEDRUM).requiresTool().strength(1.5F, 6.0F),
            true
    );

    public static final Block PERMAFROST_WALL = register(
            "permafrost_wall",
            WallBlock::new,
            AbstractBlock.Settings.copyShallow(PERMAFROST).solid(),
            true
    );

    public static final Block PERMAFROST_SLAB = register(
            "permafrost_slab",
            SlabBlock::new,
            AbstractBlock.Settings.copyShallow(PERMAFROST).solid(),
            true
    );

    public static final Block PERMAFROST_STAIRS = register(
            "permafrost_stairs",
            settings -> new StairsBlock(PERMAFROST.getDefaultState(), settings),
            AbstractBlock.Settings.copyShallow(PERMAFROST),
            true
    );

    public static final Block COBBLED_PERMAFROST = register(
            "cobbled_permafrost",
            Block::new,
            AbstractBlock.Settings.create().sounds(BlockSoundGroup.STONE).mapColor(MapColor.PALE_PURPLE).instrument(NoteBlockInstrument.BASEDRUM).requiresTool().strength(2.0F, 6.0F),
            true
    );

    public static final Block COBBLED_PERMAFROST_WALL = register(
            "cobbled_permafrost_wall",
            WallBlock::new,
            AbstractBlock.Settings.copyShallow(COBBLED_PERMAFROST).solid(),
            true
    );

    public static final Block COBBLED_PERMAFROST_SLAB = register(
            "cobbled_permafrost_slab",
            SlabBlock::new,
            AbstractBlock.Settings.copyShallow(COBBLED_PERMAFROST).solid(),
            true
    );

    public static final Block COBBLED_PERMAFROST_STAIRS = register(
            "cobbled_permafrost_stairs",
            settings -> new StairsBlock(COBBLED_PERMAFROST.getDefaultState(), settings),
            AbstractBlock.Settings.copyShallow(COBBLED_PERMAFROST),
            true
    );

    public static final Block PERMAFROST_BRICKS = register(
            "permafrost_bricks",
            Block::new,
            AbstractBlock.Settings.create().mapColor(MapColor.PALE_PURPLE).instrument(NoteBlockInstrument.BASEDRUM).requiresTool().strength(1.5F, 6.0F),
            true
    );

    public static final Block PERMAFROST_BRICK_WALL = register(
            "permafrost_brick_wall",
            WallBlock::new,
            AbstractBlock.Settings.copyShallow(PERMAFROST_BRICKS).solid(),
            true
    );

    public static final Block PERMAFROST_BRICK_SLAB = register(
            "permafrost_brick_slab",
            SlabBlock::new,
            AbstractBlock.Settings.copyShallow(PERMAFROST_BRICKS).solid(),
            true
    );

    public static final Block PERMAFROST_BRICK_STAIRS = register(
            "permafrost_brick_stairs",
            settings -> new StairsBlock(PERMAFROST_BRICKS.getDefaultState(), settings),
            AbstractBlock.Settings.copyShallow(PERMAFROST_BRICKS),
            true
    );

    public static final Block POLISHED_PERMAFROST = register(
            "polished_permafrost",
            Block::new,
            AbstractBlock.Settings.create().mapColor(MapColor.PALE_PURPLE).instrument(NoteBlockInstrument.BASEDRUM).requiresTool().strength(1.5F, 6.0F),
            true
    );

    public static final Block POLISHED_PERMAFROST_WALL = register(
            "polished_permafrost_wall",
            WallBlock::new,
            AbstractBlock.Settings.copyShallow(POLISHED_PERMAFROST).solid(),
            true
    );

    public static final Block POLISHED_PERMAFROST_SLAB = register(
            "polished_permafrost_slab",
            SlabBlock::new,
            AbstractBlock.Settings.copyShallow(POLISHED_PERMAFROST).solid(),
            true
    );

    public static final Block POLISHED_PERMAFROST_STAIRS = register(
            "polished_permafrost_stairs",
            settings -> new StairsBlock(POLISHED_PERMAFROST.getDefaultState(), settings),
            AbstractBlock.Settings.copyShallow(POLISHED_PERMAFROST),
            true
    );

    public static final Block POINTED_PERMAFROST = register(
            "pointed_permafrost",
            PointedPermafrostBlock::new,
            AbstractBlock.Settings.create().mapColor(MapColor.PALE_PURPLE).solid().instrument(NoteBlockInstrument.BASEDRUM).nonOpaque().sounds(BlockSoundGroup.POINTED_DRIPSTONE).ticksRandomly().strength(1.5F, 3.0F).dynamicBounds().offset(AbstractBlock.OffsetType.XZ).pistonBehavior(PistonBehavior.DESTROY).solidBlock(Blocks::never),
            true
    );

    public static final Block PERMAFROST_GOLD_ORE = register(
            "permafrost_gold_ore",
            settings -> new ExperienceDroppingBlock(ConstantIntProvider.create(0), settings),
            AbstractBlock.Settings.create().mapColor(MapColor.STONE_GRAY).instrument(NoteBlockInstrument.BASEDRUM).requiresTool().strength(3.0F, 3.0F),
            true
    );

    public static final Block PERMAFROST_IRON_ORE = register(
            "permafrost_iron_ore",
            settings -> new ExperienceDroppingBlock(ConstantIntProvider.create(0), settings),
            AbstractBlock.Settings.create().mapColor(MapColor.STONE_GRAY).instrument(NoteBlockInstrument.BASEDRUM).requiresTool().strength(3.0F, 3.0F),
            true
    );

    public static final Block PERMAFROST_COAL_ORE = register(
            "permafrost_coal_ore",
            settings -> new ExperienceDroppingBlock(UniformIntProvider.create(0, 2), settings),
            AbstractBlock.Settings.create().mapColor(MapColor.STONE_GRAY).instrument(NoteBlockInstrument.BASEDRUM).requiresTool().strength(3.0F, 3.0F),
            true
    );

    public static final Block PERMAFROST_LAPIS_ORE = register(
            "permafrost_lapis_ore",
            settings -> new ExperienceDroppingBlock(UniformIntProvider.create(2, 5), settings),
            AbstractBlock.Settings.create().mapColor(MapColor.STONE_GRAY).instrument(NoteBlockInstrument.BASEDRUM).requiresTool().strength(3.0F, 3.0F),
            true
    );

    public static final Block PERMAFROST_DIAMOND_ORE = register(
            "permafrost_diamond_ore",
            settings -> new ExperienceDroppingBlock(UniformIntProvider.create(3, 7), settings),
            AbstractBlock.Settings.create().mapColor(MapColor.STONE_GRAY).instrument(NoteBlockInstrument.BASEDRUM).requiresTool().strength(3.0F, 3.0F),
            true
    );

    public static final Block PERMAFROST_REDSTONE_ORE = register(
            "permafrost_redstone_ore",
            RedstoneOreBlock::new,
            AbstractBlock.Settings.create()
                    .mapColor(MapColor.STONE_GRAY)
                    .instrument(NoteBlockInstrument.BASEDRUM)
                    .requiresTool()
                    .ticksRandomly()
                    .luminance(createLightLevelFromLitBlockState(9))
                    .strength(3.0F, 3.0F),
            true
    );

    public static final Block PERMAFROST_EMERALD_ORE = register(
            "permafrost_emerald_ore",
            settings -> new ExperienceDroppingBlock(UniformIntProvider.create(3, 7), settings),
            AbstractBlock.Settings.create().mapColor(MapColor.STONE_GRAY).instrument(NoteBlockInstrument.BASEDRUM).requiresTool().strength(3.0F, 3.0F),
            true
    );

    public static final Block PERMAFROST_COPPER_ORE = register(
            "permafrost_copper_ore", settings -> new ExperienceDroppingBlock(ConstantIntProvider.create(0), settings), AbstractBlock.Settings.copyShallow(PERMAFROST_IRON_ORE),
            true
    );


}