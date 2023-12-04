package com.robertx22.age_of_exile.uncommon.localization;

import com.robertx22.age_of_exile.mmorpg.SlashRef;
import com.robertx22.age_of_exile.uncommon.interfaces.IAutoLocName;
import net.minecraft.ChatFormatting;

import java.util.Locale;

// to use a "%" here, do %% or '%'
public enum Words implements IAutoLocName {
    TAGS("Tags"),
    ITEM_TYPE("Item Type: "),
    SPELL_STATS("Spell Stats:"),
    CASTED_TIMES_CHANNEL("Casted %1$s times during channel."),
    INSTANT_CAST("Instant Cast"),
    CAPPED_TO_WEP_DMG(" (" + "Capped to %1$s%% of Wep-Dmg)"),

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
    WEAPON("Weapon:"),
    ARMOR("Armor:"),
    JEWERLY("Jewelry:"),
    PROFESSIONS("Professions"),
    POTION("Potion"),
    SUPPGEM("Support Gem"),
    AURA("Aura"),
    MAJOR("Major"),
    MEAL("Meal"),
    WIKI("Wiki"),
    LESSER("Lesser"),
    MEDIUM("Medium"),
    GREATER("Greater"),
    Easy("Easy"),
    Medium("Medium"),
    Hard("Hard"),
    VERY_HARD("Very Hard"),

    FAVOR_REGEN_PER_HOUR(ChatFormatting.LIGHT_PURPLE + "Regenerates %1$s per Hour"),
    FAVOR_PER_CHEST(ChatFormatting.GREEN + "Gain %1$s per Chest Looted"),
    FAVOR_PER_DEATH(ChatFormatting.RED + "You Lose %1$s on Death"),

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
    AscClasses("Classes"),
    Salvaging("Salvaging"),
    SkillGem("Skills"),
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

    Unsalvagable("Unsalvageable"),

    UsableOn("Usable On"),

    Prefix_Space(" "),
    Suffix_Space(" "),
    TM_Prefix_Space(" "),
    TM_Suffix_Space(" "),
    Tooltips_Rarity("Rarity: "),
    Tooltips_Item_Type("Item Type: "),
    Tooltips_Tier("Tier: "),
    Tooltips_Item_Tier("Item Tier: "),
    Tooltips_Level("Level: "),
    Use_Tip("[Drag onto gear to use]"),
    Stat_Req("%1$s Min: "),
    Potential("Potential: %1$s%%"),
    Quality("Quality: %1$s%%"),
    Energy_Cost_Per_Mob("Energy Cost: %1$s + %2$s Per mob, x %3$s Dmg"),
    Durability("Durability: "),
    Unbreakable("Unbreakable"),
    Restores("Restores: %1$s%%"),
    Favor_In_GUI(" Favor"),
    Current_Favor("Current: %1$s"),
    Loot_Exp_Multiplier("Loot/Exp Multiplier: %1$sx");


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
