package net.damonj17.blockma;

import net.damonj17.blockma.block.ModBlocks;
import net.damonj17.blockma.item.ModItemGroups;
import net.damonj17.blockma.item.ModItems;
import net.damonj17.blockma.world.*;
import net.damonj17.blockma.world.gen.ModWorldGeneration;
import net.fabricmc.api.ModInitializer;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.feature.SmallDripstoneFeature;
import net.minecraft.world.gen.feature.SmallDripstoneFeatureConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BlockmaMod implements ModInitializer {
	public static final String MOD_ID = "blockma";

	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static final Identifier LARGE_PERMAFROST_FEATURE_ID = Identifier.of(BlockmaMod.MOD_ID, "large_permafrost");
	public static final LargePermafrostFeature LARGE_PERMAFROST_FEATURE = new LargePermafrostFeature(LargePermafrostFeatureConfig.CODEC);
	public static final Identifier POINTED_PERMAFROST_FEATURE_ID = Identifier.of(BlockmaMod.MOD_ID, "pointed_permafrost");
	public static final SmallPermafrostFeature POINTED_PERMAFROST_FEATURE = new SmallPermafrostFeature(SmallPermafrostFeatureConfig.CODEC);
	public static final Identifier PERMAFROST_CLUSTER_FEATURE_ID = Identifier.of(BlockmaMod.MOD_ID, "permafrost_cluster");
	public static final PermafrostClusterFeature PERMAFROST_CLUSTER_FEATURE = new PermafrostClusterFeature(PermafrostClusterFeatureConfig.CODEC);

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.
		LOGGER.info("Blockma Item Groups");
		ModItemGroups.registerItemGroups();
		LOGGER.info("Blockma Items");
		ModItems.initialize();
		LOGGER.info("Blockma Blocks");
		ModBlocks.initialize();
		LOGGER.info("Blockma Worldgen");
		ModWorldGeneration.generateModWorldGen();
		Registry.register(Registries.FEATURE, LARGE_PERMAFROST_FEATURE_ID, LARGE_PERMAFROST_FEATURE);
		Registry.register(Registries.FEATURE, POINTED_PERMAFROST_FEATURE_ID, POINTED_PERMAFROST_FEATURE);
		Registry.register(Registries.FEATURE, PERMAFROST_CLUSTER_FEATURE_ID, PERMAFROST_CLUSTER_FEATURE);
	}
}