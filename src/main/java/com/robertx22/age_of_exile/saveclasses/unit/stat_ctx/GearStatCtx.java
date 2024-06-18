package com.robertx22.age_of_exile.saveclasses.unit.stat_ctx;

import com.robertx22.age_of_exile.saveclasses.ExactStatData;
import com.robertx22.age_of_exile.saveclasses.item_classes.GearItemData;

import java.util.List;

public class GearStatCtx {


    public static SimpleStatCtx of(GearItemData gear, List<ExactStatData> stats) {
        SimpleStatCtx s = new SimpleStatCtx(StatContext.StatCtxType.GEAR, gear.GetBaseGearType().gear_slot, stats);
        return s;
    }

}
