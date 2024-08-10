package com.robertx22.mine_and_slash.uncommon.threat_aggro;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;

import java.util.Comparator;
import java.util.HashMap;

public class ThreatData {

    public HashMap<String, Integer> map = new HashMap<>();

    public void addThreat(LivingEntity threatCreatorEntity, Mob mob, int threat) {

        String key = threatCreatorEntity.getUUID().toString();
        int cur = map.getOrDefault(key, 0);
        map.put(key, cur + threat);

        String highest = getHighest();
        if (!highest.isEmpty()) {
            if (key.equals(highest)) {
                if (mob.getTarget() != threatCreatorEntity) {
                    mob.setTarget(threatCreatorEntity);
                }
            }
        }

    }

    public String getHighest() {

        if (map.isEmpty()) {
            return "";
        }

        return map.entrySet()
                .stream()
                .max(Comparator.comparingInt(x -> x.getValue()))
                .get()
                .getKey();

    }

}
