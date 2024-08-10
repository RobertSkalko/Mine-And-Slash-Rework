package com.robertx22.mine_and_slash.mixins;

import com.robertx22.mine_and_slash.mixin_ducks.DamageSourceDuck;
import net.minecraft.world.damagesource.DamageSource;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(DamageSource.class)
public class DamageSourceMixin implements DamageSourceDuck {

    private float mnsDamage = 0;
    private float originalHP = 1;

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
    public void setOriginalHP(float hp) {
        this.originalHP = hp;
    }

    @Override
    public float getOriginalHP() {
        return originalHP;
    }

    @Override
    public boolean hasMnsDamageOverride() {
        return mnsOverride;
    }
}
