package com.robertx22.age_of_exile.capability.entity;

import com.robertx22.age_of_exile.database.data.stats.types.resources.LeechCapStat;
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

        float leechMaxPerSec = data.getUnit().getCalculatedStat(LeechCapStat.getInstance()).getValueOrBase(LeechCapStat.getInstance()) / 100F;

        for (Map.Entry<ResourceType, Float> entry : map.entrySet()) {

            float num = entry.getValue();

            float max = leechMaxPerSec * data.getResources().getMax(data.entity, entry.getKey());

            if (num > max) {
                num = max;
            }

            addLeech(entry.getKey(), -num);
            data.getResources().restore(data.entity, entry.getKey(), num);
        }


    }
}
