package com.robertx22.age_of_exile.database.data.stats.datapacks.stats;

import com.robertx22.age_of_exile.aoe_data.database.stats.old.DatapackStats;
import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.StatScaling;
import com.robertx22.age_of_exile.database.data.stats.datapacks.base.BaseDatapackStat;
import com.robertx22.age_of_exile.database.data.stats.name_regex.StatNameRegex;
import com.robertx22.age_of_exile.saveclasses.unit.StatContainer;
import com.robertx22.age_of_exile.saveclasses.unit.StatData;
import com.robertx22.age_of_exile.uncommon.interfaces.AddToAfterCalcEnd;
import net.minecraft.ChatFormatting;

public class AddPerPercentOfOther extends BaseDatapackStat implements AddToAfterCalcEnd {

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


        DatapackStats.tryAdd(this);
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
    public void affectStats(StatContainer copy, StatContainer stats, StatData statData) {
        StatData add_to = stats.getOrCreateCalculatedStat(stat_to_add_to);
        StatData adder = copy.getOrCreateCalculatedStat(adder_stat);
        StatData thisstat = copy.getCalculatedStat(this.GUID());

        float multi = thisstat.getValue() / 100F;
        float val = adder.getValue() * multi;

        add_to.setValue(add_to.getValue() + val);
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
