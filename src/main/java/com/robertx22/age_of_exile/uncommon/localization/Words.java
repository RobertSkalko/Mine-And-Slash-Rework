package com.robertx22.age_of_exile.uncommon.localization;

import com.robertx22.age_of_exile.mmorpg.SlashRef;
import com.robertx22.age_of_exile.uncommon.interfaces.IAutoLocName;

import java.util.Locale;

// to use a "%" here, do %% or '%'
public enum Words implements IAutoLocName {
    TAGS("Tags: "),

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
    NOT_AFFECTED_BY_POTENT("Not affected by potential."),
    STATUS_EFFECT("Status Effect"),
    PET_BASIC("Pet Basic Attack:"),
    PER_STACK(" (per stack): "),
    WEAPON("On Weapon: "),
    ARMOR("On Armor: "),
    JEWERLY("On Jewelry: "),
    PROFESSIONS("Professions"),
    SUPPGEM("Support Gem"),
    AURA("Aura"),
    MAJOR("Major"),
    WIKI("Wiki"),
    LESSER("Lesser"),
    MEDIUM("Medium"),
    GREATER("Greater"),
    Easy("Easy"),
    Medium("Medium"),
    Hard("Hard"),
    VERY_HARD("Very Hard"),

    AddSocket("Adds a socket"),
    Nothing("Nothing"),

    UpgradeAffix("Upgrades an affix"),
    UpgradeEnchant("Upgrades or Rerolls the Enchant"),
    UpgradeQuality("Upgrade Quality"),
    DowngradeAffix("Downgrades an affix"),
    DeletesAllAffixes("Deletes all affixes"),
    DestroysItem("DESTROYS the Item"),
    UpgradesUniqueStats("Adds 10% to unique stats"),

    Soul("Soul"),
    DungeonKey("Dungeon Key"),
    Corrupted("Corrupted"),
    AscClasses("Classes"),
    Salvaging("Salvaging"),
    SkillGem("Skills"),
    Gear("Gear"),
    All("All"),
    Rune("Rune"),
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

    AllowedOn("Allowed On"),

    Unique_Gear("Unique Gear"),

    Normal_Gear("Normal Gear"),

    Requirements("Requirements"),


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

    Character("Character"),
    Stats("Stats"),

    Tier("Tier"),

    Unsalvagable("Unsalvageable"),

    UsableOn("Usable On"),

    Energy_Cost_Per_Mob("Energy Cost: %1$s + %2$s Per mob, x %3$s Dmg"),
    CAPPED_TO_LVL("[Capped to LVL]"),
    MULTIPLY_STAT_INCREASED("Increased"),
    MULTIPLY_STAT_REDUCED("Reduced"),
    MULTIPLICATIVE_DAMAGE_MORE("More"),
    MULTIPLICATIVE_DAMAGE_LESS("Less"),
    PERCENT_INCREASED_STAT("Extra "),
    EMPTY_BOX("Box"),
    LEVEL_UP_TYPE_PLAYER("Player"),
    SPELL_TAG_SPARATOR(", ");


    private String localization = "";

    Words(String str) {
        this.localization = str;

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
    public String GUID() {
        return this.name()
                .toLowerCase(Locale.ROOT);
    }
}
