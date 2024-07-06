package com.robertx22.age_of_exile.gui.texts.textblocks;

import com.robertx22.age_of_exile.gui.texts.ExileTooltips;
import net.minecraft.network.chat.Component;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.List;

public class NameBlock extends AbstractTextBlock {

    @Nonnull
    private final List<? extends Component> name;

    public NameBlock(@Nonnull List<? extends Component> name) {
        this.name = name;
    }

    public NameBlock(Component name) {
        this.name = Collections.singletonList(name);
    }

    @Override
    public List<? extends Component> getAvailableComponents() {
        //not colorize, leave it to ExileTooltips
        return name;
    }


    @Override
    public ExileTooltips.BlockCategories getCategory() {
        return ExileTooltips.BlockCategories.NAME;
    }
}
