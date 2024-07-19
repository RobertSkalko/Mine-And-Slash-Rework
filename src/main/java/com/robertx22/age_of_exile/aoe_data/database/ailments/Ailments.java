package com.robertx22.age_of_exile.aoe_data.database.ailments;

import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import net.minecraft.ChatFormatting;

import java.util.ArrayList;
import java.util.List;

public class Ailments {

    public static List<Ailment> ALL = new ArrayList<>();

    public static Ailment BURN = new Ailment("burn", Elements.Fire, true, false, 1, 0, 60, x -> "Deals " + x.getPercentDamage() + "% of the damage as " + x.element.getIconNameDmgWithSpecialColor(ChatFormatting.BLUE) + " for " + x.getDurationSeconds() + "s.");
    public static Ailment POISON = new Ailment("poison", Elements.Shadow, true, false, 1.5F, 0, 200, x -> "Deals " + x.getPercentDamage() + "% of the damage as " + x.element.getIconNameDmgWithSpecialColor(ChatFormatting.BLUE) + " for " + x.getDurationSeconds() + "s.");
    public static Ailment BLEED = new Ailment("bleed", Elements.Physical, true, false, 1.2F, 0, 100, x -> "Deals " + x.getPercentDamage() + "% of the damage as " + x.element.getIconNameDmgWithSpecialColor(ChatFormatting.BLUE) + " for " + x.getDurationSeconds() + "s.");

    public static Ailment FREEZE = new Ailment("freeze", Elements.Cold, false, true, 0.85F, 0.1F, 0, x -> "Slows Enemy and Accumulates " + x.getPercentDamage() + "% of the damage which can be activated with SHATTER to deal bonus " + x.element.getIconNameDmgWithSpecialColor(ChatFormatting.BLUE));

    public static Ailment ELECTRIFY = new Ailment("electrify", Elements.Nature, false, true, 1, 0.1F, 0, x -> "Accumulates " + x.getPercentDamage() + "% of the damage which can be activated with SHOCK to deal bonus " + x.element.getIconNameDmgWithSpecialColor(ChatFormatting.BLUE));


    public static void init() {

        BURN.registerToExileRegistry();
        POISON.registerToExileRegistry();
        BLEED.registerToExileRegistry();
        FREEZE.registerToExileRegistry();
        ELECTRIFY.registerToExileRegistry();

    }
}
