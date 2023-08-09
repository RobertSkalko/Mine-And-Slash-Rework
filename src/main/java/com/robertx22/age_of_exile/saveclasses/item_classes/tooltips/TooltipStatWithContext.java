package com.robertx22.age_of_exile.saveclasses.item_classes.tooltips;

import com.robertx22.age_of_exile.database.data.StatMod;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.ITooltipList;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.TooltipInfo;
import net.minecraft.network.chat.MutableComponent;

import java.util.List;

public class TooltipStatWithContext implements ITooltipList {

    public TooltipStatInfo statinfo;
    public
    StatMod mod;
    public
    Integer level;

    public TooltipStatWithContext(TooltipStatInfo statinfo, StatMod mod, Integer level) {
        this.statinfo = statinfo;
        this.mod = mod;
        this.level = level;
    }

    @Override
    public List<MutableComponent> GetTooltipString(TooltipInfo info) {
        return statinfo.stat.getTooltipList(this);
    }

    public boolean showStatRanges() {
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
