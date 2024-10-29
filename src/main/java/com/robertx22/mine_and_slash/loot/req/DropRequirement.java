package com.robertx22.mine_and_slash.loot.req;

import com.robertx22.mine_and_slash.database.data.league.LeagueMechanic;
import com.robertx22.mine_and_slash.database.registry.ExileDB;

public class DropRequirement {

    private String league = "";
    int req_lvl = 0;

    private DropRequirement() {

    }

    public boolean canDropInLeague(LeagueMechanic m, int lvl) {
        if (lvl < req_lvl) {
            return false;
        }
        if (league.isEmpty()) {
            return true;
        } else {
            return m.GUID().equals(league);
        }
    }

    public boolean isFromLeague(LeagueMechanic m) {
        return m.GUID().equals(league);
    }

    public boolean hasLeague() {
        return !this.league.isEmpty();
    }

    public LeagueMechanic getLeague() {
        if (!league.isEmpty()) {
            return ExileDB.LeagueMechanics().get(league);
        }
        return null;
    }

    public static class Builder {
        DropRequirement r;

        public Builder(DropRequirement r) {
            this.r = r;
        }

        public static Builder of() {
            return new Builder(new DropRequirement());
        }

        public Builder setOnlyDropsInLeague(String le) {
            r.league = le;
            return this;
        }

        public Builder setLevelReq(int lvl) {
            r.req_lvl = lvl;
            return this;
        }

        public DropRequirement build() {
            return r;
        }

    }

}
