package com.robertx22.age_of_exile.aoe_data.database.base_stats;

import com.robertx22.age_of_exile.aoe_data.database.stats.Stats;
import com.robertx22.age_of_exile.database.data.base_stats.BaseStatsConfig;
import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.types.offense.WeaponDamage;
import com.robertx22.age_of_exile.database.data.stats.types.resources.RegeneratePercentStat;
import com.robertx22.age_of_exile.database.data.stats.types.resources.energy.Energy;
import com.robertx22.age_of_exile.database.data.stats.types.resources.energy.EnergyRegen;
import com.robertx22.age_of_exile.database.data.stats.types.resources.health.Health;
import com.robertx22.age_of_exile.database.data.stats.types.resources.health.HealthRegen;
import com.robertx22.age_of_exile.database.data.stats.types.resources.magic_shield.MagicShieldRegen;
import com.robertx22.age_of_exile.database.data.stats.types.resources.mana.Mana;
import com.robertx22.age_of_exile.database.data.stats.types.resources.mana.ManaRegen;
import com.robertx22.library_of_exile.registry.ExileRegistryInit;

public class BaseStatsAdder implements ExileRegistryInit {

    public static String PLAYER = "player";
    public static String MOB = "mob";

    @Override
    public void registerAll() {
        playerStats().addToSerializables();
        mob().addToSerializables();


    }

    public static BaseStatsConfig mob() {

        BaseStatsConfig c = new BaseStatsConfig();

        c.id = MOB;

        c.scaled(Stats.ACCURACY.get(), 5);

        return c;

    }

    public static BaseStatsConfig playerStats() {

        BaseStatsConfig c = new BaseStatsConfig();

        c.id = PLAYER;

        c.nonScaled(RegeneratePercentStat.MAGIC_SHIELD, 2);

        c.scaled(WeaponDamage.getInstance(), 2);

        c.scaled(Health.getInstance(), 50);
        c.scaled(Mana.getInstance(), 50);
        c.scaled(Energy.getInstance(), 50);

        c.scaled(HealthRegen.getInstance(), 2);
        c.scaled(MagicShieldRegen.getInstance(), 2);
        c.scaled(ManaRegen.getInstance(), 3);
        c.scaled(EnergyRegen.getInstance(), 5);

        c.nonScaled(Stats.MAX_SUMMON_CAPACITY.get(), 3);

        // why did i add this again? I think its a must
        c.nonScaled(Stats.CRIT_CHANCE.get(), 1);
        c.nonScaled(Stats.CRIT_DAMAGE.get(), 1);

        for (Stat cap : Stats.LEECH_CAP.getAll()) {
            c.nonScaled(cap, 5);
        }

        return c;

    }

}
