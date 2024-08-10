package com.robertx22.mine_and_slash.database.data.prophecy.starts;

import com.robertx22.mine_and_slash.database.data.league.LeagueMechanics;
import com.robertx22.mine_and_slash.database.data.league.ProphecyLeague;
import com.robertx22.mine_and_slash.database.data.prophecy.ProphecyModifierType;
import com.robertx22.mine_and_slash.database.data.prophecy.ProphecyStart;
import com.robertx22.mine_and_slash.database.registry.ExileDB;
import com.robertx22.mine_and_slash.loot.LootInfo;
import com.robertx22.mine_and_slash.loot.blueprints.GearBlueprint;
import com.robertx22.mine_and_slash.loot.blueprints.ItemBlueprint;
import com.robertx22.mine_and_slash.uncommon.interfaces.data_items.IRarity;

public class ProphecyUniqueGearProphecy extends ProphecyStart {

    public String league = new ProphecyLeague().GUID();

    @Override
    public ItemBlueprint create(int lvl, int tier) {

        var info = LootInfo.ofLevel(lvl);
        info.league = ExileDB.LeagueMechanics().get(league);
        info.map_tier = tier;

        var b = new GearBlueprint(info);
        b.uniquePart.set(ExileDB.UniqueGears().getFilterWrapped(x -> x.canSpawnInLeague(LeagueMechanics.PROPHECY)).random());
        b.rarity.set(ExileDB.GearRarities().get(IRarity.UNIQUE_ID));
        return b;
    }

    @Override
    public boolean acceptsModifier(ProphecyModifierType type) {
        return false;
    }

    @Override
    public String GUID() {
        return "prophecy_unique";
    }

    @Override
    public int Weight() {
        return 50;
    }

    @Override
    public String locNameForLangFile() {
        return "Prophecy League Unique";
    }
}
