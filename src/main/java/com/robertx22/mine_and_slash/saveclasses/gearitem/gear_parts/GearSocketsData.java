package com.robertx22.mine_and_slash.saveclasses.gearitem.gear_parts;

import com.robertx22.mine_and_slash.database.data.MinMax;
import com.robertx22.mine_and_slash.database.data.StatMod;
import com.robertx22.mine_and_slash.database.data.profession.ExplainedResult;
import com.robertx22.mine_and_slash.database.data.runewords.RuneWord;
import com.robertx22.mine_and_slash.database.registry.ExileDB;
import com.robertx22.mine_and_slash.saveclasses.ExactStatData;
import com.robertx22.mine_and_slash.saveclasses.gearitem.gear_bases.IGearPartTooltip;
import com.robertx22.mine_and_slash.saveclasses.gearitem.gear_bases.IStatsContainer;
import com.robertx22.mine_and_slash.saveclasses.gearitem.gear_bases.StatRangeInfo;
import com.robertx22.mine_and_slash.saveclasses.item_classes.GearItemData;
import com.robertx22.mine_and_slash.uncommon.localization.Chats;
import com.robertx22.mine_and_slash.uncommon.localization.Itemtips;
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

    public void removeRuneword() {
        this.rw = "";
        this.rp = 0;
    }

    public List<SocketData> getSocketed() {
        return so;
    }

    public int getFirstGemIndex() {
        int i = 0;
        for (SocketData s : this.so) {
            if (s.isGem()) {
                return i;
            }
            i++;
        }
        return -1;
    }

    public int lastFilledSocketIndex() {

        for (int i = so.size() - 1; i > -1; i--) {
            var socket = so.get(i);

            if (socket.isGem() || socket.isRune()) {
                return i;
            }
        }
        return -1;
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
    public List<Component> GetTooltipString(StatRangeInfo info, GearItemData gear) {
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
                r.stats.stream().map(x -> x.ToExactStat(rp, gear.lvl)).forEach(x -> list.addAll(x.GetTooltipString()));
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
