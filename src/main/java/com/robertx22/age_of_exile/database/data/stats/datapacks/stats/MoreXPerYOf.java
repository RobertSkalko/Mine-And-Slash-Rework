package com.robertx22.age_of_exile.database.data.stats.datapacks.stats;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.StatScaling;
import com.robertx22.age_of_exile.database.data.stats.datapacks.base.BaseDatapackStat;
import com.robertx22.age_of_exile.database.data.stats.name_regex.StatNameRegex;
import com.robertx22.age_of_exile.saveclasses.unit.StatData;
import com.robertx22.age_of_exile.saveclasses.unit.Unit;
import com.robertx22.age_of_exile.uncommon.interfaces.AddToAfterCalcEnd;
import net.minecraft.ChatFormatting;

public class MoreXPerYOf extends BaseDatapackStat implements AddToAfterCalcEnd {

    public static String SER_ID = "more_x_per_y";

    public String adder_stat;
    public String stat_to_add_to;
    public int perEach = 10;

    transient String locname;

    public MoreXPerYOf(Stat adder_stat, Stat stat_to_add_to, int per) {
        super(SER_ID);
        this.perEach = per;

        this.id = stat_to_add_to.GUID() + "_per_" + perEach + "_" + adder_stat.GUID();

        this.adder_stat = adder_stat.GUID();
        this.stat_to_add_to = stat_to_add_to.GUID();

        this.is_perc = true;
        this.min = 0;
        this.scaling = StatScaling.NONE;
        this.is_long = true;

        this.locname = ChatFormatting.GREEN + Stat.VAL1 + " " + stat_to_add_to.getIconNameFormat() + " per " + perEach + " " + adder_stat.getIconNameFormat();
    }

    public MoreXPerYOf(String adder_stat, String stat_to_add_to) {
        super(SER_ID);
        this.stat_to_add_to = stat_to_add_to;
        this.adder_stat = adder_stat;

        this.is_long = true;
        this.is_perc = true;
        this.min = 0;
        this.scaling = StatScaling.NONE;
    }

    // todo this doesnt cap stat values to min max etc
    @Override
    public void affectStats(Unit data, StatData statData) {
        StatData add_to = data.getOrCreateCalculatedStat(stat_to_add_to);
        StatData adder = data.getOrCreateCalculatedStat(adder_stat);

        float val = (int) (adder.getValue() / perEach) * statData.getValue();

        add_to.setValue(add_to.getValue() + val);

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
    public String locDescForLangFile() {
        return "";
    }

    @Override
    public String locNameForLangFile() {
        return locname;
    }

}