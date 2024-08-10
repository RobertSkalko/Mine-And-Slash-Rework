package com.robertx22.mine_and_slash.uncommon.effectdatas.rework.condition;

import com.robertx22.mine_and_slash.aoe_data.database.stats.base.EffectCtx;
import com.robertx22.mine_and_slash.database.data.stats.Stat;
import com.robertx22.mine_and_slash.database.registry.ExileDB;
import com.robertx22.mine_and_slash.saveclasses.unit.StatData;
import com.robertx22.mine_and_slash.uncommon.datasaving.Load;
import com.robertx22.mine_and_slash.uncommon.effectdatas.EffectEvent;
import com.robertx22.mine_and_slash.uncommon.interfaces.EffectSides;

public class IsAtMaxCharges extends StatCondition {

    EffectSides side;
    String effect;

    public IsAtMaxCharges(EffectCtx ctx, EffectSides side) {
        super("is_" + side.id + "_max_" + ctx.id, "is_mns_effect_max_charges");
        this.side = side;
        this.effect = ctx.resourcePath;
    }

    IsAtMaxCharges() {
        super("", "is_mns_effect_max_charges");
    }

    @Override
    public boolean can(EffectEvent event, EffectSides statSource, StatData data, Stat stat) {
        var d = Load.Unit(event.getSide(side)).getStatusEffectsData();

        var eff = ExileDB.ExileEffects().get(effect);
        if (d.has(eff)) {
            return d.get(eff).stacks >= eff.getMaxCharges(event.targetData);
        }
        return false;
    }

    @Override
    public Class<? extends StatCondition> getSerClass() {
        return IsAtMaxCharges.class;
    }

}
