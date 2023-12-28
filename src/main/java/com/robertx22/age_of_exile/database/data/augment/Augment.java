package com.robertx22.age_of_exile.database.data.augment;

import com.robertx22.age_of_exile.database.data.aura.AuraGem;
import com.robertx22.age_of_exile.uncommon.enumclasses.PlayStyle;
import com.robertx22.library_of_exile.registry.ExileRegistryType;
import com.robertx22.library_of_exile.registry.IAutoGson;
import com.robertx22.library_of_exile.registry.JsonExileRegistry;

import java.util.Arrays;

// todo might not be needed
public class Augment implements JsonExileRegistry<Augment>, IAutoGson<Augment> {

    public static AuraGem SERIALIZER = new AuraGem("", "", PlayStyle.STR, 0, Arrays.asList());

    public String id = "";
    public int weight = 1000;

    @Override
    public ExileRegistryType getExileRegistryType() {
        return null;//  return ExileRegistryTypes.AUGMENT;
    }

    @Override
    public Class<Augment> getClassForSerialization() {
        return Augment.class;
    }

    @Override
    public String GUID() {
        return id;
    }

    @Override
    public int Weight() {
        return weight;
    }
}
