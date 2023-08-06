package com.robertx22.age_of_exile.aoe_data.database.ailments;

import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;

public class Ailments {

    public static String BURN = "burn";
    public static String POISON = "burn";
    public static String BLEED = "bleed";
    public static String FREEZE = "freeze";
    public static String ELECTRIFY = "electrify";

    public static void init() {

        
        new Ailment(BURN, Elements.Fire, true, false, 0.5F, 0, 60, x -> "Deals " + x.getPercentDamage() + "% of the damage as " + x.element.getIconNameDmg() + " for " + x.getDurationSeconds() + "s.").registerToExileRegistry();
        new Ailment(POISON, Elements.Chaos, true, false, 1, 0, 200, x -> "Deals " + x.getPercentDamage() + "% of the damage as " + x.element.getIconNameDmg() + " for " + x.getDurationSeconds() + "s.").registerToExileRegistry();
        new Ailment(BLEED, Elements.Physical, true, false, 0.5F, 0, 100, x -> "Deals " + x.getPercentDamage() + "% of the damage as " + x.element.getIconNameDmg() + " for " + x.getDurationSeconds() + "s.").registerToExileRegistry();
        new Ailment(FREEZE, Elements.Cold, false, true, 0.75F, 0.1F, 0, x -> "Slows Enemy and Accumulates " + x.getPercentDamage() + "% of the damage which can be activated to deal bonus " + x.element.getIconNameDmg()).registerToExileRegistry();
        new Ailment(ELECTRIFY, Elements.Lightning, false, true, 1, 0.1F, 0, x -> "Accumulates " + x.getPercentDamage() + "% of the damage which can be activated to deal bonus " + x.element.getIconNameDmg()).registerToExileRegistry();

    }
}
