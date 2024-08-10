package com.robertx22.mine_and_slash.mixin_ducks;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.sounds.SoundEvent;

public interface LivingEntityAccesor {

    void myknockback(LivingEntity target);

    SoundEvent myGetHurtSound(DamageSource source);

    float myGetHurtVolume();

    float myGetHurtPitch();

}
