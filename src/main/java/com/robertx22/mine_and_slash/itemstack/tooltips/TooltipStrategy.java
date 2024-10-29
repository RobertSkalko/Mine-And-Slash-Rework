package com.robertx22.mine_and_slash.itemstack.tooltips;

import com.robertx22.mine_and_slash.gui.texts.ExileTooltips;
import com.robertx22.mine_and_slash.itemstack.ExileTipCtx;

// just manually call them 1 by 1, no need to register or whatever
public abstract class TooltipStrategy {
    public static PotentialAndCorruptionTip POTENTIAL_AND_CORRUPTION = new PotentialAndCorruptionTip();

    public TooltipStrategy() {

    }


    public abstract void apply(ExileTooltips tip, ExileTipCtx ctx);
}
