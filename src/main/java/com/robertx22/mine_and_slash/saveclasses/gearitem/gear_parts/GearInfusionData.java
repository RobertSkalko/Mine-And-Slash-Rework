package com.robertx22.mine_and_slash.saveclasses.gearitem.gear_parts;

import com.robertx22.mine_and_slash.database.data.rarities.GearRarity;
import com.robertx22.mine_and_slash.database.registry.ExileDB;
import com.robertx22.mine_and_slash.itemstack.CustomItemData;
import com.robertx22.mine_and_slash.itemstack.ExileStack;
import com.robertx22.mine_and_slash.saveclasses.ExactStatData;
import com.robertx22.mine_and_slash.saveclasses.gearitem.gear_bases.IGearPartTooltip;
import com.robertx22.mine_and_slash.saveclasses.gearitem.gear_bases.IStatsContainer;
import com.robertx22.mine_and_slash.saveclasses.gearitem.gear_bases.StatRangeInfo;
import com.robertx22.mine_and_slash.uncommon.interfaces.data_items.IRarity;
import com.robertx22.mine_and_slash.uncommon.localization.Itemtips;
import net.minecraft.network.chat.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GearInfusionData implements IStatsContainer, IGearPartTooltip {

    public String en = "";
    public String rar = IRarity.COMMON_ID;


    public boolean isEmpty() {
        return !ExileDB.Affixes().isRegistered(en);
    }

    @Override
    public Part getPart() {
        return Part.OTHER;
    }

    public boolean canUpgradeToRarity(String newrar) {
        var r = ExileDB.GearRarities().get(this.rar);
        return r.hasHigherRarity() && r.getHigherRarity().GUID().equals(newrar);
    }

    @Override
    public List<Component> GetTooltipString(StatRangeInfo info, ExileStack stack) {

        var gear = stack.GEAR.get();

        List<Component> list = new ArrayList<>();

        GearRarity rarity = ExileDB.GearRarities().get(rar);

        list.add(Itemtips.INFUSED.locName(Component.literal(stack.CUSTOM.getOrCreate().data.get(CustomItemData.KEYS.ENCHANT_TIMES) + "").withStyle(rarity.textFormatting())).withStyle(rarity.textFormatting()));

        for (ExactStatData stat : GetAllStats(stack)) {
            list.addAll(stat.GetTooltipString());
        }

        return list;
    }


    public int getPercent() {
        return ExileDB.GearRarities().get(rar).stat_percents.max;
    }

    @Override
    public List<ExactStatData> GetAllStats(ExileStack stack) {
        return ExileDB.Affixes().get(en).getStats().stream().map(x -> x.ToExactStat(getPercent(), stack.GEAR.get().lvl)).collect(Collectors.toList());
    }
}
