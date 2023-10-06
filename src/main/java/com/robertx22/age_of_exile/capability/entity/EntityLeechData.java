package com.robertx22.age_of_exile.capability.entity;

import com.robertx22.age_of_exile.aoe_data.database.stats.Stats;
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

    // todo implement expiration after 5s
    public void onSecondUseLeeches(EntityData data) {


        for (Map.Entry<ResourceType, Float> entry : map.entrySet()) {
            float leechMaxPerSec = data.getUnit().getCalculatedStat(Stats.LEECH_CAP.get(entry.getKey())).getValue() / 100F;

            float num = entry.getValue();

            float maxres = data.getResources().getMax(data.entity, entry.getKey());

            float max = leechMaxPerSec * maxres;

            if (num > max) {
                num = max;
            }

            addLeech(entry.getKey(), -num);
            data.getResources().restore(data.entity, entry.getKey(), num);
        }


    }
}
