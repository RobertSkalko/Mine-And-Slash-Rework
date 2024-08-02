package com.robertx22.age_of_exile.database.data.stats.datapacks.serializers;

import com.google.gson.JsonObject;
import com.robertx22.age_of_exile.database.data.stats.datapacks.base.IStatSerializer;
import com.robertx22.age_of_exile.database.data.stats.datapacks.stats.AttributeStat;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;

import java.util.UUID;

public class AttributeStatSer implements IStatSerializer<AttributeStat> {

    @Override
    public JsonObject statToJson(AttributeStat obj) {
        JsonObject json = new JsonObject();
        json.addProperty("attribute_id", obj.attributeId);
        json.addProperty("uuid", obj.uuid.toString());
        json.addProperty("operation", obj.operation.name());

        
        this.saveBaseStatValues(obj, json);
        return json;
    }

    @Override
    public AttributeStat getStatFromJson(JsonObject json) {

        ResourceLocation ide = new ResourceLocation(json.get("attribute_id")
                .getAsString());

        Attribute attri = BuiltInRegistries.ATTRIBUTE.get(ide);

        var oper = AttributeModifier.Operation.valueOf(json.get("operation").getAsString());

        AttributeStat stat = new AttributeStat("", "", UUID.fromString(json.get("uuid")
                .getAsString()), attri, false,
                oper); // percent and id is loaded by basevalues

        this.loadBaseStatValues(stat, json);
        return stat;
    }
}