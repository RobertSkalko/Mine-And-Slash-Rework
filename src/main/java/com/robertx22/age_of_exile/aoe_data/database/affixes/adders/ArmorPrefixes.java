package com.robertx22.age_of_exile.aoe_data.database.affixes.adders;

import com.robertx22.age_of_exile.aoe_data.database.affixes.AffixBuilder;
import com.robertx22.age_of_exile.database.data.StatMod;
import com.robertx22.age_of_exile.database.data.gear_types.bases.BaseGearType.SlotTag;
import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.types.defense.Armor;
import com.robertx22.age_of_exile.database.data.stats.types.defense.DodgeRating;
import com.robertx22.age_of_exile.database.data.stats.types.resources.magic_shield.MagicShield;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;
import com.robertx22.library_of_exile.registry.ExileRegistryInit;

import java.util.ArrayList;
import java.util.List;

public class ArmorPrefixes implements ExileRegistryInit {

    static class TYPE {
        Stat stat;
        String name;
        SlotTag tag;

        public float flatMulti = 1;

        public TYPE(Stat stat, String name, SlotTag tag, float flatMulti) {
            this.stat = stat;
            this.name = name;
            this.tag = tag;
            this.flatMulti = flatMulti;
        }
    }

    @Override
    public void registerAll() {


        List<TYPE> stats = new ArrayList<>();

        stats.add(new TYPE(Armor.getInstance(), "Reinforced", SlotTag.armor_stat, 1));
        stats.add(new TYPE(MagicShield.getInstance(), "Fortified", SlotTag.magic_shield_stat, 0.25f));
        stats.add(new TYPE(DodgeRating.getInstance(), "Scaled", SlotTag.dodge_stat, 1));


        for (TYPE type : stats) {
            AffixBuilder.Normal("item_flat_" + type.stat.GUID())
                    .Named(type.name)
                    .stats(new StatMod(3 * type.flatMulti, 8 * type.flatMulti, type.stat, ModType.ITEM_FLAT))
                    .includesTags(type.tag)
                    .Prefix()
                    .Build();


            AffixBuilder.Normal("item_perc_" + type.stat.GUID())
                    .Named(type.name)
                    .stats(new StatMod(10, 150, type.stat, ModType.ITEM_PERCENT))
                    .includesTags(type.tag)
                    .Prefix()
                    .Build();

            AffixBuilder.Normal("item_both_" + type.stat.GUID())
                    .Named(type.name)
                    .stats(new StatMod(2 * type.flatMulti, 4 * type.flatMulti, type.stat, ModType.ITEM_FLAT), new StatMod(5, 30, type.stat, ModType.ITEM_PERCENT))
                    .includesTags(type.tag)
                    .Prefix()
                    .Build();

        }


    }
}
