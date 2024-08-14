package com.robertx22.mine_and_slash.database.data.map_affix;

import com.robertx22.library_of_exile.registry.ExileRegistryType;
import com.robertx22.library_of_exile.registry.IAutoGson;
import com.robertx22.library_of_exile.registry.JsonExileRegistry;
import com.robertx22.mine_and_slash.database.data.StatMod;
import com.robertx22.mine_and_slash.database.data.league.ProphecyLeague;
import com.robertx22.mine_and_slash.database.data.mob_affixes.MobAffix;
import com.robertx22.mine_and_slash.database.registry.ExileRegistryTypes;
import com.robertx22.mine_and_slash.maps.AffectedEntities;
import com.robertx22.mine_and_slash.saveclasses.ExactStatData;
import com.robertx22.mine_and_slash.uncommon.enumclasses.Elements;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MapAffix implements JsonExileRegistry<MobAffix>, IAutoGson<MapAffix> {

    public static MapAffix SER = new MapAffix("");

    List<StatMod> stats = new ArrayList<>();
    String id = "";
    int weight = 1000;
    public AffectedEntities affected = AffectedEntities.Mobs;
    public Elements map_resist = Elements.Physical;
    public int map_resist_bonus_needed = 0;

    public String prophecy_type = "";

    public String req = "";

    public MapAffix(String id) {
        this.id = id;
    }

    public MapAffix setProphecyLeague() {
        this.req = new ProphecyLeague().GUID();
        return this;
    }

    public MapAffix addMod(StatMod mod) {
        this.stats.add(mod);
        return this;
    }

    public MapAffix affectsPlayer() {
        this.affected = AffectedEntities.Players;
        return this;
    }

    public MapAffix upsMapResistRequirement(Elements ele, int number) {
        this.map_resist = ele;
        this.map_resist_bonus_needed = number;
        return this;
    }

    public MapAffix setWeight(int weight) {
        this.weight = weight;
        return this;
    }

    // todo
    public float getLootMulti() {
        return 1;
    }

    @Override
    public ExileRegistryType getExileRegistryType() {
        return ExileRegistryTypes.MAP_AFFIX;
    }

    @Override
    public String GUID() {
        return id;
    }

    @Override
    public int Weight() {
        return weight;
    }


    public List<ExactStatData> getStats(int perc, int lvl) {
        return stats.stream().map(x -> x.ToExactStat(perc, lvl)).collect(Collectors.toList());
    }

    @Override
    public Class<MapAffix> getClassForSerialization() {
        return MapAffix.class;
    }
}
