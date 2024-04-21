package com.robertx22.age_of_exile.database.data.prophecy.starts;

import com.robertx22.age_of_exile.database.data.prophecy.ProphecyModifierType;
import com.robertx22.age_of_exile.database.data.prophecy.ProphecyStart;
import com.robertx22.age_of_exile.loot.LootInfo;
import com.robertx22.age_of_exile.loot.blueprints.ItemBlueprint;
import com.robertx22.age_of_exile.loot.blueprints.JewelBlueprint;

public class JewelProphecy extends ProphecyStart {
    @Override
    public ItemBlueprint create(int lvl, int tier) {
        var info = LootInfo.ofLevel(lvl);
        info.map_tier = tier;

        return new JewelBlueprint(info);
    }

    @Override
    public boolean acceptsModifier(ProphecyModifierType type) {
        return true;
    }

    @Override
    public String GUID() {
        return "jewel";
    }

    @Override
    public int Weight() {
        return 150;
    }

    @Override
    public String locNameForLangFile() {
        return "Jewel Prophecy";
    }
}
