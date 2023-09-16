package com.robertx22.age_of_exile.uncommon.stat_calculation;

import com.robertx22.age_of_exile.maps.MapData;
import com.robertx22.age_of_exile.maps.MapItemData;
import com.robertx22.age_of_exile.saveclasses.unit.stat_ctx.StatContext;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.utilityclasses.WorldUtils;
import net.minecraft.world.entity.LivingEntity;

import java.util.ArrayList;
import java.util.List;

public class CommonStatUtils {

    public static List<StatContext> addExactCustomStats(LivingEntity en) {
        return Load.Unit(en)
                .getCustomExactStats()
                .getStatAndContext(en);
    }

    public static List<StatContext> addMapAffixStats(LivingEntity en) {

        var list = new ArrayList<StatContext>();

        if (WorldUtils.isMapWorldClass(en.level())) {

            MapData map = Load.mapAt(en.level(), en.blockPosition());
            if (map != null) {
                MapItemData data = map.map;

                for (StatContext stat : data.getStatAndContext(en)) {
                    list.add(stat);
                }
            }
        }

        return list;

    }

}
