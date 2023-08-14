package com.robertx22.age_of_exile.database.data.stats.effects.defense;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.StatScaling;
import com.robertx22.age_of_exile.database.data.stats.types.ElementalStat;
import com.robertx22.age_of_exile.mmorpg.SlashRef;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;

import java.util.List;

public class MaxElementalResist extends ElementalStat {


    @Override
    public List<Stat> generateAllPossibleStatVariations() {
        List<Stat> list = super.generateAllPossibleStatVariations();
        return list;
    }

    public MaxElementalResist(Elements element) {
        super(element);
        this.min = -300;

        this.group = StatGroup.ELEMENTAL;
        this.is_perc = true;
        this.scaling = StatScaling.NONE;

        this.format = element.format.getName();
        this.icon = element.icon;

        this.max = 15;
    }

    @Override
    public Stat newGeneratedInstance(Elements element) {
        return new MaxElementalResist(element);
    }

    @Override
    public String GUID() {
        return "max_" + this.getElement().guidName + "_resist";
    }

    @Override
    public String locDescForLangFile() {
        return "Adds element resist above the 75% cap (max 90%)";
    }

    @Override
    public String locDescLangFileGUID() {
        return SlashRef.MODID + ".stat_desc." + "max_ele_resist";
    }

    @Override
    public String locNameForLangFile() {
        return "Max " + this.getElement().dmgName + " Resistance";
    }


}

