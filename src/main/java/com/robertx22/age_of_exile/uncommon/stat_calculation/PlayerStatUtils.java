package com.robertx22.age_of_exile.uncommon.stat_calculation;

import com.robertx22.age_of_exile.aoe_data.database.base_stats.BaseStatsAdder;
import com.robertx22.age_of_exile.capability.entity.EntityData;
import com.robertx22.age_of_exile.database.data.base_stats.BaseStatsConfig;
import com.robertx22.age_of_exile.database.data.stats.types.generated.ElementalResist;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.saveclasses.ExactStatData;
import com.robertx22.age_of_exile.saveclasses.unit.stat_ctx.MiscStatCtx;
import com.robertx22.age_of_exile.saveclasses.unit.stat_ctx.StatContext;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;
import net.minecraft.world.entity.LivingEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class PlayerStatUtils {
    
    public static List<StatContext> addNewbieElementalResists(EntityData data) {
        List<ExactStatData> stats = new ArrayList<>();

        int value = 50;

        if (data.getLevel() > 24) {
            value = 25;
        }
        if (data.getLevel() > 49) {
            return Arrays.asList(new MiscStatCtx(stats));
        }
        for (Elements ele : Elements.getAllSingle()) {
            if (ele != Elements.Physical) {
                stats.add(ExactStatData.noScaling(value, ModType.FLAT, new ElementalResist(ele).GUID()));
            }
        }
        return Arrays.asList(new MiscStatCtx(stats));

    }

    public static List<StatContext> AddPlayerBaseStats(LivingEntity en) {

        try {
            BaseStatsConfig stats = ExileDB.BaseStats()
                    .get(BaseStatsAdder.PLAYER);
            Objects.requireNonNull(stats);
            return stats.getStatAndContext(en);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Arrays.asList();

    }

}
