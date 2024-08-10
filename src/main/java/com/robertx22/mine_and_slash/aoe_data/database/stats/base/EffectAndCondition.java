package com.robertx22.mine_and_slash.aoe_data.database.stats.base;

import com.robertx22.mine_and_slash.aoe_data.database.stat_conditions.StatConditions;
import com.robertx22.mine_and_slash.uncommon.effectdatas.rework.EventData;
import com.robertx22.mine_and_slash.uncommon.effectdatas.rework.condition.StatCondition;
import com.robertx22.mine_and_slash.uncommon.enumclasses.AttackType;
import com.robertx22.library_of_exile.registry.IGUID;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

public class EffectAndCondition extends AutoHashClass {

    public EffectCtx effect;
    public Condition con;

    public EffectAndCondition(EffectCtx effect, Condition con) {
        this.effect = effect;
        this.con = con;
    }

    @Override
    public int hashCode() {
        return effect.hashCode() + con.hashCode();
    }

    @Override
    public String GUID() {
        return effect.GUID() + "_on_" + con.GUID();
    }

    public enum Condition implements IGUID {
        HIT("hit", "Hit", () -> Arrays.asList(StatConditions.ATTACK_TYPE_MATCHES.get(AttackType.hit))),
        CRIT("crit", "Crit", () -> Arrays.asList(StatConditions.IS_BOOLEAN.get(EventData.CRIT), StatConditions.ATTACK_TYPE_MATCHES.get(AttackType.hit)));

        public String id;
        public String name;
        public Supplier<List<StatCondition>> con;

        Condition(String id, String name, Supplier<List<StatCondition>> con) {
            this.id = id;
            this.name = name;
            this.con = con;
        }

        @Override
        public String GUID() {
            return id;
        }
    }
}
