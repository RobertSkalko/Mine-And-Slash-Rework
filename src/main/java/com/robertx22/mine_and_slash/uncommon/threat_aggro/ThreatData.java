package com.robertx22.mine_and_slash.uncommon.threat_aggro;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ThreatData {
    public HashMap<String, Integer> map = new HashMap<>();

    public void addThreat(LivingEntity threatCreatorEntity, Mob mob, int threat) {
        String key = threatCreatorEntity.getUUID().toString();
        int cur = map.getOrDefault(key, 0);
        map.put(key, cur + threat);
        updateMobTargetWithHighestThreat(mob, threatCreatorEntity, key);
    }

    private void updateMobTargetWithHighestThreat(Mob mob, LivingEntity threatCreatorEntity, String key) {
        String highestKey = getHighest();
        if (highestKey.equals(key)) {
            if (mob.getTarget() != threatCreatorEntity) {
                mob.setTarget(threatCreatorEntity);
            }
            return;
        }

        Entity threat = ((ServerLevel)mob.level()).getEntity(UUID.fromString(highestKey));
        if (threat == null || !threat.isAlive()) {
            map.remove(highestKey);
            updateMobTargetWithHighestThreat(mob, threatCreatorEntity, key);
        }
    }

    public String getHighest() {
        if (map.isEmpty()) {
            return "";
        }

        return map.entrySet().stream().max(Comparator.comparingInt(Map.Entry::getValue)).get().getKey();
    }
}
