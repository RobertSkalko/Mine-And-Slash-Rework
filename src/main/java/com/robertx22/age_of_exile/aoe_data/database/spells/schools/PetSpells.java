package com.robertx22.age_of_exile.aoe_data.database.spells.schools;

import com.robertx22.age_of_exile.aoe_data.database.spells.PartBuilder;
import com.robertx22.age_of_exile.aoe_data.database.spells.SpellBuilder;
import com.robertx22.age_of_exile.aoe_data.database.spells.SpellCalcs;
import com.robertx22.age_of_exile.database.data.spells.SpellTag;
import com.robertx22.age_of_exile.database.data.spells.components.SpellConfiguration;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.CastingWeapon;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.enumclasses.PlayStyle;
import com.robertx22.library_of_exile.registry.ExileRegistryInit;

import java.util.Arrays;

public class PetSpells implements ExileRegistryInit {

    public static String PET_BASIC = "pet_basic";
    public static String SPIDER = "spider_basic";

    @Override
    public void registerAll() {

        SpellBuilder.of(PET_BASIC, PlayStyle.INT, SpellConfiguration.Builder.energy(3, 1), "Basic Pet Attack",
                        Arrays.asList(SpellTag.summon, SpellTag.damage))
                .defaultAndMaxLevel(1)
                .manualDesc(
                        "Basic Attack dealing " + SpellCalcs.PET_BASIC.getLocDmgTooltip() + " " + Elements.Physical.getIconNameDmg() + " to single enemy."
                )
                .weaponReq(CastingWeapon.ANY_WEAPON)
                .onHit(PartBuilder.damage(SpellCalcs.PET_BASIC, Elements.Physical))
                .levelReq(1)
                .build();

        SpellBuilder.of(SPIDER, PlayStyle.INT, SpellConfiguration.Builder.instant(2, 1), "Spider Pet Attack",
                        Arrays.asList(SpellTag.summon, SpellTag.damage))
                .defaultAndMaxLevel(1)
                .manualDesc(
                        "Basic Attack dealing " + SpellCalcs.SPIDER_PET_BASIC.getLocDmgTooltip() + " " + Elements.Chaos.getIconNameDmg() + " to single enemy."
                )
                .weaponReq(CastingWeapon.ANY_WEAPON)
                .onHit(PartBuilder.damage(SpellCalcs.SPIDER_PET_BASIC, Elements.Chaos))
                .levelReq(1)
                .build();
    }
}
