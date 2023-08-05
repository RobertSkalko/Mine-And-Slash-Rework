package com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases;

import net.minecraft.network.chat.Component;

import java.util.List;

public interface ITooltipList {

    public default List<Component> GetTooltipStringWithNoExtraSpellInfo(TooltipInfo info) {
        info.showAbilityExtraInfo = false;
        List<Component> list = GetTooltipString(info);
        info.showAbilityExtraInfo = true;
        return list;
    }

    public abstract List<Component> GetTooltipString(TooltipInfo info);
}


