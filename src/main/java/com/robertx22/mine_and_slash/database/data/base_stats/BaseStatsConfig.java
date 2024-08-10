package com.robertx22.mine_and_slash.database.data.base_stats;

import com.robertx22.mine_and_slash.database.OptScaleExactStat;
import com.robertx22.mine_and_slash.database.data.stats.Stat;
import com.robertx22.mine_and_slash.database.registry.ExileRegistryTypes;
import com.robertx22.mine_and_slash.saveclasses.ExactStatData;
import com.robertx22.mine_and_slash.saveclasses.gearitem.gear_bases.IStatCtx;
import com.robertx22.mine_and_slash.saveclasses.unit.stat_ctx.SimpleStatCtx;
import com.robertx22.mine_and_slash.saveclasses.unit.stat_ctx.StatContext;
import com.robertx22.mine_and_slash.uncommon.datasaving.Load;
import com.robertx22.mine_and_slash.uncommon.enumclasses.ModType;
import com.robertx22.library_of_exile.registry.ExileRegistryType;
import com.robertx22.library_of_exile.registry.IAutoGson;
import com.robertx22.library_of_exile.registry.JsonExileRegistry;
import net.minecraft.world.entity.LivingEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BaseStatsConfig implements JsonExileRegistry<BaseStatsConfig>, IAutoGson<BaseStatsConfig>, IStatCtx {
    public static BaseStatsConfig SERIALIZER = new BaseStatsConfig();

    public String id;
    List<OptScaleExactStat> base_stats = new ArrayList<>();

    public void scaled(Stat stat, float val) {
        base_stats.add(new OptScaleExactStat(val, stat, ModType.FLAT).scale());
    }

    public void nonScaled(Stat stat, float val) {
        base_stats.add(new OptScaleExactStat(val, stat, ModType.FLAT));
    }

    @Override
    public List<StatContext> getStatAndContext(LivingEntity en) {
        List<ExactStatData> stats = new ArrayList<>();
        base_stats.forEach(x -> stats.add(x.toExactStat(Load.Unit(en)
                .getLevel())));
        return Arrays.asList(new SimpleStatCtx(StatContext.StatCtxType.BASE_STAT, stats));
    }

    @Override
    public int Weight() {
        return 1000;
    }

    @Override
    public Class<BaseStatsConfig> getClassForSerialization() {
        return BaseStatsConfig.class;
    }

    @Override
    public ExileRegistryType getExileRegistryType() {
        return ExileRegistryTypes.BASE_STATS;
    }

    @Override
    public String GUID() {
        return id;
    }
}
