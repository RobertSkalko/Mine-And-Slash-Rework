package com.robertx22.mine_and_slash.saveclasses.jewel;

import com.robertx22.mine_and_slash.database.data.MinMax;
import com.robertx22.mine_and_slash.database.data.StatMod;
import com.robertx22.mine_and_slash.database.data.affixes.Affix;
import com.robertx22.mine_and_slash.database.data.aura.AuraGem;
import com.robertx22.mine_and_slash.database.registry.ExileDB;
import com.robertx22.mine_and_slash.saveclasses.ExactStatData;
import com.robertx22.mine_and_slash.saveclasses.gearitem.gear_bases.IStatCtx;
import com.robertx22.mine_and_slash.saveclasses.unit.stat_ctx.SimpleStatCtx;
import com.robertx22.mine_and_slash.saveclasses.unit.stat_ctx.StatContext;
import com.robertx22.mine_and_slash.uncommon.localization.Words;
import com.robertx22.mine_and_slash.uncommon.utilityclasses.ClientOnly;
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
                list.addAll(stat.GetTooltipString());
            }
        }

        return list;

    }
}
