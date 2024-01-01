package com.robertx22.age_of_exile.aoe_data.database.runewords;

import com.robertx22.age_of_exile.aoe_data.database.gear_slots.GearSlots;
import com.robertx22.age_of_exile.aoe_data.database.stats.Stats;
import com.robertx22.age_of_exile.aoe_data.database.stats.base.ResourceAndAttack;
import com.robertx22.age_of_exile.database.data.stats.types.MaxSpellLevel;
import com.robertx22.age_of_exile.database.data.stats.types.gear_base.GearDamage;
import com.robertx22.age_of_exile.database.data.stats.types.gear_base.GearDefense;
import com.robertx22.age_of_exile.database.data.stats.types.resources.energy.Energy;
import com.robertx22.age_of_exile.database.data.stats.types.resources.health.Health;
import com.robertx22.age_of_exile.database.data.stats.types.resources.mana.Mana;
import com.robertx22.age_of_exile.saveclasses.unit.ResourceType;
import com.robertx22.age_of_exile.tags.all.SpellTags;
import com.robertx22.age_of_exile.uncommon.enumclasses.AttackType;
import com.robertx22.age_of_exile.vanilla_mc.items.gemrunes.RuneType;
import com.robertx22.library_of_exile.registry.ExileRegistryInit;

import java.util.Arrays;

public class Runewords implements ExileRegistryInit {

    // todo add more stuff

    @Override
    public void registerAll() {

        RunewordBuilder.of("abyssal_depths", "Abyssal Depths",
                Arrays.asList(
                        GearDefense.getInstance().mod(25, 100).percent(),
                        new MaxSpellLevel(SpellTags.summon).mod(1, 1),
                        Health.getInstance().mod(5, 10).percent(),
                        Energy.getInstance().mod(10, 25).percent(),
                        Mana.getInstance().mod(10, 25).percent(),
                        Stats.SUMMON_DAMAGE.get().mod(5, 15)
                ),
                Arrays.asList(RuneType.NOS, RuneType.MOS, RuneType.ITA, RuneType.CEN),
                GearSlots.CHEST);


        RunewordBuilder.of("the_novice", "The Novice",
                Arrays.asList(
                        GearDamage.getInstance().mod(30, 75).percent(),
                        Stats.RESOURCE_ON_HIT.get(new ResourceAndAttack(ResourceType.energy, AttackType.hit)).mod(1, 1),
                        Stats.RESOURCE_ON_HIT.get(new ResourceAndAttack(ResourceType.mana, AttackType.hit)).mod(1, 1)
                ),
                Arrays.asList(RuneType.NOS, RuneType.DOS, RuneType.ITA, RuneType.MOS),
                GearSlots.allWeapons());


    }
}
