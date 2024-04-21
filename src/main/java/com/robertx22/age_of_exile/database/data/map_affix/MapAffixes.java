package com.robertx22.age_of_exile.database.data.map_affix;

import com.robertx22.age_of_exile.aoe_data.database.stats.Stats;
import com.robertx22.age_of_exile.database.data.StatMod;
import com.robertx22.age_of_exile.database.data.stats.effects.defense.MaxElementalResist;
import com.robertx22.age_of_exile.database.data.stats.types.defense.Armor;
import com.robertx22.age_of_exile.database.data.stats.types.defense.DodgeRating;
import com.robertx22.age_of_exile.database.data.stats.types.generated.BonusAttackDamage;
import com.robertx22.age_of_exile.database.data.stats.types.generated.ElementalResist;
import com.robertx22.age_of_exile.database.data.stats.types.resources.energy.Energy;
import com.robertx22.age_of_exile.database.data.stats.types.resources.energy.EnergyRegen;
import com.robertx22.age_of_exile.database.data.stats.types.resources.health.Health;
import com.robertx22.age_of_exile.database.data.stats.types.resources.health.HealthRegen;
import com.robertx22.age_of_exile.database.data.stats.types.resources.magic_shield.MagicShield;
import com.robertx22.age_of_exile.database.data.stats.types.resources.magic_shield.MagicShieldRegen;
import com.robertx22.age_of_exile.database.data.stats.types.resources.mana.Mana;
import com.robertx22.age_of_exile.database.data.stats.types.resources.mana.ManaRegen;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;

import java.util.List;

public class MapAffixes {

    public static void init() {

        prophecyAffixes();

        List<Elements> elements = Elements.getAllSingle();

        for (Elements element : elements) {

            // mobs
            new MapAffix(element.guidName + "_atk").addMod(new StatMod(1, 3, new BonusAttackDamage(element))).upsMapResistRequirement(element, 25).addToSerializables();
            new MapAffix(element.guidName + "_res").addMod(new StatMod(20, 60, new ElementalResist(element))).addToSerializables();

            // players
            new MapAffix(element.guidName + "_minus_res").addMod(new StatMod(-15, -30, new ElementalResist(element))).affectsPlayer().addToSerializables();

        }

        // mobs
        new MapAffix("crit").addMod(new StatMod(25, 100, Stats.CRIT_CHANCE.get())).addToSerializables();
        new MapAffix("crit_dmg").addMod(new StatMod(20, 50, Stats.CRIT_DAMAGE.get())).addToSerializables();

        var allres = new MapAffix("all_ele_res");
        for (Elements ele : Elements.getAllSingle()) {
            allres.addMod(new StatMod(20, 50, new ElementalResist(ele), ModType.FLAT));
        }
        allres.addToSerializables();

        new MapAffix("health").addMod(new StatMod(20, 50, Health.getInstance(), ModType.MORE)).addToSerializables();
        new MapAffix("armor").addMod(new StatMod(20, 100, Armor.getInstance(), ModType.MORE)).addToSerializables();


        // players
        new MapAffix("minus_ene_reg").addMod(new StatMod(-20, -75, ManaRegen.getInstance(), ModType.MORE)).affectsPlayer().addToSerializables();
        new MapAffix("minus_mana_reg").addMod(new StatMod(-20, -75, EnergyRegen.getInstance(), ModType.MORE)).affectsPlayer().addToSerializables();
        new MapAffix("minus_hp_reg").addMod(new StatMod(-20, -75, HealthRegen.getInstance(), ModType.MORE)).affectsPlayer().addToSerializables();
        new MapAffix("minus_ms_reg").addMod(new StatMod(-20, -75, MagicShieldRegen.getInstance(), ModType.MORE)).affectsPlayer().addToSerializables();


    }

    private static void prophecyAffixes() {


        List<Elements> elements = Elements.getAllSingle();

        for (Elements element : elements) {
            prophecyAffix("prophecy_max_ele_res" + element.guidName).addMod(new MaxElementalResist(element).mod(-5, -5)).addMod(new ElementalResist(element).mod(-25, -25));
            prophecyAffix("prophecy_ele_dmg" + element.guidName).addMod(Stats.ELEMENTAL_DAMAGE.get(element).mod(-50, -50));
            prophecyAffix("prophecy_always_crit" + element.guidName).addMod(Stats.ALWAYS_CRIT_WHEN_HIT_BY_ELEMENT.get(element).mod(1, 1));

        }
        prophecyAffix("prophecy_hp").addMod(Health.getInstance().mod(-25, -25).percent());
        prophecyAffix("prophecy_mana").addMod(Mana.getInstance().mod(-25, -25).percent());
        prophecyAffix("prophecy_ene").addMod(Energy.getInstance().mod(-25, -25).percent());
        prophecyAffix("prophecy_ms").addMod(MagicShield.getInstance().mod(-25, -25).percent());
        prophecyAffix("prophecy_armor").addMod(Armor.getInstance().mod(-25, -25).percent());
        prophecyAffix("prophecy_dodge").addMod(DodgeRating.getInstance().mod(-25, -25).percent());

        prophecyAffix("prophecy_total_dmg").addMod(Stats.TOTAL_DAMAGE.get().mod(-10, -10).more());
        prophecyAffix("prophecy_crit").addMod(Stats.CRIT_CHANCE.get().mod(-25, -25).more());
        prophecyAffix("prophecy_crit_dmg").addMod(Stats.CRIT_DAMAGE.get().mod(-25, -25).more());

    }

    static MapAffix prophecyAffix(String id) {
        var m = new MapAffix(id).setProphecyLeague().affectsPlayer();
        m.addToSerializables();
        return m;
    }

}
