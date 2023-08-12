package com.robertx22.age_of_exile.uncommon.stat_calculation;

import com.robertx22.age_of_exile.saveclasses.unit.stat_ctx.StatContext;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import net.minecraft.world.entity.LivingEntity;

import java.util.List;

public class CommonStatUtils {

    public static List<StatContext> addExactCustomStats(LivingEntity en) {
        return Load.Unit(en)
                .getCustomExactStats()
                .getStatAndContext(en);
    }


}
