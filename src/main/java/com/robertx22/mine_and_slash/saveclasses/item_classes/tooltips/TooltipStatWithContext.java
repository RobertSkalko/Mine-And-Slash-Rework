package com.robertx22.mine_and_slash.saveclasses.item_classes.tooltips;

import com.robertx22.mine_and_slash.database.data.StatMod;
import com.robertx22.mine_and_slash.saveclasses.gearitem.gear_bases.ITooltipList;
import net.minecraft.network.chat.MutableComponent;

import java.util.List;

public class TooltipStatWithContext implements ITooltipList {


    public TooltipStatInfo statinfo;
    public StatMod mod;
    public Integer level;

    public boolean showNumber = true;
    public boolean disablestatranges = false;

    public TooltipStatWithContext(TooltipStatInfo statinfo, StatMod mod, Integer level) {
        this.statinfo = statinfo;
        this.mod = mod;
        this.level = level;
    }


    @Override
    public List<MutableComponent> GetTooltipString() {
        return statinfo.stat.getTooltipList(this);
    }

    public boolean showStatRanges() {
        if (disablestatranges) {
            return false;
        }
        if (statinfo.useInDepthStats()) {
            if (mod != null) {
                if (level != null) {
                    return true;
                }
            }
        }
        return false;
    }
}
