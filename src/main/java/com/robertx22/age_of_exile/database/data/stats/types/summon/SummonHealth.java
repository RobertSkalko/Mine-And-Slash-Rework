package com.robertx22.age_of_exile.database.data.stats.types.summon;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.StatScaling;
import com.robertx22.age_of_exile.database.data.stats.types.SummonStat;
import com.robertx22.age_of_exile.database.data.stats.types.resources.health.Health;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;

public class SummonHealth extends SummonStat {
    public SummonHealth() {
        this.is_perc = true;
        this.scaling = StatScaling.NONE;
    }

    @Override
    public Elements getElement() {
        return Elements.Physical;
    }

    @Override
    public Stat getStatToGiveToSummon() {
        return Health.getInstance();
    }

    @Override
    public ModType getModType() {
        return ModType.MORE;
    }

    @Override
    public String locDescForLangFile() {
        return "Gives more hp to summons.";
    }

    @Override
    public String locNameForLangFile() {
        return "Summon Health";
    }

    @Override
    public String GUID() {
        return "summon_health";
    }
}
