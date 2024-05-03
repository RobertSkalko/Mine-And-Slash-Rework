package com.robertx22.age_of_exile.uncommon.localization;

import com.robertx22.age_of_exile.mmorpg.SlashRef;
import com.robertx22.age_of_exile.uncommon.interfaces.IAutoLocName;

import java.util.Locale;

// to use a "%" here, do %% or '%'
public enum Words implements IAutoLocName {
    ENABLED("Enabled"),
    DISABLED("Disabled"),

    CAST_FAIL_MSGS("Spell Casting Failure Messages\n\nWill print helpful messages when you fail to cast a spell\nParticularly Helpful for New Players"),
    AUTOMATIC_PVE("Auto PVE\n\nPlayers who enable this are considered Teamed up and Allies\n- Must be in Nearby (Radius set by config)"),

    WHILE_UNDER_AURA("While Under Effect of %1$s:"),
    CURSE_OFFERS("Curse Offers:"),
    REWARD_OFFERS("Reward Offers:"),
    ACCEPTED_CURSES("Accepted Curses:"),
    UBER_FRAG("Uber Fragment"),

    LOAD("Load"),
    CLAIM("Claim"),
    ITEM("Item"),
    CURRENT_PROPHECY_CURRENCY("Current Prophecy Coins: %1$s"),
    AVG_LVL("Average LVL: %1$s"),
    AVG_TIER("Average Tier: %1$s"),
    X_ITEMS("Reward Amount: %1$s"),
    COSTS_FAVOR("Costs %1$s Prophecy Coins"),
    PROPHECIES("Prophecies"),
    CURRENTLY_SELECTED("Currently Selected"),
    Configs("Configs"),
    POSSIBLE_DROS("Possible Gear Drops:"),
    CAN_UPGRADE_MAP_RARITY("Can Upgrade Map Rarity\nThis Depends on Map level"),
    LASTS_SEC("Lasts %1$ss"),
    MOD_NAME("Mine and Slash"),
    TAGS("Tags: "),
    CAPPED_BECAUSE_PLAYER_LEVEL(" - Next Slot Unlocks at Player Level %1$s"),
    IMPLICIT_STATS("Implicit Stats: "),
    ON_SLOTS("Slots: "),
    SHIFT_TO_SHOW_EFFECT("Shift to Show Status Effect info."),
    ONLY_SHOW_ALLOCATED("Only Show Allocated Talents, Toggle ON/OFF"),

    SPELL_STATS("Spell Stats:"),
    CASTED_TIMES_CHANNEL("Casted %1$s times during channel."),
    INSTANT_CAST("Instant Cast"),
    CAPPED_TO_WEP_DMG(" (Capped to %1$s%% of Wep-Dmg)"),

    CAST_TIME("Cast Time: %1$ss"),
    MANA_COST("Mana Cost: %1$s"),
    ENE_COST("Energy Cost: %1$s"),
    COOLDOWN("Cooldown: %1$ss"),
    MAX_CHARGES("Max Charges: %1$s"),
    CHARGE_REGEN("Charge Regen: %1$ss"),

    GAME_CHANGER("Game Changer Talent"),
    POTENTIAL_COST("Potential Cost: %1$s"),
    NOT_A_POTENTIAL_CONSUMER("Does not consume potential when used"),
    STATUS_EFFECT("Status Effect"),
    PET_BASIC("Pet Basic Attack:"),
    PER_STACK(" (per stack): "),
    WEAPON("On Weapon: "),
    ARMOR("On Armor: "),
    JEWERLY("On Jewelry: "),
    PROFESSIONS("Professions"),
    SUPPGEM("Support Gem"),
    AURA("Augment"),
    MAJOR("Major"),
    WIKI("Library"),
    LESSER("Lesser"),
    MEDIUM("Medium"),
    GREATER("Greater"),
    Easy("Easy"),
    Medium("Medium"),
    Hard("Hard"),
    VERY_HARD("Very Hard"),

    AddSocket("Adds a socket"),
    AddPotential("Add Potential"),
    Nothing("Nothing"),

