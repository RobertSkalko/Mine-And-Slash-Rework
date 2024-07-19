package com.robertx22.age_of_exile.uncommon.localization;

import com.robertx22.age_of_exile.mmorpg.SlashRef;
import com.robertx22.age_of_exile.uncommon.interfaces.IAutoLocName;
import net.minecraft.ChatFormatting;

import java.util.Locale;

public enum Gui implements IAutoLocName {
    EXP_GAIN_PERCENT("+%1$s %2$s Exp (%3$s%%)"),
    RESTED_COMBAT_EXP("Rested Combat Exp: "),
    RESTED_PROF_EXP("Rested Prof. Exp: "),

    Favor_In_GUI(" Favor"),
    Current_Favor("Current: %1$s"),
    Loot_Exp_Multiplier("Loot Multiplier: %1$sx"),

    STATUS_BAR_LEVEL("Level %1$s %2$s%%"),
    MAINHUB_LEVEL("Level: "),
    STATS_POINTS("Points: "),
    TALENT_RESET_POINTS("Respecs: "),
    TALENT_POINTS("Points: "),
    PASSIVE_POINTS("Passive Points: "),
    SPELL_POINTS("Spell Points: "),
    STATUS_BAR_HUGER("H: %1$s S: %2$s"),
    STATS_INFLUENCE("For each point: "),
    STAT_TOTAL("Total: "),
    SALVAGE_TIP_ON("Auto Salvage for %1$s %2$s: On"),
    SALVAGE_TIP_OFF("Auto Salvage for %1$s %2$s: Off"),
    PROF_NAME("%1$s: %2$s"),
    SPELL_DAMAGE_PROPORTION("(%1$s%% of %2$s§7)"),
    PROF_LEVEL_AND_EXP("Level: %1$s EXP: %2$s/%3$s"),
    AVAILABLE_SUPPORT_SLOTS("Support Slots: %1$s/5 Available"),
    FAVOR_REGEN_PER_HOUR(ChatFormatting.LIGHT_PURPLE + "Regenerates %1$s " + ChatFormatting.LIGHT_PURPLE + "per Hour"),
    FAVOR_PER_CHEST(ChatFormatting.GREEN + "Gain %1$s per Chest Looted"),
    FAVOR_PER_DEATH(ChatFormatting.RED + "You Lose %1$s on Death"),
    COMMA_SEPARATOR(", ");


    private String localization = "";

    Gui(String str) {
        this.localization = str;

    }

    @Override
    public AutoLocGroup locNameGroup() {
        return AutoLocGroup.Gui;
    }

    @Override
    public String locNameLangFileGUID() {
        return SlashRef.MODID + ".gui." + GUID();
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
