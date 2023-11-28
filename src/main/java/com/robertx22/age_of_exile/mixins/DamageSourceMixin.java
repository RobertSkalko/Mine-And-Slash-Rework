package com.robertx22.age_of_exile.mixins;

import com.robertx22.age_of_exile.mixin_ducks.DamageSourceDuck;
import net.minecraft.world.damagesource.DamageSource;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(DamageSource.class)
public class DamageSourceMixin implements DamageSourceDuck {

    private float mnsDamage = 0;

    private boolean mnsOverride = false;

    @Override
    public void setMnsDamage(float dmg) {
        mnsDamage = dmg;
        mnsOverride = true;
    }

    @Override
    public float getMnsDamage() {
        return mnsDamage;
    }

    @Override
    public boolean hasMnsDamageOverride() {
        return mnsOverride;
    }
}
