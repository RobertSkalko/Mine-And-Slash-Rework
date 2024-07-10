package com.robertx22.age_of_exile.saveclasses.gearitem.gear_parts;

import com.robertx22.age_of_exile.database.data.StatMod;
import com.robertx22.age_of_exile.database.data.unique_items.UniqueGear;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.saveclasses.ExactStatData;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.IGearPartTooltip;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.IRerollable;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.IStatsContainer;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.TooltipInfo;
import com.robertx22.age_of_exile.saveclasses.item_classes.GearItemData;
import com.robertx22.age_of_exile.saveclasses.item_classes.tooltips.TooltipStatInfo;
import com.robertx22.age_of_exile.saveclasses.item_classes.tooltips.TooltipStatWithContext;
import com.robertx22.age_of_exile.uncommon.MathHelper;
import com.robertx22.age_of_exile.uncommon.localization.Itemtips;
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
    public List<Component> GetTooltipString(TooltipInfo info, GearItemData gear) {

        info.minmax = getMinMax(gear);

        List<Component> list = new ArrayList<Component>();
        list.add(Itemtips.UNIQUE_STATS.locName().withStyle(ChatFormatting.YELLOW));
        getAllStatsWithCtx(gear, info).forEach(x -> {
            if (true || !x.mod.GetStat().is_long) {
                list.addAll(x.GetTooltipString(info));
            }
        });

        return list;

    }

    public UniqueGear getUnique(GearItemData gear) {
        return ExileDB.UniqueGears().get(gear.data.get(GearItemData.KEYS.UNIQUE_ID));

    }

    @Override
    public Part getPart() {
        return Part.UNIQUE_STATS;
    }

    public List<TooltipStatWithContext> getAllStatsWithCtx(GearItemData gear, TooltipInfo info) {
        List<TooltipStatWithContext> list = new ArrayList<>();
        int i = 0;
        for (StatMod mod : getUnique(gear).uniqueStats()) {
            ExactStatData exact = mod.ToExactStat(perc.get(i), gear.getLevel());
            list.add(new TooltipStatWithContext(new TooltipStatInfo(exact, perc.get(i), info), mod, (int) gear.getLevel()));
            i++;
        }
        return list;
    }

    @Override
    public List<ExactStatData> GetAllStats(GearItemData gear) {

        List<ExactStatData> list = new ArrayList<>();

        try {
            int i = 0;
            for (StatMod mod : getUnique(gear).uniqueStats()) {
                list.add(mod.ToExactStat(perc.get(i), gear.getLevel()));
                i++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;

    }
}
