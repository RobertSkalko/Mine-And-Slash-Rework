package com.robertx22.mine_and_slash.itemstack.tooltips;

import com.robertx22.mine_and_slash.gui.texts.ExileTooltips;
import com.robertx22.mine_and_slash.itemstack.ExileTipCtx;

public class DataTooltips {

    public static void apply(ExileTooltips tip, ExileTipCtx ctx) {

        TooltipStrategy.POTENTIAL_AND_CORRUPTION.apply(tip, ctx);

    }
}
