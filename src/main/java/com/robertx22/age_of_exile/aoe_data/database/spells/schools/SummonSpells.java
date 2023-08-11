package com.robertx22.age_of_exile.aoe_data.database.spells.schools;

import com.robertx22.age_of_exile.aoe_data.database.spells.SpellBuilder;
import com.robertx22.age_of_exile.aoe_data.database.spells.SummonType;
import com.robertx22.age_of_exile.database.data.spells.SpellTag;
import com.robertx22.age_of_exile.database.data.spells.components.SpellConfiguration;
import com.robertx22.age_of_exile.mmorpg.registers.common.SlashEntities;
import com.robertx22.age_of_exile.uncommon.enumclasses.PlayStyle;
import com.robertx22.library_of_exile.registry.ExileRegistryInit;

import java.util.Arrays;

public class SummonSpells implements ExileRegistryInit {

    @Override
    public void registerAll() {


        SpellBuilder.of("summon_fire_golem", PlayStyle.INT, SpellConfiguration.Builder.instant(40, 20 * 60).setSummonType(SummonType.GOLEM), "Summon Fire Golem",
                        Arrays.asList(SpellTag.summon, SpellTag.damage, SpellTag.golem))
                .manualDesc("Summon a Golem to aid you in combat. Sometimes casts an AOE spell")
                .summons(SlashEntities.FIRE_GOLEM.get(), 20 * 60 * 5, 1)
                .build();

        SpellBuilder.of("summon_cold_golem", PlayStyle.INT, SpellConfiguration.Builder.instant(40, 20 * 60).setSummonType(SummonType.GOLEM), "Summon Frost Golem",
                        Arrays.asList(SpellTag.summon, SpellTag.damage, SpellTag.golem))
                .manualDesc("Summon a Golem to aid you in combat. Sometimes casts an AOE spell")
                .summons(SlashEntities.COLD_GOLEM.get(), 20 * 60 * 5, 1)
                .build();

        SpellBuilder.of("summon_lightning_golem", PlayStyle.INT, SpellConfiguration.Builder.instant(40, 20 * 60).setSummonType(SummonType.GOLEM), "Summon Lightning Golem",
                        Arrays.asList(SpellTag.summon, SpellTag.damage, SpellTag.golem))
                .manualDesc("Summon a Golem to aid you in combat. Sometimes casts an AOE spell")
                .summons(SlashEntities.LIGHTNING_GOLEM.get(), 20 * 60 * 5, 1)
                .build();


        SpellBuilder.of("summon_spirit_wolf", PlayStyle.INT, SpellConfiguration.Builder.instant(30, 30 * 20).setSummonType(SummonType.SPIRIT_WOLF), "Summon Spirit Wolf",
                        Arrays.asList(SpellTag.summon, SpellTag.damage))
                .manualDesc("Summon a Spirit Wolf to aid you in combat.")
                .summons(SlashEntities.SPIRIT_WOLF.get(), 20 * 30, 1)
                .build();

        SpellBuilder.of("summon_zombie", PlayStyle.INT, SpellConfiguration.Builder.instant(30, 30 * 60).setSummonType(SummonType.ZOMBIE), "Summon Zombie",
                        Arrays.asList(SpellTag.summon, SpellTag.damage))
                .manualDesc("Summon a Pet Zombie, don't worry, it's cute!")
                .summons(SlashEntities.ZOMBIE.get(), 20 * 60, 1)
                .build();

        SpellBuilder.of("summon_skeleton_army", PlayStyle.INT, SpellConfiguration.Builder.instant(60, 30 * 60).setSummonType(SummonType.SKELETON), "Summon Skeletons",
                        Arrays.asList(SpellTag.summon, SpellTag.damage))
                .manualDesc("Summons many skeletons for a short duration")
                .summons(SlashEntities.SKELETON.get(), 20 * 20, 3)
                .build();


    }


}
