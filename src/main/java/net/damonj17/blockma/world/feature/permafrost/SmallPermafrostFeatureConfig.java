package net.damonj17.blockma.world.feature.permafrost;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.gen.feature.FeatureConfig;

public class SmallPermafrostFeatureConfig implements FeatureConfig {
    public static final Codec<SmallPermafrostFeatureConfig> CODEC = RecordCodecBuilder.create((instance) -> {
        return instance.group(Codec.floatRange(0.0F, 1.0F).fieldOf("chance_of_taller_permafrost").orElse(0.2F).forGetter((config) -> {
            return config.chanceOfTallerPermafrost;
        }), Codec.floatRange(0.0F, 1.0F).fieldOf("chance_of_directional_spread").orElse(0.7F).forGetter((config) -> {
            return config.chanceOfDirectionalSpread;
        }), Codec.floatRange(0.0F, 1.0F).fieldOf("chance_of_spread_radius2").orElse(0.5F).forGetter((config) -> {
            return config.chanceOfSpreadRadius2;
        }), Codec.floatRange(0.0F, 1.0F).fieldOf("chance_of_spread_radius3").orElse(0.5F).forGetter((config) -> {
            return config.chanceOfSpreadRadius3;
        })).apply(instance, SmallPermafrostFeatureConfig::new);
    });
    public final float chanceOfTallerPermafrost;
    public final float chanceOfDirectionalSpread;
    public final float chanceOfSpreadRadius2;
    public final float chanceOfSpreadRadius3;

    public SmallPermafrostFeatureConfig(float chanceOfTallerPermafrost, float chanceOfDirectionalSpread, float chanceOfSpreadRadius2, float chanceOfSpreadRadius3) {
        this.chanceOfTallerPermafrost = chanceOfTallerPermafrost;
        this.chanceOfDirectionalSpread = chanceOfDirectionalSpread;
        this.chanceOfSpreadRadius2 = chanceOfSpreadRadius2;
        this.chanceOfSpreadRadius3 = chanceOfSpreadRadius3;
    }
}
