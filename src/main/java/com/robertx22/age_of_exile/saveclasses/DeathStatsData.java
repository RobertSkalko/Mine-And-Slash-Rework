package com.robertx22.age_of_exile.saveclasses;

import com.robertx22.age_of_exile.capability.player.PlayerData;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;

import java.util.HashMap;


public class DeathStatsData {

    public BlockPos deathPos;

    public String deathDim;


    public HashMap<Elements, Float> dmg = new HashMap<Elements, Float>();


    public boolean died = false;

    public static void record(Player player, Elements ele, float amount) {
        PlayerData stats = Load.playerRPGData(player);
        Elements element = ele == null ? Elements.Physical : ele;
        stats.deathStats.record(element, amount);
    }

    private void record(Elements ele, float amount) {

        if (died) {
            clear();
            died = false;
        }

        float total = dmg.getOrDefault(ele, 0F) + amount;
        dmg.put(ele, total);
    }

    public void clear() {
        dmg.clear();
    }

}
