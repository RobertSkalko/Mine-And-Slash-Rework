package com.robertx22.age_of_exile.saveclasses.gearitem.gear_parts;

import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.age_of_exile.database.data.stats.tooltips.StatTooltipType;
import com.robertx22.age_of_exile.saveclasses.ExactStatData;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.*;
import com.robertx22.age_of_exile.saveclasses.item_classes.GearItemData;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;
import com.robertx22.library_of_exile.utils.RandomUtils;
import info.loenwind.autosave.annotations.Storable;
import info.loenwind.autosave.annotations.Store;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.IGearPart.Part;

@Storable
public class BaseStatsData implements IRerollable, IStatsContainer, IGearPartTooltip {

    @Store
    public Integer perc = 0;

    @Override
    public void RerollFully(GearItemData gear) {

        perc = getMinMax(gear).random();

        gear.rp = RandomUtils.randomFromList(new ArrayList<>(RareItemAffixNames.prefixAny
                .keySet()));
        gear.rs = RandomUtils.randomFromList(new ArrayList<>(RareItemAffixNames.getSuffixMap(gear.GetBaseGearType())
                .keySet()));

    }

    @Override
    public void RerollNumbers(GearItemData gear) {
        RerollFully(gear);
    }

    @Override
    public List<Component> GetTooltipString(TooltipInfo info, GearItemData gear) {


        List<ExactStatData> all = getBaseItemStats(gear);


        info.statTooltipType = StatTooltipType.BASE_LOCAL_STATS;

        List<Component> list = new ArrayList<>();
        list.add(new TextComponent(" "));

        for (ExactStatData stat : all) {
            list.addAll(stat.GetTooltipString(info));
        }

        info.statTooltipType = StatTooltipType.NORMAL;

        return list;

        /*
        List<Tuple<Stat, List<ITextComponent>>> pairs = new ArrayList<>();

        List<StatModifier> stats = gear.GetBaseGearType().base_stats;


        if (!info.useInDepthStats()) {

        }
        ExactStatData.combine(all);

        for (int i = 0; i < this.perc.size(); i++) {
            if (all.size() > i) {
                int perc = this.perc.get(i);
                if (all.size() > i) {

                    TooltipStatInfo ctx = new TooltipStatInfo(all.get(i), perc, info);

                    pairs.add(new Tuple(all.get(i)
                            .getStat()
                            , info.statTooltipType.impl.getTooltipList(gear.getRarity()
                            .textFormatting(), new TooltipStatWithContext(ctx, stats.size() > i ? stats.get(i) : null, (int) gear.getILVL()))));
                }
            }
        }

        pairs.sort(Comparator.comparingInt(x -> x.getA().order));

        pairs.forEach(x -> {
            list.addAll(x.getB());
        });

        info.statTooltipType = StatTooltipType.NORMAL;

        return list;


         */
    }

    @Override
    public boolean isBaseStats() {
        return true;
    }

    public List<ExactStatData> getBaseItemStats(GearItemData gear) {

        List<ExactStatData> local = new ArrayList<>();

        try {

            for (StatModifier mod : gear.GetBaseGearType()
                    .baseStats()) {
                local.add(mod.ToExactStat(perc, gear.getILVL()));

            }

            for (IStatsContainer co : gear.GetAllStatContainersExceptBase()) {
                for (ExactStatData stat : co.GetAllStats(gear)) {
                    if (stat.getType().isItemLocal()) {

                        for (ExactStatData l : local) {
                            if (l.getStat().GUID().equals(stat.getStatId())) {
                                if (l.getType().isFlat() && l.getType() == stat.getType()) {
                                    l.add(stat);
                                }
                            }
                        }

                    }
                }
            }


            for (IStatsContainer co : gear.GetAllStatContainersExceptBase()) {
                for (ExactStatData stat : co.GetAllStats(gear)) {
                    if (stat.getType().isItemLocal()) {

                        for (ExactStatData l : local) {
                            if (l.getStat().GUID().equals(stat.getStatId())) {
                                if (stat.getType() == ModType.ITEM_PERCENT) {
                                    l.percentIncrease = stat.getAverageValue();
                                    l.increaseByAddedPercent();
                                }
                            }
                        }

                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


        return local;
    }

    @Override
    public List<ExactStatData> GetAllStats(GearItemData gear) {
        return getBaseItemStats(gear).stream()
                .map(x -> ExactStatData.noScaling(x.getFirstValue(), x.getType().toNonItemType(), x.getStatId())).collect(Collectors.toList());

    }

    @Override
    public Part getPart() {
        return Part.BASE_STATS;
    }
}
