package com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases;

import net.minecraft.network.chat.MutableComponent;

import java.util.List;

public interface ITooltipList {

    public default List<MutableComponent> GetTooltipStringWithNoExtraSpellInfo(TooltipInfo info) {
        info.showAbilityExtraInfo = false;
        List<MutableComponent> list = GetTooltipString(info);
        info.showAbilityExtraInfo = true;
        return list;
    }

    public abstract List<MutableComponent> GetTooltipString(TooltipInfo info);
}


