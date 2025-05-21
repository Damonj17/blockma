package net.damonj17.blockma.world;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.math.floatprovider.FloatProvider;
import net.minecraft.util.math.intprovider.IntProvider;
import net.minecraft.world.gen.feature.FeatureConfig;

public class PermafrostClusterFeatureConfig implements FeatureConfig {
    public static final Codec<PermafrostClusterFeatureConfig> CODEC = RecordCodecBuilder.create((instance) -> {
        return instance.group(Codec.intRange(1, 512).fieldOf("floor_to_ceiling_search_range").forGetter((config) -> {
            return config.floorToCeilingSearchRange;
        }), IntProvider.createValidatingCodec(1, 128).fieldOf("height").forGetter((config) -> {
            return config.height;
        }), IntProvider.createValidatingCodec(1, 128).fieldOf("radius").forGetter((config) -> {
            return config.radius;
        }), Codec.intRange(0, 64).fieldOf("max_stalagmite_stalactite_height_diff").forGetter((config) -> {
            return config.maxStalagmiteStalactiteHeightDiff;
        }), Codec.intRange(1, 64).fieldOf("height_deviation").forGetter((config) -> {
            return config.heightDeviation;
        }), IntProvider.createValidatingCodec(0, 128).fieldOf("permafrost_block_layer_thickness").forGetter((config) -> {
            return config.permafrostBlockLayerThickness;
        }), FloatProvider.createValidatedCodec(0.0F, 2.0F).fieldOf("density").forGetter((config) -> {
            return config.density;
        }), FloatProvider.createValidatedCodec(0.0F, 2.0F).fieldOf("wetness").forGetter((config) -> {
            return config.wetness;
        }), Codec.floatRange(0.0F, 1.0F).fieldOf("chance_of_permafrost_column_at_max_distance_from_center").forGetter((config) -> {
            return config.chanceOfPermafrostColumnAtMaxDistanceFromCenter;
        }), Codec.intRange(1, 64).fieldOf("max_distance_from_edge_affecting_chance_of_permafrost_column").forGetter((config) -> {
            return config.maxDistanceFromCenterAffectingChanceOfPermafrostColumn;
        }), Codec.intRange(1, 64).fieldOf("max_distance_from_center_affecting_height_bias").forGetter((config) -> {
            return config.maxDistanceFromCenterAffectingHeightBias;
        })).apply(instance, PermafrostClusterFeatureConfig::new);
    });
    public final int floorToCeilingSearchRange;
    public final IntProvider height;
    public final IntProvider radius;
    public final int maxStalagmiteStalactiteHeightDiff;
    public final int heightDeviation;
    public final IntProvider permafrostBlockLayerThickness;
    public final FloatProvider density;
    public final FloatProvider wetness;
    public final float chanceOfPermafrostColumnAtMaxDistanceFromCenter;
    public final int maxDistanceFromCenterAffectingChanceOfPermafrostColumn;
    public final int maxDistanceFromCenterAffectingHeightBias;

    public PermafrostClusterFeatureConfig(int floorToCeilingSearchRange, IntProvider height, IntProvider radius, int maxStalagmiteStalactiteHeightDiff, int heightDeviation, IntProvider permafrostBlockLayerThickness, FloatProvider density, FloatProvider wetness, float wetnessMean, int maxDistanceFromCenterAffectingChanceOfPermafrostColumn, int maxDistanceFromCenterAffectingHeightBias) {
        this.floorToCeilingSearchRange = floorToCeilingSearchRange;
        this.height = height;
        this.radius = radius;
        this.maxStalagmiteStalactiteHeightDiff = maxStalagmiteStalactiteHeightDiff;
        this.heightDeviation = heightDeviation;
        this.permafrostBlockLayerThickness = permafrostBlockLayerThickness;
        this.density = density;
        this.wetness = wetness;
        this.chanceOfPermafrostColumnAtMaxDistanceFromCenter = wetnessMean;
        this.maxDistanceFromCenterAffectingChanceOfPermafrostColumn = maxDistanceFromCenterAffectingChanceOfPermafrostColumn;
        this.maxDistanceFromCenterAffectingHeightBias = maxDistanceFromCenterAffectingHeightBias;
    }
}