    UpgradeAffix("Upgrades an affix"),
    RerollsAffix("Rerolls an Affix"),
    UpgradeEnchant("Upgrades or Rerolls the Enchant"),
    UpgradeQuality("Upgrade Quality"),
    CHAOS_STAT_SUCCESS("Upgrades the Item randomly."),
    DowngradeAffix("Downgrades an affix"),
    DeletesAllAffixes("Deletes all affixes"),
    DestroysItem("DESTROYS the Item"),
    UpgradesUniqueStats("Adds 10% to unique stats"),

    Soul("Soul"),
    DungeonKey("Dungeon Key"),
    Corrupted("Corrupted"),
    CorruptsItemHarvest("Turns the Item Corrupted (no benefits)"),
    AscClasses("Classes"),
    Salvaging("Salvaging"),
    SkillGem("Skill Gem"),
    Hotbar("Spell Hotbar"),
    Gear("Gear"),
    All("All"),
    Rune("Rune"),
    Runeword("Runeword"),
    LevelRewards("Level Rewards"),
    Gem("Gem"),
    // name2

    ClickToOpen("Click to Open"),

    Crate("Crate"),

    Talents("Talents"),

    PressAltForStatInfo("Press Alt for Stat Desc"),

    MustBeGear("Must be a Gear Item"),

    Broken("Broken"),


    Decreased("Decreased"),

    Increased("Increased"),

    PressShiftForRequirements("Press Shift for Requirements"),

    isUnique("Is Unique"),

    hasUniqueStats("Has Unique stats"),

    hasSet("Has Set"),

    isNotUnique("Is Not Unique"),

    IsCommon("Is Common Rarity"),

    HasHigherRarity("Has higher rarity"),
    CantGetMoreAffixes("Can't have more affixes."),

    HasEmptySockets("Has Empty Sockets"),
    IsNotCorrupted("Is not corrupted"),

    NoDuplicateSockets("No Duplicate Sockets"),

    AllowedOn("Allowed On: "),

    Unique_Gear("Unique Gear"),

    Normal_Gear("Normal Gear"),

    Requirements("Requirements"),
    AreaContains("Area Contains an Uber Boss Portal"),


    Jewel("Jewel"),
    Jewels("Jewels"),


    Loot("Loot"),
    Exp("Exp"),

    Currency("Currency"),
    Backpack("Backpack"),

    Gears("Gears"),

    Level("Level"),

    Map("Map"),

    Maps("Maps"),

    Affixes_Affecting_All("All"),

    Mob_Affixes("Mob Affixes"),

    None("None"),

    Player_Affixes("Player Affixes"),
    ProphecyPlayerAffix("Player Gains Debuff:"),
    ProphecyPlayerAffixTaken("Currently Taken Debuff:"),
    ProphecyPlayerAffixInfo("Your rate of Prophecy Coins gain will increase."),

    Character("Character"),
    Characters("Characters"),
    Stats("Stats"),

    Tier("Tier"),

    Unsalvagable("Unsalvageable"),

    UsableOn("Usable On: "),

    Energy_Cost_Per_Mob("Energy Cost: %1$s + %2$s Per mob, x %3$s Dmg"),
    CAPPED_TO_LVL("[Capped to LVL]"),
    MULTIPLY_STAT_INCREASED("Increased"),
    MULTIPLY_STAT_REDUCED("Reduced"),
    MULTIPLICATIVE_DAMAGE_MORE("More"),
    MULTIPLICATIVE_DAMAGE_LESS("Less"),
    INCREASE_PERCENT_STAT("Extra ", "use for stat like \"(Extra) (attack speed)\", this is different with multiply stat prefix."),
    EMPTY_BOX("Box"),
    LEVEL_UP_TYPE_PLAYER("Player");


    private String localization = "";

    private String localeContext = null;

    Words(String str) {
        this.localization = str;

    }

    Words(String localization, String localeContext) {
        this.localization = localization;
        this.localeContext = localeContext;
    }

    @Override
    public AutoLocGroup locNameGroup() {
        return AutoLocGroup.Words;
    }

    @Override
    public String locNameLangFileGUID() {
        return SlashRef.MODID + ".word." + GUID();
    }

    @Override
    public String locNameForLangFile() {
        return localization;
    }

    @Override
    public String additionLocInformation() {
        return this.localeContext;
    }

    @Override
    public String GUID() {
        return this.name()
                .toLowerCase(Locale.ROOT);
    }


}
