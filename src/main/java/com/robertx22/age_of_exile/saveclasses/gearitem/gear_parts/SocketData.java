package com.robertx22.age_of_exile.saveclasses.gearitem.gear_parts;

import com.robertx22.age_of_exile.database.data.gear_types.bases.SlotFamily;
import com.robertx22.age_of_exile.database.data.gems.Gem;
import com.robertx22.age_of_exile.database.data.runes.Rune;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.saveclasses.ExactStatData;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.IGearPart;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.IGearPartTooltip;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.IStatsContainer;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.TooltipInfo;
import com.robertx22.age_of_exile.saveclasses.item_classes.GearItemData;
import net.minecraft.network.chat.Component;

import java.util.ArrayList;
import java.util.List;


public class SocketData implements IGearPartTooltip, IStatsContainer {


    // gem id
    public String g = "";
    public int p = 0;


    public SocketData() {
    }


    public boolean isEmpty() {
        return getGem() == null;
    }

    public boolean isGem() {
        return getGem() != null;
    }

    public boolean isRune() {
        return getRune() != null;
    }

    @Override
    public List<Component> GetTooltipString(TooltipInfo info, GearItemData gear) {
        List<Component> list = new ArrayList<Component>();
        GetAllStats(gear).forEach(x -> list.addAll(x.GetTooltipString(info)));
        return list;
    }

    @Override
    public IGearPart.Part getPart() {
        return Part.SOCKETS;
    }

    public Gem getGem() {
        if (ExileDB.Gems().isRegistered(g)) {
            return ExileDB.Gems().get(g);
        }
        return null;
    }

    public Rune getRune() {
        if (ExileDB.Runes().isRegistered(g)) {
            return ExileDB.Runes().get(g);
        }
        return null;
    }

    @Override
    public List<ExactStatData> GetAllStats(GearItemData gear) {
        SlotFamily fam = gear.GetBaseGearType()
                .family();

        List<ExactStatData> stats = new ArrayList<>();

        try {
            if (isGem()) {
                getGem().getFor(fam).forEach(x -> {
                    stats.add(x.toExactStat((int) gear.getLevel()));
                });
            }
            if (isRune()) {
                getRune().getFor(fam).forEach(x -> {
                    stats.add(x.ToExactStat(p, (int) gear.getLevel()));
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return stats;

    }

}
