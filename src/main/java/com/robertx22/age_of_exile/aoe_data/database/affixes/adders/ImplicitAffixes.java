package com.robertx22.age_of_exile.aoe_data.database.affixes.adders;

import com.robertx22.age_of_exile.aoe_data.database.affixes.AffixBuilder;
import com.robertx22.age_of_exile.aoe_data.database.ailments.Ailments;
import com.robertx22.age_of_exile.aoe_data.database.stats.Stats;
import com.robertx22.age_of_exile.database.data.StatMod;
import com.robertx22.age_of_exile.database.data.stats.types.ailment.AilmentChance;
import com.robertx22.age_of_exile.tags.all.EffectTags;
import com.robertx22.age_of_exile.tags.all.SlotTags;
import com.robertx22.age_of_exile.tags.all.SpellTags;
import com.robertx22.age_of_exile.tags.imp.SlotTag;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.library_of_exile.registry.ExileRegistryInit;

import java.util.function.Consumer;

// implicits can't be rerolled, have way less variation and are there to add flavor and mostly reliable stats for newbies
public class ImplicitAffixes implements ExileRegistryInit {

    @Override
    public void registerAll() {

        new Builder(SlotTags.LEATHER_HELMET).build(x -> {
            x.add("bandit_mask", "Bandit Mask", Stats.DAMAGE_PER_SPELL_TAG.get(SpellTags.trap).mod(5, 10));
        });

        new Builder(SlotTags.sword).build(x -> {
            x.add("rusted_sword", "Rusted Sword", new AilmentChance(Ailments.POISON).mod(4, 10));
        });

        new Builder(SlotTags.staff).build(x -> {
            x.add("oakwood_staff", "Oak Wood Staff", Stats.DAMAGE_PER_SPELL_TAG.get(SpellTags.thorns).mod(10, 20));
            x.add("cleric_staff", "Cleric Staff", Stats.HEAL_STRENGTH.get().mod(10, 20));
            x.add("glacial_staff", "Glacial Staff", Stats.ELEMENTAL_DAMAGE.get(Elements.Cold).mod(10, 15));
            x.add("wildfire_staff", "Wildfire Staff", Stats.ELEMENTAL_DAMAGE.get(Elements.Fire).mod(5, 10), new AilmentChance(Ailments.BURN).mod(3, 5));
            x.add("cursed_staff", "Cursed Staff", Stats.ELEMENTAL_DAMAGE.get(Elements.Chaos).mod(5, 10), Stats.EFFECT_DURATION_YOU_CAST_PER_TAG.get(EffectTags.curse).mod(5, 10));
            x.add("lightning_staff", "Lightning Staff", Stats.ELEMENTAL_DAMAGE.get(Elements.Lightning).mod(5, 10), Stats.CRIT_CHANCE.get().mod(2, 3));
        });


    }

    static class Builder {

        SlotTag slot;


        public Builder(SlotTag slot) {
            this.slot = slot;
        }

        public void build(Consumer<Builder> c) {
            c.accept(this);
        }

        public void add(String id, String name, StatMod... mods) {
            AffixBuilder.Normal(id)
                    .Named(name)
                    .stats(mods)
                    .mustIncludesAllTags(slot)
                    .Implicit()
                    .Build();
        }
    }
}
