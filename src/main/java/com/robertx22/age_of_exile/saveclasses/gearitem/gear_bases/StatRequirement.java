package com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases;

import com.robertx22.age_of_exile.aoe_data.database.stats.old.DatapackStats;
import com.robertx22.age_of_exile.capability.entity.EntityData;
import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.StatScaling;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.uncommon.enumclasses.PlayStyle;
import com.robertx22.age_of_exile.uncommon.localization.Chats;
import com.robertx22.age_of_exile.uncommon.localization.Itemtips;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class StatRequirement {


    public static String CHECK_YES_ICON = "\u2714"; // they removed the damn icons..
    public static String NO_ICON = "\u274C";

    public static StatRequirement EMPTY = new StatRequirement();

    public HashMap<String, Float> base_req = new HashMap<>();
    public HashMap<String, Float> scaling_req = new HashMap<>();

    public StatRequirement(StatRequirement r) {
        //this.base_req = new HashMap<>(r.base_req);
        this.scaling_req = new HashMap<>(r.scaling_req);
    }

    public static StatRequirement of(PlayStyle... styles) {
        StatRequirement r = new StatRequirement();
        float multi = 0.5F / (float) styles.length;

        for (PlayStyle style : styles) {
            r.setStyleReq(style, multi);
        }

        return r;
    }


    public StatRequirement() {
    }

    public boolean meetsReq(int lvl, EntityData data) {

        for (Map.Entry<String, Float> en : this.scaling_req.entrySet()) {
            Stat x = ExileDB.Stats().get(en.getKey());
            int num = getScalingReq(x, lvl);
            if (num > data.getUnit().getCalculatedStat(x).getValue()) {
                return false;
            }
        }
        for (Map.Entry<String, Float> en : this.base_req.entrySet()) {
            Stat x = ExileDB.Stats().get(en.getKey());
            int num = getNonScalingReq(x, lvl);
            if (num > data.getUnit().getCalculatedStat(x).getValue()) {
                return false;
            }
        }
        return true;

    }

    public List<Component> getReqDifference(int lvl, EntityData data) {
        ArrayList<Component> components = new ArrayList<>();

        for (Map.Entry<String, Float> en : this.scaling_req.entrySet()) {
            Stat x = ExileDB.Stats().get(en.getKey());
            int num = getScalingReq(x, lvl);
            float targetValue = data.getUnit().getCalculatedStat(x).getValue();
            if (num > targetValue) {
                components.add(Chats.NOT_MEET_MAP_REQ.locName(x.locName(), num, targetValue));
            }
        }
        for (Map.Entry<String, Float> en : this.base_req.entrySet()) {
            Stat x = ExileDB.Stats().get(en.getKey());
            int num = getNonScalingReq(x, lvl);
            float targetValue = data.getUnit().getCalculatedStat(x).getValue();
            if (num > targetValue) {
                components.add(Chats.NOT_MEET_MAP_REQ.locName(x.locName(), num, targetValue));
            }
        }
        return components;

    }

    public float getLackingResistNumber(int lvl, EntityData data) {

        float lacking = 0;

        for (Map.Entry<String, Float> en : this.scaling_req.entrySet()) {
            Stat x = ExileDB.Stats().get(en.getKey());
            int num = getScalingReq(x, lvl);
            if (num > data.getUnit().getCalculatedStat(x).getValue()) {
                lacking += Math.abs(num - data.getUnit().getCalculatedStat(x).getValue());
            }
        }
        for (Map.Entry<String, Float> en : this.base_req.entrySet()) {
            Stat x = ExileDB.Stats().get(en.getKey());
            int num = getNonScalingReq(x, lvl);
            if (num > data.getUnit().getCalculatedStat(x).getValue()) {
                lacking += Math.abs(num - data.getUnit().getCalculatedStat(x).getValue());
            }
        }
        return lacking;

    }

    public List<Component> GetTooltipString(int lvl, EntityData data) {
        List<Component> list = new ArrayList<>();

        for (Map.Entry<String, Float> en : this.scaling_req.entrySet()) {
            Stat x = ExileDB.Stats().get(en.getKey());
            int num = getScalingReq(x, lvl);
            if (num > 0) {
                list.add(getTooltip(num, x, data));
            }
        }
        for (Map.Entry<String, Float> en : this.base_req.entrySet()) {
            Stat x = ExileDB.Stats().get(en.getKey());
            int num = getNonScalingReq(x, lvl);
            if (num > 0) {
                list.add(getTooltip(num, x, data));
            }
        }


        return list;
    }

    private int getScalingReq(Stat stat, int lvl) {
        return (int) StatScaling.STAT_REQ.scale(scaling_req.getOrDefault(stat.GUID(), 0F), lvl);
    }

    private int getNonScalingReq(Stat stat, int lvl) {
        return this.base_req.get(stat.GUID()).intValue();
    }

    public StatRequirement setStyleReq(PlayStyle style, float req) {
        this.scaling_req.put(style.getStat().GUID(), req);
        return this;
    }

    public StatRequirement setDex(float req) {
        this.scaling_req.put(DatapackStats.DEX.GUID(), req);
        return this;
    }

    public StatRequirement setInt(float req) {
        this.scaling_req.put(DatapackStats.INT.GUID(), req);
        return this;
    }

    public StatRequirement setStr(float req) {
        this.scaling_req.put(DatapackStats.STR.GUID(), req);
        return this;
    }


    public boolean isEmpty() {
        return this.scaling_req.isEmpty() && this.base_req.isEmpty();
    }


    static Component getTooltip(int req, Stat stat, EntityData data) {

        String perc = "";

        if (stat.is_perc) {
            perc = "%";
        }


        if (data.getUnit()
                .getCalculatedStat(stat)
                .getValue() >= req) {
            return Component.literal(ChatFormatting.GREEN + "" + ChatFormatting.BOLD + CHECK_YES_ICON + " ").append(Itemtips.Stat_Req.locName(stat.locName())
                            .withStyle(ChatFormatting.GRAY))
                    .append("" + ChatFormatting.GRAY + req + perc + " ");
        } else {

            return Component.literal(ChatFormatting.RED + "" + ChatFormatting.BOLD + NO_ICON + " ").append(Itemtips.Stat_Req.locName(stat.locName())
                            .withStyle(ChatFormatting.DARK_GRAY))
                    .append("" + ChatFormatting.DARK_GRAY + req + perc + " ");

        }

    }


}
