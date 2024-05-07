package com.robertx22.age_of_exile.database.data.stats.datapacks.stats;

import com.robertx22.age_of_exile.aoe_data.database.stats.base.EffectCtx;
import com.robertx22.age_of_exile.aoe_data.database.stats.old.DatapackStats;
import com.robertx22.age_of_exile.capability.entity.EntityData;
import com.robertx22.age_of_exile.database.OptScaleExactStat;
import com.robertx22.age_of_exile.database.data.stats.StatScaling;
import com.robertx22.age_of_exile.database.data.stats.datapacks.base.BaseDatapackStat;
import com.robertx22.age_of_exile.database.data.stats.datapacks.base.CoreStatData;
import com.robertx22.age_of_exile.database.data.stats.name_regex.StatNameRegex;
import com.robertx22.age_of_exile.database.data.stats.types.core_stats.base.ICoreStat;
import com.robertx22.age_of_exile.saveclasses.ExactStatData;
import com.robertx22.age_of_exile.saveclasses.unit.InCalcStatContainer;
import com.robertx22.age_of_exile.saveclasses.unit.InCalcStatData;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class BonusStatPerEffectStacks extends BaseDatapackStat implements ICoreStat {

    public static String SER_ID = "bonus_stat_per_effect";

    public CoreStatData data = new CoreStatData();

    String locname;

    public String effect;


    public static BonusStatPerEffectStacks of(EffectCtx effect, String id, String name, OptScaleExactStat... stats) {
        return new BonusStatPerEffectStacks(effect.GUID(), id, name, StatScaling.NONE, CoreStatData.of(Arrays.asList(stats)));
    }

    public BonusStatPerEffectStacks(String effect, String id, String name, StatScaling scal, CoreStatData data) {
        super(SER_ID);

        this.effect = effect;
        this.id = id;

        this.data = data;

        this.is_perc = false;
        this.min = 0;
        this.group = StatGroup.CORE;
        this.scaling = scal;
        this.is_long = false;

        this.locname = name;


        DatapackStats.tryAdd(this);
    }

    public BonusStatPerEffectStacks() {
        super(SER_ID);
    }

    @Override
    public final String locDescForLangFile() {
        return "Adds stats depending on how many effect stacks you have currently.";
    }


    public List<ExactStatData> getMods(EntityData data, int amount) {

        return this.data.stats.stream()
                .map(x -> {
                    float val = data.statusEffects.getStacks(effect);
                    val *= amount;
                    val *= x.v1;
                    ExactStatData exact = ExactStatData.levelScaled(val, x.getStat(), x.getModType(), x.scale_to_lvl ? data.getLevel() : 1);
                    return exact;
                })
                .collect(Collectors.toList());

    }

    @Override
    public final List<OptScaleExactStat> statsThatBenefit() {
        return data.stats;
    }

    @Override
    public void addToOtherStats(EntityData endata, InCalcStatContainer unitdata, InCalcStatData data) {
        for (ExactStatData x : getMods(endata, (int) data.getValue())) {
            x.applyToStatInCalc(unitdata);
        }
    }

    @Override
    public StatNameRegex getStatNameRegex() {
        return StatNameRegex.BASIC;
    }

    @Override
    public boolean IsPercent() {
        return this.is_perc;
    }

    @Override
    public String locNameForLangFile() {
        return locname;
    }

}