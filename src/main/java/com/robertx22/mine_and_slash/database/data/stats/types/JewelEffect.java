package com.robertx22.mine_and_slash.database.data.stats.types;

import com.robertx22.mine_and_slash.database.data.stats.Stat;
import com.robertx22.mine_and_slash.saveclasses.unit.stat_ctx.StatContext;
import com.robertx22.mine_and_slash.saveclasses.unit.stat_ctx.modify.IStatCtxModifier;
import com.robertx22.mine_and_slash.uncommon.enumclasses.Elements;
import net.minecraft.ChatFormatting;

public class JewelEffect extends Stat {

    private JewelEffect() {
        this.icon = "\u2663";
        this.format = ChatFormatting.GOLD.getName();

        this.statContextModifier = new IStatCtxModifier() {
            @Override
            public StatContext.StatCtxType getCtxTypeNeeded() {
                return StatContext.StatCtxType.JEWEL;
            }
        };

    }

    public static JewelEffect getInstance() {
        return JewelEffect.SingletonHolder.INSTANCE;
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
        return "Increases stats your jewels give you by %";
    }

    @Override
    public String GUID() {
        return "jewel_effect";
    }

    @Override
    public String locNameForLangFile() {
        return "Jewel Effect";
    }

    private static class SingletonHolder {
        private static final JewelEffect INSTANCE = new JewelEffect();
    }
}