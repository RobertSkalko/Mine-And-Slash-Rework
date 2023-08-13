package com.robertx22.age_of_exile.saveclasses.gearitem.gear_parts;

import com.robertx22.age_of_exile.database.data.StatMod;
import com.robertx22.age_of_exile.database.data.stats.tooltips.StatTooltipType;
import com.robertx22.age_of_exile.database.data.stats.types.gear_base.IBaseStatModifier;
import com.robertx22.age_of_exile.saveclasses.ExactStatData;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.*;
import com.robertx22.age_of_exile.saveclasses.item_classes.GearItemData;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;
import com.robertx22.library_of_exile.utils.RandomUtils;
import net.minecraft.network.chat.Component;

import java.util.ArrayList;
import java.util.List;


public class BaseStatsData implements IRerollable, IStatsContainer, IGearPartTooltip {


    public Integer p = 0;

    @Override
    public void RerollFully(GearItemData gear) {

        p = getMinMax(gear).random();

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


        List<ExactStatData> all = GetAllStats(gear);


        info.statTooltipType = StatTooltipType.BASE_LOCAL_STATS;

        List<Component> list = new ArrayList<>();
        list.add(Component.literal(" "));

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

    @Override
    public List<ExactStatData> GetAllStats(GearItemData affixStats) {

        List<ExactStatData> baseStats = new ArrayList<>();

        try {

            for (StatMod mod : affixStats.GetBaseGearType()
                    .baseStats()) {
                baseStats.add(mod.ToExactStat(p, affixStats.getLevel()));

            }


            for (IStatsContainer affixStat : affixStats.GetAllStatContainersExceptBase()) {
                for (ExactStatData affixStatData : affixStat.GetAllStats(affixStats)) {
                    if (affixStatData.getStat() instanceof IBaseStatModifier mod) {
                        for (ExactStatData baseStat : baseStats) {
                            if (mod.canModifyBaseStat(baseStat.getStat())) {
                                if (affixStatData.getType() == ModType.FLAT) {
                                    baseStat.add(ExactStatData.noScaling(affixStatData.getAverageValue(), ModType.FLAT, baseStat.getStatId()));
                                }
                            }
                        }

                    }
                }
            }

            for (IStatsContainer affixStat : affixStats.GetAllStatContainersExceptBase()) {
                for (ExactStatData affixStatData : affixStat.GetAllStats(affixStats)) {
                    if (affixStatData.getStat() instanceof IBaseStatModifier mod) {
                        for (ExactStatData baseStat : baseStats) {
                            if (mod.canModifyBaseStat(baseStat.getStat())) {
                                if (affixStatData.getType() == ModType.PERCENT) {
                                    //baseStat.add(ExactStatData.noScaling(affixStatData.getAverageValue(), ModType.PERCENT, baseStat.getStatId()));
                                    baseStat.percentIncrease = affixStatData.getAverageValue();
                                    baseStat.increaseByAddedPercent();
                                }
                            }
                        }

                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


        return baseStats;
    }

    /*
    @Override
    public List<ExactStatData> GetAllStats(GearItemData gear) {

        return getBaseItemStats(gear).stream().map(x -> ExactStatData.noScaling(x.getFirstValue(), x.getType(), x.getStatId())).collect(Collectors.toList());

    }


     */
    @Override
    public Part getPart() {
        return Part.BASE_STATS;
    }
}
