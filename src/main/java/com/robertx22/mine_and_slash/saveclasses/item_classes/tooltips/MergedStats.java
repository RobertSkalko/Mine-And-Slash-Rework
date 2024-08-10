package com.robertx22.mine_and_slash.saveclasses.item_classes.tooltips;

import com.robertx22.mine_and_slash.saveclasses.ExactStatData;
import com.robertx22.mine_and_slash.saveclasses.gearitem.gear_bases.IGearPartTooltip;
import com.robertx22.mine_and_slash.saveclasses.gearitem.gear_bases.StatRangeInfo;
import com.robertx22.mine_and_slash.saveclasses.item_classes.GearItemData;
import net.minecraft.network.chat.Component;

import java.util.ArrayList;
import java.util.List;

public class MergedStats implements IGearPartTooltip {

    public List<TooltipStatInfo> mergedList;

    public MergedStats(List<ExactStatData> stats, StatRangeInfo info) {


        stats.removeIf(x -> x.getStat().is_long);

        List<TooltipStatInfo> infolist = new ArrayList<>();

        stats.forEach(x -> infolist.add(new TooltipStatInfo(x, -99, info)));

        this.mergedList = TooltipStatInfo.mergeDuplicates(infolist);

        //  this.mergedList.sort(Comparator.comparingInt(x -> 100 - x.stat.translate()
        //    .length()));

    }

    @Override
    public List<Component> GetTooltipString(StatRangeInfo info, GearItemData gear) {
        List<Component> tooltip = new ArrayList<>();
        mergedList.forEach(x -> tooltip.addAll(x.GetTooltipString()));
        return tooltip;
    }

    @Override
    public Part getPart() {
        return Part.OTHER;
    }
}
