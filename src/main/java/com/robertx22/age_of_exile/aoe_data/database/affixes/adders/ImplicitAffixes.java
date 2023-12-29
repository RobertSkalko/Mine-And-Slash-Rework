package com.robertx22.age_of_exile.aoe_data.database.affixes.adders;

import com.robertx22.age_of_exile.aoe_data.database.affixes.AffixBuilder;
import com.robertx22.age_of_exile.aoe_data.database.stats.Stats;
import com.robertx22.age_of_exile.tags.all.SlotTags;
import com.robertx22.age_of_exile.tags.all.SpellTags;
import com.robertx22.library_of_exile.registry.ExileRegistryInit;

// implicits can't be rerolled, have way less variation and are there to add flavor and mostly reliable stats for newbies
public class ImplicitAffixes implements ExileRegistryInit {

    @Override
    public void registerAll() {
        // todo
        AffixBuilder.Normal("bandit_mask")
                .Named("Bandit Mask")
                .stats(Stats.DAMAGE_PER_SPELL_TAG.get(SpellTags.trap).mod(5, 10))
                .mustIncludesAllTags(SlotTags.LEATHER_HELMET)
                .Implicit()
                .Build();

        AffixBuilder.Normal("oakwood_staff")
                .Named("Oak Wood Staff")
                .stats(Stats.DAMAGE_PER_SPELL_TAG.get(SpellTags.thorns).mod(10, 15))
                .mustIncludesAllTags(SlotTags.staff)
                .Implicit()
                .Build();


    }
}
