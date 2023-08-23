package com.robertx22.age_of_exile.uncommon.effectdatas.rework.condition;

import com.google.gson.JsonObject;
import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.registry.ExileRegistryTypes;
import com.robertx22.age_of_exile.saveclasses.unit.StatData;
import com.robertx22.age_of_exile.uncommon.effectdatas.EffectEvent;
import com.robertx22.age_of_exile.uncommon.interfaces.EffectSides;
import com.robertx22.library_of_exile.registry.Database;
import com.robertx22.library_of_exile.registry.ExileRegistryType;
import com.robertx22.library_of_exile.registry.IAutoGson;
import com.robertx22.library_of_exile.registry.JsonExileRegistry;

import java.util.HashMap;

public abstract class StatCondition implements JsonExileRegistry<StatCondition>, IAutoGson<StatCondition> {

    public static StatCondition SERIALIZER = new RandomRollCondition();
    public static HashMap<String, StatCondition> SERIALIZERS = new HashMap<>();


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
        String ser = json.get("ser")
                .getAsString();

        if (!SERIALIZERS.containsKey(ser)) {
            System.out.println(this.id + " has no serializer!");
        }

        StatCondition t = null;

        t = GSON.fromJson(json, SERIALIZERS.get(ser).getSerClass());
        t.onLoadedFromJson();


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
