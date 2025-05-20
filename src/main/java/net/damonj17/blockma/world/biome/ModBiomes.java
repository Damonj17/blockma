package net.damonj17.blockma.world.biome;

import net.damonj17.blockma.BlockmaMod;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.sound.MusicSound;
import net.minecraft.sound.MusicType;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.Biome;
//import net.minecraft.client.sound.MusicType;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.BiomeMoodSound;
import net.minecraft.world.biome.BiomeEffects;
import net.minecraft.world.biome.GenerationSettings;
import net.minecraft.world.biome.SpawnSettings;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.DefaultBiomeFeatures;
import net.minecraft.world.gen.feature.VegetationPlacedFeatures;

public class ModBiomes {
    //public static final RegistryKey<Biome> PERMAFROST_CAVE = RegistryKey.of(RegistryKeys.BIOME,
    //        new Identifier(BlockmaMod.MOD_ID,"permafrost_cave"));


    public static void bootstrap(Registerable<Biome> context) {
        //context.register(PERMAFROST_CAVE, permafrostCave(context));
    }

    public static void globalOverworldGeneration(GenerationSettings.LookupBackedBuilder builder) {
        DefaultBiomeFeatures.addLandCarvers(builder);
        DefaultBiomeFeatures.addAmethystGeodes(builder);
        DefaultBiomeFeatures.addDungeons(builder);
        DefaultBiomeFeatures.addMineables(builder);
        DefaultBiomeFeatures.addSprings(builder);
        DefaultBiomeFeatures.addFrozenTopLayer(builder);
    }

    //public static Biome permafrostCave(Registerable<Biome> context) {
    //    SpawnSettings.Builder builder = new SpawnSettings.Builder();
    //    DefaultBiomeFeatures.addDripstoneCaveMobs(builder);
    //    GenerationSettings.LookupBackedBuilder lookupBackedBuilder = new GenerationSettings.LookupBackedBuilder(featureLookup, carverLookup);
    //    DefaultBiomeFeatures.addLandCarvers(lookupBackedBuilder);
    //    DefaultBiomeFeatures.addAmethystGeodes(lookupBackedBuilder);
    //    DefaultBiomeFeatures.addDungeons(lookupBackedBuilder);
    //    DefaultBiomeFeatures.addMineables(lookupBackedBuilder);
    //    DefaultBiomeFeatures.addSprings(lookupBackedBuilder);
    //    DefaultBiomeFeatures.addFrozenTopLayer(lookupBackedBuilder);
    //    DefaultBiomeFeatures.addPlainsTallGrass(lookupBackedBuilder);
    //    DefaultBiomeFeatures.addDefaultOres(lookupBackedBuilder, true);
    //    DefaultBiomeFeatures.addDefaultDisks(lookupBackedBuilder);
    //    DefaultBiomeFeatures.addPlainsFeatures(lookupBackedBuilder);
    //    DefaultBiomeFeatures.addDefaultMushrooms(lookupBackedBuilder);
    //    DefaultBiomeFeatures.addDefaultVegetation(lookupBackedBuilder, false);
    //    DefaultBiomeFeatures.addPermafrost(lookupBackedBuilder);
    //    MusicSound musicSound = MusicType.createIngameMusic(SoundEvents.MUSIC_OVERWORLD_DRIPSTONE_CAVES);
    //    return new Biome.Builder()
    //            .precipitation(true)
    //            .downfall(0.4F)
    //            .temperature(0.4F)
    //            .generationSettings(lookupBackedBuilder.build())
    //            .spawnSettings(spawnBuilder.build())
    //            .effects((new BiomeEffects.Builder())
    //                    .waterColor(0xe82e3b)
    //                    .waterFogColor(0xbf1b26)
    //                    .skyColor(0x30c918)
    //                    .grassColor(0x7f03fc)
    //                    .foliageColor(0xd203fc)
    //                    .fogColor(0x22a1e6)
    //                    .moodSound(BiomeMoodSound.CAVE)
    //                    .music(musicSound).build())
    //            .build();
        //, builder, lookupBackedBuilder, musicSound);
    //}
}