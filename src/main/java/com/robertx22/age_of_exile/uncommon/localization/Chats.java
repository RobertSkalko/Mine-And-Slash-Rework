package com.robertx22.age_of_exile.uncommon.localization;

import com.robertx22.age_of_exile.mmorpg.SlashRef;
import com.robertx22.age_of_exile.uncommon.interfaces.IAutoLocName;

import java.util.Locale;

public enum Chats implements IAutoLocName {
    NOT_MEET_MAP_REQ_FIRST_LINE("You haven't met the map requirement: "),
    NOT_MEET_MAP_REQ("%1$s: %2$s/%3$s"),
    PROPHECIES_GUIDE("1) Kill Monsters while Cursed to gain Prophecy Coins\n\n2) You can Buy Rewards in this GUI\n\n3) The More curses you accept, the more coins you will earn.\n\nYour Coins and Prophecies reset on entering another map."),

    PROPHECY_MAP_DEATHCURRENCY("You lost %1$s Prophecy Coins"),
    EMPTY_MAP_FORCED_TP("The Map you're currently inside was deleted. This can happen if you entered another player's map and they started a new one."),
    TP_TO_DUNGEON_MAPNAME("Teleported to the [%1$s] Dungeon"),
    ITEM_CANT_CORRUPT_TWICE("This item is already corrupted."),
    CORRUPT_CANT_BE_MODIFIED("Corrupted Items can't be modified. They can only be sockeded with Gems etc."),
    ALT_TO_SHOW_OTHER_SPELL("Press [Alt] to show the other Spell"),
    MUST_BE_IN_MAP_TO_ACCEPT_PROPHECY("You must be inside the same Map Dungeon to accept Prophecy Rewards"),
    NOT_ENOUGH_FAVOR_TO_BUY_PROPHECY("Not enough Currency to buy the Prophecy. You can gain more Currency by killing dungeon mobs while under Prophecy Debuffs."),
    RESISTS_TOO_LOW_FOR_MAP("You lack Resistances to Open/Enter this Map."),
    MAX_MAP_RARITY_FOR_LVL("This map is at maximum possible rarity for it's level."),
    MAX_MAP_RARITY("This map is at maximum possible rarity."),
    NO_MORE_LIVES_REMAINING("You have no more lives remaining."),
    MAP_DEATH_LIVES_LOSS("You have died, you can only enter the map %1$s more times."),
    ALREADY_CASTING("Already Casting a Spell"),
    PROPHECY_ALTAR_MSG("The altar reacted to your touch! (You can now open your prophecies GUI and choose to a curse to apply to yourself)"),
    SPELL_IS_ON_CD("Spell is on cooldown"),
    PLAYER_TOUCHED_ALTAR_AGAIN("You've used this altar before"),
    PROPHECY_PLEASE_SPEND("You already have a choice. (Open your prophecy gui from the main hub and pick an affix)"),
    USING_TEST_SPELL("You are trying to use a test spell outside test environment"),
    NO_CHARGES("You lack spell charges."),
    NOT_IN_THIS_DIMENSION("You can't cast this spell in this dimension."),
    NO_MANA("Not enough Resources to cast."),
    WRONG_CASTING_WEAPON("That weapon type Can't cast this spell"),
    NOT_MNS_WEAPON("You need a Mine and Slash Weapon (Imbued with Stats) to Cast spells."),
    CAST_FAILED("[Cast Fail]: "),
    STACKS_DONT_MULTIPLY_STATS("[Buff Stacks Don't multiply Stats]"),
    SOULLESS_GEAR_INFO("This Gear Lacks a Soul\nSouls contain Mine and Slash Stats\nSouls Are Crafted with Stations or Drop from Mobs"),

