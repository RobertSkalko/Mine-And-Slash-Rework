package com.robertx22.mine_and_slash.uncommon.effectdatas.rework.condition;

import com.robertx22.mine_and_slash.database.data.stats.Stat;
import com.robertx22.mine_and_slash.database.registry.ExileDB;
import com.robertx22.mine_and_slash.saveclasses.unit.StatData;
import com.robertx22.mine_and_slash.uncommon.effectdatas.EffectEvent;
import com.robertx22.mine_and_slash.uncommon.interfaces.EffectSides;

import java.util.ArrayList;
import java.util.List;

public class EitherIsTrueCondition extends StatCondition {

    List<String> ifs = new ArrayList<>();

    public EitherIsTrueCondition(String id, List<String> conditions) {
        super(id, "either_is_true");
        this.ifs = conditions;

    }

    EitherIsTrueCondition() {
        super("", "either_is_true");
    }

    @Override
    public boolean can(EffectEvent event, EffectSides statSource, StatData data, Stat stat) {
        return ifs.stream()
            .anyMatch(x -> ExileDB.StatConditions()
                .get(x)
                .can(event, statSource, data, stat));
    }

    @Override
    public Class<? extends StatCondition> getSerClass() {
        return EitherIsTrueCondition.class;
    }

}
