package com.robertx22.mine_and_slash.aoe_data.database.affixes.adders;

import com.robertx22.library_of_exile.registry.ExileRegistryInit;
import com.robertx22.mine_and_slash.aoe_data.database.affixes.AffixBuilder;
import com.robertx22.mine_and_slash.aoe_data.database.stats.EffectStats;
import com.robertx22.mine_and_slash.database.data.stats.types.defense.Armor;
import com.robertx22.mine_and_slash.database.data.stats.types.defense.DodgeRating;
import com.robertx22.mine_and_slash.database.data.stats.types.loot.TreasureQuality;
import com.robertx22.mine_and_slash.tags.all.EffectTags;
import com.robertx22.mine_and_slash.tags.all.SlotTags;

public class ParagonGearAffixes implements ExileRegistryInit {

    @Override
    public void registerAll() {

        AffixBuilder.Paragon("virtue", "Virtue")
                .stats(TreasureQuality.getInstance().mod(3, 15))
                .includesTags(SlotTags.necklace).
                Build();

        AffixBuilder.Paragon("grace", "Grace")
                .stats(DodgeRating.getInstance().mod(3, 15).percent())
                .includesTags(SlotTags.boots).
                Build();

        AffixBuilder.Paragon("heart", "Heart")
                .stats(Armor.getInstance().mod(3, 15).percent())
                .includesTags(SlotTags.chest).
                Build();

        AffixBuilder.Paragon("generosity", "Generosity")
                .stats(EffectStats.EFFECT_OF_BUFFS_GIVEN_PER_EFFECT_TAG.get(EffectTags.positive).mod(5, 20))
                .includesTags(SlotTags.boots).
                Build();

    }


}
