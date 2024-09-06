package com.robertx22.mine_and_slash.uncommon.localization;

import com.robertx22.mine_and_slash.mmorpg.SlashRef;
import com.robertx22.mine_and_slash.uncommon.interfaces.IAutoLocName;

import java.util.Locale;

public enum Itemtips implements IAutoLocName {
    //add "tip" at the end to avoid same variable call
    //you can use \n in here, just remember use the splitLongText() to process it.
    //a special method splitLongText() in TooltipsUtils.java can split the sentence into different line by replacing the "\n", achieving a actual line break function.
    JEWEL_UPGRADE_2("[Click the Jewel with the Stone]"),
    JEWEL_UPGRADE_1("To Upgrade needs: %1$sx %2$s"),
    DROP_LEVEL_RANGE("Drops at Levels: %1$s"),
    LEVEL_RANGE("Levels: %1$s"),
    DROP_CHANCE("Drop Chance: %1$s"),
    PRIMARY_PROFESSION_MAT_INFO("All Professions have 2 primary material types:\n - Tier Material from Gathering professions\n - Rarity Material from Salvaging.\nInsert both to see Recipes!"),
    PRIMARY_PROFESSION_USE_JEI("You can Use JEI Mod Too"),
    BUFF_CONSUMABLE_TYPE("%1$s Consumable"),
    BUFF_CONSUMABLE_DURATION_MINUTES("Duration: %1$s minutes"),
    BUFF_CONSUMABLE_INFO("You can have One Consumable active per each type:\nElixirs, Meals and Seafoods."),
    DROP_CHANCE_EXTRA_INFO("Drop Chance assumes you're killing a mob of your level"),
    SOUL_LOCKED_TO_TYPE("Locked Armor type: %1$s"),
    PROF_MAT_LEVEL_RANGE_INFO("Gained when your profession level is:"),

    CHECK_GEAR_STATS_IN_SOUL("This soul has gear stat, you can press Shift to check out."),
    INFUSION_GEAR_LEVEL_RANGE("Gear level range: %1$s - %2$s"),
    SOUL_GENERATE_GEAR_LEVEL_RANGE("Generated Gear Level: %1$s - %2$s"),
    BUFF_TIP("Buff Stats: "),
    LOOT_CHEST_KEY_DESC("Used to unlock Loot chests obtained from the Fishing Profession."),
    SOUL_MODIFIER_TIP("Soul modifier can be applied to gear soul, which then generate \nonly the corresponding gear type when use."),
    DRAG_AND_DROP_TO_USE("[Drag and Drop on item to Use]"),
    DRAG_AND_DROP_TO_USE_DESC("- Hold the item in inventory, drag it towards the item you want to use it on, and click."),
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

    LEVEL_REQ("Player Level Min: %1$s"), // todo is this too long
    Stat_Req("%1$s Min: "),
    POTENTIAL("Potential: %1$s"),
    QUALITY("Quality: %1$s%%"),
    Durability("Durability: "),
    Unbreakable("Unbreakable"),
    RECIPE_MATERIAL("RECIPE MATERIAL:"),
    Restores("Restores: %1$s %2$s"),

    CHEST_CONTAINS("Contains: %1$s"),
    NEED_KEY("Needs %1$s "),
    EMPTY_SOCKET("[Socket]"),

    AURA_RESERVATION("Aura Reservation: "),
    REMAINING_AURA_CAPACITY("Remaining Augment Capacity: "),
    SUPPORT_GEM_COST("Resource Cost Multiplier: %1$s%%"),
    SUPPORT_GEM_ONLY_ONE("Only One Allowed: "),
    SUPPORT_GEM_EXPLANATION("Boost the power of the Spell it's socketed into."),
    AUGMENT_EXPLANATION("Enhances Player stats when equipped."),
    SOUL_EXTRACTOR_TIP("Click on items to extract their soul.\nDeletes the Item in the process."),

    GEM_OPEN_GUI_TIP("Right Click to Open Gui"),
    SOUL_STONE_TIP("Right click to produce a common gear soul."),
    UNIQUE_JEWEL_USE("Right click to create"),
    TP_BACK_ITEM("Right click to return from the Map\nExits the Map Dimension."),
    UBER_BOSS_MAP_TIP("Right click to Generate Map Item\nLeads to a Boss Fight"),
    UBER_BOSS_FRAG_TIP("Combine with More Fragments in the Crafting Table\nCreates a Map That contains an Uber Boss"),
    RUNE_ITEM_USAGE("Use on Gear with sockets to Insert\nIf same rune is already socketed, rerolls it\n\nAll Runewords are shown in the Wiki.\nUse to view craftable Runewords."),
    GEM_ITEM_USAGE("Use on Gear with sockets to Insert.\nCan also be Crafted"),
    STONE_REPAIRE_DURABILITY("Repairs %1$s durability."),
    NOT_A_RANDOM_MNS_DROP_CHECK_MODPACK("No normal Drop-rate, this item might be obtainable in other ways. Check your Modpack's guide."),
    SOUL_CLEANER_USAGE_AND_WARNING("Click on items to remove their soul.\nThe item remains but the stats will be deleted."),
    SOCKET_EXTRACTOR_USAGE("Extracts a Gem from the Item"),
    INFUSED("Infused(%1$s/10): "),
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
