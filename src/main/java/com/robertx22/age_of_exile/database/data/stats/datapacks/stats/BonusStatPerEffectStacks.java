package com.robertx22.age_of_exile.database.data.stats.datapacks.stats;

import com.robertx22.age_of_exile.database.OptScaleExactStat;
import com.robertx22.age_of_exile.database.data.stats.StatScaling;
import com.robertx22.age_of_exile.database.data.stats.datapacks.base.BaseDatapackStat;
import com.robertx22.age_of_exile.database.data.stats.datapacks.base.CoreStatData;
import com.robertx22.age_of_exile.database.data.stats.name_regex.StatNameRegex;
import com.robertx22.age_of_exile.database.data.stats.types.core_stats.base.ICoreStat;
import com.robertx22.age_of_exile.saveclasses.ExactStatData;
import com.robertx22.age_of_exile.saveclasses.unit.InCalcStatContainer;
import com.robertx22.age_of_exile.saveclasses.unit.InCalcStatData;

import java.util.List;
import java.util.stream.Collectors;

public class BonusStatPerEffectStacks extends BaseDatapackStat implements ICoreStat {

    public static String SER_ID = "bonus_stat_per_effect";

    public CoreStatData data = new CoreStatData();

    String locname;

    public BonusStatPerEffectStacks(String id, String name, StatScaling scal, CoreStatData data) {
        super(SER_ID);

        this.id = id;

        this.data = data;

        this.is_perc = false;
        this.min = 0;
        this.group = StatGroup.CORE;
        this.scaling = scal;
        this.is_long = false;

        this.locname = name;
    }

    @Override
    public final String locDescForLangFile() {
        return "Adds stats depending on how many effect stacks you have currently.";
    }


    public List<ExactStatData> getMods(int amount) {

        return data.stats.stream()
                .map(x -> {
                    ExactStatData exact = ExactStatData.levelScaled(amount * x.v1, x.getStat(), x.getModType(), 1);
                    return exact;
                })
                .collect(Collectors.toList());

    }

    @Override
    public final List<OptScaleExactStat> statsThatBenefit() {
        return data.stats;
    }

    @Override
    public void addToOtherStats(InCalcStatContainer unitdata, InCalcStatData data) {
        for (ExactStatData x : getMods((int) data.getValue())) {
            x.applyToStatInCalc(unitdata);
        }
    }

    @Override
    public StatNameRegex getStatNameRegex() {
        return StatNameRegex.BASIC;
    }

    @Override
    public boolean IsPercent() {
        return false;
    }

    @Override
    public String locNameForLangFile() {
        return locname;
    }

}