package com.robertx22.age_of_exile.vanilla_mc.potion_effects;

import com.robertx22.age_of_exile.database.data.exile_effects.ExileEffect;
import com.robertx22.age_of_exile.database.data.exile_effects.ExileEffectInstanceData;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.saveclasses.ExactStatData;
import com.robertx22.age_of_exile.saveclasses.unit.stat_ctx.SimpleStatCtx;
import com.robertx22.age_of_exile.saveclasses.unit.stat_ctx.StatContext;
import net.minecraft.world.entity.LivingEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class EntityStatusEffectsData {


    public ConcurrentHashMap<String, ExileEffectInstanceData> exileMap = new ConcurrentHashMap<>();

    public int getStacks(String eff) {
        if (exileMap.containsKey(eff)) {
            return exileMap.get(eff).stacks;
        }
        return 0;
    }

    public void tick(LivingEntity en) {

        if (exileMap.isEmpty()) {
            return;
        }

        exileMap.entrySet().removeIf(x -> !ExileDB.ExileEffects().isRegistered(x.getKey()));

        for (Map.Entry<String, ExileEffectInstanceData> e : exileMap.entrySet()) {
            e.getValue().ticks_left--;
            ExileEffect eff = ExileDB.ExileEffects().get(e.getKey());
            if (eff != null) {
                eff.onTick(en, e.getValue());
            }
        }


        // todo this is probably bit laggy per tick no?
        List<ExileEffect> removed = new ArrayList<>();

        exileMap.entrySet().removeIf(x -> {
            boolean bo = x.getValue().shouldRemove();
            if (bo) {
                removed.add(ExileDB.ExileEffects().get(x.getKey()));
            }
            return bo;
        });

        for (ExileEffect eff : removed) {
            eff.onRemove(en);
        }

    }

    public boolean has(ExileEffect eff) {
        return this.exileMap.containsKey(eff.GUID()) && !exileMap.get(eff.GUID()).shouldRemove();
    }

    public ExileEffectInstanceData get(ExileEffect eff) {
        return exileMap.getOrDefault(eff.GUID(), new ExileEffectInstanceData());
    }

    public ExileEffectInstanceData getOrCreate(ExileEffect eff) {
        if (!exileMap.containsKey(eff.GUID())) {
            exileMap.put(eff.GUID(), new ExileEffectInstanceData());
        }
        return exileMap.getOrDefault(eff.GUID(), new ExileEffectInstanceData());
    }

    public void delete(ExileEffect eff) {
        exileMap.remove(eff.GUID());
    }


    public List<ExileEffect> getEffects() {
        return exileMap.keySet().stream().map(x -> ExileDB.ExileEffects().get(x)).collect(Collectors.toList());
    }

    public StatContext getStats(LivingEntity en) {

        List<ExactStatData> stats = new ArrayList<>();

        for (Map.Entry<String, ExileEffectInstanceData> e : exileMap.entrySet()) {
            ExileEffect eff = ExileDB.ExileEffects().get(e.getKey());
            if (eff != null) {
                var data = e.getValue();
                stats.addAll(eff.getExactStats(e.getValue().getCaster(en.level()), data.getSpell(), data.stacks, data.str_multi));
            }
        }

        return new SimpleStatCtx(StatContext.StatCtxType.POTION_EFFECT, stats);

    }
}
