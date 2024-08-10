package com.robertx22.mine_and_slash.gui.texts.textblocks;

import com.robertx22.mine_and_slash.gui.texts.ExileTooltips;

public abstract class StatBlock extends AbstractTextBlock {

    public StatBlock() {
        super();
    }

    @Override
    public ExileTooltips.BlockCategories getCategory() {
        return ExileTooltips.BlockCategories.STAT;
    }
}
