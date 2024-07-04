package com.robertx22.age_of_exile.gui.texts.textblocks;

import com.robertx22.age_of_exile.gui.texts.ExileTooltips;

public abstract class StatBlock extends AbstractTextBlock {

    public StatBlock() {
        super();
    }

    @Override
    public ExileTooltips.BlockCategories getCategory() {
        return ExileTooltips.BlockCategories.STAT;
    }
}
