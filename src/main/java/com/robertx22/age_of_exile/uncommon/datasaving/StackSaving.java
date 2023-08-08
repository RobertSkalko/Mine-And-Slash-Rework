package com.robertx22.age_of_exile.uncommon.datasaving;

import com.robertx22.age_of_exile.saveclasses.item_classes.GearItemData;
import com.robertx22.age_of_exile.saveclasses.skill_gem.SkillGemData;
import com.robertx22.age_of_exile.saveclasses.stat_soul.StatSoulData;
import com.robertx22.age_of_exile.vanilla_mc.items.crates.gem_crate.LootCrateData;
import com.robertx22.library_of_exile.utils.ItemstackDataSaver;

public class StackSaving {

    public static ItemstackDataSaver<GearItemData> GEARS = of(new ItemstackDataSaver<>("GEAR_ITEM_DATA", GearItemData.class, () -> new GearItemData()));
    public static ItemstackDataSaver<SkillGemData> SKILL_GEM = of(new ItemstackDataSaver<>("skill_gem_data", SkillGemData.class, () -> new SkillGemData()));
    public static ItemstackDataSaver<StatSoulData> STAT_SOULS = of(new ItemstackDataSaver<>("stat_soul", StatSoulData.class, () -> new StatSoulData()));
    public static ItemstackDataSaver<LootCrateData> GEM_CRATE = of(new ItemstackDataSaver<>("loot_crate", LootCrateData.class, () -> new LootCrateData()));
    
    static ItemstackDataSaver of(ItemstackDataSaver t) {
        return t;
    }

    public static void init() {

    }
}
