package com.robertx22.age_of_exile.database.data.stats.tooltips;

import com.robertx22.age_of_exile.saveclasses.item_classes.tooltips.TooltipStatInfo;
import com.robertx22.age_of_exile.saveclasses.item_classes.tooltips.TooltipStatWithContext;
import com.robertx22.age_of_exile.uncommon.utilityclasses.TooltipUtils;
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

        MutableComponent txt = Component.literal(ChatFormatting.BLUE + info.stat.getStatNameRegex()
                .translate(format, ctx, info.type, info.firstValue, info.stat));

        if (ctx.statinfo.stat.is_long) {
            return longStat(ctx, txt);
        }


        if (ctx.showStatRanges()) {
            txt.append(" ")
                    .append(getPercentageView(ctx.statinfo.percent));
        }
        if (ctx.showStatRanges()) {
            if (ctx.statinfo.affix_rarity != null) {
                txt.append(ctx.statinfo.affix_rarity.textFormatting().toString() + " [").append(TooltipUtils.rarityShort(ctx.statinfo.affix_rarity)).append("]" + ChatFormatting.RESET);
            }
        }

        list.add(txt);

        if (info.shouldShowDescriptions()) {
            list.addAll(info.stat.getCutDescTooltip());
        }

        return list;

    }

    public static MutableComponent getPercentageView(int perc) {
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

        return Component.literal(format + " [" + perc + "%]" + ChatFormatting.RESET);

    }

}
