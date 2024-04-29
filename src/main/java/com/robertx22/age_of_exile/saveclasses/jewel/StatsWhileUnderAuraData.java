package com.robertx22.age_of_exile.saveclasses.jewel;

import com.robertx22.age_of_exile.database.data.MinMax;
import com.robertx22.age_of_exile.database.data.StatMod;
import com.robertx22.age_of_exile.database.data.affixes.Affix;
import com.robertx22.age_of_exile.database.data.aura.AuraGem;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.saveclasses.ExactStatData;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.IStatCtx;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.TooltipInfo;
import com.robertx22.age_of_exile.saveclasses.unit.stat_ctx.SimpleStatCtx;
import com.robertx22.age_of_exile.saveclasses.unit.stat_ctx.StatContext;
import com.robertx22.age_of_exile.uncommon.localization.Words;
import com.robertx22.age_of_exile.uncommon.utilityclasses.ClientOnly;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StatsWhileUnderAuraData implements IStatCtx {


    public String affix = "";
    public int perc = 0;
    public int lvl = 0;

    public StatsWhileUnderAuraData(Affix affix, int lvl) {
        this.affix = affix.GUID();
        this.perc = new MinMax(0, 100).random();
        this.lvl = lvl;
    }

    public StatsWhileUnderAuraData() {
    }

    public AuraGem getAura() {
        return ExileDB.AuraGems().get(getAffix().eye_aura_req);
    }

    public Affix getAffix() {
        return ExileDB.Affixes().get(affix);
    }

    @Override
    public List<StatContext> getStatAndContext(LivingEntity en) {
        List<ExactStatData> stats = new ArrayList<>();

        var affix = getAffix();

        for (StatMod mod : affix.getStats()) {
            stats.add(mod.ToExactStat(perc, lvl));
        }
        return Arrays.asList(new SimpleStatCtx(StatContext.StatCtxType.JEWEL, stats));
    }

    public List<Component> getTooltip() {

        List<Component> list = new ArrayList<>();

        list.add(Words.WHILE_UNDER_AURA.locName(getAura().locName().withStyle(ChatFormatting.LIGHT_PURPLE)).withStyle(ChatFormatting.GOLD));

        for (StatContext ctx : getStatAndContext(ClientOnly.getPlayer())) {
            for (ExactStatData stat : ctx.stats) {
                list.addAll(stat.GetTooltipString(new TooltipInfo()));
            }
        }

        return list;

    }
}
