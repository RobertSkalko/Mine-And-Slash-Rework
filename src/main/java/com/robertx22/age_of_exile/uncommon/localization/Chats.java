package com.robertx22.age_of_exile.uncommon.localization;

import com.robertx22.age_of_exile.mmorpg.SlashRef;
import com.robertx22.age_of_exile.uncommon.interfaces.IAutoLocName;
import net.minecraft.ChatFormatting;

import java.util.Locale;

public enum Chats implements IAutoLocName {
    ITEM_NON_NBT("This is an empty item, without any NBT/Random Stats, this means you got it through creative mode or a /give command. If you want to test the mod, you can use the /mine_and_slash give command which creates items with working NBT."),

    NOT_FAMILY("Can't be applied to an item of that slot family."),
    ENCHANT_UPGRADE_RARITY("Enchants must be applied consecutively. A common enchant has to come before an uncommon enchant may be applied."),
    THIS_ITEM_CANT_BE_USED_MORE_THAN_X_TIMES("This item has already reached maximum uses: (%1$s)"),
    NOT_GEAR("This is not a gear item."),
    NOT_CORRECT_TIER_LEVEL("You are trying to use an in item with a different level range than possible."),
    CANT_GO_ABOVE_MAX_LEVEL("You can't upgrade an item past maximum level: %1$s"),
    NEEDS_EMPTY_OR_RUNE("Requires an empty socket, or a rune inside the item. If the item has a rune, it will be re-rolled."),
    NOT_JEWEL("This is not a jewel item."),
    NOT_TOOL("This is not a tool item, or the tool has no stats yet."),
    NEED_EMPTY_SOCKET("Requires an empty socket"),
    ALREADY_MAX_SOCKETS("Item already has maximum possible sockets."),
    ALREADY_MAX_LINKS("Item already has maximum possible links."),
    NEEDS_AN_AFFIX("Item needs an affix."),
    NOT_SKILLGEM("This is not a skill gem."),
    BE_UNIQUE("The item must be of unique rarity."),
    DESTROYS_OUTPUT("When Placed in a Profession Station with other inputs/materials, it will make all recipes produced give 3X the EXP but it will destroy the output."),
    CANT_GO_ABOVE("Can't upgrade past %1$s"),
    AWARDED_TALENTS("You have been rewarded %1$s Talent Points."),

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
    NOT_OWNER(ChatFormatting.RED + "You are not the owner of this Station."),

    FAVOR_UP(ChatFormatting.GREEN + "As a result of your achievements, your Favor has risen to %1$s!"),
    FAVOR_DOWN(ChatFormatting.RED + "As a result of death, your Favor has fallen to %1$s. Remember this disgrace."),


    DEATH_EXP_LOSS_MSG("As a result of dying, you have lost %1$s Exp and received %2$s Exp debt."),
    FAVOR_DEATH_MSG("Your Favor has fallen by %1$s Points."),

    Dev_tools_enabled_contact_the_author("Devs tools enabled, if you see this please contact the author of Mine and Slash [robertx22], he forgot to disable them!"),
    Not_enough_experience("Not enough experience"),
    Can_not_go_over_maximum_level("Can not go over maximum level");

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
