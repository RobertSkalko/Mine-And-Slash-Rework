package com.robertx22.age_of_exile.saveclasses.gearitem.gear_parts;

import com.robertx22.age_of_exile.database.data.rarities.GearRarity;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.saveclasses.ExactStatData;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.IGearPartTooltip;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.IStatsContainer;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.StatRangeInfo;
import com.robertx22.age_of_exile.saveclasses.item_classes.GearItemData;
import com.robertx22.age_of_exile.uncommon.interfaces.data_items.IRarity;
import com.robertx22.age_of_exile.uncommon.localization.Itemtips;
import net.minecraft.network.chat.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GearEnchantData implements IStatsContainer, IGearPartTooltip {

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
    public List<Component> GetTooltipString(StatRangeInfo info, GearItemData gear) {

        List<Component> list = new ArrayList<>();

        GearRarity rarity = ExileDB.GearRarities().get(rar);

        list.add(Itemtips.Enchanted.locName(Component.literal(gear.data.get(GearItemData.KEYS.ENCHANT_TIMES) + "").withStyle(rarity.textFormatting())).withStyle(rarity.textFormatting()));

        for (ExactStatData stat : GetAllStats(gear)) {
            list.addAll(stat.GetTooltipString());
        }

        return list;
    }


    public int getPercent() {
        return ExileDB.GearRarities().get(rar).stat_percents.max;
    }

    @Override
    public List<ExactStatData> GetAllStats(GearItemData gear) {
        return ExileDB.Affixes().get(en).getStats().stream().map(x -> x.ToExactStat(getPercent(), gear.lvl)).collect(Collectors.toList());
    }
}
