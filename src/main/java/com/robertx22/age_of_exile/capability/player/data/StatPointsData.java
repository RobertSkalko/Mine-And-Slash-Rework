package com.robertx22.age_of_exile.capability.player.data;

import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.saveclasses.ExactStatData;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.IStatCtx;
import com.robertx22.age_of_exile.saveclasses.unit.stat_ctx.SimpleStatCtx;
import com.robertx22.age_of_exile.saveclasses.unit.stat_ctx.StatContext;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;
import net.minecraft.world.entity.LivingEntity;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class StatPointsData implements IStatCtx {


    public HashMap<String, Integer> map = new HashMap<>();

    public void reset() {
        this.map.clear();
    }

    public int getAllocatedPoints() {
        int spent = 0;

        for (Map.Entry<String, Integer> x : map.entrySet()) {
            spent += x.getValue();
        }
        return spent;
    }

    @Override
    public List<StatContext> getStatAndContext(LivingEntity en) {

        var list = map.entrySet().stream().map(x -> {
            float val = x.getValue();
            ExactStatData stat = ExactStatData.levelScaled(val, ExileDB.Stats()
                    .get(x.getKey()), ModType.FLAT, 1);
            return stat;
        }).collect(Collectors.toList());

        return Arrays.asList(new SimpleStatCtx(StatContext.StatCtxType.STAT_POINTS, list));
    }
}
