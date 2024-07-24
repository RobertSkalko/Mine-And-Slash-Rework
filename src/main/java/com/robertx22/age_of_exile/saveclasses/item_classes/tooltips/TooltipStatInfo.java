package com.robertx22.age_of_exile.saveclasses.item_classes.tooltips;

import com.robertx22.age_of_exile.database.OptScaleExactStat;
import com.robertx22.age_of_exile.database.data.rarities.GearRarity;
import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.saveclasses.ExactStatData;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.ITooltipList;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.StatRangeInfo;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;
import net.minecraft.network.chat.MutableComponent;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TooltipStatInfo implements ITooltipList {

    public Stat stat;

    public float firstValue;

    public ModType type;

    public StatRangeInfo tooltipInfo;

    public int percent = -99;

    public GearRarity affix_rarity = null;

    public TooltipStatInfo(ExactStatData data, int percent, StatRangeInfo info) {

        this.stat = data.getStat();
        this.firstValue = data.getFirstValue();
        this.type = data.getType();
        this.tooltipInfo = info;
        this.percent = percent;
    }

    public TooltipStatInfo(OptScaleExactStat data, StatRangeInfo info) {
        this.stat = data.getStat();
        this.firstValue = data.v1;
        this.type = data.getModType();
        this.tooltipInfo = info;
    }

    public boolean shouldShowDescriptions() {
        return tooltipInfo.shouldShowDescriptions();
    }

    public boolean useInDepthStats() {
        return tooltipInfo.useInDepthStats();
    }

    public void combine(TooltipStatInfo another) {
        this.firstValue += another.firstValue;

    }

    public boolean canBeCombined(TooltipStatInfo another) {
        return stat.GUID()
                .equals(another.stat.GUID()) && type.equals(another.type);
    }

    @Override
    public List<MutableComponent> GetTooltipString() {
        return stat.getTooltipList(new TooltipStatWithContext(this, null, null));
    }

    public static List<TooltipStatInfo> mergeDuplicates(List<TooltipStatInfo> duplicates) {

        List<TooltipStatInfo> list = new ArrayList<>();

        for (TooltipStatInfo duplicate : duplicates) {

            Optional<TooltipStatInfo> found = list.stream()
                    .filter(x -> x.canBeCombined(duplicate))
                    .findFirst();

            if (found.isPresent()) {
                found.get()
                        .combine(duplicate);

            } else {
                list.add(duplicate);
            }
        }

        return list;

    }

}
