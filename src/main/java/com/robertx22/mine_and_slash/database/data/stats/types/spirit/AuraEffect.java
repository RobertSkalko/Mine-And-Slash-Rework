package com.robertx22.mine_and_slash.database.data.stats.types.spirit;

import com.robertx22.mine_and_slash.database.data.stats.Stat;
import com.robertx22.mine_and_slash.saveclasses.unit.stat_ctx.StatContext;
import com.robertx22.mine_and_slash.saveclasses.unit.stat_ctx.modify.IStatCtxModifier;
import com.robertx22.mine_and_slash.uncommon.enumclasses.Elements;
import net.minecraft.ChatFormatting;

public class AuraEffect extends Stat {

    private AuraEffect() {
        this.icon = "\u2663";
        this.format = ChatFormatting.AQUA.getName();

        this.statContextModifier = new IStatCtxModifier() {
            @Override
            public StatContext.StatCtxType getCtxTypeNeeded() {
                return StatContext.StatCtxType.AURA;
            }
        };

    }

    public static AuraEffect getInstance() {
        return AuraEffect.SingletonHolder.INSTANCE;
    }

    @Override
    public boolean IsPercent() {
        return true;
    }

    @Override
    public Elements getElement() {
        return Elements.ALL;
    }

    @Override
    public String locDescForLangFile() {
        return "Increases stats your auras give you by %";
    }

    @Override
    public String GUID() {
        return "aura_effect";
    }

    @Override
    public String locNameForLangFile() {
        return "Augment Effect";
    }

    private static class SingletonHolder {
        private static final AuraEffect INSTANCE = new AuraEffect();
    }


}
