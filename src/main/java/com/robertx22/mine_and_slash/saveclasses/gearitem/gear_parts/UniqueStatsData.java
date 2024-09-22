package com.robertx22.mine_and_slash.saveclasses.gearitem.gear_parts;

import com.robertx22.mine_and_slash.database.data.StatMod;
import com.robertx22.mine_and_slash.database.data.unique_items.UniqueGear;
import com.robertx22.mine_and_slash.database.registry.ExileDB;
import com.robertx22.mine_and_slash.itemstack.CustomItemData;
import com.robertx22.mine_and_slash.itemstack.ExileStack;
import com.robertx22.mine_and_slash.saveclasses.ExactStatData;
import com.robertx22.mine_and_slash.saveclasses.gearitem.gear_bases.*;
import com.robertx22.mine_and_slash.saveclasses.item_classes.GearItemData;
import com.robertx22.mine_and_slash.saveclasses.item_classes.tooltips.TooltipStatInfo;
import com.robertx22.mine_and_slash.saveclasses.item_classes.tooltips.TooltipStatWithContext;
import com.robertx22.mine_and_slash.uncommon.MathHelper;
import com.robertx22.mine_and_slash.uncommon.localization.Itemtips;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;

import java.util.ArrayList;
import java.util.List;


public class UniqueStatsData implements IGearPartTooltip, IRerollable, IStatsContainer {

    public UniqueStatsData() {

    }

    public List<Integer> perc = new ArrayList<Integer>();

    @Override
    public void RerollFully(GearItemData gear) {
        this.RerollNumbers(gear);
    }

    public static int MAX_STATS = 10;

    @Override
    public void RerollNumbers(GearItemData gear) {
        perc.clear();
        // wont ever have more than 10 unique stats.
        for (int i = 0; i < MAX_STATS; i++) {
            perc.add(getMinMax(gear).random());
        }

    }

    public void increaseAllBy(GearItemData gear, int add) {

        int max = getMinMax(gear).max;

        // wont ever have more than 10 unique stats.
        for (int i = 0; i < perc.size(); i++) {

            int num = MathHelper.clamp(perc.get(i) + add, 0, max);

            perc.set(i, num);

        }

    }


    @Override
    public List<Component> GetTooltipString(StatRangeInfo info, ExileStack stack) {

        var gear = stack.GEAR.get();
        List<Component> list = new ArrayList<Component>();
        list.add(Itemtips.UNIQUE_STATS.locName().withStyle(ChatFormatting.YELLOW));
        getAllStatsWithCtx(stack, new StatRangeInfo(ModRange.of(gear.getRarity().stat_percents))).forEach(x -> {
            list.addAll(x.GetTooltipString());
        });

        return list;

    }

    public UniqueGear getUnique(ExileStack stack) {
        return ExileDB.UniqueGears().get(stack.CUSTOM.getOrCreate().data.get(CustomItemData.KEYS.UNIQUE_ID));
    }

    @Override
    public Part getPart() {
        return Part.UNIQUE_STATS;
    }

    public List<TooltipStatWithContext> getAllStatsWithCtx(ExileStack stack, StatRangeInfo info) {
        var gear = stack.GEAR.get();
        List<TooltipStatWithContext> list = new ArrayList<>();
        int i = 0;
        for (StatMod mod : getUnique(stack).uniqueStats()) {
            ExactStatData exact = mod.ToExactStat(perc.get(i), gear.getLevel());
            list.add(new TooltipStatWithContext(new TooltipStatInfo(exact, perc.get(i), info), mod, (int) gear.getLevel()));
            i++;
        }
        return list;
    }

    @Override
    public List<ExactStatData> GetAllStats(ExileStack stack) {
        var gear = stack.GEAR.get();
        List<ExactStatData> list = new ArrayList<>();

        try {
            int i = 0;
            for (StatMod mod : getUnique(stack).uniqueStats()) {
                list.add(mod.ToExactStat(perc.get(i), gear.getLevel()));
                i++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;

    }
}
