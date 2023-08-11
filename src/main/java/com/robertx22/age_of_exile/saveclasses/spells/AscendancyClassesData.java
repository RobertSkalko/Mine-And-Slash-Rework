package com.robertx22.age_of_exile.saveclasses.spells;

import com.robertx22.age_of_exile.database.OptScaleExactStat;
import com.robertx22.age_of_exile.database.data.game_balance_config.GameBalanceConfig;
import com.robertx22.age_of_exile.database.data.perks.Perk;
import com.robertx22.age_of_exile.database.data.spell_school.AscendancyClass;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.saveclasses.ExactStatData;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.IApplyableStats;
import com.robertx22.age_of_exile.saveclasses.unit.stat_ctx.SimpleStatCtx;
import com.robertx22.age_of_exile.saveclasses.unit.stat_ctx.StatContext;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.utilityclasses.LevelUtils;
import net.minecraft.world.entity.LivingEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;


public class AscendancyClassesData implements IApplyableStats {

    public HashMap<String, Integer> allocated_lvls = new HashMap<>();
    public String school = "";


    public void reset() {
        this.allocated_lvls = new HashMap<>();
        this.school = "";
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
        if (!this.school.isEmpty() && !this.school.equals(school.GUID())) {
            return false;
        }
        if (allocated_lvls.getOrDefault(perk.GUID(), 0) >= perk.getMaxLevel()) {
            return false;
        }
        var point = school.perks.get(perk.GUID());

        if (this.allocated_lvls.entrySet().stream().anyMatch(x -> school.perks.get(x.getKey()).y == point.y)) {
            return false; // only allow 1 point per row, if i want to not hardcode this, use Oneofakind types
        }
        return true;
    }

    public void learn(Perk perk, AscendancyClass school) {

        if (this.school.isEmpty()) {
            this.school = school.GUID();
        }
        int current = allocated_lvls.getOrDefault(perk.GUID(), 0);
        allocated_lvls.put(perk.GUID(), current + 1);

    }


    @Override
    public List<StatContext> getStatAndContext(LivingEntity en) {
        List<ExactStatData> stats = new ArrayList<>();
        for (String s : this.allocated_lvls.keySet()) {
            for (OptScaleExactStat stat : ExileDB.Perks().get(s).stats) {
                stats.add(stat.toExactStat(Load.Unit(en).getLevel()));
            }

        }
        return Arrays.asList(new SimpleStatCtx(StatContext.StatCtxType.TALENT, stats));
    }

}
