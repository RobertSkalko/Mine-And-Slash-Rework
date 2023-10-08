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

    public enum PointType {
        SPELL, PASSIVE;

        public boolean is(String perkid) {
            if (this == SPELL) {
                return ExileDB.Perks().get(perkid).isSpell();
            } else {
                return !ExileDB.Perks().get(perkid).isSpell();
            }

        }
    }

    public int getRemainingPoints(LivingEntity en, PointType type) {
        if (type == PointType.SPELL) {
            return getFreeSpellPoints(en);
        } else {
            return getFreePassivePoints(en);
        }
    }


    public int getLevel(String id) {
        return allocated_lvls.getOrDefault(id, 0);
    }

    public int getFreeSpellPoints(LivingEntity entity) {
        return (int) (GameBalanceConfig.get().CLASS_POINTS_AT_MAX_LEVEL * LevelUtils.getMaxLevelMultiplier(Load.Unit(entity).getLevel())) - getSpentPoints(PointType.SPELL);
    }

    public int getFreePassivePoints(LivingEntity entity) {
        return (int) (GameBalanceConfig.get().PASSIVE_POINTS_AT_MAX_LEVEL * LevelUtils.getMaxLevelMultiplier(Load.Unit(entity).getLevel())) - getSpentPoints(PointType.PASSIVE);
    }

    public int getSpentPoints(PointType type) {
        int total = 0;

        for (Map.Entry<String, Integer> en : allocated_lvls.entrySet()) {
            if (type.is(en.getKey())) {
                total += en.getValue();
            }
        }

        return total;
    }

    public boolean canLearn(LivingEntity en, AscendancyClass school, Perk perk) {

        PointType type = perk.getPointType();

        if (getRemainingPoints(en, PointType.PASSIVE) < 1) {
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
                data.increaseByAddedPercent();
                stats.add(data);
            }


        }
        return Arrays.asList(new SimpleStatCtx(StatContext.StatCtxType.TALENT, stats));
    }

}
