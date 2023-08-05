package com.robertx22.age_of_exile.saveclasses.gearitem.gear_parts;

import com.robertx22.age_of_exile.database.data.gems.Gem;
import com.robertx22.age_of_exile.saveclasses.ExactStatData;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.IGearPartTooltip;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.IStatsContainer;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.TooltipInfo;
import com.robertx22.age_of_exile.saveclasses.item_classes.GearItemData;
import com.robertx22.age_of_exile.uncommon.utilityclasses.TooltipUtils;
import com.robertx22.age_of_exile.uncommon.wrappers.SText;
import info.loenwind.autosave.annotations.Storable;
import info.loenwind.autosave.annotations.Store;
import net.minecraft.network.chat.Component;
import net.minecraft.ChatFormatting;

import java.util.ArrayList;
import java.util.List;

import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.IGearPart.Part;

@Storable
public class GearSocketsData implements IStatsContainer, IGearPartTooltip {

    @Store
    public List<SocketData> sockets = new ArrayList<>();

    @Store
    private int slots = 1;

    public int getSocketedGemsCount() {
        return sockets.size();
    }

    @Override
    public List<ExactStatData> GetAllStats(GearItemData gear) {
        List<ExactStatData> list = new ArrayList<>();
        for (int i = 0; i < gear.getTotalSockets(); i++) {
            if (sockets.size() > i) {
                list.addAll(sockets.get(i)
                    .GetAllStats(gear));
            }
        }
        return list;
    }

    @Override
    public List<Component> GetTooltipString(TooltipInfo info, GearItemData gear) {
        List<Component> list = new ArrayList<Component>();

        for (int i = 0; i < gear.getTotalSockets(); i++) {
            if (sockets.size() > i) {
                SocketData data = sockets.get(i);
                Gem gem = data.getGem();
                list.add(new SText(gem.getFormat() + "[" + TooltipUtils.STAR + "] ").append(data.GetTooltipString(info, gear)
                    .get(0)));
            }
        }

        for (int i = 0; i < gear.getEmptySockets(); i++) {
            list.add(new SText(ChatFormatting.YELLOW + "[Socket]"));
        }

        return list;
    }

    @Override
    public Part getPart() {
        return null;
    }
}
