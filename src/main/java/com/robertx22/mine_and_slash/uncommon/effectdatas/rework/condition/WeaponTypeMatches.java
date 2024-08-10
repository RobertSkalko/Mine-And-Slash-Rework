package com.robertx22.mine_and_slash.uncommon.effectdatas.rework.condition;

import com.robertx22.mine_and_slash.database.data.stats.Stat;
import com.robertx22.mine_and_slash.saveclasses.unit.StatData;
import com.robertx22.mine_and_slash.uncommon.effectdatas.EffectEvent;
import com.robertx22.mine_and_slash.uncommon.enumclasses.WeaponTypes;
import com.robertx22.mine_and_slash.uncommon.interfaces.EffectSides;

public class WeaponTypeMatches extends StatCondition {

    public String type;

    public WeaponTypeMatches(WeaponTypes type) {
        super("is_" + type.id + "_wep_type", "wep_type_match");
        this.type = type.id;
    }

    public WeaponTypeMatches() {
        super("", "wep_type_match");
    }

    @Override
    public boolean can(EffectEvent event, EffectSides statSource, StatData data, Stat stat) {
        var wep = event.data.getWeaponType();
        return wep.id.equals(type);
    }

    @Override
    public Class<? extends StatCondition> getSerClass() {
        return WeaponTypeMatches.class;
    }

}

