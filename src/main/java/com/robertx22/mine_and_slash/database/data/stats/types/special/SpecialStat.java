package com.robertx22.mine_and_slash.database.data.stats.types.special;

import com.robertx22.mine_and_slash.database.data.stats.Stat;
import com.robertx22.mine_and_slash.database.data.stats.StatScaling;
import com.robertx22.mine_and_slash.saveclasses.unit.stat_ctx.modify.IStatCtxModifier;
import com.robertx22.mine_and_slash.uncommon.enumclasses.Elements;
import com.robertx22.mine_and_slash.uncommon.interfaces.IStatEffect;

public class SpecialStat extends Stat {

    String id;
    String longName;

    public SpecialStat(String id, String longName, IStatEffect effect) {
        this.id = id;
        this.longName = longName;
        this.statEffect = effect;
        this.is_long = true;

        this.scaling = StatScaling.NONE;

        this.is_perc = true;
        this.registerToExileRegistry();
    }

    public SpecialStat(String id, String longName, IStatCtxModifier ctxmod) {
        this(id, longName, (IStatEffect) null);
        this.statContextModifier = ctxmod;
    }

    @Override
    public Elements getElement() {
        return Elements.Physical;
    }

    @Override
    public String locDescForLangFile() {
        return "";
    }

    @Override
    public String locNameForLangFile() {
        return longName;
    }

    @Override
    public String GUID() {
        return id;
    }
}
