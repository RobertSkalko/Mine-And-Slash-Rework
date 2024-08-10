package com.robertx22.mine_and_slash.uncommon.utilityclasses;

import com.robertx22.mine_and_slash.database.data.exile_effects.ExileEffect;
import com.robertx22.mine_and_slash.database.registry.ExileDB;
import com.robertx22.mine_and_slash.tags.imp.EffectTag;
import com.robertx22.mine_and_slash.uncommon.datasaving.Load;
import net.minecraft.world.entity.LivingEntity;

public class ExileEffectUtils {

    public static int countEffectsWithTag(LivingEntity en, EffectTag tag) {

        int amount = 0;

        for (String k : Load.Unit(en)
                .getStatusEffectsData().exileMap.keySet()) {
            ExileEffect eff = ExileDB.ExileEffects()
                    .get(k);
            if (eff.hasTag(tag)) {
                amount++;
            }
        }

        return amount;

    }
}
