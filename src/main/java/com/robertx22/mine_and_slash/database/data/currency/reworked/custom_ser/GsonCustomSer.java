package com.robertx22.mine_and_slash.database.data.currency.reworked.custom_ser;

import com.google.gson.JsonObject;
import com.robertx22.library_of_exile.registry.IAutoGson;
import com.robertx22.library_of_exile.registry.serialization.ISerializable;

public interface GsonCustomSer<T> extends ICustomSer<T>, ISerializable<T> {

    Class<?> getClassForSerialization();

    @Override
    default T fromJson(JsonObject json) {
        GsonCustomSer<T> ser = (GsonCustomSer<T>) getSerMap().map.get(json.get("serializer").getAsString());
        var t = ser.fromJsonNormal(json, ser.getClassForSerialization());
        return t;
    }

    default T fromJsonNormal(JsonObject json, Class<?> c) {
        T t = (T) IAutoGson.GSON.fromJson(json, c);
        return t;
    }

    @Override
    default JsonObject toJson() {
        return IAutoGson.PARSER.parse(IAutoGson.GSON.toJson(this)).getAsJsonObject();
    }
}
