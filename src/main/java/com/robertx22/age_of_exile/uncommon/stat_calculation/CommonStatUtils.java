package com.robertx22.age_of_exile.uncommon.stat_calculation;

import com.robertx22.age_of_exile.aoe_data.database.base_stats.BaseStatsAdder;
import com.robertx22.age_of_exile.database.data.base_stats.BaseStatsConfig;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.maps.MapData;
import com.robertx22.age_of_exile.maps.MapItemData;
import com.robertx22.age_of_exile.saveclasses.unit.stat_ctx.StatContext;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.utilityclasses.WorldUtils;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class CommonStatUtils {

    public static List<StatContext> addExactCustomStats(LivingEntity en) {
        return Load.Unit(en)
                .getCustomExactStats()
                .getStatAndContext(en);
    }

    public static List<StatContext> addBaseStats(LivingEntity en) {

        try {
            String id = BaseStatsAdder.PLAYER;
            if (en instanceof Player == false) {
                id = BaseStatsAdder.MOB;
            }

            BaseStatsConfig stats = ExileDB.BaseStats().get(id);

            Objects.requireNonNull(stats);

            return stats.getStatAndContext(en);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return Arrays.asList();

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