    CHARACTER_LOAD_INFO("CLICK to Load this Character\n\nCharacter Feature saves:\n- Your level and exp\n- Your spells, stats, talents, hotbar setup\n\nIt does not save your gear, gems jewels etc.\n\nEach Character starts from Level 1"),
    NO_DUPLICATE_AURA("You can't equip duplicate aura gems."),
    YOU_LACK_JEWEL_SLOTS("You lack jewel slots! You gain these typically from the Talent tree. You can use the search bar in the Talents screen to find them."),
    LACK_AURA_CAPACITY("You lack the Augment capacity to equip all these Augments."),

    CANT_EQUIP_THAT_MANY_SUPPORTS("You can't equip that many Support Gems! You can increase the number of slots by leveling the skill and by increasing your player level!."),
    CANT_USE_MULTIPLE_SAME_SUPPORTS("You can't use Multiples of those Support Gems."),

    NONE_MAGE("Non Mage weapons can use."),
    CREATE_NEW_CHARACTER("Create New Character\n\nName Must be Unique"),
    ANY_ITEM("Any weapon can use."),
    REQUIRE_MELEE("Requires Melee weapon to use."),
    REQUIRE_MAGE("Requires Mage Weapon to use."),
    REQUIRE_RANGED("Requires Ranged Weapon to use."),
    INFUSES_STATS("Infuses stats into blank gear."),
    RIGHT_CLICK_TO_GEN_ITEM("You can also right-click to generate an item."),

    REQ_LVL("Required Level: %1$s"),
    ONLY_ONE_OF_TYPE("Can only have one Perk of this type:"),
    RIGHT_CLICK_OPEN("Right Click to Open"),

    MAX_STACKS("Maximum Stacks: %1$s"),
    NO_ENCHANT_ON_ITEM("There's no Mns Enchant on this item"),

    ITEM_NON_NBT("This is an empty item, without any NBT/Random Stats, this means you got it through creative mode or a /give command. If you want to test the mod, you can use the /mine_and_slash give command which creates items with working NBT."),

    NOT_FAMILY("Can't be applied to an item of that slot family."),
    ENCHANT_UPGRADE_RARITY("Enchants must be applied consecutively. A common enchant has to come before an uncommon enchant may be applied."),
    THIS_ITEM_CANT_BE_USED_MORE_THAN_X_TIMES("This item has already reached maximum uses: (%1$s)"),
    NOT_GEAR("This is not a gear item."),
    CANT_RUNE_THIS_UNIQUE("Can't insert Runes into this Unique"),
    RUNE_IS_ALREADY_MAXED("The inserted Rune is already at 100%. You can't to upgrade it any more."),
    GEAR_NO_POTENTIAL("Gear Has no remaining potential and Can't be crafted any further. Note, you can still use currencies that cost 0 potential to craft, like gems, runes etc."),
    NOT_MAP("This is not a map item."),
    NOT_CORRECT_TIER_LEVEL("You are trying to use an in item with a different level range than possible."),
    CANT_GO_ABOVE_MAX_LEVEL("You can't upgrade an item past maximum level: %1$s"),
    NEEDS_EMPTY_OR_RUNE("Requires an empty socket, or a rune inside the item. If the item has a rune, it will be re-rolled."),
    NOT_JEWEL("This is not a jewel item."),
    NOT_TOOL("This is not a tool item, or the tool has no stats yet."),
    NEED_EMPTY_SOCKET("Requires an empty socket"),
    CANT_HAVE_MORE_GEMS_THAN_RUNES_IN_RUNEWORD("Runeword Items Can not have more Gems than Runes socketed"),
    CANT_HAVE_MORE_RUNES_THAN_GEMS_IN_NON_RUNED("Normal(Non-Runed) Items Can not have more Runes than Gems socketed"),
    ALREADY_MAX_SOCKETS("Item already has maximum possible sockets."),
    ALREADY_MAX_LINKS("Item already has maximum possible links."),
    NEEDS_AN_AFFIX("Item needs an affix."),
    NOT_SKILLGEM("This is not a skill gem."),
    BE_UNIQUE("The item must be of unique rarity."),
    DESTROYS_OUTPUT("When Placed in a Profession Station with other inputs/materials, it will make all recipes produced give 3X the EXP but it will destroy the output."),
    CANT_GO_ABOVE("Can't upgrade past %1$s"),
    AWARDED_TALENTS("You have been rewarded %1$s Talent Points."),
    FAILED_TO_AWARD_TALENTS("Failed to give more talent points, you have reached the cap. The maximum allowed bonus talent points is controlled by the config file."),

