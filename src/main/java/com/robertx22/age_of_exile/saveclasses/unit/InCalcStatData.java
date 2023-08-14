package com.robertx22.age_of_exile.saveclasses.unit;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.saveclasses.ExactStatData;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;
import net.minecraft.util.Mth;


public class InCalcStatData {

    // guid
    public String id = "";

    private float Flat = 0;
    private float Percent = 0;
    private float Multi = 1;
    //private boolean calc = false;


    private InCalcStatData() {

    }

    public InCalcStatData(String id) {
        this.id = id;
    }

    public void clear() {
        this.Flat = 0;
        this.Percent = 0;
        this.Multi = 0;
    }

    private float calcValue() {
        Stat stat = this.GetStat();

        float finalValue = stat.base;

        finalValue += Flat;

        finalValue *= 1 + Percent / 100;

        if (stat.getMultiUseType() == Stat.MultiUseType.MULTIPLY_STAT) {
            finalValue *= Multi;
        }

        return Mth.clamp(finalValue, stat.min, stat.max);

    }

    public Stat GetStat() {
        return ExileDB.Stats()
                .get(id);
    }

    public void addAlreadyScaledFlat(float val1) {
        this.Flat += val1;
    }

    public void addFullyTo(InCalcStatData other) {
        other.Flat += Flat;
        other.Percent += Percent;
        other.Multi += Multi; // todo might be buggy

    }

 
    public void add(ExactStatData modData) {
        ModType type = modData.getType();


        float v1 = modData.getFirstValue();

        if (type == ModType.FLAT) {
            Flat += v1;
        } else if (type == ModType.PERCENT) {
            Percent += v1;
        } else if (type == ModType.MORE) {
            Multi *= 1 + (v1 / 100F);
        }

    }

    public float getValue() {
        return Flat;
    }

    public StatData getCalculated() {
        float mu = 1;
        if (GetStat().getMultiUseType() == Stat.MultiUseType.MULTIPLICATIVE_DAMAGE) {
            mu = Multi;
        }
        return new StatData(this.id, calcValue(), mu);
    }
}
