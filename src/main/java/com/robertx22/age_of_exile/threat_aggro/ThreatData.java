package com.robertx22.age_of_exile.threat_aggro;

import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;

import java.util.Comparator;
import java.util.HashMap;

public class ThreatData {

    public HashMap<String, Integer> map = new HashMap<>();

    public void addThreat(Player player, Mob mob, int threat) {

        String key = player.getUUID()
                .toString();
        int cur = map.getOrDefault(key, 0);
        map.put(key, cur + threat);

        String highest = getHighest();
        if (!highest.isEmpty()) {
            if (key.equals(highest)) {
                if (mob.getTarget() != player) {
                    mob.setTarget(player);
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
