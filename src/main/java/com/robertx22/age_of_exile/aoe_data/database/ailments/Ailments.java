package com.robertx22.age_of_exile.aoe_data.database.ailments;

import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;

public class Ailments {

    public static Ailment BURN = new Ailment("burn", Elements.Fire, true, false, 0.5F, 0, 60, x -> "Deals " + x.getPercentDamage() + "% of the damage as " + x.element.getIconNameDmg() + " for " + x.getDurationSeconds() + "s.");
    public static Ailment POISON = new Ailment("poison", Elements.Chaos, true, false, 1, 0, 200, x -> "Deals " + x.getPercentDamage() + "% of the damage as " + x.element.getIconNameDmg() + " for " + x.getDurationSeconds() + "s.");
    public static Ailment BLEED = new Ailment("bleed", Elements.Physical, true, false, 0.5F, 0, 100, x -> "Deals " + x.getPercentDamage() + "% of the damage as " + x.element.getIconNameDmg() + " for " + x.getDurationSeconds() + "s.");

    public static Ailment FREEZE = new Ailment("freeze", Elements.Cold, false, true, 0.75F, 0.1F, 0, x -> "Slows Enemy and Accumulates " + x.getPercentDamage() + "% of the damage which can be activated to deal bonus " + x.element.getIconNameDmg());

    public static Ailment ELECTRIFY = new Ailment("electrify", Elements.Lightning, false, true, 1, 0.1F, 0, x -> "Accumulates " + x.getPercentDamage() + "% of the damage which can be activated to deal bonus " + x.element.getIconNameDmg());


    public static void init() {

        BURN.registerToExileRegistry();
        POISON.registerToExileRegistry();
        BLEED.registerToExileRegistry();
        FREEZE.registerToExileRegistry();
        ELECTRIFY.registerToExileRegistry();

    }
}
