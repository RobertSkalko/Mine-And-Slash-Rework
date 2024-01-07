package com.robertx22.age_of_exile.saveclasses.perks;

import com.robertx22.age_of_exile.capability.entity.EntityData;
import com.robertx22.age_of_exile.config.forge.ServerContainer;
import com.robertx22.age_of_exile.database.data.game_balance_config.GameBalanceConfig;
import com.robertx22.age_of_exile.database.data.perks.Perk;
import com.robertx22.age_of_exile.database.data.perks.PerkStatus;
import com.robertx22.age_of_exile.database.data.talent_tree.TalentTree;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.saveclasses.ExactStatData;
import com.robertx22.age_of_exile.saveclasses.PointData;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.IStatCtx;
import com.robertx22.age_of_exile.saveclasses.unit.stat_ctx.StatContext;
import com.robertx22.age_of_exile.saveclasses.unit.stat_ctx.TalentStatCtx;
import com.robertx22.age_of_exile.uncommon.MathHelper;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

import java.util.*;


public class TalentsData implements IStatCtx {


    SchoolData perks = new SchoolData();

    public int reset_points = 5;


    public int getAllocatedPoints(TalentTree.SchoolType type) {
        return this.perks.getAllocatedPointsInSchool();
    }

    public int getFreePoints(EntityData data, TalentTree.SchoolType type) {

        int num = 0;

        num = (int) GameBalanceConfig.get().STARTING_TALENT_POINTS;
        num += GameBalanceConfig.get().TALENT_POINTS_PER_LVL * data.getLevel();

        num -= this.getAllocatedPoints(type);

        if (data.getEntity() instanceof Player p) {
            num += MathHelper.clamp(Load.player(p).bonusTalents, 0, ServerContainer.get().MAX_ADDITIONAL_TALENT_POINTS.get());
        }
        return num;
    }

    public SchoolData getSchool() {
        return perks;

    }

    public void allocate(Player player, TalentTree school, PointData point) {
        getSchool().allocate(point);
    }

    public void remove(TalentTree school, PointData point) {
        getSchool().unAllocate(point);
    }

    public boolean hasFreePoints(EntityData data, TalentTree.SchoolType type) {
        return getFreePoints(data, type) > 0;
    }

    public boolean canAllocate(TalentTree school, PointData point, EntityData data, Player player) {

        if (!hasFreePoints(data, school.getSchool_type())) {
            return false;
        }

        Perk perk = school.calcData.getPerk(point);


        if (perk.one_kind != null && !perk.one_kind.isEmpty()) {
            return getAllAllocatedPerks().values().stream().noneMatch(x -> x.one_kind != null && x.one_kind.equals(perk.one_kind));
        }

        // todo you can take multiple starts??

        if (!perk.is_entry) {
            Set<PointData> con = school.calcData.connections.get(point);

            if (con == null || !con.stream()
                    .anyMatch(x -> getSchool()
                            .isAllocated(x))) {
                return false;
            }
        }

        return true;

    }

    public boolean canRemove(TalentTree school, PointData point) {

        if (!getSchool().isAllocated(point)) {
            return false;
        }

        if (reset_points < 1) {
            return false;
        }

        for (PointData con : school.calcData.connections.get(point)) {
            if (getSchool().isAllocated(con)) {
                Perk perk = school.calcData.getPerk(con);
                if (perk.is_entry) {
                    continue;
                }
                if (!hasPathToStart(school, con, point)) {
                    return false;
                }
            }
        }

        return true;

    }

    private boolean hasPathToStart(TalentTree school, PointData check, PointData toRemove) {
        Queue<PointData> openSet = new ArrayDeque<>();

        openSet.addAll(school.calcData.connections.get(check));

        Set<PointData> closedSet = new HashSet<>();

        while (!openSet.isEmpty()) {
            PointData current = openSet.poll();

            Perk perk = school.calcData.getPerk(current);

            if (current.equals(toRemove) || !getSchool().isAllocated(current)) {
                continue; // skip exploring this path
            }

            if (perk.is_entry) {
                return true;
            }

            if (!closedSet.add(current)) {
                continue; // we already visited it
            }

            openSet.addAll(school.calcData.connections.get(current));

        }

        return false;
    }

    public void clearAllTalents() {

        this.perks = new SchoolData();


    }

    public HashMap<PointData, Perk> getAllAllocatedPerks() {


        HashMap<PointData, Perk> perks = new HashMap<>();

        TalentTree school = ExileDB.TalentTrees().get("talents");
        if (school != null) {
            for (PointData p : this.perks.getAllocatedPoints(school)) {
                perks.put(p, school.calcData.getPerk(p));
            }
        }

        /*
        for (TalentTree.SchoolType type : TalentTree.SchoolType.values()) {
            for (Map.Entry<String, SchoolData> x : getPerks(type)
                    .entrySet()) {
                if (ExileDB.TalentTrees()
                        .isRegistered(x.getKey())) {

                    TalentTree school = ExileDB.TalentTrees()
                            .get(x.getKey());
                    if (school != null) {
                        for (PointData p : x.getValue()
                                .getAllocatedPoints(school)) {
                            perks.put(p, school.calcData.getPerk(p));
                        }
                    }
                }
            }
        }

         */
        return perks;
    }

    public PerkStatus getStatus(Player player, TalentTree school, PointData point) {

        if (isAllocated(school, point)) {
            return PerkStatus.CONNECTED;
        }
        if (canAllocate(school, point, Load.Unit(player), player)) {
            return PerkStatus.POSSIBLE;
        } else {
            return PerkStatus.BLOCKED;
        }
    }

    public Perk.Connection getConnection(TalentTree school, PointData one, PointData two) {

        if (isAllocated(school, one)) {
            if (isAllocated(school, two)) {
                return Perk.Connection.LINKED;
            }
            return Perk.Connection.POSSIBLE;
        } else if (isAllocated(school, two)) {
            return Perk.Connection.POSSIBLE;
        }

        return Perk.Connection.BLOCKED;
    }


    public boolean isAllocated(TalentTree school, PointData point) {
        return getSchool().isAllocated(point);
    }

    @Override
    public List<StatContext> getStatAndContext(LivingEntity en) {
        List<StatContext> ctx = new ArrayList<>();

        HashMap<PointData, Perk> map = getAllAllocatedPerks();

        int lvl = Load.Unit(en).getLevel();

        List<ExactStatData> stats = new ArrayList<>();

        map.forEach((key, value) -> {
            value.stats.forEach(s -> stats.add(s.toExactStat(lvl)));
        });

        ctx.add(new TalentStatCtx(stats));

        return ctx;
    }

}
