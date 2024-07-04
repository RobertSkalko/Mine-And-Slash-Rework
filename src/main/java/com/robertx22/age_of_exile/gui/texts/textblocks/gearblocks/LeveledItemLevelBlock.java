package com.robertx22.age_of_exile.gui.texts.textblocks.gearblocks;

import com.robertx22.age_of_exile.gui.texts.ExileTooltips;
import com.robertx22.age_of_exile.gui.texts.textblocks.AbstractTextBlock;
import com.robertx22.age_of_exile.uncommon.utilityclasses.TooltipUtils;
import lombok.RequiredArgsConstructor;
import net.minecraft.network.chat.Component;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.List;
@RequiredArgsConstructor
public class LeveledItemLevelBlock extends AbstractTextBlock {

    private final int level;
    @Override
    public List<? extends Component> getAvailableComponents() {
        return Collections.singletonList(TooltipUtils.level(level));
    }

    @Override
    public ExileTooltips.BlockCategories getCategory() {
        return ExileTooltips.BlockCategories.LEVELED_ITEM_LEVEL;
    }
}
