package com.robertx22.age_of_exile.vanilla_mc.potion_effects;

import com.robertx22.age_of_exile.database.data.exile_effects.ExileEffectInstanceData;
import com.robertx22.age_of_exile.saveclasses.ExactStatData;
import com.robertx22.age_of_exile.saveclasses.unit.stat_ctx.MiscStatCtx;
import com.robertx22.age_of_exile.saveclasses.unit.stat_ctx.StatContext;
import com.robertx22.age_of_exile.vanilla_mc.potion_effects.types.ExileStatusEffect;
import net.minecraft.world.entity.LivingEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class EntityStatusEffectsData {


    public HashMap<String, ExileEffectInstanceData> exileMap = new HashMap<>();


    public ExileEffectInstanceData get(ExileStatusEffect eff) {
        return exileMap.getOrDefault(eff.GUID(), null);
    }

    public void set(ExileStatusEffect eff, ExileEffectInstanceData data) {
        exileMap.put(eff.GUID(), data);
    }


    /*
    public void expireConsumableEffect(PlayerSkillEnum skill) {
        cons.remove(skill.GUID());
    }


     */
    public StatContext getStats(LivingEntity en) {

        List<ExactStatData> stats = new ArrayList<>();

        // todo effects need stats

        /*
        for (Map.Entry<String, ExileEffectInstanceData> e : exileMap.entrySet()) {
            stats.addAll(e.getValue().)
        }

         */

        return new MiscStatCtx(stats);

    }
}
