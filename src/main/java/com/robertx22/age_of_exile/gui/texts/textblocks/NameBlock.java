package com.robertx22.age_of_exile.gui.texts.textblocks;

import com.google.common.collect.ImmutableList;
import lombok.AllArgsConstructor;
import net.minecraft.network.chat.Component;

import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
@AllArgsConstructor
public class NameBlock extends AbstractTextBlock{

    @Nonnull
    private final List<? extends Component> name;

    @Override
    public List<? extends Component> getAvailableComponents() {
        //not colorize, leave it to ExileTooltips
        return name;
    }
}
