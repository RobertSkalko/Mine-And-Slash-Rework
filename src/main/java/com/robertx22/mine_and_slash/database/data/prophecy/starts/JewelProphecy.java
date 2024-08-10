package com.robertx22.mine_and_slash.database.data.prophecy.starts;

import com.robertx22.mine_and_slash.database.data.prophecy.ProphecyModifierType;
import com.robertx22.mine_and_slash.database.data.prophecy.ProphecyStart;
import com.robertx22.mine_and_slash.loot.LootInfo;
import com.robertx22.mine_and_slash.loot.blueprints.ItemBlueprint;
import com.robertx22.mine_and_slash.loot.blueprints.JewelBlueprint;

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
