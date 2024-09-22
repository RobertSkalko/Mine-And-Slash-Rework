package com.robertx22.mine_and_slash.aoe_data.database.spells.schools;

import com.robertx22.library_of_exile.registry.ExileRegistryInit;
import com.robertx22.mine_and_slash.aoe_data.database.spells.PartBuilder;
import com.robertx22.mine_and_slash.aoe_data.database.spells.SpellBuilder;
import com.robertx22.mine_and_slash.aoe_data.database.spells.SpellCalcs;
import com.robertx22.mine_and_slash.database.data.spells.components.Spell;
import com.robertx22.mine_and_slash.database.data.spells.components.SpellConfiguration;
import com.robertx22.mine_and_slash.database.data.spells.spell_classes.CastingWeapon;
import com.robertx22.mine_and_slash.tags.all.SpellTags;
import com.robertx22.mine_and_slash.uncommon.enumclasses.Elements;
import com.robertx22.mine_and_slash.uncommon.enumclasses.PlayStyle;

import java.util.Arrays;

public class PetSpells implements ExileRegistryInit {

    public static String LIGHTNING_GOLEM = "lightning_golem_basic";
    public static String ZOMBIE = "zombie_basic";
    public static String SKELETON = "skeleton_basic";
    public static String FIRE_GOLEM = "fire_golem_basic";
    public static String FROST_GOLEM = "frost_golem_basic";
    public static String SPIDER = "spider_basic";
    public static String WOLF = "wolf_basic";

    static Spell basic(String summon, String id) {
        return SpellBuilder.of(id, PlayStyle.INT, SpellConfiguration.Builder.energy(3, 1).setUsesSupportGemsFrom(summon), "Fire Golem Attack",
                        Arrays.asList(SpellTags.summon, SpellTags.damage, SpellTags.PHYSICAL))
                .defaultAndMaxLevel(1)
                .manualDesc(
                        "Attack dealing " + SpellCalcs.PET_BASIC.getLocDmgTooltip() + " " + Elements.Physical.getIconNameDmg() + " to single enemy."
                )
                .weaponReq(CastingWeapon.ANY_WEAPON)
                .onHit(PartBuilder.damage(SpellCalcs.PET_BASIC, Elements.Physical))
                .levelReq(1)

                .build();
    }


    @Override
    public void registerAll() {

        basic(SummonSpells.SUMMON_SPIRIT_WOLF, WOLF);
        basic(SummonSpells.SUMMON_FIRE_GOLEM, FIRE_GOLEM);
        basic(SummonSpells.SUMMON_COLD_GOLEM, FROST_GOLEM);
        basic(SummonSpells.SUMMON_LIGHTNING_GOLEM, LIGHTNING_GOLEM);
        basic(SummonSpells.SUMMON_ZOMBIE, ZOMBIE);
        basic(SummonSpells.SUMMON_SKELETAL_ARMY, SKELETON);


        SpellBuilder.of(SPIDER, PlayStyle.INT, SpellConfiguration.Builder.instant(2, 1)
                                .setUsesSupportGemsFrom(SummonSpells.SUMMON_SPIDER), "Spider Pet Attack",
                        Arrays.asList(SpellTags.summon, SpellTags.damage, SpellTags.CHAOS))
                .defaultAndMaxLevel(1)
                .manualDesc(
                        "Attack dealing " + SpellCalcs.SPIDER_PET_BASIC.getLocDmgTooltip() + " " + Elements.Shadow.getIconNameDmg() + " to single enemy."
                )
                .weaponReq(CastingWeapon.ANY_WEAPON)
                .onHit(PartBuilder.damage(SpellCalcs.SPIDER_PET_BASIC, Elements.Shadow))
                .levelReq(1)
                .build();
    }
}
