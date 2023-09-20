package com.robertx22.age_of_exile.aoe_data.database.affixes.adders;

import com.robertx22.age_of_exile.aoe_data.database.affixes.AffixBuilder;
import com.robertx22.age_of_exile.database.data.StatMod;
import com.robertx22.age_of_exile.database.data.gear_types.bases.BaseGearType.SlotTag;
import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.types.gear_base.GearDefense;
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
        String suffix;

        public TYPE(String suffix, Stat stat, String name, SlotTag tag, float flatMulti) {
            this.stat = stat;
            this.name = name;
            this.suffix = suffix;
            this.tag = tag;
            this.flatMulti = flatMulti;
        }
    }


    @Override
    public void registerAll() {


        List<TYPE> stats = new ArrayList<>();

        var ARMOR = new TYPE("armor", GearDefense.getInstance(), "Reinforced", SlotTag.armor_stat, 1);
        var MS = new TYPE("ms", GearDefense.getInstance(), "Fortified", SlotTag.magic_shield_stat, 0.33f);
        var DODGE = new TYPE("dodge", GearDefense.getInstance(), "Scaled", SlotTag.dodge_stat, 1);

        stats.add(ARMOR);
        stats.add(MS);
        stats.add(DODGE);


        for (TYPE type : stats) {
            AffixBuilder.Normal("item_flat_" + type.suffix)
                    .Named(type.name)
                    .stats(new StatMod(10, 50, type.stat, ModType.PERCENT))
                    .includesTags(type.tag)
                    .Prefix()
                    .Build();


            AffixBuilder.Normal("item_perc_" + type.suffix)
                    .Named(type.name)
                    .stats(new StatMod(10, 150, type.stat, ModType.PERCENT))
                    .includesTags(type.tag)
                    .Prefix()
                    .Build();

            AffixBuilder.Normal("item_both_" + type.suffix)
                    .Named(type.name)
                    .stats(new StatMod(10, 50, type.stat, ModType.PERCENT))
                    .includesTags(type.tag)
                    .Prefix()
                    .Build();

        }


    }
}
