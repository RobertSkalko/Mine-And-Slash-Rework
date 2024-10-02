package com.robertx22.mine_and_slash.aoe_data.database.unique_gears.uniques.armor;

import com.robertx22.library_of_exile.registry.ExileRegistryInit;
import com.robertx22.mine_and_slash.aoe_data.database.ailments.Ailments;
import com.robertx22.mine_and_slash.aoe_data.database.base_gear_types.BaseGearTypes;
import com.robertx22.mine_and_slash.aoe_data.database.stats.OffenseStats;
import com.robertx22.mine_and_slash.aoe_data.database.unique_gears.UniqueGearBuilder;
import com.robertx22.mine_and_slash.database.data.StatMod;
import com.robertx22.mine_and_slash.database.data.stats.effects.defense.MaxElementalResist;
import com.robertx22.mine_and_slash.database.data.stats.types.ailment.AilmentChance;
import com.robertx22.mine_and_slash.database.data.stats.types.ailment.AilmentProcStat;
import com.robertx22.mine_and_slash.database.data.stats.types.gear_base.GearDefense;
import com.robertx22.mine_and_slash.database.data.stats.types.generated.ElementalResist;
import com.robertx22.mine_and_slash.database.data.stats.types.loot.TreasureQuality;
import com.robertx22.mine_and_slash.tags.all.SpellTags;
import com.robertx22.mine_and_slash.uncommon.enumclasses.Elements;
import com.robertx22.mine_and_slash.uncommon.enumclasses.ModType;

import java.util.Arrays;

public class BootsUniques implements ExileRegistryInit {
    @Override
    public void registerAll() {
        UniqueGearBuilder.of("ice_stomper", "Ice Stomper", BaseGearTypes.PLATE_BOOTS)
                .stats(Arrays.asList(
                        new StatMod(25, 150, GearDefense.getInstance(), ModType.PERCENT),
                        new StatMod(25, 50, new AilmentProcStat(Ailments.FREEZE), ModType.FLAT),
                        new StatMod(25, 50, new AilmentProcStat(Ailments.ELECTRIFY), ModType.FLAT),
                        new StatMod(-25, -25, new ElementalResist(Elements.Shadow), ModType.FLAT)
                ))
                .build();

        UniqueGearBuilder.of("sparkfinder", "Sparkfinder", BaseGearTypes.LEATHER_BOOTS)
                .stats(Arrays.asList(
                        GearDefense.getInstance().mod(25, 100).percent(),
                        TreasureQuality.getInstance().mod(5, 15).percent(),
                        new ElementalResist(Elements.Fire).mod(-50, 75).percent()
                ))
                .build();

        UniqueGearBuilder.of("madness_pursuit", "Pursuit of Madness", BaseGearTypes.CLOTH_BOOTS)
                .setReplacesName()
                .stats(Arrays.asList(
                        new StatMod(25, 100, GearDefense.getInstance(), ModType.PERCENT),
                        new StatMod(25, 50, OffenseStats.DAMAGE_PER_SPELL_TAG.get(SpellTags.curse), ModType.FLAT),
                        new StatMod(50, 100, new ElementalResist(Elements.Shadow), ModType.FLAT),
                        new StatMod(-25, -25, new ElementalResist(Elements.Cold), ModType.FLAT),
                        new StatMod(-25, -25, new ElementalResist(Elements.Nature), ModType.FLAT),
                        new StatMod(-25, -25, new ElementalResist(Elements.Fire), ModType.FLAT)
                ))
                .build();

        UniqueGearBuilder.of("fire_step", "Flaming Steps", BaseGearTypes.LEATHER_BOOTS)
                .keepsBaseName()
                .stat(GearDefense.getInstance().mod(50, 100).percent())
                .stat(new AilmentChance(Ailments.BURN).mod(5, 10))
                .stat(OffenseStats.ELEMENTAL_DAMAGE.get(Elements.Fire).mod(20, 20))
                .stat(new MaxElementalResist(Elements.Fire).mod(5, 5))
                .build();

    }
}
