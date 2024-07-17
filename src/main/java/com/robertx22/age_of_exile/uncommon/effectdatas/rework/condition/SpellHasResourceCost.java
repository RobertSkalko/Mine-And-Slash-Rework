package com.robertx22.age_of_exile.uncommon.effectdatas.rework.condition;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.saveclasses.unit.ResourceType;
import com.robertx22.age_of_exile.saveclasses.unit.StatData;
import com.robertx22.age_of_exile.uncommon.effectdatas.EffectEvent;
import com.robertx22.age_of_exile.uncommon.interfaces.EffectSides;

public class SpellHasResourceCost extends StatCondition {
    ResourceType type;

    public SpellHasResourceCost(ResourceType type) {
        super("spell_has_resource_type_cost_" + type.id, "spell_has_resource_type_cost");
        this.type = type;
    }

    @Override
    public boolean can(EffectEvent event, EffectSides statSource, StatData data, Stat stat) {
        return event.isSpell() && event.getSpell().hasCost(type);
    }

    @Override
    public Class<? extends StatCondition> getSerClass() {
        return SpellHasResourceCost.class;
    }
}
