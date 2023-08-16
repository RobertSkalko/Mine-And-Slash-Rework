package com.robertx22.age_of_exile.capability.entity;

import com.robertx22.age_of_exile.saveclasses.unit.ResourceType;

import java.util.HashMap;
import java.util.Map;

public class EntityLeechData {


    private HashMap<ResourceType, Float> map = new HashMap<>();

    public void addLeech(ResourceType type, float num) {
        if (!map.containsKey(type)) {
            map.put(type, 0f);
        }
        float fi = num + map.get(type);

        map.put(type, fi);
    }

    static float MAX_LEECH_PERCENT_PER_SECOND = 0.1F; // todo maybe a stat

    public void onSecondUseLeeches(EntityData data) {

        for (Map.Entry<ResourceType, Float> entry : map.entrySet()) {

            float num = entry.getValue();

            float max = MAX_LEECH_PERCENT_PER_SECOND * data.getResources().get(data.entity, entry.getKey());

            if (num > max) {
                num = max;
            }

            addLeech(entry.getKey(), -num);
            data.getResources().restore(data.entity, entry.getKey(), num);
        }


    }
}
