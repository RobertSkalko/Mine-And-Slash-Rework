package com.robertx22.age_of_exile.saveclasses.item_classes.rework;

import java.util.HashMap;

public class GenericDataHolder {

    private HashMap<String, String> map = new HashMap<>();


    public <T> void set(DataKey<T> key, T obj) {
        String saved = key.objectToString(obj);
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
