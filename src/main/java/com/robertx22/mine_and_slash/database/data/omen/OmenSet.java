package com.robertx22.mine_and_slash.database.data.omen;

import com.robertx22.mine_and_slash.mmorpg.UNICODE;
import com.robertx22.mine_and_slash.saveclasses.ExactStatData;
import com.robertx22.mine_and_slash.saveclasses.gearitem.gear_parts.AffixData;
import com.robertx22.mine_and_slash.uncommon.datasaving.Load;
import com.robertx22.mine_and_slash.uncommon.localization.Itemtips;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.player.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class OmenSet {


    public HashMap<Integer, List<ExactStatData>> stats = new HashMap<>();
    OmenData data;

    public OmenSet(OmenData data) {
        this.data = data;

        int max = 0;
        for (Integer num : data.rarities.values()) {
            max += num;
        }
      
        int index = max;


        int perc = OmenData.getStatPercent(data.rarities, data.slot_req, data.getRarity());

        if (!stats.containsKey(index)) {
            stats.put(index, new ArrayList<>());
        }

        stats.get(index).addAll(data.getOmen().mods.stream().map(x -> x.ToExactStat(perc, data.lvl)).collect(Collectors.toList()));

        index--;

        for (AffixData affix : data.aff) {
            stats.put(index, affix.GetAllStats(data.lvl));

            index--;

            if (index < 2) {
                index = 2;
            }
        }


    }


    public List<ExactStatData> getStats(Player p) {
        List<ExactStatData> all = new ArrayList<>();

        int fill = Load.player(p).omensFilled;

        for (Map.Entry<Integer, List<ExactStatData>> en : this.stats.entrySet()) {
            if (fill >= en.getKey()) {
                for (ExactStatData stat : en.getValue()) {
                    all.add(stat);
                }
            }
        }
        return all;

    }

    public List<MutableComponent> getTooltip(Player p) {
        List<MutableComponent> all = new ArrayList<>();

        int fill = Load.player(p).omensFilled;

        for (Map.Entry<Integer, List<ExactStatData>> en : this.stats.entrySet()) {

            var color = fill >= en.getKey() ? ChatFormatting.LIGHT_PURPLE : ChatFormatting.GRAY;

            all.add(Component.literal(UNICODE.STAR + " ").append(Itemtips.OMEN_SET_PIECES.locName(en.getKey())).withStyle(color));

            for (ExactStatData stat : en.getValue()) {
                all.addAll(stat.GetTooltipString().stream().map(x -> x.withStyle(ChatFormatting.GRAY)).collect(Collectors.toList()));
            }
        }

        return all;

    }
}
