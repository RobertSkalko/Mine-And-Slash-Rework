package com.robertx22.age_of_exile.saveclasses.item_classes.rework;

import java.util.HashMap;
import java.util.function.Function;

public class GenericDataHolder {

    private HashMap<String, String> map = new HashMap<>();


    public <T> void set(DataKey<T> key, T obj) {
        String saved = key.objectToString(obj);
        map.put(key.key, saved);
    }

    public <T> void set(DataKey<T> key, Function<T, T> obj) {
        String saved = key.objectToString(obj.apply((T) map.get(key)));
        map.put(key.key, saved);
    }

    public <T> T get(DataKey<T> key) {
        T o = key.get(map);
        return o;
    }

    public <T> T getOrDefault(DataKey<T> key, T def) {
        T o = key.get(map);
        if (o == null) {
            return def;
        }
        return o;
    }
}
