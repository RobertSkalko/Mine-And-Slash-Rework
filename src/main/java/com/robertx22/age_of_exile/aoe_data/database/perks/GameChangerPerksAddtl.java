package com.robertx22.age_of_exile.aoe_data.database.perks;

import com.robertx22.age_of_exile.aoe_data.database.stats.Stats;
import com.robertx22.age_of_exile.database.OptScaleExactStat;
import com.robertx22.age_of_exile.database.data.exile_effects.EffectTags;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;
import com.robertx22.library_of_exile.registry.ExileRegistryInit;

public class GameChangerPerksAddtl implements ExileRegistryInit {

    @Override
    public void registerAll() {


        PerkBuilder.gameChanger("curse_master", "Curse Master",
                new OptScaleExactStat(20, Stats.DAMAGE_TO_CURSED.get(), ModType.FLAT),
                new OptScaleExactStat(-20, Stats.EFFECT_OF_BUFFS_GIVEN_PER_EFFECT_TAG.get(EffectTags.curse), ModType.MORE)
        );

    }

}
