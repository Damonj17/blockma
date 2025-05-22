package net.damonj17.blockma.world.feature;

import net.damonj17.blockma.BlockmaMod;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.ConfiguredFeatures;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.block.Blocks;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.structure.rule.BlockMatchRuleTest;
import net.minecraft.structure.rule.RuleTest;
import net.minecraft.structure.rule.TagMatchRuleTest;

public class ModConfiguredFeatures {
    public static final RegistryKey<ConfiguredFeature<?, ?>> POINTED_PERMAFROST = ConfiguredFeatures.of("permafrost_cluster");
    public static final RegistryKey<ConfiguredFeature<?, ?>> PERMAFROST_CLUSTER = ConfiguredFeatures.of("large_permafrost");
    public static final RegistryKey<ConfiguredFeature<?, ?>> LARGE_PERMAFROST = ConfiguredFeatures.of("pointed_permafrost");
    //public static final RegistryKey<ConfiguredFeature<?, ?>> PERMAFROST_ORE_COAL = ConfiguredFeatures.of("permafrost_ore_coal");
    //public static final RegistryKey<ConfiguredFeature<?, ?>> PERMAFROST_ORE_COAL_BURIED = ConfiguredFeatures.of("permafrost_ore_coal_buried");
    //public static final RegistryKey<ConfiguredFeature<?, ?>> PERMAFROST_ORE_IRON = ConfiguredFeatures.of("permafrost_ore_iron");
    //public static final RegistryKey<ConfiguredFeature<?, ?>> PERMAFROST_ORE_IRON_SMALL = ConfiguredFeatures.of("permafrost_ore_iron_small");
    //public static final RegistryKey<ConfiguredFeature<?, ?>> PERMAFROST_ORE_GOLD = ConfiguredFeatures.of("permafrost_ore_gold");
    //public static final RegistryKey<ConfiguredFeature<?, ?>> PERMAFROST_ORE_GOLD_BURIED = ConfiguredFeatures.of("permafrost_ore_gold_buried");
    //public static final RegistryKey<ConfiguredFeature<?, ?>> PERMAFROST_ORE_REDSTONE = ConfiguredFeatures.of("permafrost_ore_redstone");
    //public static final RegistryKey<ConfiguredFeature<?, ?>> PERMAFROST_ORE_DIAMOND_SMALL = ConfiguredFeatures.of("permafrost_ore_diamond_small");
    //public static final RegistryKey<ConfiguredFeature<?, ?>> PERMAFROST_ORE_DIAMOND_MEDIUM = ConfiguredFeatures.of("permafrost_ore_diamond_medium");
    //public static final RegistryKey<ConfiguredFeature<?, ?>> PERMAFROST_ORE_DIAMOND_LARGE = ConfiguredFeatures.of("permafrost_ore_diamond_large");
    //public static final RegistryKey<ConfiguredFeature<?, ?>> PERMAFROST_ORE_DIAMOND_BURIED = ConfiguredFeatures.of("permafrost_ore_diamond_buried");
    //public static final RegistryKey<ConfiguredFeature<?, ?>> PERMAFROST_ORE_LAPIS = ConfiguredFeatures.of("permafrost_ore_lapis");
    //public static final RegistryKey<ConfiguredFeature<?, ?>> PERMAFROST_ORE_LAPIS_BURIED = ConfiguredFeatures.of("permafrost_ore_lapis_buried");

    public static void bootstrap(Registerable<ConfiguredFeature<?, ?>> context) {
        RuleTest stoneReplaceables = new TagMatchRuleTest(BlockTags.STONE_ORE_REPLACEABLES);
        RuleTest deepslateReplaceables = new TagMatchRuleTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES);
        RuleTest netherReplaceables = new TagMatchRuleTest(BlockTags.BASE_STONE_NETHER);
        RuleTest endReplaceables = new BlockMatchRuleTest(Blocks.END_STONE);

        //List<OreFeatureConfig.Target> overworldPinkGarnetOres =
        //        List.of(OreFeatureConfig.createTarget(stoneReplaceables, ModBlocks.PINK_GARNET_ORE.getDefaultState()),
        //                OreFeatureConfig.createTarget(deepslateReplaceables, ModBlocks.PINK_GARNET_DEEPSLATE_ORE.getDefaultState()));
        //List<OreFeatureConfig.Target> netherPinkGarnetOres =
        //        List.of(OreFeatureConfig.createTarget(netherReplaceables, ModBlocks.PINK_GARNET_NETHER_ORE.getDefaultState()));
        //List<OreFeatureConfig.Target> endPinkGarnetOres =
        //        List.of(OreFeatureConfig.createTarget(endReplaceables, ModBlocks.PINK_GARNET_END_ORE.getDefaultState()));

        //register(context, PINK_GARNET_ORE_KEY, Feature.ORE, new OreFeatureConfig(overworldPinkGarnetOres, 12));
        //register(context, NETHER_PINK_GARNET_ORE_KEY, Feature.ORE, new OreFeatureConfig(netherPinkGarnetOres, 9));
        //register(context, END_PINK_GARNET_ORE_KEY, Feature.ORE, new OreFeatureConfig(endPinkGarnetOres, 9));
    }

    public static RegistryKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return RegistryKey.of(RegistryKeys.CONFIGURED_FEATURE, Identifier.of(BlockmaMod.MOD_ID, name));
    }

    private static <FC extends FeatureConfig, F extends Feature<FC>> void register(Registerable<ConfiguredFeature<?, ?>> context,
                                                                                   RegistryKey<ConfiguredFeature<?, ?>> key, F feature, FC configuration) {
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }
}