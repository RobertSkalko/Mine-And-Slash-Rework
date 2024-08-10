package com.robertx22.mine_and_slash.gui.texts.textblocks;

import com.robertx22.mine_and_slash.gui.texts.ExileTooltips;
import net.minecraft.network.chat.Component;

import java.util.List;

public abstract class AbstractTextBlock {

    public abstract List<? extends Component> getAvailableComponents();

    public abstract ExileTooltips.BlockCategories getCategory();
}
