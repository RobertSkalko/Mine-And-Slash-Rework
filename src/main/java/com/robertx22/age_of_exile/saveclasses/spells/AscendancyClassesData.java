package com.robertx22.age_of_exile.saveclasses.spells;

import com.robertx22.age_of_exile.database.OptScaleExactStat;
import com.robertx22.age_of_exile.database.data.game_balance_config.GameBalanceConfig;
import com.robertx22.age_of_exile.database.data.perks.Perk;
import com.robertx22.age_of_exile.database.data.spell_school.AscendancyClass;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.saveclasses.ExactStatData;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.IStatCtx;
import com.robertx22.age_of_exile.saveclasses.unit.stat_ctx.SimpleStatCtx;
import com.robertx22.age_of_exile.saveclasses.unit.stat_ctx.StatContext;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.utilityclasses.LevelUtils;
import net.minecraft.world.entity.LivingEntity;

import java.util.*;


public class AscendancyClassesData implements IStatCtx {

    public HashMap<String, Integer> allocated_lvls = new HashMap<>();
    public List<String> school = new ArrayList<>();


    public void reset() {
        this.allocated_lvls = new HashMap<>();
        this.school = new ArrayList<>();
    }


    public int getLevel(String id) {
        return allocated_lvls.getOrDefault(id, 0);
    }

    public int getFreeSpellPoints(LivingEntity entity) {
        return (int) (GameBalanceConfig.get().CLASS_POINTS_AT_MAX_LEVEL * LevelUtils.getMaxLevelMultiplier(Load.Unit(entity).getLevel())) - getSpentPoints();
    }

    public int getSpentPoints() {
        int total = 0;
        for (Integer x : allocated_lvls.values()) {
            total += x;
        }
        return total;
    }

    public boolean canLearn(LivingEntity en, AscendancyClass school, Perk perk) {
        if (getFreeSpellPoints(en) < 1) {
            return false;
        }
        if (!school.isLevelEnoughFor(en, perk)) {
            return false;
        }
        if (this.school.size() > 1 && !this.school.contains(school.GUID())) {
            return false;
        }
        if (allocated_lvls.getOrDefault(perk.GUID(), 0) >= perk.getMaxLevel()) {
            return false;
        }
        var point = school.perks.get(perk.GUID());

        if (this.allocated_lvls.entrySet().stream().anyMatch(x -> school.perks.containsKey(x.getKey()) && school.perks.get(x.getKey()).y == point.y)) {
            // return false; // only allow 1 point per row, if i want to not hardcode this, use Oneofakind types
        }
        return true;
    }

    public void learn(Perk perk, AscendancyClass school) {

        if (!this.school.contains(school.GUID())) {
            this.school.add(school.GUID());
        }
        int current = allocated_lvls.getOrDefault(perk.GUID(), 0);
        allocated_lvls.put(perk.GUID(), current + 1);

    }


    @Override
    public List<StatContext> getStatAndContext(LivingEntity en) {
        List<ExactStatData> stats = new ArrayList<>();
        for (Map.Entry<String, Integer> s : this.allocated_lvls.entrySet()) {
            for (OptScaleExactStat stat : ExileDB.Perks().get(s.getKey()).stats) {
                var data = stat.toExactStat(Load.Unit(en).getLevel());
                data.percentIncrease = (s.getValue() - 1) * 100;
                data.increaseByAddedPercent(); // todo test if this works
                stats.add(data);
            }

        }
        return Arrays.asList(new SimpleStatCtx(StatContext.StatCtxType.TALENT, stats));
    }

}
