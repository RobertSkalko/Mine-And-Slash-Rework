package com.robertx22.age_of_exile.database.data.stats.datapacks.stats;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.StatScaling;
import com.robertx22.age_of_exile.database.data.stats.datapacks.base.BaseDatapackStat;
import com.robertx22.age_of_exile.database.data.stats.name_regex.StatNameRegex;
import com.robertx22.age_of_exile.saveclasses.unit.InCalcStatData;
import com.robertx22.age_of_exile.saveclasses.unit.Unit;
import com.robertx22.age_of_exile.uncommon.interfaces.IAffectsStatsInCalc;
import net.minecraft.ChatFormatting;

public class AddPerPercentOfOther extends BaseDatapackStat implements IAffectsStatsInCalc {

    public static String SER_ID = "one_to_other";

    public String adder_stat;
    public String stat_to_add_to;

    transient String locname;

    public AddPerPercentOfOther(Stat adder_stat, Stat stat_to_add_to) {
        super(SER_ID);
        this.id = stat_to_add_to.GUID() + "_per_perc_of_" + adder_stat.GUID();
        this.adder_stat = adder_stat.GUID();
        this.stat_to_add_to = stat_to_add_to.GUID();

        this.is_perc = true;
        this.min = 0;
        this.scaling = StatScaling.NONE;
        this.is_long = true;

        this.locname = ChatFormatting.GRAY + "Gain " + ChatFormatting.GREEN +
                Stat.VAL1 + "%" + ChatFormatting.GRAY + " of your "
                + adder_stat.getIconNameFormat()
                + ChatFormatting.GRAY + " as extra "
                + stat_to_add_to.getIconNameFormat();
    }

    public AddPerPercentOfOther(String adder_stat, String stat_to_add_to) {
        super(SER_ID);
        this.stat_to_add_to = stat_to_add_to;
        this.adder_stat = adder_stat;

        this.is_long = true;
        this.is_perc = true;
        this.min = 0;
        this.scaling = StatScaling.NONE;
    }

    @Override
    public void affectStats(Unit data, InCalcStatData statData) {
        InCalcStatData add_to = data.getStatInCalculation(stat_to_add_to);
        InCalcStatData adder = data.getStatInCalculation(adder_stat);
        InCalcStatData thisstat = data.getStatInCalculation(this.GUID());

        float multi = thisstat.getValue() / 100F;
        float val = adder.getValue() * multi;

        add_to.addAlreadyScaledFlat(val);
    }

    @Override
    public StatNameRegex getStatNameRegex() {
        return StatNameRegex.BASIC;
    }

    @Override
    public boolean IsPercent() {
        return true;
    }

    @Override
    public String locDescForLangFile() {
        return "";
    }

    @Override
    public String locNameForLangFile() {
        return locname;
    }

}
