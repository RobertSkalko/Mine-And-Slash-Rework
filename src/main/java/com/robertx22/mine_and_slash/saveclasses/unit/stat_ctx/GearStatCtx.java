package com.robertx22.mine_and_slash.saveclasses.unit.stat_ctx;

import com.robertx22.mine_and_slash.saveclasses.ExactStatData;
import com.robertx22.mine_and_slash.saveclasses.item_classes.GearItemData;

import java.util.List;

public class GearStatCtx {


    public static SimpleStatCtx of(GearItemData gear, List<ExactStatData> stats) {
        SimpleStatCtx s = new SimpleStatCtx(StatContext.StatCtxType.GEAR, gear.GetBaseGearType().gear_slot, stats);
        return s;
    }

}
