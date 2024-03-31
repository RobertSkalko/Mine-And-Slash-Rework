package com.robertx22.age_of_exile.uncommon.datasaving;

import com.robertx22.age_of_exile.database.data.loot_chest.base.LootChestData;
import com.robertx22.age_of_exile.maps.MapItemData;
import com.robertx22.age_of_exile.mmorpg.SlashRef;
import com.robertx22.age_of_exile.saveclasses.item_classes.GearItemData;
import com.robertx22.age_of_exile.saveclasses.jewel.JewelItemData;
import com.robertx22.age_of_exile.saveclasses.prof_tool.ProfessionToolData;
import com.robertx22.age_of_exile.saveclasses.skill_gem.SkillGemData;
import com.robertx22.age_of_exile.saveclasses.stat_soul.StatSoulData;
import com.robertx22.age_of_exile.vanilla_mc.items.crates.gem_crate.LootCrateData;
import com.robertx22.library_of_exile.utils.ItemstackDataSaver;

public class StackSaving {

    public static ItemstackDataSaver<GearItemData> GEARS = of(new ItemstackDataSaver<>(SlashRef.MODID + "_gear", GearItemData.class, () -> new GearItemData()));
    public static ItemstackDataSaver<SkillGemData> SKILL_GEM = of(new ItemstackDataSaver<>(SlashRef.MODID + "_skill_gem", SkillGemData.class, () -> new SkillGemData()));
    public static ItemstackDataSaver<StatSoulData> STAT_SOULS = of(new ItemstackDataSaver<>(SlashRef.MODID + "_stat_soul", StatSoulData.class, () -> new StatSoulData()));
    public static ItemstackDataSaver<LootCrateData> GEM_CRATE = of(new ItemstackDataSaver<>(SlashRef.MODID + "_loot_crate", LootCrateData.class, () -> new LootCrateData()));
    public static ItemstackDataSaver<MapItemData> MAP = of(new ItemstackDataSaver<>(SlashRef.MODID + "_map", MapItemData.class, () -> new MapItemData()));
    public static ItemstackDataSaver<JewelItemData> JEWEL = of(new ItemstackDataSaver<>(SlashRef.MODID + "_jewel", JewelItemData.class, () -> new JewelItemData()));
    public static ItemstackDataSaver<LootChestData> LOOT_CHEST = of(new ItemstackDataSaver<>(SlashRef.MODID + "_loot_chest", LootChestData.class, () -> new LootChestData()));
    public static ItemstackDataSaver<ProfessionToolData> TOOL = of(new ItemstackDataSaver<>(SlashRef.MODID + "_tool_stats", ProfessionToolData.class, () -> new ProfessionToolData()));
  
    static ItemstackDataSaver of(ItemstackDataSaver t) {
        return t;
    }

    public static void init() {

    }
}
