package com.robertx22.age_of_exile.saveclasses.gearitem.gear_parts;

import com.robertx22.age_of_exile.database.data.gems.Gem;
import com.robertx22.age_of_exile.database.data.runes.Rune;
import com.robertx22.age_of_exile.mmorpg.UNICODE;
import com.robertx22.age_of_exile.saveclasses.ExactStatData;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.IGearPartTooltip;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.IStatsContainer;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.TooltipInfo;
import com.robertx22.age_of_exile.saveclasses.item_classes.GearItemData;
import com.robertx22.library_of_exile.wrappers.ExileText;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;

import java.util.ArrayList;
import java.util.List;


public class GearSocketsData implements IStatsContainer, IGearPartTooltip {


    // socketed gems
    private List<SocketData> so = new ArrayList<>();
    // socket count
    private int sl = 0;

    public List<SocketData> getSocketed() {
        return so;
    }


    public void addSocket() {
        sl++;
    }

    public void removeRune() {
        so.removeIf(x -> x.isRune());
    }

    public boolean canAddSocket(GearItemData gear) {
        return sl < gear.getRarity().max_sockets;
    }

    public int getTotalSockets() {
        return sl;
    }

    public int getSocketedGemsCount() {
        return so.size();
    }

    @Override
    public List<ExactStatData> GetAllStats(GearItemData gear) {
        List<ExactStatData> list = new ArrayList<>();
        for (SocketData s : this.getSocketed()) {
            list.addAll(s.GetAllStats(gear));
        }
        return list;
    }


    @Override
    public List<Component> GetTooltipString(TooltipInfo info, GearItemData gear) {
        List<Component> list = new ArrayList<Component>();

        try {
            for (int i = 0; i < getSocketedGemsCount(); i++) {
                SocketData data = so.get(i);
                if (data.isGem()) {
                    Gem gem = data.getGem();
                    list.add(ExileText.ofText(gem.getFormat() + "[" + UNICODE.STAR + "] ").get().append(data.GetTooltipString(info, gear)
                            .get(0)));
                }

                if (data.isRune()) {
                    Rune gem = data.getRune();
                    list.add(ExileText.ofText(gem.getFormat(data) + "[" + UNICODE.CUBE + "] ").get().append(data.GetTooltipString(info, gear).get(0)));
                }
            }

            for (int i = 0; i < gear.getEmptySockets(); i++) {
                list.add(ExileText.ofText(ChatFormatting.YELLOW + "[Socket]").get());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    @Override
    public Part getPart() {
        return null;
    }
}
