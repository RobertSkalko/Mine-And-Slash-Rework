package com.robertx22.age_of_exile.aoe_data.database.affixes.adders;

import com.robertx22.age_of_exile.aoe_data.database.affixes.AffixBuilder;
import com.robertx22.age_of_exile.aoe_data.database.affixes.ElementalAffixBuilder;
import com.robertx22.age_of_exile.aoe_data.database.stats.Stats;
import com.robertx22.age_of_exile.database.data.StatMod;
import com.robertx22.age_of_exile.database.data.gear_types.bases.BaseGearType.SlotTag;
import com.robertx22.age_of_exile.database.data.stats.types.gear_base.GearDamage;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;
import com.robertx22.library_of_exile.registry.ExileRegistryInit;

import java.util.Arrays;

public class WeaponPrefixes implements ExileRegistryInit {
    @Override
    public void registerAll() {

        AffixBuilder.Normal("wep_item_flat")
                .Named("Deadly")
                .stats(new StatMod(1, 6, GearDamage.getInstance(), ModType.FLAT))
                .includesTags(SlotTag.weapon_family)
                .Prefix()
                .Build();
        AffixBuilder.Normal("wep_item_perc")
                .Named("Cruel")
                .stats(new StatMod(10, 150, GearDamage.getInstance(), ModType.PERCENT))
                .includesTags(SlotTag.weapon_family)
                .Prefix()
                .Build();
        AffixBuilder.Normal("wep_item_both")
                .Named("Miserable")
                .stats(new StatMod(1, 3, GearDamage.getInstance(), ModType.FLAT),
                        new StatMod(10, 50, GearDamage.getInstance(), ModType.PERCENT))
                .includesTags(SlotTag.weapon_family)
                .Prefix()
                .Build();

        ElementalAffixBuilder.start()
                .guid(x -> x.guidName + "_wep_dmg")
                .add(Elements.Fire, "Scorched")
                .add(Elements.Cold, "Chilled")
                .add(Elements.Chaos, "Poisoned")
                .add(Elements.Physical, "Tyrannical")
                .stats(x -> Arrays.asList(new StatMod(3, 10, Stats.ELEMENTAL_DAMAGE.get(x), ModType.FLAT)))
                .includesTags(SlotTag.weapon_family)
                .Prefix()
                .Build();

        ElementalAffixBuilder.start()
                .guid(x -> x.guidName + "_spell_ele_dmg")
                .add(Elements.Fire, "Scorched")
                .add(Elements.Cold, "Chilled")
                .add(Elements.Lightning, "Poisoned")
                .stats(x -> Arrays.asList(new StatMod(5, 15, Stats.ELEMENTAL_SPELL_DAMAGE.get(x))))
                .Weight(500)
                .includesTags(SlotTag.mage_weapon)
                .Prefix()
                .Build();

        AffixBuilder.Normal("desolation")
                .Named("Desolation")
                .stats(new StatMod(2, 6, Stats.CRIT_CHANCE.get()), new StatMod(3, 10, Stats.CRIT_DAMAGE.get()))
                .includesTags(SlotTag.mage_weapon)
                .Weight(100)
                .Prefix()
                .Build();


        AffixBuilder.Normal("true_hit")
                .Named("True Hit")
                .stats(new StatMod(2, 10, Stats.CRIT_CHANCE.get(), ModType.FLAT))
                .includesTags(SlotTag.weapon_family)
                .Prefix()
                .Build();

        AffixBuilder.Normal("crit_prefix")
                .Named("Critical")
                .stats(new StatMod(4, 12, Stats.CRIT_DAMAGE.get(), ModType.FLAT))
                .includesTags(SlotTag.weapon_family)
                .Prefix()
                .Build();

        AffixBuilder.Normal("heal_crit_prefix")
                .Named("Truthful")
                .stats(new StatMod(3, 12, Stats.CRIT_CHANCE.get(), ModType.FLAT))
                .includesTags(SlotTag.staff)
                .Prefix()
                .Build();

        AffixBuilder.Normal("heal_crit_dmg_prefix")
                .Named("Inspiring")
                .stats(new StatMod(5, 20, Stats.CRIT_DAMAGE.get(), ModType.FLAT))
                .includesTags(SlotTag.staff)
                .Prefix()
                .Build();

    }

}
