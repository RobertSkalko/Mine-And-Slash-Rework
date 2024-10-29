package com.robertx22.mine_and_slash.saveclasses.gearitem.gear_parts;

import com.robertx22.library_of_exile.wrappers.ExileText;
import com.robertx22.mine_and_slash.database.data.affixes.Affix;
import com.robertx22.mine_and_slash.database.data.requirements.bases.GearRequestedFor;
import com.robertx22.mine_and_slash.database.registry.ExileDB;
import com.robertx22.mine_and_slash.itemstack.ExileStack;
import com.robertx22.mine_and_slash.itemstack.StackKeys;
import com.robertx22.mine_and_slash.saveclasses.ExactStatData;
import com.robertx22.mine_and_slash.saveclasses.gearitem.gear_bases.IGearPartTooltip;
import com.robertx22.mine_and_slash.saveclasses.gearitem.gear_bases.IRerollable;
import com.robertx22.mine_and_slash.saveclasses.gearitem.gear_bases.IStatsContainer;
import com.robertx22.mine_and_slash.saveclasses.gearitem.gear_bases.StatRangeInfo;
import com.robertx22.mine_and_slash.saveclasses.item_classes.GearItemData;
import com.robertx22.mine_and_slash.saveclasses.item_classes.tooltips.TooltipStatInfo;
import com.robertx22.mine_and_slash.saveclasses.item_classes.tooltips.TooltipStatWithContext;
import com.robertx22.mine_and_slash.uncommon.localization.Words;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


public class ImplicitStatsData implements IGearPartTooltip, IRerollable, IStatsContainer {


    public Integer p = 0;
    public String imp = "";

    @Override
    public void RerollFully(GearItemData gear) {
        var opt = ExileDB.Affixes().getFilterWrapped(x -> x.type == Affix.AffixSlot.implicit && x.meetsRequirements(new GearRequestedFor(gear)));
        if (!opt.list.isEmpty()) {
            this.imp = opt.random().GUID();
        }
        p = getMinMax(gear).random();

    }

    @Override
    public void RerollNumbers(GearItemData gear) {
        RerollFully(gear);
    }

    @Override
    public List<Component> GetTooltipString(StatRangeInfo info, ExileStack stack) {
        var gear = stack.get(StackKeys.GEAR).get();

        List<Component> list = new ArrayList<>();

        List<ExactStatData> stats = GetAllStats(stack);


        if (!stats.isEmpty()) {
            list.add(Words.IMPLICIT_STATS.locName().withStyle(ChatFormatting.BLUE));

            getAllStatsWithCtx(gear, info).forEach(x -> list.addAll(x.GetTooltipString()));
            list.add(ExileText.ofText("").get());

        }
        return list;
    }


    public Affix get() {
        return ExileDB.Affixes().get(imp);
    }

    public boolean has() {
        return ExileDB.Affixes().isRegistered(imp);
    }

    @Override
    public Part getPart() {
        return Part.IMPLICIT_STATS;
    }

    public List<TooltipStatWithContext> getAllStatsWithCtx(GearItemData gear, StatRangeInfo info) {
        List<TooltipStatWithContext> list = new ArrayList<>();

        if (has()) {

            get().getStats()
                    .forEach(x -> {
                        ExactStatData exact = x.ToExactStat(p, gear.getLevel());
                        list.add(new TooltipStatWithContext(new TooltipStatInfo(exact, p, info), x, (int) gear.getLevel()));
                    });
        }
        return list;
    }

    @Override
    public List<ExactStatData> GetAllStats(ExileStack stack) {

        if (has()) {
            var gear = stack.get(StackKeys.GEAR).get();
            return get().getStats()
                    .stream()
                    .map(x -> x.ToExactStat(p, gear.lvl))
                    .collect(Collectors.toList());
        }
        return Arrays.asList();
    }
}