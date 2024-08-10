package com.robertx22.mine_and_slash.saveclasses.gearitem.gear_bases;

import com.robertx22.mine_and_slash.database.data.MinMax;
import com.robertx22.mine_and_slash.saveclasses.item_classes.GearItemData;
import net.minecraft.network.chat.Component;

import java.util.List;

public interface IGearPartTooltip extends IGearPart {

    public List<Component> GetTooltipString(StatRangeInfo info, GearItemData gear);

    default MinMax getMinMax(GearItemData gear) {
        return new MinMax(0, 100);
    }
}


