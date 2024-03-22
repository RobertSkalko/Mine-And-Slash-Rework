package com.robertx22.age_of_exile.database.data.prophecy;

import com.robertx22.age_of_exile.database.registry.ExileRegistryTypes;
import com.robertx22.library_of_exile.registry.ExileRegistryType;
import com.robertx22.library_of_exile.registry.IAutoGson;
import com.robertx22.library_of_exile.registry.JsonExileRegistry;


// 1 will be say "mythic item/gear", the other will be "boots item"
public class ProphecyModifier implements JsonExileRegistry<ProphecyModifier>, IAutoGson<ProphecyModifier> {
    public static ProphecyModifier SERIALIZER = new ProphecyModifier();

    public float cost_multi = 1;
    public String data = "";
    public ProphecyModifierType modifier_type = ProphecyModifierType.GEAR_TYPE;
    public String id = "";
    public int weight = 1000;
    
    public int tier_req = 0;
    public int lvl_req = 0;


    @Override
    public ExileRegistryType getExileRegistryType() {
        return ExileRegistryTypes.PROPHECY_MODIFIER;
    }

    @Override
    public String GUID() {
        return id;
    }

    @Override
    public int Weight() {
        return weight;
    }

    @Override
    public Class<ProphecyModifier> getClassForSerialization() {
        return ProphecyModifier.class;
    }
}
