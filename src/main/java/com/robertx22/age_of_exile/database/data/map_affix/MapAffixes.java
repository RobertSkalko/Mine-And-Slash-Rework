package com.robertx22.age_of_exile.database.data.map_affix;

import com.robertx22.age_of_exile.aoe_data.database.stats.Stats;
import com.robertx22.age_of_exile.database.data.StatMod;
import com.robertx22.age_of_exile.database.data.stats.types.defense.Armor;
import com.robertx22.age_of_exile.database.data.stats.types.generated.BonusAttackDamage;
import com.robertx22.age_of_exile.database.data.stats.types.generated.ElementalResist;
import com.robertx22.age_of_exile.database.data.stats.types.resources.energy.EnergyRegen;
import com.robertx22.age_of_exile.database.data.stats.types.resources.health.Health;
import com.robertx22.age_of_exile.database.data.stats.types.resources.health.HealthRegen;
import com.robertx22.age_of_exile.database.data.stats.types.resources.magic_shield.MagicShieldRegen;
import com.robertx22.age_of_exile.database.data.stats.types.resources.mana.ManaRegen;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;

import java.util.List;

public class MapAffixes {

    public static void init() {

        List<Elements> elements = Elements.getAllSingleElementals();

        for (Elements element : elements) {

            // mobs
            new MapAffix(element.guidName + "_atk").addMod(new StatMod(1, 3, new BonusAttackDamage(element))).addToSerializables();
            new MapAffix(element.guidName + "_res").addMod(new StatMod(20, 60, new ElementalResist(element))).addToSerializables();

            // players
            new MapAffix(element.guidName + "_minus_res").addMod(new StatMod(-20, -60, new ElementalResist(element))).affectsPlayer().addToSerializables();

        }

        // mobs
        new MapAffix("crit").addMod(new StatMod(25, 100, Stats.CRIT_CHANCE.get())).addToSerializables();
        new MapAffix("crit_dmg").addMod(new StatMod(20, 50, Stats.CRIT_DAMAGE.get())).addToSerializables();
        new MapAffix("all_ele_res").addMod(new StatMod(20, 50, new ElementalResist(Elements.All), ModType.FLAT)).addToSerializables();

        new MapAffix("health").addMod(new StatMod(20, 50, Health.getInstance(), ModType.MORE)).addToSerializables();
        new MapAffix("armor").addMod(new StatMod(20, 100, Armor.getInstance(), ModType.MORE)).addToSerializables();


        // players
        new MapAffix("minus_ene_reg").addMod(new StatMod(-20, -75, ManaRegen.getInstance(), ModType.MORE)).affectsPlayer().addToSerializables();
        new MapAffix("minus_mana_reg").addMod(new StatMod(-20, -75, EnergyRegen.getInstance(), ModType.MORE)).affectsPlayer().addToSerializables();
        new MapAffix("minus_hp_reg").addMod(new StatMod(-20, -75, HealthRegen.getInstance(), ModType.MORE)).affectsPlayer().addToSerializables();
        new MapAffix("minus_ms_reg").addMod(new StatMod(-20, -75, MagicShieldRegen.getInstance(), ModType.MORE)).affectsPlayer().addToSerializables();


    }
}
