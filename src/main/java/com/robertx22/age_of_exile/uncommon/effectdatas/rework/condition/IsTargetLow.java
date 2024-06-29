package com.robertx22.age_of_exile.uncommon.effectdatas.rework.condition;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.saveclasses.unit.StatData;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.effectdatas.EffectEvent;
import com.robertx22.age_of_exile.uncommon.interfaces.EffectSides;
import net.minecraft.world.entity.LivingEntity;

public class IsTargetLow extends StatCondition {

    EffectSides side;
    int perc;

    public IsTargetLow(String id, int percent, EffectSides side) {
        super(id, "is_target_low");
        this.side = side;
        this.perc = percent;
    }

    IsTargetLow() {
        super("", "is_target_low");
    }

    @Override
    public boolean can(EffectEvent event, EffectSides statSource, StatData data, Stat stat) {

        LivingEntity en = event.getSide(side);
        var endata = Load.Unit(en);
        float ms = endata.getResources().getMagicShield();
        float msmax = endata.getUnit().magicShieldData().getValue();

        float hp = en.getHealth();
        float maxhp = en.getMaxHealth();

        if (maxhp > msmax) {
            return perc > hp / maxhp * 100;
        } else {
            return perc > ms / msmax * 100;
        }


    }

    @Override
    public Class<? extends StatCondition> getSerClass() {
        return IsTargetLow.class;
    }

}
