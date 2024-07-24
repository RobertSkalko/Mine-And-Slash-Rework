package com.robertx22.age_of_exile.gui.texts.textblocks;

import com.robertx22.age_of_exile.gui.texts.ExileTooltips;
import net.minecraft.network.chat.Component;

import java.util.List;

public abstract class AbstractTextBlock {

    public abstract List<? extends Component> getAvailableComponents();

    public abstract ExileTooltips.BlockCategories getCategory();
}
