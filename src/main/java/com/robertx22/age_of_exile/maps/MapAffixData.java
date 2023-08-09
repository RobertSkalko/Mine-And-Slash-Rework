package com.robertx22.age_of_exile.maps;


import com.robertx22.age_of_exile.database.data.map_affix.MapAffix;
import com.robertx22.age_of_exile.database.registry.ExileDB;

public class MapAffixData {

    public MapAffixData() {

    }


    public MapAffixData(MapAffix affix, int percent) {
        this.id = affix.GUID();
        this.p = percent;

    }

    public float getBonusLootMultiplier() {
        return 0.01F + (((float) p / 1200) * getAffix().getLootMulti());
    }

    public String id;
    public int p;

    public MapAffix getAffix() {
        return ExileDB.MapAffixes().get(id);
    }


}