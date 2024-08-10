package com.robertx22.mine_and_slash.database.data.stats.tooltips;

import com.robertx22.mine_and_slash.database.data.StatMod;
import com.robertx22.mine_and_slash.database.data.rarities.GearRarity;
import com.robertx22.mine_and_slash.database.data.stats.IUsableStat;
import com.robertx22.mine_and_slash.database.data.stats.types.LearnSpellStat;
import com.robertx22.mine_and_slash.saveclasses.gearitem.gear_bases.ModRange;
import com.robertx22.mine_and_slash.saveclasses.item_classes.tooltips.TooltipStatInfo;
import com.robertx22.mine_and_slash.saveclasses.item_classes.tooltips.TooltipStatWithContext;
import com.robertx22.mine_and_slash.uncommon.utilityclasses.TooltipUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

import java.util.ArrayList;
import java.util.List;


public class NormalStatTooltip implements IStatTooltipType {


    @Override
    public List<MutableComponent> getTooltipList(ChatFormatting format, TooltipStatWithContext ctx) {

        TooltipStatInfo info = ctx.statinfo;


        List<MutableComponent> list = new ArrayList<>();

        MutableComponent txt = Component.literal("").append(Component.literal(info.stat.getStatNameRegex().translate(format, ctx, info.type, info.firstValue, info.stat)).withStyle(ChatFormatting.BLUE));

        if (ctx.statinfo.stat.is_long) {
            return longStat(ctx, txt);
        }


        if (ctx.showStatRanges()) {
            txt.append(" ").append(getPercentageView(info.percent, ctx.mod, ctx.statinfo.affix_rarity, ctx.level, info.tooltipInfo.minmax));
        }

        list.add(txt);

        if (info.shouldShowDescriptions() && !(info.stat instanceof LearnSpellStat)) {
            list.addAll(info.stat.getCutDescTooltip());

            if (ctx.statinfo.stat instanceof IUsableStat usable) {
                list.add(Component.literal("Formula: Stat Percent = statNum / ( base + statNum)"));
            }
        }

        return list;

    }

    public static MutableComponent getPercentageView(int perc, StatMod mod, GearRarity rar, int lvl, ModRange max) {
        if (mod == null || max.hide) {
            return Component.empty();
        }


        ChatFormatting format = ChatFormatting.RED;
        if (perc > 25) {
            format = ChatFormatting.YELLOW;
        }
        if (perc > 50) {
            format = ChatFormatting.GREEN;
        }
        if (perc > 75) {
            format = ChatFormatting.AQUA;
        }
        if (perc > 90) {
            format = ChatFormatting.LIGHT_PURPLE;
        }

        if (rar != null) {
            format = rar.textFormatting();
        }

        var v1 = mod.ToExactStat(max.minmax.min, lvl).getValue();
        var v2 = mod.ToExactStat(max.minmax.max, lvl).getValue();

        var mid = Component.literal((int) v1 + "" + " - " + (int) v2 + "");

        var text = Component.literal(" [").append(mid).append("%]").withStyle(format);

        if (rar != null) {
            text.append(Component.literal(" [" + TooltipUtils.rarityShort(rar).getString() + "]").withStyle(rar.textFormatting()));
        }

        text.append(Component.literal(" [" + perc + "%]" + " {" + max.minmax.min + "%" + "-" + max.minmax.max + "%}").withStyle(format));

        return text;

    }

}
