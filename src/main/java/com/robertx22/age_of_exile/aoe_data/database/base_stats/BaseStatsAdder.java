package com.robertx22.age_of_exile.aoe_data.database.base_stats;

import com.robertx22.age_of_exile.aoe_data.database.stats.Stats;
import com.robertx22.age_of_exile.database.data.base_stats.BaseStatsConfig;
import com.robertx22.age_of_exile.database.data.stats.types.offense.WeaponDamage;
import com.robertx22.age_of_exile.database.data.stats.types.resources.RegeneratePercentStat;
import com.robertx22.age_of_exile.database.data.stats.types.resources.energy.Energy;
import com.robertx22.age_of_exile.database.data.stats.types.resources.health.Health;
import com.robertx22.age_of_exile.database.data.stats.types.resources.mana.Mana;
import com.robertx22.library_of_exile.registry.ExileRegistryInit;

public class BaseStatsAdder implements ExileRegistryInit {

    public static String PLAYER = "player";

    @Override
    public void registerAll() {
        playerStats().addToSerializables();


    }

    public static BaseStatsConfig playerStats() {

        BaseStatsConfig c = new BaseStatsConfig();

        c.id = PLAYER;

        c.nonScaled(RegeneratePercentStat.HEALTH, 2);
        c.nonScaled(RegeneratePercentStat.MAGIC_SHIELD, 2);

        c.nonScaled(RegeneratePercentStat.MANA, 3);
        c.nonScaled(RegeneratePercentStat.ENERGY, 5);

        c.scaled(WeaponDamage.getInstance(), 2);

        c.scaled(Health.getInstance(), 50);
        c.scaled(Mana.getInstance(), 30);
        c.scaled(Energy.getInstance(), 30);

        c.nonScaled(Stats.MAX_SUMMON_CAPACITY.get(), 3);

        // why did i add this again? I think its a must
        c.nonScaled(Stats.CRIT_CHANCE.get(), 1);
        c.nonScaled(Stats.CRIT_DAMAGE.get(), 1);

        return c;

    }

}
