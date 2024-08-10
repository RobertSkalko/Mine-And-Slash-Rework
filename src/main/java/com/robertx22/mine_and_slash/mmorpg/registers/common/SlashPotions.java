package com.robertx22.mine_and_slash.mmorpg.registers.common;

import com.robertx22.mine_and_slash.mmorpg.registers.deferred_wrapper.Def;
import com.robertx22.mine_and_slash.mmorpg.registers.deferred_wrapper.RegObj;
import com.robertx22.mine_and_slash.vanilla_mc.potion_effects.ModStatusEffect;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class SlashPotions {

    public static RegObj<MobEffect> KNOCKBACK_RESISTANCE = Def.potion("knockback_resist", () -> new ModStatusEffect(net.minecraft.world.effect.MobEffectCategory.BENEFICIAL, 1)
            .addAttributeModifier(Attributes.KNOCKBACK_RESISTANCE, "648D7564-6A60-4F59-8ABE-C2C27A6DD7A9", 0.1F, AttributeModifier.Operation.ADDITION));

    public static RegObj<MobEffect> MEAL = Def.potion("meal", () -> new ModStatusEffect(net.minecraft.world.effect.MobEffectCategory.BENEFICIAL, 1));
    public static RegObj<MobEffect> FISH = Def.potion("fish", () -> new ModStatusEffect(net.minecraft.world.effect.MobEffectCategory.BENEFICIAL, 1));
    public static RegObj<MobEffect> POTION = Def.potion("potion", () -> new ModStatusEffect(net.minecraft.world.effect.MobEffectCategory.BENEFICIAL, 1));

    public static RegObj<MobEffect> INSTANT_ARROWS = Def.potion("instant_arrows", () -> new ModStatusEffect(net.minecraft.world.effect.MobEffectCategory.BENEFICIAL, 1));


    public static void init() {

        // todo make my own potions so i'm not screwed by vanilla's 255 registry limit
        // todo check if this version still has that limit or not


    }

}
