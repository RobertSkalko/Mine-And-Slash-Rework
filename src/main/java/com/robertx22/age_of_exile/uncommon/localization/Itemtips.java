package com.robertx22.age_of_exile.uncommon.localization;

import com.robertx22.age_of_exile.mmorpg.SlashRef;
import com.robertx22.age_of_exile.uncommon.interfaces.IAutoLocName;

import java.util.Locale;

public enum Itemtips implements IAutoLocName {
    //add "tip" at the end to avoid same variable call
    //you can use \n in here, just remember use the splitLongText() to process it.
    //a special method splitLongText() in TooltipsUtils.java can split the sentence into different line by replacing the "\n", achieving a actual line break function.
    SOUL_LOCKED_TO_TYPE("Locked Armor type: %1$s"),
    CHECK_GEAR_STATS_IN_SOUL("This soul has gear stat, you can press Shift to check out."),
    ENCHANTMENT_GEAR_LEVEL_RANGE("Gear level range: %1$s - %2$s"),
    SOUL_GENERATE_GEAR_LEVEL_RANGE("Generated Gear Level: %1$s - %2$s"),
    BUFF_TIP("Buff Stats: "),
    SOUL_MODIFIER_TIP("Soul modifier can be applied to gear soul, which then generate \nonly the corresponding gear type when use."),
    SOUL_MODIFIER_USE_TIP("[Drag onto gear soul to use]"),
    PROF_TOOL_LEVEL_CAP("Capped Drops to LVL %1$s"),
    PROF_TOOL_STATS_TIP("Tool Stats: "),
    PROF_TOOL_EXP_TIP("Exp: %1$s/%2$s"),
    SOUL_TIER_TIP("Soul tier determine the level range of generated gear"),
    MAP_TIER_TIP("Map tier indicate the difficulty and loot quality of this map"),
    SHIFT_TIP("[Shift]: Detail"),
    ALT_TIP("[Alt]: Desc"),
    CTRL_TIP("[Ctrl]: Hide"),
    TIER_INFLUENCE("Tier Influence For Mob"),
    RARITY_LINE("%1$s Item"),
    RARITY_TIP("Rarity: "),
    ITEM_TYPE("Item Type: %1$s"),
    TIER_TIP("Tier %1$s"),
    ITEM_TIER_TIP("Item Tier: %1$s"),
    LEVEL_TIP("Level %1$s"),

    LEVEL_REQ("Level Min: %1$s"),
    GEAR_SOUL_USE_TIP("[Drag onto gear to use]"),
    Stat_Req("%1$s Min: "),
    POTENTIAL("Potential: %1$s"),
    QUALITY("Quality: %1$s%%"),
    Durability("Durability: "),
    Unbreakable("Unbreakable"),
    Restores("Restores: %1$s"),

    CHEST_CONTAINS("Contains: %1$s"),
    NEED_KEY("Needs %1$s "),
    EMPTY_SOCKET("[Socket]"),

    AURA_RESERVATION("Aura Reservation: "),
    REMAINING_AURA_CAPACITY("Remaining Augment Capacity: "),
    SUPPORT_GEM_COST("Resource Cost Multiplier: %1$s%%"),
    SUPPORT_GEM_ONLY_ONE("Only One Allowed: "),
    SOUL_EXTRACTOR_TIP("Click on items to extract their soul.\nWorks only on that rarity.\nDeletes the Item in the process."),

    GEM_OPEN_GUI_TIP("Right Click to Open Gui"),
    SOUL_STONE_TIP("Right click to produce a common gear soul."),
    UNIQUE_JEWEL_USE("Right click to create"),
    TP_BACK_ITEM("Right click to return from the Map\nExits the Map Dimension."),
    UBER_BOSS_MAP_TIP("Right click to Generate Map Item\nLeads to a Boss Fight"),
    UBER_BOSS_FRAG_TIP("Combine with More Fragments in the Crafting Table\nCreates a Map That contains an Uber Boss"),
    RUNE_ITEM_USAGE("Use on Gear with sockets to Insert\nIf same rune is already socketed, rerolls it\n\nAll Runewords are shown in the Wiki.\nUse to view craftable Runewords."),
    STONE_REPAIRE_DURABILITY("Repairs %1$s durability."),
    SOUL_CLEANER_USAGE_AND_WARNING("Click on items to remove their soul.\nThe item remains but the stats will be deleted."),
    SOCKET_EXTRACTOR_USAGE("Click on gear to extract a gem."),
    Enchanted("Enchanted(%1$s/10): "),
    OUTCOME_TIP("%1$s, Chance: %2$s", "for currency tooltips"),
    Exp("Bonus Exp: %1$s%%"),
    Loot("Bonus Loot: %1$s%%"),
    PREFIX_STATS("Prefix Stats: "),
    AFFIX_STATS("Affix Stats: "),
    COR_STATS("Corruption Stats: "),
    UNIQUE_STATS("Unique Stats: "),
    JEWEL_STATS("Jewel Stats: "),
    SUFFIX_STATS("Suffix Stats: "),
    MAP_LEAGUE_SPAWN("Can Spawn: ");

    private String localization = "";

    private String localeContext = null;

    Itemtips(String str) {
        this.localization = str;
    }

    Itemtips(String localization, String localeContext) {
        this.localization = localization;
        this.localeContext = localeContext;
    }

    @Override
    public IAutoLocName.AutoLocGroup locNameGroup() {
        return AutoLocGroup.Misc;
    }

    @Override
    public String locNameLangFileGUID() {
        return SlashRef.MODID + ".item_tips." + GUID();
    }

    @Override
    public String locNameForLangFile() {
        return localization;
    }

    @Override
    public String additionLocInformation() {
        return localeContext;
    }

    @Override
    public String GUID() {
        return this.name()
                .toLowerCase(Locale.ROOT);
    }

}
