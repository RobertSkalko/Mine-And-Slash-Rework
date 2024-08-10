package com.robertx22.mine_and_slash.uncommon.effectdatas.rework.condition;

import com.google.gson.JsonObject;
import com.robertx22.library_of_exile.main.ExileLog;
import com.robertx22.library_of_exile.registry.Database;
import com.robertx22.library_of_exile.registry.ExileRegistryType;
import com.robertx22.library_of_exile.registry.IAutoGson;
import com.robertx22.library_of_exile.registry.JsonExileRegistry;
import com.robertx22.mine_and_slash.database.data.stats.Stat;
import com.robertx22.mine_and_slash.database.registry.ExileRegistryTypes;
import com.robertx22.mine_and_slash.saveclasses.unit.StatData;
import com.robertx22.mine_and_slash.uncommon.effectdatas.EffectEvent;
import com.robertx22.mine_and_slash.uncommon.interfaces.EffectSides;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class StatCondition implements JsonExileRegistry<StatCondition>, IAutoGson<StatCondition> {

    public static List<StatCondition> ALL = new ArrayList<>();

    public static StatCondition SERIALIZER = new RandomRollCondition();
    public static HashMap<String, StatCondition> SERIALIZERS = new HashMap<>();


    public static void loadclass() {

    }


    public static void addSer(StatCondition eff) {
        SERIALIZERS.put(eff.ser, eff);
    }

    public String id = "";
    public String ser = "";
    public Boolean is = null;

    public boolean getConditionBoolean() {
        return is == null ? true : is;
    }

    public StatCondition(String id, String ser) {
        this.ser = ser;
        this.id = id;

        ALL.add(this);
    }

    public StatCondition flipCondition() {
        this.is = false;
        this.id += "_is_false";
        return this;
    }

    @Override
    public void addToSerializables() {
        Database.getRegistry(this.getExileRegistryType()).addSerializable(this);
        addSer(this);
    }

    public abstract boolean can(EffectEvent event, EffectSides statSource, StatData data, Stat stat);

    @Override
    public final StatCondition fromJson(JsonObject json) {
        String ser = json.get("ser").getAsString();

        if (!SERIALIZERS.containsKey(ser)) {
            ExileLog.get().warn(this.id + " has no serializer!");
        }

        StatCondition t = null;
        try {
            t = null;

            t = GSON.fromJson(json, SERIALIZERS.get(ser).getSerClass());
            t.onLoadedFromJson();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


        return t;
    }

    @Override
    public int Weight() {
        return 1000;
    }

    @Override
    public ExileRegistryType getExileRegistryType() {
        return ExileRegistryTypes.STAT_CONDITION;
    }

    @Override
    public String GUID() {
        return id;
    }

    @Override
    public Class getClassForSerialization() {
        return null;
    }

    public abstract Class<? extends StatCondition> getSerClass();
}
