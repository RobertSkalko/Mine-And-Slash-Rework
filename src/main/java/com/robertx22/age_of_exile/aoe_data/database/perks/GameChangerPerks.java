package com.robertx22.age_of_exile.aoe_data.database.perks;

import com.robertx22.age_of_exile.aoe_data.database.stats.Stats;
import com.robertx22.age_of_exile.aoe_data.database.stats.old.DatapackStats;
import com.robertx22.age_of_exile.database.OptScaleExactStat;
import com.robertx22.age_of_exile.database.data.stats.types.ailment.AllAilmentDamage;
import com.robertx22.age_of_exile.database.data.stats.types.ailment.HitDamage;
import com.robertx22.age_of_exile.database.data.stats.types.defense.Armor;
import com.robertx22.age_of_exile.database.data.stats.types.generated.ElementalResist;
import com.robertx22.age_of_exile.database.data.stats.types.resources.DamageAbsorbedByMana;
import com.robertx22.age_of_exile.database.data.stats.types.resources.blood.BloodUser;
import com.robertx22.age_of_exile.database.data.stats.types.resources.blood.HealthRestorationToBlood;
import com.robertx22.age_of_exile.database.data.stats.types.resources.health.Health;
import com.robertx22.age_of_exile.database.data.stats.types.resources.health.HealthRegen;
import com.robertx22.age_of_exile.database.data.stats.types.resources.magic_shield.MagicShield;
import com.robertx22.age_of_exile.database.data.stats.types.resources.magic_shield.MagicShieldHeal;
import com.robertx22.age_of_exile.database.data.stats.types.resources.mana.Mana;
import com.robertx22.age_of_exile.database.data.stats.types.resources.mana.ManaRegen;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;
import com.robertx22.library_of_exile.registry.ExileRegistryInit;

public class GameChangerPerks implements ExileRegistryInit {

    @Override
    public void registerAll() {


        PerkBuilder.gameChanger("summoner", "Summoner",
                new OptScaleExactStat(+3, Stats.MAX_SUMMON_CAPACITY.get(), ModType.FLAT),
                new OptScaleExactStat(50, Stats.SUMMON_DAMAGE.get(), ModType.MORE),
                new OptScaleExactStat(-25, Stats.TOTAL_DAMAGE.get(), ModType.MORE)
        );

        PerkBuilder.gameChanger("ms_all_in", "Stare of Abyss",
                new OptScaleExactStat(-100, Health.getInstance(), ModType.MORE),
                new OptScaleExactStat(30, MagicShield.getInstance(), ModType.MORE),
                new OptScaleExactStat(50, new ElementalResist(Elements.Chaos), ModType.MORE)
        );

        PerkBuilder.gameChanger("warlock", "Warlock",
                new OptScaleExactStat(-25, HitDamage.getInstance(), ModType.MORE),
                new OptScaleExactStat(30, Stats.DOT_DAMAGE.get(), ModType.MORE)
        );

        PerkBuilder.gameChanger("mantra", "Mantra",
                new OptScaleExactStat(3, DatapackStats.PHYS_DMG_PER_MANA),
                new OptScaleExactStat(-100, Stats.CRIT_CHANCE.get(), ModType.MORE)
        );

        PerkBuilder.gameChanger("sniper", "Sniper",
                new OptScaleExactStat(-25, Stats.TOTAL_DAMAGE.get(), ModType.MORE),
                new OptScaleExactStat(-25, Stats.COOLDOWN_REDUCTION.get()),
                new OptScaleExactStat(50, Stats.PROJECTILE_DAMAGE.get(), ModType.MORE)
        );

        PerkBuilder.gameChanger("blood_mage", "Blood Mage",
                new OptScaleExactStat(1, BloodUser.getInstance(), ModType.FLAT),
                new OptScaleExactStat(50, HealthRestorationToBlood.getInstance(), ModType.FLAT),
                new OptScaleExactStat(50, DatapackStats.BLOOD_PER_10STR, ModType.FLAT), // todo might be bad
                new OptScaleExactStat(-100, Mana.getInstance(), ModType.MORE)
        );

        // put this on physical side of the tree
        PerkBuilder.gameChanger("elemental_purity", "Elemental Purity",
                new OptScaleExactStat(25, Stats.ELEMENTAL_DAMAGE.get(Elements.Elemental), ModType.FLAT),
                new OptScaleExactStat(-100, Stats.ELEMENTAL_DAMAGE.get(Elements.Physical), ModType.FLAT)
        );

        PerkBuilder.gameChanger("refined_taste", "Refined Taste",
                new OptScaleExactStat(50, Stats.INCREASED_LEECH.get(), ModType.FLAT),
                new OptScaleExactStat(-75, HealthRegen.getInstance(), ModType.MORE),
                new OptScaleExactStat(-75, ManaRegen.getInstance(), ModType.MORE)
        );


        PerkBuilder.gameChanger("steady_hand", "Steady Hand",
                new OptScaleExactStat(-100, Stats.CRIT_DAMAGE.get(), ModType.MORE),
                new OptScaleExactStat(-100, Stats.CRIT_DAMAGE.get(), ModType.MORE),
                new OptScaleExactStat(25, Stats.TOTAL_DAMAGE.get(), ModType.FLAT)
        );

        PerkBuilder.gameChanger("true_hit", "True Hit",
                new OptScaleExactStat(25, Stats.CRIT_DAMAGE.get(), ModType.MORE),
                new OptScaleExactStat(-25, Stats.NON_CRIT_DAMAGE.get(), ModType.FLAT)
        );


        PerkBuilder.gameChanger("harmony", "Harmony",

                new OptScaleExactStat(50, MagicShieldHeal.getInstance(), ModType.FLAT),
                new OptScaleExactStat(-25, Health.getInstance(), ModType.MORE),
                new OptScaleExactStat(-25, Armor.getInstance(), ModType.MORE)
        );

        PerkBuilder.gameChanger("mana_battery", "Mana Battery",
                new OptScaleExactStat(50, DamageAbsorbedByMana.getInstance(), ModType.FLAT),
                new OptScaleExactStat(25, new ElementalResist(Elements.Lightning), ModType.FLAT)
        );

        PerkBuilder.gameChanger("divinity", "Divinity",
                new OptScaleExactStat(50, DatapackStats.HEAL_TO_SPELL_DMG, ModType.FLAT),
                new OptScaleExactStat(-50, Stats.CRIT_DAMAGE.get(), ModType.MORE)
        );

        PerkBuilder.gameChanger("tormentor", "Tormentor",
                new OptScaleExactStat(35, AllAilmentDamage.getInstance(), ModType.MORE),
                new OptScaleExactStat(-10, Stats.TOTAL_DAMAGE.get(), ModType.MORE)
        );

    }

}
