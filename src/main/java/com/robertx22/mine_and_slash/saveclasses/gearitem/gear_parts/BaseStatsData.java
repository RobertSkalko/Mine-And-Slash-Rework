package com.robertx22.mine_and_slash.saveclasses.gearitem.gear_parts;

import com.robertx22.mine_and_slash.database.data.StatMod;
import com.robertx22.mine_and_slash.database.data.stats.tooltips.StatTooltipType;
import com.robertx22.mine_and_slash.database.data.stats.types.gear_base.IBaseStatModifier;
import com.robertx22.mine_and_slash.itemstack.ExileStack;
import com.robertx22.mine_and_slash.itemstack.StackKeys;
import com.robertx22.mine_and_slash.saveclasses.ExactStatData;
import com.robertx22.mine_and_slash.saveclasses.gearitem.gear_bases.IGearPartTooltip;
import com.robertx22.mine_and_slash.saveclasses.gearitem.gear_bases.IRerollable;
import com.robertx22.mine_and_slash.saveclasses.gearitem.gear_bases.IStatsContainer;
import com.robertx22.mine_and_slash.saveclasses.gearitem.gear_bases.StatRangeInfo;
import com.robertx22.mine_and_slash.saveclasses.item_classes.GearItemData;
import com.robertx22.mine_and_slash.uncommon.enumclasses.ModType;
import net.minecraft.network.chat.Component;

import java.util.ArrayList;
import java.util.List;


public class BaseStatsData implements IRerollable, IStatsContainer, IGearPartTooltip {


    public Integer p = 0;

    @Override
    public void RerollFully(GearItemData gear) {

        p = gear.getRarity().stat_percents.random();

    }

    @Override
    public void RerollNumbers(GearItemData gear) {
        RerollFully(gear);
    }


    @Override
    public List<Component> GetTooltipString(StatRangeInfo info, ExileStack stack) {

        var gear = stack.get(StackKeys.GEAR).get();

        List<ExactStatData> all = GetAllStats(stack);


        info.statTooltipType = StatTooltipType.BASE_LOCAL_STATS;

        List<Component> list = new ArrayList<>();
        list.add(Component.literal(" "));

        for (ExactStatData stat : all) {
            list.addAll(stat.GetTooltipString());
        }

        info.statTooltipType = StatTooltipType.NORMAL;

        return list;


    }

    @Override
    public boolean isBaseStats() {
        return true;
    }

    @Override
    public List<ExactStatData> GetAllStats(ExileStack stack) {

        var gear = stack.get(StackKeys.GEAR).get();

        List<ExactStatData> baseStats = new ArrayList<>();

        try {
            int perc = (int) (p * gear.getQualityBaseStatsMulti(stack));

            for (StatMod mod : gear.GetBaseGearType().baseStats()) {
                baseStats.add(mod.ToExactStat(perc, gear.getLevel()));
            }

            var list = gear.GetAllStatContainersExceptBase();

            var allstats = new ArrayList<ExactStatData>();
            for (IStatsContainer cont : list) {
                allstats.addAll(cont.GetAllStats(stack));
            }
            allstats.removeIf(x -> x.getStat() instanceof IBaseStatModifier == false);

            for (ExactStatData affixStatData : allstats) {
                if (affixStatData.getStat() instanceof IBaseStatModifier mod) {
                    for (ExactStatData baseStat : baseStats) {
                        if (mod.canModifyBaseStat(baseStat.getStat())) {
                            if (affixStatData.getType() == ModType.FLAT) {
                                baseStat.add(ExactStatData.noScaling(affixStatData.getValue(), ModType.FLAT, baseStat.getStatId()));
                            }
                        }
                    }
                }
            }


            for (ExactStatData affixStatData : allstats) {
                if (affixStatData.getStat() instanceof IBaseStatModifier mod) {
                    for (ExactStatData baseStat : baseStats) {
                        if (mod.canModifyBaseStat(baseStat.getStat())) {
                            if (affixStatData.getType() == ModType.PERCENT) {
                                baseStat.percentIncrease = affixStatData.getValue();
                                baseStat.increaseByAddedPercent();
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
