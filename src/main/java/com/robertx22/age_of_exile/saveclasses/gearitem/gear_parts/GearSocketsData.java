package com.robertx22.age_of_exile.saveclasses.gearitem.gear_parts;

import com.robertx22.age_of_exile.database.data.MinMax;
import com.robertx22.age_of_exile.database.data.StatMod;
import com.robertx22.age_of_exile.database.data.profession.ExplainedResult;
import com.robertx22.age_of_exile.database.data.runewords.RuneWord;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.saveclasses.ExactStatData;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.IGearPartTooltip;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.IStatsContainer;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.TooltipInfo;
import com.robertx22.age_of_exile.saveclasses.item_classes.GearItemData;
import com.robertx22.age_of_exile.uncommon.localization.Chats;
import com.robertx22.age_of_exile.uncommon.localization.Itemtips;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;

import java.util.ArrayList;
import java.util.List;


public class GearSocketsData implements IStatsContainer, IGearPartTooltip {


    // socketed gems
    private List<SocketData> so = new ArrayList<>();
    // socket count
    private int sl = 0;

    private String rw = ""; // runeword
    private int rp = 0; // runeword perc

    public boolean hasRuneWord() {
        return ExileDB.RuneWords().isRegistered(rw);
    }

    public RuneWord getRuneWord() {
        return ExileDB.RuneWords().get(rw);
    }

    public void setRuneword(RuneWord r) {
        this.rw = r.GUID();
        this.rp = new MinMax(0, 100).random();
    }

    public List<SocketData> getSocketed() {
        return so;
    }


    public void addSocket() {
        sl++;
    }

    public void removeRune() {
        so.removeIf(x -> x.isRune());
    }

    public ExplainedResult canAddSocket(GearItemData gear) {


        if (sl < gear.getRarity().sockets.max) {
            return ExplainedResult.success();
        }
        return ExplainedResult.failure(Chats.ALREADY_MAX_SOCKETS.locName());
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

        if (hasRuneWord()) {
            for (StatMod stat : getRuneWord().stats) {
                list.add(stat.ToExactStat(rp, gear.lvl));
            }
        }
        return list;
    }


    @Override
    public List<Component> GetTooltipString(TooltipInfo info, GearItemData gear) {
        List<Component> list = new ArrayList<Component>();

        try {
            for (int i = 0; i < getSocketedGemsCount(); i++) {
                SocketData data = so.get(i);

                if (data.isGem() || data.isRune()) {
                    list.addAll(data.GetTooltipString(info, gear, true));
                }
            }

            for (int i = 0; i < gear.getEmptySockets(); i++) {
                list.add(Itemtips.EMPTY_SOCKET.locName().withStyle(ChatFormatting.GRAY));
            }


            if (hasRuneWord()) {
                var r = getRuneWord();
                list.add(Component.empty());
                list.add(r.locName().withStyle(ChatFormatting.DARK_PURPLE).append(ChatFormatting.DARK_PURPLE + ": "));
                r.stats.stream().map(x -> x.ToExactStat(rp, gear.lvl)).forEach(x -> list.addAll(x.GetTooltipString(info)));
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
