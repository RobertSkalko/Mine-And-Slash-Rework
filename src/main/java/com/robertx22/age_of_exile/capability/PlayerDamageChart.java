package com.robertx22.age_of_exile.capability;

import net.minecraft.world.entity.player.Player;

import java.util.HashMap;

public class PlayerDamageChart {

    static HashMap<String, Float> dmg = new HashMap<>();

    public static void onDamage(Player player, float damage) {
        float num = dmg.getOrDefault(player.getStringUUID(), 0F) + damage;
        dmg.put(player.getStringUUID(), num);
    }

    public static float getDamage(Player player) {
        return dmg.getOrDefault(player.getStringUUID(), 0F);
    }

    public static void clear(Player p) {
        dmg.put(p.getStringUUID(), 0F);
    }
}
