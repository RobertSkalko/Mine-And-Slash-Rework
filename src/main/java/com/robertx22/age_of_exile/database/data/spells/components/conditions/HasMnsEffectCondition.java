package com.robertx22.age_of_exile.database.data.spells.components.conditions;

import com.robertx22.age_of_exile.aoe_data.database.stats.base.EffectCtx;
import com.robertx22.age_of_exile.database.data.exile_effects.ExileEffect;
import com.robertx22.age_of_exile.database.data.spells.components.MapHolder;
import com.robertx22.age_of_exile.database.data.spells.map_fields.MapField;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.SpellCtx;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;

import java.util.Arrays;

public class HasMnsEffectCondition extends EffectCondition {

    public HasMnsEffectCondition() {
        super(Arrays.asList(MapField.EXILE_POTION_ID));
    }


    @Override
    public boolean canActivate(SpellCtx ctx, MapHolder data) {
        ExileEffect potion = data.getExileEffect();
        int stacks = data.getOrDefault(MapField.EFFECT_STACKS, 1D).intValue();
        var d = Load.Unit(ctx.target).getStatusEffectsData();
        return d.has(potion) && d.get(potion).stacks > stacks;
    }

    public MapHolder create(EffectCtx effect) {
        MapHolder d = new MapHolder();
        d.type = GUID();
        d.put(MapField.EXILE_POTION_ID, effect.id);
        return d;
    }

    @Override
    public String GUID() {
        return "has_mns_effect";
    }
}