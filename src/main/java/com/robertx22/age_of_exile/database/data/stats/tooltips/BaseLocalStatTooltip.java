package com.robertx22.age_of_exile.database.data.stats.tooltips;

import com.robertx22.age_of_exile.database.data.stats.name_regex.StatNameRegex;
import com.robertx22.age_of_exile.saveclasses.item_classes.tooltips.TooltipStatInfo;
import com.robertx22.age_of_exile.saveclasses.item_classes.tooltips.TooltipStatWithContext;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

import java.util.ArrayList;
import java.util.List;


public class BaseLocalStatTooltip implements IStatTooltipType {

    @Override
    public List<Component> getTooltipList(ChatFormatting format, TooltipStatWithContext ctx) {
        TooltipStatInfo info = ctx.statinfo;

        List<Component> list = new ArrayList<Component>();
        if (true) {
            String icon = "\u25CF";
            list.add(Component.literal(icon + " ")
                    .append(info.stat.locName())
                    .append(": ")
                    .withStyle(format != null ? format : ChatFormatting.WHITE)
                    .append(Component.literal((int) info.firstValue + "")
                            .withStyle(ChatFormatting.GRAY)));

            return list;

        }
        String icon = ChatFormatting.RED + info.stat.icon + " ";

        if (ctx.statinfo.stat.is_long) {
            icon = "";
        }

        MutableComponent txt = Component.literal(StatNameRegex.BASIC_LOCAL
                .translate(format, ctx, info.type, info.firstValue, info.stat));

        if (ctx.statinfo.stat.is_long) {
            return longStat(ctx, txt);
        }

        if (ctx.showStatRanges()) {
            txt.append(" ")
                    .append(NormalStatTooltip.getPercentageView(ctx.statinfo.percent));
        }


        list.add(txt);

        if (info.shouldShowDescriptions()) {
            list.addAll(info.stat.getCutDescTooltip());
        }

        return list;

    }
}
