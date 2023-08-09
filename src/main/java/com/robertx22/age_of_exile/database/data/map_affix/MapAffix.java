package com.robertx22.age_of_exile.database.data.map_affix;

import com.robertx22.age_of_exile.database.data.StatMod;
import com.robertx22.age_of_exile.database.data.mob_affixes.MobAffix;
import com.robertx22.age_of_exile.database.registry.ExileRegistryTypes;
import com.robertx22.age_of_exile.saveclasses.ExactStatData;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.IApplyableStats;
import com.robertx22.age_of_exile.saveclasses.map.AffectedEntities;
import com.robertx22.age_of_exile.saveclasses.unit.stat_ctx.SimpleStatCtx;
import com.robertx22.age_of_exile.saveclasses.unit.stat_ctx.StatContext;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.library_of_exile.registry.ExileRegistry;
import com.robertx22.library_of_exile.registry.ExileRegistryType;
import net.minecraft.world.entity.LivingEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MapAffix implements ExileRegistry<MobAffix>, IApplyableStats {

    List<StatMod> stats = new ArrayList<>();
    String id = "";
    int weight = 1000;
    public AffectedEntities affected = AffectedEntities.Mobs;

    public MapAffix(String id) {
        this.id = id;
    }

    public MapAffix addMod(StatMod mod) {
        this.stats.add(mod);
        return this;
    }

    public MapAffix affectsPlayer() {
        this.affected = AffectedEntities.Players;
        return this;
    }

    public MapAffix setWeight(int weight) {
        this.weight = weight;
        return this;
    }

    @Override
    public ExileRegistryType getExileRegistryType() {
        return ExileRegistryTypes.MOB_AFFIX;
    }

    @Override
    public String GUID() {
        return id;
    }

    @Override
    public int Weight() {
        return weight;
    }


    @Override
    public List<StatContext> getStatAndContext(LivingEntity en) {
        List<ExactStatData> stats = new ArrayList<>();
        try {
            this.stats.forEach(x -> stats.add(x.ToExactStat(100, Load.Unit(en)
                    .getLevel())));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Arrays.asList(new SimpleStatCtx(StatContext.StatCtxType.MOB_AFFIX, stats));
    }

}
