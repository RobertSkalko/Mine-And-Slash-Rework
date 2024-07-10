package com.robertx22.age_of_exile.gui.texts.textblocks.usableitemblocks;

import com.robertx22.age_of_exile.gui.texts.ExileTooltips;
import com.robertx22.age_of_exile.gui.texts.textblocks.AbstractTextBlock;
import net.minecraft.network.chat.Component;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.function.Supplier;

// This block use to handle the text line, like the currency usage etc. these lines are not stat but still important.
public class UsageBlock extends AbstractTextBlock {

    @Nonnull
    public List<? extends Component> components;

    public UsageBlock(@Nonnull List<? extends Component> components) {
        this.components = components;
    }

    public UsageBlock(@Nonnull Supplier<List<? extends Component>> components) {
        this.components = components.get();
    }


    @Override
    public List<? extends Component> getAvailableComponents() {
        return components;
    }

    @Override
    public ExileTooltips.BlockCategories getCategory() {
        return ExileTooltips.BlockCategories.USAGE;
    }
}
