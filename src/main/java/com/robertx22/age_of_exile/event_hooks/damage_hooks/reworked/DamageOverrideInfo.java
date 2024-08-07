package com.robertx22.age_of_exile.event_hooks.damage_hooks.reworked;

import net.minecraft.world.damagesource.DamageSource;

public class DamageOverrideInfo {


    // todo how would i know that an attack is meant to be say a spell from other mod, and not a basic atk fail?
    // do i just not assume damage dmg type at all?

    // so if no source - enviro, if its mns dmg ignore, if is basic atk- basic, if not: its non mns other mod dmg and uses rage
    // maybe only try to basic atk if u hold a weapon

    public float vanillaDamage = 0;
    public float finalDamage = 0;

    public DamageSource source;

    public DamageType type = DamageType.NONE;
    public Result result = Result.NONE;
    public CancelReason cancelReason = CancelReason.NONE;
    public OverrideMethod overrideMethod = OverrideMethod.NONE;

    public static DamageOverrideInfo of(DamageSource source, float dmg, DamageType type) {
        DamageOverrideInfo b = new DamageOverrideInfo();
        b.type = type;
        b.source = source;
        b.vanillaDamage = dmg;
        b.finalDamage = dmg;
        return b;
    }

    public DamageOverrideInfo cancel(CancelReason reason) {
        this.cancelReason = reason;
        this.result = Result.DAMAGE_CANCELLED;
        return this;
    }

    public DamageOverrideInfo overrideDamage(OverrideMethod reason, float dmg) {
        this.overrideMethod = reason;
        this.finalDamage = dmg;
        return this;
    }

    public enum DamageType {
        NONE, ENVIRO_DAMAGE, BASIC_ATTACK, MINE_AND_SLASH, OTHER
    }

    public enum Result {
        NONE, DAMAGE_STAYS_VANILLA, DAMAGE_REPLACED, DAMAGE_TRANSFORMED, DAMAGE_CANCELLED
    }

    public enum CancelReason {
        NONE, NOT_VALID_BASIC_ATTACK
    }

    public enum OverrideMethod {
        NONE, OVERRIDE, CANCEL
    }
}
