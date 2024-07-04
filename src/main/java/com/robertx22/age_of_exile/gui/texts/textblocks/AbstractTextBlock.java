package com.robertx22.age_of_exile.gui.texts.textblocks;

import com.robertx22.age_of_exile.gui.texts.ExileTooltips;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.TooltipInfo;
import com.robertx22.library_of_exile.wrappers.ExileText;
import net.minecraft.network.chat.Component;

import javax.annotation.Nonnull;
import java.util.List;

public abstract class AbstractTextBlock {

    public abstract List<? extends Component> getAvailableComponents();

    public abstract ExileTooltips.BlockCategories getCategory();
}
