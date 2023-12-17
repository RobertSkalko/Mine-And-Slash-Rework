package com.robertx22.age_of_exile.aoe_data.database.affixes.adders;

import com.robertx22.age_of_exile.aoe_data.database.affixes.AffixBuilder;
import com.robertx22.age_of_exile.aoe_data.database.stats.Stats;
import com.robertx22.age_of_exile.database.data.StatMod;
import com.robertx22.age_of_exile.database.data.gear_types.bases.BaseGearType.SlotTag;
import com.robertx22.age_of_exile.database.data.stats.types.defense.ArmorPenetration;
import com.robertx22.age_of_exile.database.data.stats.types.offense.SkillDamage;
import com.robertx22.age_of_exile.saveclasses.unit.ResourceType;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;
import com.robertx22.library_of_exile.registry.ExileRegistryInit;

public class WeaponSuffixes implements ExileRegistryInit {

    @Override
    public void registerAll() {

        AffixBuilder.Normal("of_pene")
                .Named("Of Penetration")
                .stats(new StatMod(5, 25, ArmorPenetration.getInstance(), ModType.FLAT))
                .includesTags(SlotTag.weapon_family)
                .Suffix()
                .Build();

        AffixBuilder.Normal("of_vampirism")
                .Named("Of Vampirism")
                .stats(new StatMod(1, 5, Stats.LIFESTEAL.get(), ModType.FLAT))
                .includesTags(SlotTag.weapon_family)
                .Suffix()
                .Build();


        AffixBuilder.Normal("of_gluttony")
                .Named("Of Gluttony")
                .stats(new StatMod(1, 6, Stats.RESOURCE_ON_KILL.get(ResourceType.health), ModType.FLAT))
                .includesTags(SlotTag.weapon_family)
                .Suffix()
                .Build();

        AffixBuilder.Normal("of_consumption")
                .Named("Of Consumption")
                .stats(new StatMod(1, 6, Stats.RESOURCE_ON_KILL.get(ResourceType.mana), ModType.FLAT))
                .includesTags(SlotTag.weapon_family)
                .Suffix()
                .Build();

        AffixBuilder.Normal("of_fast_cast")
                .Named("Of Faster Casting")
                .stats(new StatMod(7, 20, Stats.CAST_SPEED.get(), ModType.FLAT))
                .includesTags(SlotTag.mage_weapon, SlotTag.jewelry_family)
                .Suffix()
                .Build();

        AffixBuilder.Normal("of_less_cd")
                .Named("Of Repetition")
                .stats(new StatMod(6, 15, Stats.COOLDOWN_REDUCTION.get(), ModType.FLAT))
                .includesTags(SlotTag.mage_weapon, SlotTag.jewelry_family)
                .Suffix()
                .Build();

        AffixBuilder.Normal("of_spell_dmg")
                .Named("Of Spell Damage")
                .stats(new StatMod(6, 15, SkillDamage.getInstance(), ModType.FLAT))
                .includesTags(SlotTag.mage_weapon, SlotTag.jewelry_family)
                .Suffix()
                .Build();

        AffixBuilder.Normal("heal_suff")
                .Named("Of Restoration")
                .stats(new StatMod(5, 20, Stats.HEAL_STRENGTH.get(), ModType.FLAT))
                .includesTags(SlotTag.staff)
                .Suffix()
                .Build();
    }
}
