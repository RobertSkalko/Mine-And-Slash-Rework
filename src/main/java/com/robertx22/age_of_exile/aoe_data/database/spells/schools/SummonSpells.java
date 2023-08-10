package com.robertx22.age_of_exile.aoe_data.database.spells.schools;

import com.robertx22.age_of_exile.aoe_data.database.spells.SpellBuilder;
import com.robertx22.age_of_exile.database.data.spells.SpellTag;
import com.robertx22.age_of_exile.database.data.spells.components.SpellConfiguration;
import com.robertx22.age_of_exile.mmorpg.registers.common.SlashEntities;
import com.robertx22.age_of_exile.uncommon.enumclasses.PlayStyle;
import com.robertx22.library_of_exile.registry.ExileRegistryInit;

import java.util.Arrays;

public class SummonSpells implements ExileRegistryInit {

    @Override
    public void registerAll() {
        SpellBuilder.of("summon_spirit_wolf", PlayStyle.INT, SpellConfiguration.Builder.instant(30, 30 * 20), "Summon Spirit Wolf",
                        Arrays.asList(SpellTag.summon, SpellTag.damage))
                .manualDesc("Summon a Spirit Wolf to aid you in combat.")
                .summons(SlashEntities.SPIRIT_WOLF.get(), 20 * 60, 1)
                .build();

        SpellBuilder.of("summon_skeletons", PlayStyle.INT, SpellConfiguration.Builder.instant(60, 30 * 60), "Summon Skeletons",
                        Arrays.asList(SpellTag.summon, SpellTag.damage))
                .manualDesc("Summons many skeletons for a short duration")
                .summons(SlashEntities.SKELETON.get(), 20 * 20, 3)
                .build();


    }


}
