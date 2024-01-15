package com.robertx22.age_of_exile.uncommon.localization;

import com.robertx22.age_of_exile.mmorpg.SlashRef;
import com.robertx22.age_of_exile.uncommon.interfaces.IAutoLocName;

import java.util.Locale;

public enum Itemtips implements IAutoLocName {
    //add "tip" at the end to avoid same variable call
    //you can use \n in here, just remember use the splitLongText() to process it.
    //a special method splitLongText() in TooltipsUtils.java can split the sentence into different line by replacing the "\n", achieving a actual line break function.
    RARITY_TIP("Rarity: "),
    ITEM_TYPE("Item Type: "),
    TIER_TIP("Tier: "),
    ITEM_TIER_TIP("Item Tier: "),
    LEVEL_TIP("Level: "),
    USE_TIP("[Drag onto gear to use]"),
    Stat_Req("%1$s Min: "),
    POTENTIAL("Potential: %1$s%%"),
    QUALITY("Quality: %1$s%%"),
    Durability("Durability: "),
    Unbreakable("Unbreakable"),
    Restores("Restores: %1$s%%"),

    CHEST_CONTAINS("Contains: "),
    NEED_KEY("Needs Key: "),
    EMPTY_SOCKET("[Socket]"),

    AURA_RESERVATION("Aura Reservation: "),
    REMAINING_AURA_CAPACITY("Remaining Aura Capacity: "),
    SUPPORT_GEM_COST("Resource Cost Multiplier: %1$s%%"),
    SUPPORT_GEM_ONLY_ONE("Only One Allowed: "),
    SOUL_EXTRACTOR_TIP("Click on items to extract their soul.\nWorks only on that rarity.\nDeletes the Item in the process."),

    GEM_OPEN_GUI_TIP("Right Click to Open Gui"),
    SOUL_STONE_TIP("Right click to produce a common gear soul."),
    UNIQUE_JEWEL_USE("Right click to create"),
    TP_BACK_ITEM("Right click to return from the Map\nExits the Map Dimension."),
    RUNE_ITEM_USAGE("Use on Gear with sockets to Insert\nIf same rune is already socketed, rerolls it\n\nAll Runewords are shown in the Wiki.\nUse to view craftable Runewords."),
    STONE_REPAIRE_DURABILITY("Repairs %1$s durability."),
    SOUL_CLEANER_USAGE_AND_WARNING("Click on items to remove their soul.\nThe item remains but the stats will be deleted."),
    SOCKET_EXTRACTOR_USAGE("Click on gear to extract a gem."),
    Enchanted("Enchanted: "),
    OUTCOME_TIP("%1$s, Base Weight: %2$s", "for currency tooltips"),
    Exp("Exp: %1$s%%"),
    Loot("Loot: %1$s%%"),
    PREFIX_STATS("Prefix Stats: "),
    COR_STATS("Base Stats: "),
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
