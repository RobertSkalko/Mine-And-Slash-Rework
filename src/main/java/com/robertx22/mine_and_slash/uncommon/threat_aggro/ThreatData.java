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
    public HashMap<UUID, Integer> map = new HashMap<>();

    public void addThreat(LivingEntity threatCreatorEntity, Mob mob, int threat) {
        UUID uuid = threatCreatorEntity.getUUID();
        var cur = map.getOrDefault(uuid, 0);
        map.put(uuid, cur + threat);
        updateMobTargetWithHighestThreat(mob, threatCreatorEntity, uuid);
    }

    private void updateMobTargetWithHighestThreat(Mob mob, LivingEntity threatCreatorEntity, UUID key) {
        var highestKey = getHighest();
        if (highestKey == null) {
            return;
        }

        if (highestKey.equals(key)) {
            if (mob.getTarget() != threatCreatorEntity) {
                mob.setTarget(threatCreatorEntity);
            }
            return;
        }

        Entity threat = ((ServerLevel)mob.level()).getEntity(highestKey);
        if (threat == null || !threat.isAlive()) {
            map.remove(highestKey);
            updateMobTargetWithHighestThreat(mob, threatCreatorEntity, key);
        }
    }

    public UUID getHighest() {
        if (map.isEmpty()) {
            return null;
        }

        return map.entrySet().stream().max(Comparator.comparingInt(Map.Entry::getValue)).get().getKey();
    }
}
