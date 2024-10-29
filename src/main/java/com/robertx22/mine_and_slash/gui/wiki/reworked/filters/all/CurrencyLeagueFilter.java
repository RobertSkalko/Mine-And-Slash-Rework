package com.robertx22.mine_and_slash.gui.wiki.reworked.filters.all;

import com.robertx22.mine_and_slash.database.data.currency.reworked.ExileCurrency;
import com.robertx22.mine_and_slash.database.data.league.LeagueMechanic;
import com.robertx22.mine_and_slash.gui.wiki.BestiaryEntry;
import com.robertx22.mine_and_slash.gui.wiki.reworked.filters.GroupFilterEntry;
import net.minecraft.network.chat.MutableComponent;

public class CurrencyLeagueFilter extends GroupFilterEntry {
    LeagueMechanic league;

    public CurrencyLeagueFilter(LeagueMechanic league) {
        this.league = league;
    }

    @Override
    public boolean isValid(BestiaryEntry e) {
        return e.obj instanceof ExileCurrency currency && currency.drop_req.hasLeague() && currency.drop_req.getLeague().GUID().equals(league.GUID());
    }

    @Override
    public MutableComponent getName() {
        return league.locName();
    }
}
