package com.robertx22.age_of_exile.database.data.stats.tooltips;

import com.robertx22.age_of_exile.saveclasses.item_classes.tooltips.TooltipStatWithContext;
import com.robertx22.age_of_exile.uncommon.utilityclasses.TooltipUtils;
import com.robertx22.library_of_exile.wrappers.ExileText;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.MutableComponent;

import java.util.ArrayList;
import java.util.List;

public interface IStatTooltipType {
    List<MutableComponent> getTooltipList(ChatFormatting format, TooltipStatWithContext info);

    default List<MutableComponent> longStat(TooltipStatWithContext ctx, MutableComponent txt) {
        List<MutableComponent> list = new ArrayList<>();

        int i = 0;
        for (MutableComponent x : TooltipUtils.cutIfTooLong(txt, ChatFormatting.GRAY)) {
            if (i == 0) {
                x = ExileText.ofText(ChatFormatting.LIGHT_PURPLE + "\u25C6" + " " + ChatFormatting.GRAY).get().append(x);
            } else {
                x = ExileText.ofText(" ").get().append(x);
            }
            list.add(x);
            i++;
        }
        return list;

    }

}
