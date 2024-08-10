package com.robertx22.mine_and_slash.uncommon.effectdatas.rework.action;

import com.google.gson.JsonObject;
import com.robertx22.mine_and_slash.database.data.stats.Stat;
import com.robertx22.mine_and_slash.database.registry.ExileRegistryTypes;
import com.robertx22.mine_and_slash.saveclasses.unit.StatData;
import com.robertx22.mine_and_slash.uncommon.effectdatas.EffectEvent;
import com.robertx22.mine_and_slash.uncommon.interfaces.EffectSides;
import com.robertx22.library_of_exile.registry.ExileRegistryType;
import com.robertx22.library_of_exile.registry.IAutoGson;
import com.robertx22.library_of_exile.registry.JsonExileRegistry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class StatEffect implements JsonExileRegistry<StatEffect>, IAutoGson<StatEffect> {

    public static HashMap<String, StatEffect> SERIALIZERS = new HashMap<>();
    public static List<StatEffect> ALL_SERIAZABLE = new ArrayList<>();
    public static StatEffect SERIALIZER = new SetBooleanEffect();


    public static void init() {

    }

    static void addSer(StatEffect eff) {
        SERIALIZERS.put(eff.ser, eff);

    }

    public String id = "";
    public String ser = "";

    public StatEffect(String id, String ser) {
        this.id = id;
        this.ser = ser;

        addSer(this);
        ALL_SERIAZABLE.add(this);
    }

    public abstract void activate(EffectEvent event, EffectSides statSource, StatData data, Stat stat);

    @Override
    public ExileRegistryType getExileRegistryType() {
        return ExileRegistryTypes.STAT_EFFECT;
    }

    @Override
    public int Weight() {
        return 1000;
    }

    @Override
    public String GUID() {
        return id;
    }

    @Override
    public final StatEffect fromJson(JsonObject json) {
        String ser = json.get("ser")
                .getAsString();

        StatEffect t = GSON.fromJson(json, SERIALIZERS.get(ser)
                .getSerClass());
        t.onLoadedFromJson();
        return t;
    }

    @Override
    public Class<StatEffect> getClassForSerialization() {
        return null;
    }

    public abstract Class<? extends StatEffect> getSerClass();
}

