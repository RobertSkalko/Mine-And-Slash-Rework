package com.robertx22.age_of_exile.database.data.stats.types.spirit;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.saveclasses.ExactStatData;
import com.robertx22.age_of_exile.saveclasses.unit.stat_ctx.StatContext;
import com.robertx22.age_of_exile.saveclasses.unit.stat_ctx.modify.IStatCtxModifier;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import net.minecraft.ChatFormatting;

public class AuraEffect extends Stat {

    private AuraEffect() {
        this.icon = "\u2663";
        this.format = ChatFormatting.AQUA.getName();

        this.statContextModifier = new IStatCtxModifier() {
            @Override
            public void modify(ExactStatData stat, StatContext ctx) {
                float multi = 1F + stat.getValue() / 100F;
                ctx.stats.forEach(x -> {
                    x.multiplyBy(multi);
                });
            }

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
