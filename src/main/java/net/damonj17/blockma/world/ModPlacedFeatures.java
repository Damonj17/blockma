package net.damonj17.blockma.world;

import net.damonj17.blockma.BlockmaMod;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.placementmodifier.PlacementModifier;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.placementmodifier.HeightRangePlacementModifier;

import java.util.List;

public class ModPlacedFeatures {

    public static final RegistryKey<PlacedFeature> POINTED_PERMAFROST = PlacedFeatures.of("permafrost_cluster");
    public static final RegistryKey<PlacedFeature> PERMAFROST_CLUSTER = PlacedFeatures.of("large_permafrost");
    public static final RegistryKey<PlacedFeature> LARGE_PERMAFROST = PlacedFeatures.of("pointed_permafrost");

    public static void bootstrap(Registerable<PlacedFeature> context) {
        var configuredFeatures = context.getRegistryLookup(RegistryKeys.CONFIGURED_FEATURE);

        //register(context, POINTED_PERMAFROST, configuredFeatures.getOrThrow(ModConfiguredFeatures.PINK_GARNET_ORE_KEY),
        //        ModOrePlacement.modifiersWithCount(14,
        //                HeightRangePlacementModifier.trapezoid(YOffset.fixed(-80), YOffset.fixed(80))));
        //register(context, PERMAFROST_CLUSTER, configuredFeatures.getOrThrow(ModConfiguredFeatures.NETHER_PINK_GARNET_ORE_KEY),
        //        ModOrePlacement.modifiersWithCount(14,
        //                HeightRangePlacementModifier.uniform(YOffset.fixed(-80), YOffset.fixed(80))));
        //register(context, LARGE_PERMAFROST, configuredFeatures.getOrThrow(ModConfiguredFeatures.END_PINK_GARNET_ORE_KEY),
        //        ModOrePlacement.modifiersWithCount(14,
        //                HeightRangePlacementModifier.uniform(YOffset.fixed(-80), YOffset.fixed(80))));

    }

    public static RegistryKey<PlacedFeature> registerKey(String name) {
        return RegistryKey.of(RegistryKeys.PLACED_FEATURE, Identifier.of(BlockmaMod.MOD_ID, name));
    }

    private static void register(Registerable<PlacedFeature> context, RegistryKey<PlacedFeature> key, RegistryEntry<ConfiguredFeature<?, ?>> configuration,
                                 List<PlacementModifier> modifiers) {
        context.register(key, new PlacedFeature(configuration, List.copyOf(modifiers)));
    }

    private static <FC extends FeatureConfig, F extends Feature<FC>> void register(Registerable<PlacedFeature> context, RegistryKey<PlacedFeature> key,
                                                                                   RegistryEntry<ConfiguredFeature<?, ?>> configuration,
                                                                                   PlacementModifier... modifiers) {
        register(context, key, configuration, List.of(modifiers));
    }
}