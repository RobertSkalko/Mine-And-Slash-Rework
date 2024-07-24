package com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases;

import com.robertx22.age_of_exile.database.data.MinMax;
import com.robertx22.age_of_exile.saveclasses.item_classes.GearItemData;
import net.minecraft.network.chat.Component;

import java.util.List;

public interface IGearPartTooltip extends IGearPart {

    public List<Component> GetTooltipString(StatRangeInfo info, GearItemData gear);

    default MinMax getMinMax(GearItemData gear) {
        return new MinMax(0, 100);
    }
}


