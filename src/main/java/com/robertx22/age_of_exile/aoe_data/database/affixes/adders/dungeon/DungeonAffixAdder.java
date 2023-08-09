package com.robertx22.age_of_exile.aoe_data.database.affixes.adders.dungeon;

import com.robertx22.age_of_exile.aoe_data.database.affixes.AffixBuilder;
import com.robertx22.age_of_exile.aoe_data.database.affixes.ElementalAffixBuilder;
import com.robertx22.age_of_exile.aoe_data.database.stats.Stats;
import com.robertx22.age_of_exile.database.data.StatMod;
import com.robertx22.age_of_exile.database.data.stats.types.defense.Armor;
import com.robertx22.age_of_exile.database.data.stats.types.generated.BonusAttackDamage;
import com.robertx22.age_of_exile.database.data.stats.types.generated.ElementalResist;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;
import com.robertx22.library_of_exile.registry.ExileRegistryInit;

import java.util.Arrays;

public class DungeonAffixAdder implements ExileRegistryInit {

    @Override
    public void registerAll() {

        ElementalAffixBuilder.start()
                .guid(x -> x.guidName + "_wep_dmg_dun")
                .add(Elements.Fire, "Burning")
                .add(Elements.Cold, "Freezing")
                .add(Elements.Chaos, "Poisonous")
                .add(Elements.Physical, "Tyrannical")
                .stats(x -> Arrays.asList(new StatMod(1, 2, new BonusAttackDamage(x), ModType.FLAT)))
                .DungeonPrefix()
                .Build();

        ElementalAffixBuilder.start()
                .guid(x -> x.guidName + "_ele_mobs")
                .add(Elements.Fire, "Of Embers")
                .add(Elements.Cold, "Of Frost")
                .add(Elements.Chaos, "Of Diseases")
                .stats(x -> Arrays.asList(
                        new StatMod(1, 2, new BonusAttackDamage(x), ModType.FLAT),
                        new StatMod(20, 30, new ElementalResist(x), ModType.FLAT)
                ))
                .DungeonSuffix()
                .Build();

        AffixBuilder.Normal("armored_mobs")
                .Named("Armored")
                .stats(new StatMod(20, 50, Armor.getInstance(), ModType.PERCENT))
                .DungeonPrefix()
                .Build();

        AffixBuilder.Normal("life_on_hit_dun")
                .Named("Of Vampires")
                .stats(new StatMod(3, 5, Stats.LIFESTEAL.get(), ModType.FLAT))
                .DungeonSuffix()
                .Build();

    }
}
