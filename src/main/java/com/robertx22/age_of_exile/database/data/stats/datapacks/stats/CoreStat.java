package com.robertx22.age_of_exile.database.data.stats.datapacks.stats;

import com.robertx22.age_of_exile.aoe_data.database.stats.old.DatapackStats;
import com.robertx22.age_of_exile.capability.entity.EntityData;
import com.robertx22.age_of_exile.database.OptScaleExactStat;
import com.robertx22.age_of_exile.database.data.stats.StatScaling;
import com.robertx22.age_of_exile.database.data.stats.datapacks.base.BaseDatapackStat;
import com.robertx22.age_of_exile.database.data.stats.datapacks.base.CoreStatData;
import com.robertx22.age_of_exile.database.data.stats.name_regex.StatNameRegex;
import com.robertx22.age_of_exile.database.data.stats.types.core_stats.base.ICoreStat;
import com.robertx22.age_of_exile.saveclasses.ExactStatData;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.TooltipInfo;
import com.robertx22.age_of_exile.saveclasses.unit.InCalcStatContainer;
import com.robertx22.age_of_exile.saveclasses.unit.InCalcStatData;
import com.robertx22.age_of_exile.saveclasses.unit.StatData;
import com.robertx22.age_of_exile.uncommon.localization.Gui;
import com.robertx22.age_of_exile.uncommon.utilityclasses.TooltipStatsAligner;
import com.robertx22.library_of_exile.wrappers.ExileText;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CoreStat extends BaseDatapackStat implements ICoreStat {

    public static String SER_ID = "core_stat";

    public CoreStatData data = new CoreStatData();

    String locname;

    public CoreStat(String id, String name, CoreStatData data) {
        super(SER_ID);

        this.id = id;

        this.data = data;

        this.is_perc = false;
        this.min = 0;
        this.group = StatGroup.CORE;
        this.scaling = StatScaling.CORE;
        this.is_long = false;

        this.locname = name;

        DatapackStats.tryAdd(this);
    }

    @Override
    public final String locDescForLangFile() {
        // because i tend to change things and then the damn tooltip becomes outdated.
        String str = "Determines your total: ";
        for (OptScaleExactStat x : this.statsThatBenefit()) {
            str += x.getStat()
                    .locNameForLangFile() + ", ";
        }
        str = str.substring(0, str.length() - 2);

        return str;
    }

    public float getValue(StatData data) {
        return data.getValue();
    }

    public List<Component> getCoreStatTooltip(EntityData unitdata, StatData data) {

        TooltipInfo info = new TooltipInfo(unitdata, null);

        int val = (int) getValue(data);

        List<Component> list = new ArrayList<>();

        list.add(Gui.STATS_INFLUENCE.locName().withStyle(ChatFormatting.GREEN));
        List<Component> preList = new ArrayList<>();
        getMods(1).forEach(x -> preList.addAll(x.GetTooltipString(info)));
        List<Component> finalList = new TooltipStatsAligner(preList, false).buildNewTooltipsStats();
        list.addAll(finalList);

        list.add(ExileText.ofText("").get());

        list.add(
                Gui.STAT_TOTAL.locName().withStyle(ChatFormatting.GREEN));

        List<Component> prelist = new ArrayList<>();
        getMods(val).forEach(x -> prelist.addAll(x.GetTooltipString(info)));
        List<Component> finallist = new TooltipStatsAligner(prelist, false).buildNewTooltipsStats();
        list.addAll(finallist);


        return list;

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
    public void addToOtherStats(EntityData endata, InCalcStatContainer unitdata, InCalcStatData data) {
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