    NEED_PEARL("You need to have at least one %1$s in your inventory to enter."),
    PROF_MAT_DROPGUIDE("Mine and Slash Profession Material"),
    PROF_MAT_DROPGUIDE_COMMON("Common Drop"),
    PROF_MAT_DROPGUIDE_RARE("Rare Drop"),
    CAUGHT_SOMETHING("You caught something!"),
    TOOL_ADD_STAT("%1$s has been Upgraded to %2$s!"),
    TOOL_LEVEL_UP("%1$s has reached level %2$s!"),
    PROFESSION_LEVEL_UP("Your %1$s level has increased to level %2$s!"),
    TOO_MANY_BUFFS("You can't have that many buffs."),
    NEW_DAY("A new day has arrived! Profession drop rates have been boosted for the following items:"),
    TOO_LOW_LEVEL("Your level is too low."),
    CANT_SAME_BUFF("You already have a buff of that type."),
    NOT_OWNER("You are not the owner of this Station. Stations can NOT be shared, you have to craft your own."),

    FAVOR_UP("As a result of your achievements, your Favor has risen to %1$s!"),
    FAVOR_DOWN("As a result of death, your Favor has fallen to %1$s, Remember this disgrace."),


    DEATH_EXP_LOSS_MSG("As a result of dying, you have lost %1$s Exp and received %2$s Exp debt."),
    FAVOR_DEATH_MSG("Your Favor has fallen by %1$s Points."),

    Dev_tools_enabled_contact_the_author("Devs tools enabled, if you see this please contact the author of Mine and Slash [robertx22], he forgot to disable them!"),
    Not_enough_experience("Not enough experience"),
    Can_not_go_over_maximum_level("Can not go over maximum level"),
    OPEN_LOOT_CHEST("Right-click to open Loot Chest!"),
    VINES_SHRINK("The Vines appear to shrink, for now..."),
    PROF_RECIPE_NOT_FOUND("Recipe not found. Most recipes need multiple of the same item, so double-check the item numbers."),
    PROF_OUTPUT_SLOT_NOT_EMPTY("Needs at least 1 empty Output slot."),
    PROF_RECIPE_LEVEL_NOT_ENOUGH("This Recipe requires %1$s level %2$s. But your profession level is only %3$s"),
    GEAR_DROP("You do not meet the requirements of that item."),
    COMMAND_BLOCK_UNAVALIABLE("Command blocks are disabled, this will stop you from playing Mine and slash Dungeons!"),
    HOW_TO_ENABLE_COMMAND_BLOCK("To enable go to your server.properties file and put enable-command-block as true."),
    WEAPON_REQ_NOT_MET("Weapon requirements not met"),
    ENEMY_TOO_CLOSE("Can't do that. There are enemies nearby."),
    GEM_SOCKETED("Gem Socketed"),
    LEVEL_UP_MESSAGE_UP("Leveled Up!"),
    LEVEL_UP_MESSAGE_DOWN("%1$s Level: %2$s > %3$s!");

    private String localization = "";

    Chats(String str) {
        this.localization = str;
    }

    @Override
    public IAutoLocName.AutoLocGroup locNameGroup() {
        return AutoLocGroup.Chat_Messages;
    }

    @Override
    public String locNameLangFileGUID() {
        return SlashRef.MODID + ".chat." + GUID();
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
