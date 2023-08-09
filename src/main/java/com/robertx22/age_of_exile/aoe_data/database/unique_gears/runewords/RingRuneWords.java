package com.robertx22.age_of_exile.aoe_data.database.unique_gears.runewords;

import com.robertx22.age_of_exile.aoe_data.database.base_gear_types.BaseGearTypes;
import com.robertx22.age_of_exile.aoe_data.database.stats.Stats;
import com.robertx22.age_of_exile.aoe_data.database.unique_gears.UniqueGearBuilder;
import com.robertx22.age_of_exile.database.data.StatMod;
import com.robertx22.age_of_exile.database.data.exile_effects.EffectTags;
import com.robertx22.age_of_exile.database.data.stats.types.generated.ElementalResist;
import com.robertx22.age_of_exile.database.data.stats.types.resources.mana.ManaRegen;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;
import com.robertx22.age_of_exile.vanilla_mc.items.gemrunes.RuneItem;
import com.robertx22.library_of_exile.registry.ExileRegistryInit;

import java.util.Arrays;

public class RingRuneWords implements ExileRegistryInit {

    @Override
    public void registerAll() {

        UniqueGearBuilder.of(
                        "air_disaster",
                        "Aria of Disaster",
                        BaseGearTypes.RING)
                .setReplacesName()
                .stats(Arrays.asList(
                        new StatMod(15, 30, Stats.EFFECT_OF_BUFFS_GIVEN_PER_EFFECT_TAG.get(EffectTags.song), ModType.FLAT),
                        new StatMod(5, 15, Stats.AREA_DAMAGE.get(), ModType.FLAT),
                        new StatMod(3, 10, ManaRegen.getInstance(), ModType.FLAT)
                ))
                .makeRuneWordOnly(Arrays.asList(RuneItem.RuneType.ANO, RuneItem.RuneType.ORU, RuneItem.RuneType.DOS))
                .devComment("song buffer / area damage")
                .build();

        UniqueGearBuilder.of(
                        "playful_hope",
                        "Playful Hope",
                        BaseGearTypes.RING)
                .setReplacesName()
                .stats(Arrays.asList(
                        new StatMod(-75, 25, Stats.CRIT_DAMAGE.get()),
                        new StatMod(-50, 15, new ElementalResist(Elements.Cold), ModType.FLAT),
                        new StatMod(-50, 15, new ElementalResist(Elements.Fire), ModType.FLAT),
                        new StatMod(-50, 15, new ElementalResist(Elements.Chaos), ModType.FLAT)
                ))
                .makeRuneWordOnly(Arrays.asList(RuneItem.RuneType.YUN, RuneItem.RuneType.FEY, RuneItem.RuneType.ORU))
                .devComment("global crit + res, high RNG")
                .build();

    }
}
