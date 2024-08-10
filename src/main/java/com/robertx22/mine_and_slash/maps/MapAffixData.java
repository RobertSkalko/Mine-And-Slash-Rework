package com.robertx22.mine_and_slash.maps;


import com.robertx22.mine_and_slash.database.data.map_affix.MapAffix;
import com.robertx22.mine_and_slash.database.registry.ExileDB;

public class MapAffixData {

    public MapAffixData() {

    }


    public MapAffixData(MapAffix affix, int percent) {
        this.id = affix.GUID();
        this.p = percent;

    }

    public float getBonusLootMultiplier() {
        return 0.1F * getAffix().getLootMulti() * (p / 100F);

    }

    public String id;
    public int p;

    public MapAffix getAffix() {
        return ExileDB.MapAffixes().get(id);
    }


}