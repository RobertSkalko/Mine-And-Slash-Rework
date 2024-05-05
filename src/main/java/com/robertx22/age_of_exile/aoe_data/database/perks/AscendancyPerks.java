package com.robertx22.age_of_exile.aoe_data.database.perks;

import com.robertx22.age_of_exile.aoe_data.database.exile_effects.adders.ModEffects;
import com.robertx22.age_of_exile.aoe_data.database.stats.EffectStats;
import com.robertx22.age_of_exile.aoe_data.database.stats.ProcStats;
import com.robertx22.age_of_exile.aoe_data.database.stats.base.EffectAndCondition;
import com.robertx22.age_of_exile.aoe_data.database.stats.old.DatapackStats;
import com.robertx22.age_of_exile.database.OptScaleExactStat;
import com.robertx22.age_of_exile.database.data.stats.types.MaximumChargesStat;
import com.robertx22.age_of_exile.database.data.stats.types.resources.RegeneratePercentStat;
import com.robertx22.age_of_exile.database.data.stats.types.resources.magic_shield.MagicShieldRegen;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;

public class AscendancyPerks {


    public static AscendancyKey ARCANIST = new AscendancyKey("arcanist", "Arcanist");
    public static AscendancyKey LICH = new AscendancyKey("lich", "Death Lich");
    public static AscendancyKey CHIEF = new AscendancyKey("chief", "Chieftain");
    public static AscendancyKey CHAMP = new AscendancyKey("champ", "Champion");
    public static AscendancyKey RAIDER = new AscendancyKey("raider", "Raider");
    public static AscendancyKey ARCHER = new AscendancyKey("hunter", "Hunter");
    public static AscendancyKey TRICKSTER = new AscendancyKey("trickster", "Trickster");
    public static AscendancyKey ASSASSIN = new AscendancyKey("assassin", "Assassin");
    public static AscendancyKey NECRO = new AscendancyKey("necro", "Necromancer");
    public static AscendancyKey ELEMENTALIST = new AscendancyKey("elem", "Elementalist");
    public static AscendancyKey GUARDIAN = new AscendancyKey("guardian", "Guardian");
    public static AscendancyKey BATTLEMAGE = new AscendancyKey("battlemage", "Battlemage");

    public static void init() {


        LICH.of(x -> {

            x.createPerk(0, "Dark Power",
                    new OptScaleExactStat(5, DatapackStats.AOE_PER_POWER_CHARGE, ModType.FLAT),
                    new OptScaleExactStat(5, DatapackStats.DMG_PER_POWER_CHARGE, ModType.FLAT),
                    new OptScaleExactStat(1, new MaximumChargesStat(ModEffects.POWER_CHARGE), ModType.FLAT)
            );
            x.createPerk(1, "Accumulating Power",
                    new OptScaleExactStat(5, EffectStats.CHANCE_TO_GIVE_CASTER_EFFECT.get(new EffectAndCondition(ModEffects.POWER_CHARGE, EffectAndCondition.Condition.HIT)), ModType.FLAT),
                    new OptScaleExactStat(5, DatapackStats.CRIT_DMG_PER_POWER_CHARGE, ModType.FLAT)
            );
            x.createPerk(2, "Armored Mana",
                    new OptScaleExactStat(5, DatapackStats.ARMOR_PER_MANA, ModType.FLAT)
            );
            x.createPerk(3, "Dark Infusion",
                    new OptScaleExactStat(3, RegeneratePercentStat.MAGIC_SHIELD, ModType.FLAT),
                    new OptScaleExactStat(25, MagicShieldRegen.getInstance(), ModType.PERCENT)
            );
            x.createPerk(4, "Profane Explosion",
                    new OptScaleExactStat(25, ProcStats.PROFANE_EXPLOSION_ON_KILL.get(), ModType.FLAT)
            );

        });

    }
}
