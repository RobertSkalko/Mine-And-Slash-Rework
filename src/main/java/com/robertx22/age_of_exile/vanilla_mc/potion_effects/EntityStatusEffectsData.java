package com.robertx22.age_of_exile.vanilla_mc.potion_effects;

import com.robertx22.age_of_exile.database.data.exile_effects.ExileEffect;
import com.robertx22.age_of_exile.database.data.exile_effects.ExileEffectInstanceData;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.saveclasses.ExactStatData;
import com.robertx22.age_of_exile.saveclasses.unit.stat_ctx.EffectStatCtx;
import com.robertx22.age_of_exile.saveclasses.unit.stat_ctx.StatContext;
import com.robertx22.age_of_exile.vanilla_mc.potion_effects.types.ExileStatusEffect;
import net.minecraft.world.entity.LivingEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class EntityStatusEffectsData {


    public HashMap<String, ExileEffectInstanceData> exileMap = new HashMap<>();


    public ExileEffectInstanceData get(ExileStatusEffect eff) {
        return exileMap.getOrDefault(eff.GUID(), null);
    }

    public void set(ExileStatusEffect eff, ExileEffectInstanceData data) {
        exileMap.put(eff.GUID(), data);
    }


    public List<ExileEffect> getEffects() {
        return exileMap.keySet().stream().map(x -> ExileDB.ExileEffects().get(x)).collect(Collectors.toList());
    }

    public StatContext getStats(LivingEntity en) {

        List<ExactStatData> stats = new ArrayList<>();

        for (Map.Entry<String, ExileEffectInstanceData> e : exileMap.entrySet()) {
            ExileEffect eff = ExileDB.ExileEffects().get(e.getKey());
            stats.addAll(eff.getExactStats(e.getValue().getCaster(en.level()), e.getValue()));
        }

        return new EffectStatCtx(stats);

    }
}
