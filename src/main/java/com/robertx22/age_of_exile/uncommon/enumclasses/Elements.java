package com.robertx22.age_of_exile.uncommon.enumclasses;

import net.minecraft.ChatFormatting;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum Elements {

    Physical(false, true, "Physical", ChatFormatting.GOLD, "physical", "\u2726"),
    Fire(true, true, "Fire", ChatFormatting.RED, "fire", "\u2600"),
    Cold(true, true, "Cold", ChatFormatting.AQUA, "water", "\u2749"),
    Lightning(true, true, "Lightning", ChatFormatting.YELLOW, "lightning", "\u2749"),
    Chaos(false, true, "Chaos", ChatFormatting.DARK_PURPLE, "chaos", "\u273F"),

    Elemental(true, false, "Elemental", ChatFormatting.LIGHT_PURPLE, "elemental", "\u269C"),
    All(false, false, "All", ChatFormatting.LIGHT_PURPLE, "all", "\u273F");

    public boolean isSingle = true;
    public boolean isElemental = false;

    Elements(boolean isElemental, boolean isSingle, String dmgname, ChatFormatting format, String guidname, String icon) {

        this.isElemental = isElemental;
        this.isSingle = isSingle;
        this.dmgName = dmgname;
        this.format = format;
        this.guidName = guidname;
        this.icon = icon;
    }

    public String dmgName;
    public String guidName;
    public String icon;

    public ChatFormatting format;

    public String getIconNameDmg() {
        return getIconNameFormat(dmgName) + " Damage";
    }

    public String getIconNameFormat() {
        return getIconNameFormat(dmgName);
    }

    public String getIconNameFormat(String str) {
        return this.format + this.icon + " " + str + ChatFormatting.GRAY;
    }

    public boolean isPhysical() {
        return this == Physical;
    }

    public boolean isFire() {
        return this == Fire;
    }

    public boolean isElemental() {
        return this != Physical;
    }

    public boolean isWater() {
        return this == Cold;
    }

    public boolean IsChaos() {
        return this == Chaos;
    }

    private static List<Elements> allIncludingPhys = Arrays.stream(Elements.values()).filter(x -> x.isSingle).collect(Collectors.toList());
    private static List<Elements> allExcludingPhys = Arrays.stream(Elements.values()).filter(x -> !x.isPhysical()).collect(Collectors.toList());
    private static List<Elements> allSingleElementals = Arrays.stream(Elements.values()).filter(x -> x.isSingle && x.isElemental).collect(Collectors.toList());

    public static List<Elements> getAllSingleElementals() {
        return allSingleElementals;
    }

    public static List<Elements> getAllSingleIncludingPhysical() {
        return allIncludingPhys;
    }

    public static List<Elements> getEverythingBesidesPhysical() {
        return allExcludingPhys;
    }

    public boolean elementsMatch(Elements other) {
        if (other == null) {
            return false;
        }

        if (this == other) {
            return true;
        }

        if (other == All || this == All) {
            return true;
        }

        if (other == Elements.Elemental) {
            return this.isElemental;
        }
        if (this == Elements.Elemental) {
            return other.isElemental;
        }

        return false;
    }

}
