package com.robertx22.mine_and_slash.database.data.game_balance_config;

import com.robertx22.library_of_exile.registry.IGUID;
import com.robertx22.mine_and_slash.database.data.talent_tree.TalentTree;
import com.robertx22.mine_and_slash.saveclasses.spells.SpellSchoolsData;
import com.robertx22.mine_and_slash.uncommon.datasaving.Load;
import com.robertx22.mine_and_slash.uncommon.localization.Words;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

public enum PlayerPointsType implements IGUID {
    TALENTS() {
        @Override
        public String GUID() {
            return "talent";
        }

        @Override
        public Words word() {
            return Words.Talents;
        }

        @Override
        public Item matItem() {
            return Items.DIAMOND;
        }

        @Override
        public int getPointsInUse(Player p) {
            return Load.player(p).talents.getAllocatedPoints(TalentTree.SchoolType.TALENTS);
        }

        @Override
        public void fullReset(Player p) {
            Load.player(p).talents.clearAllTalents(TalentTree.SchoolType.TALENTS);
        }
    },
    SPELLS() {
        @Override
        public String GUID() {
            return "spell";
        }

        @Override
        public Words word() {
            return Words.SPELL;
        }

        @Override
        public Item matItem() {
            return Items.BOOKSHELF;
        }

        @Override
        public int getPointsInUse(Player p) {
            return Load.player(p).ascClass.getSpentPoints(SpellSchoolsData.PointType.SPELL);
        }

        @Override
        public void fullReset(Player p) {
            Load.player(p).ascClass.reset(SpellSchoolsData.PointType.SPELL);
        }
    },
    PASSIVES() {
        @Override
        public String GUID() {
            return "passive";
        }

        @Override
        public Words word() {
            return Words.Passive;
        }

        @Override
        public Item matItem() {
            return Items.EMERALD;
        }

        @Override
        public int getPointsInUse(Player p) {
            return Load.player(p).ascClass.getSpentPoints(SpellSchoolsData.PointType.PASSIVE);
        }

        @Override
        public void fullReset(Player p) {
            Load.player(p).ascClass.reset(SpellSchoolsData.PointType.PASSIVE);
        }
    },

    ASCENDANCY() {
        @Override
        public String GUID() {
            return "ascendancy";
        }

        @Override
        public Words word() {
            return Words.AscClasses;
        }

        @Override
        public Item matItem() {
            return Items.GOLDEN_APPLE;
        }

        @Override
        public int getPointsInUse(Player p) {
            return Load.player(p).talents.getAllocatedPoints(TalentTree.SchoolType.ASCENDANCY);
        }

        @Override
        public void fullReset(Player p) {
            Load.player(p).talents.clearAllTalents(TalentTree.SchoolType.ASCENDANCY);
        }
    },
    STATS() {
        @Override
        public String GUID() {
            return "stat";
        }

        @Override
        public Words word() {
            return Words.Stat;
        }

        @Override
        public Item matItem() {
            return Items.APPLE;
        }

        @Override
        public int getPointsInUse(Player p) {
            return Load.player(p).statPoints.getAllocatedPoints();
        }

        @Override
        public void fullReset(Player p) {
            Load.player(p).statPoints.reset();
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


    public abstract Item matItem();

    public abstract int getPointsInUse(Player p);

    public PlayerPointsConfig getConfig() {
        return GameBalanceConfig.get().player_points.get(this);
    }

    public boolean hasFreePoints(Player p) {
        return getFreePoints(p) > 0;
    }

    public boolean hasResetPoints(Player p) {
        return getResetPoints(p) > 0;
    }

    public void reduceResetPoints(Player p, int spent) {
        Load.player(p).points.get(this).reset_points -= spent;
    }

    public void addResetPoints(Player p, int spent) {
        Load.player(p).points.get(this).reset_points += spent;
    }

    public abstract void fullReset(Player p);

    public int getResetPoints(Player p) {
        return Load.player(p).points.get(this).reset_points;
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
