package com.robertx22.age_of_exile.capability.player.data;

import com.robertx22.age_of_exile.database.data.game_balance_config.GameBalanceConfig;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.saveclasses.ExactStatData;
import com.robertx22.age_of_exile.saveclasses.unit.Unit;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;
import net.minecraft.world.entity.player.Player;

import java.util.HashMap;
import java.util.Map;


public class StatPointsData {


    public HashMap<String, Integer> map = new HashMap<>();

    public void reset() {
        this.map.clear();
    }

    public void addStats(Unit unit) {
        map.entrySet()
                .forEach(x -> {
                    float val = x.getValue();
                    ExactStatData stat = ExactStatData.levelScaled(val, ExileDB.Stats()
                            .get(x.getKey()), ModType.FLAT, 1);
                    stat.applyToStatInCalc(unit);
                });

    }

    public int getFreePoints(Player player) {
        int total = (int) (Load.Unit(player)
                .getLevel() * GameBalanceConfig.get().STAT_POINTS_PER_LEVEL);

        int spent = 0;

        for (Map.Entry<String, Integer> x : map.entrySet()) {
            spent += x.getValue();
        }

        return total - spent;
    }
}
