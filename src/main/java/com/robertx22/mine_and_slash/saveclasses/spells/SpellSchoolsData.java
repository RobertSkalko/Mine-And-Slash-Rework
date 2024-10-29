package com.robertx22.mine_and_slash.saveclasses.spells;

import com.robertx22.mine_and_slash.database.OptScaleExactStat;
import com.robertx22.mine_and_slash.database.data.game_balance_config.PlayerPointsType;
import com.robertx22.mine_and_slash.database.data.perks.Perk;
import com.robertx22.mine_and_slash.database.data.profession.ExplainedResult;
import com.robertx22.mine_and_slash.database.data.spell_school.SpellSchool;
import com.robertx22.mine_and_slash.database.registry.ExileDB;
import com.robertx22.mine_and_slash.saveclasses.ExactStatData;
import com.robertx22.mine_and_slash.saveclasses.gearitem.gear_bases.IStatCtx;
import com.robertx22.mine_and_slash.saveclasses.unit.stat_ctx.SimpleStatCtx;
import com.robertx22.mine_and_slash.saveclasses.unit.stat_ctx.StatContext;
import com.robertx22.mine_and_slash.uncommon.datasaving.Load;
import com.robertx22.mine_and_slash.uncommon.localization.Chats;
import net.minecraft.ChatFormatting;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

import java.util.*;
import java.util.stream.Collectors;


public class SpellSchoolsData implements IStatCtx {

    public HashMap<String, Integer> allocated_lvls = new HashMap<>();


    public Set<String> school() {
        Set<String> list = new HashSet<>();
        for (Perk perk : getAllPerks()) {
            var sc = perk.getSpellSchool();
            if (!sc.isPresent()) {
                allocated_lvls.remove(perk.GUID());
            } else {
                list.add(sc.get().GUID());
            }
        }
        return list;
    }


    public List<Perk> getAllPerks() {
        return allocated_lvls.keySet().stream().map(x -> ExileDB.Perks().get(x)).collect(Collectors.toList());
    }


    public void reset(PointType type) {

        var schools = school();

        // just in case of updates
        for (Perk perk : getAllPerks()) {
            var sch = perk.getSpellSchool();
            if (!sch.isPresent() || sch.get() == null || !schools.contains(sch.get().GUID())) {
                this.allocated_lvls.remove(perk.GUID());
            }
        }
        for (Perk perk : getAllPerks()) {

            if (type == PointType.SPELL && perk.isSpell()) {
                this.allocated_lvls.remove(perk.GUID());
            }
            if (type == PointType.PASSIVE && perk.isPassive()) {
                this.allocated_lvls.remove(perk.GUID());
            }
        }

    }

    public enum PointType {
        SPELL, PASSIVE;

        public PlayerPointsType getGeneralType() {
            return this == SPELL ? PlayerPointsType.SPELLS : PlayerPointsType.PASSIVES;
        }

        public boolean is(String perkid) {
            if (this == SPELL) {
                return ExileDB.Perks().get(perkid).isSpell();
            } else {
                return !ExileDB.Perks().get(perkid).isSpell();
            }

        }
    }


    public int getLevel(String id) {
        return allocated_lvls.getOrDefault(id, 0);
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

    public ExplainedResult canLearn(Player en, SpellSchool school, Perk perk) {

        PointType type = perk.getPointType();

        if (type.getGeneralType().getFreePoints(en) < 1) {
            return ExplainedResult.failure(Chats.NOT_ENOUGH_POINTS.locName().withStyle(ChatFormatting.RED));
        }
        if (!school.isLevelEnoughFor(en, perk)) {
            return ExplainedResult.failure(Chats.TOO_LOW_LEVEL.locName().withStyle(ChatFormatting.RED));
        }
        if (!school.isLevelEnoughForSpellLevelUp(en, perk, this.getLevel(perk.GUID()))) {
            return ExplainedResult.failure(Chats.TOO_LOW_LEVEL.locName().withStyle(ChatFormatting.RED));
        }
        if (this.school().size() > 1 && !this.school().contains(school.GUID())) {
            return ExplainedResult.failure(Chats.MAX_2_CLASSES.locName().withStyle(ChatFormatting.RED));
        }
        if (allocated_lvls.getOrDefault(perk.GUID(), 0) >= perk.getMaxLevel()) {
            return ExplainedResult.failure(Chats.PERK_MAXED.locName().withStyle(ChatFormatting.RED));
        }

        return ExplainedResult.success();
    }

    public boolean canUnlearn(Player en, SpellSchool school, Perk perk) {
        if (getLevel(perk.id) < 1) {
            return false;
        }
        if (!perk.getPointType().getGeneralType().hasResetPoints(en)) {
            return false;
        }
        return true;
    }


    public void learn(Perk perk, SpellSchool school) {
        if (!this.school().contains(school.GUID())) {
            this.school().add(school.GUID());
        }
        int current = allocated_lvls.getOrDefault(perk.GUID(), 0);
        allocated_lvls.put(perk.GUID(), current + 1);
    }

    public void unlearn(Player p, Perk perk, SpellSchool school) {
        if (!this.school().contains(school.GUID())) {
            this.school().add(school.GUID());
        }
        int current = allocated_lvls.getOrDefault(perk.GUID(), 0);
        if (current > 0) {
            perk.getPointType().getGeneralType().reduceResetPoints(p, 1);
            allocated_lvls.put(perk.GUID(), current - 1);
        }
    }


    @Override
    public List<StatContext> getStatAndContext(LivingEntity en) {
        List<ExactStatData> stats = new ArrayList<>();
        for (Map.Entry<String, Integer> s : this.allocated_lvls.entrySet()) {
            if (ExileDB.Perks().isRegistered(s.getKey())) {
                for (OptScaleExactStat stat : ExileDB.Perks().get(s.getKey()).stats) {
                    var data = stat.toExactStat(Load.Unit(en).getLevel());
                    data.percentIncrease = (s.getValue() - 1) * 100;
                    data.increaseByAddedPercent();
                    stats.add(data);
                }
            }
        }
        return Arrays.asList(new SimpleStatCtx(StatContext.StatCtxType.ASCENDANCY, stats));
    }

}
