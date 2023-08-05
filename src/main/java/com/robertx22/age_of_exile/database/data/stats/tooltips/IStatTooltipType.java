package com.robertx22.age_of_exile.database.data.stats.tooltips;

import com.robertx22.age_of_exile.saveclasses.item_classes.tooltips.TooltipStatWithContext;
import com.robertx22.age_of_exile.uncommon.utilityclasses.TooltipUtils;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.ChatFormatting;

import java.util.ArrayList;
import java.util.List;

public interface IStatTooltipType {
    List<Component> getTooltipList(ChatFormatting format, TooltipStatWithContext info);

    default List<Component> longStat(TooltipStatWithContext ctx, MutableComponent txt) {
        List<Component> list = new ArrayList<>();

        int i = 0;
        for (MutableComponent x : TooltipUtils.cutIfTooLong(txt, ChatFormatting.GRAY)) {
            if (i == 0) {
                x = new TextComponent(ChatFormatting.LIGHT_PURPLE + "\u25C6" + " " + ChatFormatting.GRAY).append(x);
            } else {
                x = new TextComponent(" ").append(x);
            }
            list.add(x);
            i++;
        }
        return list;

    }

}
