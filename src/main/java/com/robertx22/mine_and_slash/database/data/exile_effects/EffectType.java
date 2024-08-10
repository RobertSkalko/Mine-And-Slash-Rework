package com.robertx22.mine_and_slash.database.data.exile_effects;

public enum EffectType {
    beneficial(net.minecraft.world.effect.MobEffectCategory.BENEFICIAL),
    negative(net.minecraft.world.effect.MobEffectCategory.HARMFUL),
    buff(net.minecraft.world.effect.MobEffectCategory.BENEFICIAL),
    neutral(net.minecraft.world.effect.MobEffectCategory.NEUTRAL);

    public net.minecraft.world.effect.MobEffectCategory type;

    EffectType(net.minecraft.world.effect.MobEffectCategory type) {
        this.type = type;
    }
}