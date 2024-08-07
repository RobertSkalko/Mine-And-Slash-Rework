package com.robertx22.age_of_exile.database.data.game_balance_config;

import com.robertx22.age_of_exile.database.data.talent_tree.TalentTree;
import com.robertx22.age_of_exile.saveclasses.spells.SpellSchoolsData;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.localization.Words;
import net.minecraft.world.entity.player.Player;

public enum PlayerPointsType {
    TALENTS() {
        @Override
        public Words word() {
            return Words.Talents;
        }

        @Override
        public int getPointsInUse(Player p) {
            return Load.player(p).talents.getAllocatedPoints(TalentTree.SchoolType.TALENTS);
        }
    },
    SPELLS() {
        @Override
        public Words word() {
            return Words.SPELL;
        }

        @Override
        public int getPointsInUse(Player p) {
            return Load.player(p).ascClass.getSpentPoints(SpellSchoolsData.PointType.SPELL);
        }
    },
    PASSIVES() {
        @Override
        public Words word() {
            return Words.Passive;
        }

        @Override
        public int getPointsInUse(Player p) {
            return Load.player(p).ascClass.getSpentPoints(SpellSchoolsData.PointType.PASSIVE);
        }
    },
    ASCENDANCY() {
        @Override
        public Words word() {
            return Words.AscClasses;
        }

        @Override
        public int getPointsInUse(Player p) {
            return Load.player(p).talents.getAllocatedPoints(TalentTree.SchoolType.ASCENDANCY);
        }
    },
    STATS() {
        @Override
        public Words word() {
            return Words.Stat;
        }

        @Override
        public int getPointsInUse(Player p) {
            return Load.player(p).statPoints.getAllocatedPoints();
        }
    };

    public abstract Words word();

    private final int getBonusPoints(Player p) {
        var data = getConfig();

        int c = Load.player(p).points.get(this).getBonusPoints();

        if (this == TALENTS) {
            // legacy code, delete in new mc update
            c += Load.player(p).bonusTalents;
        }
        if (c > data.max_bonus_points) {
            c = data.max_bonus_points;
        }
        return c;
    }

    public abstract int getPointsInUse(Player p);

    public PlayerPointsConfig getConfig() {
        return GameBalanceConfig.get().player_points.get(this);
    }

    public boolean hasFreePoints(Player p) {
        return getFreePoints(p) > 0;
    }

    public int getFreePoints(Player p) {

        var data = getConfig();

        int lvl = Load.Unit(p).getLevel();

        int current = data.base_points + (int) (lvl * data.points_per_lvl);
        int bonus = getBonusPoints(p);

        int total = current + bonus;

        if (total > data.max_total_points) {
            total = data.max_total_points;
        }

        int spent = getPointsInUse(p);

        int free = total - spent;

        return free;
    }
}
