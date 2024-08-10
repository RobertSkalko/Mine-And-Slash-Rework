package com.robertx22.mine_and_slash.capability.entity;

import com.robertx22.mine_and_slash.aoe_data.database.stats.ResourceStats;
import com.robertx22.mine_and_slash.saveclasses.unit.ResourceType;
import com.robertx22.mine_and_slash.uncommon.MathHelper;

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


        // don't allow to accumulate more than x depending on total resource
        // currently lets try with capping it to 5 seconds of regen.
        for (Map.Entry<ResourceType, Float> en : map.entrySet()) {
            float leechMaxPerSec = 5F * data.getUnit().getCalculatedStat(ResourceStats.LEECH_CAP.get(en.getKey())).getValue() / 100F;
            float max = data.getMaximumResource(en.getKey()) * leechMaxPerSec;
            float fi = MathHelper.clamp(en.getValue(), 0, max);
            map.put(en.getKey(), fi);
        }

        for (Map.Entry<ResourceType, Float> entry : map.entrySet()) {
            float leechMaxPerSec = data.getUnit().getCalculatedStat(ResourceStats.LEECH_CAP.get(entry.getKey())).getValue() / 100F;

            float num = entry.getValue();

            if (num > 1) {
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
}
