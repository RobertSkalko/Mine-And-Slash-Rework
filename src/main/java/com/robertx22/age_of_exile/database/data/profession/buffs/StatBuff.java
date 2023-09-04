package com.robertx22.age_of_exile.database.data.profession.buffs;

import com.robertx22.age_of_exile.database.data.StatMod;
import com.robertx22.age_of_exile.database.registry.ExileRegistryTypes;
import com.robertx22.age_of_exile.saveclasses.ExactStatData;
import com.robertx22.library_of_exile.registry.ExileRegistryType;
import com.robertx22.library_of_exile.registry.IAutoGson;
import com.robertx22.library_of_exile.registry.JsonExileRegistry;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class StatBuff implements IAutoGson<StatBuff>, JsonExileRegistry<StatBuff> {
    public static StatBuff SERIALIZER = new StatBuff();


    public List<StatMod> mods = new ArrayList<>();

    public String id = "";


    public boolean isSameStat(StatBuff other) {
        return mods.get(0).GetStat().GUID().equals(other.mods.get(0).GetStat().GUID());
    }

    public List<ExactStatData> getStats(int lvl, int perc) {
        return mods.stream().map(x -> x.ToExactStat((int) (perc + lvl), lvl)).collect(Collectors.toList());
    }

    @Override
    public ExileRegistryType getExileRegistryType() {
        return ExileRegistryTypes.STAT_BUFF;
    }

    @Override
    public Class<StatBuff> getClassForSerialization() {
        return StatBuff.class;
    }

    @Override
    public String GUID() {
        return id;
    }

    @Override
    public int Weight() {
        return 1000;
    }
}
