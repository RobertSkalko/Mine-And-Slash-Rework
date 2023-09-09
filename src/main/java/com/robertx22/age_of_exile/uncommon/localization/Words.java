package com.robertx22.age_of_exile.uncommon.localization;

import com.robertx22.age_of_exile.mmorpg.SlashRef;
import com.robertx22.age_of_exile.uncommon.interfaces.IAutoLocName;
import net.minecraft.ChatFormatting;

import java.util.Locale;

public enum Words implements IAutoLocName {
    PROFESSIONS("Professions"),
    POTION("Potion"),
    MEAL("Meal"),
    LESSER("Lesser"),
    MEDIUM("Medium"),
    GREATER("Greater"),
    Easy("Easy"),
    Medium("Medium"),
    Hard("Hard"),
    VERY_HARD("Very Hard"),

    FAVOR_REGEN_PER_HOUR(ChatFormatting.LIGHT_PURPLE + "Regenerates %1$s per Hour"),
    FAVOR_PER_CHEST(ChatFormatting.GREEN + "Gain %1$s on Looting a Chest"),
    FAVOR_PER_DEATH(ChatFormatting.RED + "You lose %1$s on Death"),

    AddSocket("Adds a socket"),
    Nothing("Nothing"),
    Enchanted("Enchanted:"),

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
    AscClasses("Ascension"),
    Salvaging("Salvaging"),
    SkillGem("Skill Gem"),
    Gear("Gear"),
    All("All"),
    Rune("Rune"),
    LevelRewards("Level Rewards"),
    Gem("Gem"),
    Miracle("Miracle"),
    Oblivion("Oblivion"),
    Beast("Beast"),
    Golem("Golem"),
    Spirit("Spirit"),
    Rage("Rage"),

    Beads("Beads"),
    Charm("Charm"),
    Locket("Locket"),
    //
    Band("Band"),
    Eye("Eye"),
    Loop("Loop"),
    //
    Crown("Crown"),
    Circlet("Circlet"),
    Horn("Horn"),
    //
    Cloak("Cloak"),
    Coat("Coat"),
    Mantle("Mantle"),
    Shell("Shell"),
    //
    Aegis("Aegis"),
    Barrier("Barrier"),
    Guard("Guard"),
    Tower("Tower"),
    Road("Road"),
    Hoof("Hoof"),
    Dash("Dash"),
    //

    Bane("Bane"),
    Bite("Bite"),
    Wind("Wind"),
    Star("Star"),
    Splitter("Splitter"),
    //
    Legguards("Legguards"),
    Leggings("Leggings"),
    Britches("Britches"),
    Legwraps("Legwraps"),
    //
    Creation("Creation"),
    Crest("Crest"),
    Keep("Keep"),
    Ward("Ward"),
    Refuge("Refuge"),

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
    CantGetMoreAffixes("Can't get more affixes."),

    HasEmptySockets("Has Empty Sockets"),
    IsNotCorrupted("Is not corrupted"),

    NoDuplicateSockets("No Duplicate Sockets"),

    AllowedOn("Allowed on"),

    Unique_Gear("Unique Gear"),

    Normal_Gear("Normal Gear"),

    Requirements("Requirements"),


    Jewel("Jewel"),


    Loot("Loot"), Exp("Exp"),

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

    Unsalvagable("Unsalvagable"),

    UsableOn("Usable on");

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
