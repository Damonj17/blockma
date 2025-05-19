package net.damonj17.blockma.item.data;

import net.minecraft.component.type.ConsumableComponent;
import net.minecraft.component.type.ConsumableComponents;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.consume.ApplyEffectsConsumeEffect;

import java.util.List;

public class BlockmaConsumableComponents {

    public static final ConsumableComponent GOLDEN_APPLE = ConsumableComponents.food()
            .consumeEffect(new ApplyEffectsConsumeEffect(List.of(new StatusEffectInstance(StatusEffects.REGENERATION, 100, 1), new StatusEffectInstance(StatusEffects.ABSORPTION, 2400, 0))))
            .build();

}
