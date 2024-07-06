package com.robertx22.age_of_exile.gui.texts.textblocks;

import com.google.common.collect.ImmutableList;
import com.robertx22.age_of_exile.gui.texts.ExileTooltips;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.Rarity;
import com.robertx22.age_of_exile.uncommon.localization.Itemtips;
import net.minecraft.network.chat.Component;

import javax.annotation.Nonnull;
import java.util.List;

public class RarityBlock extends AbstractTextBlock {

    @Nonnull
    public Rarity rarity;

    public RarityBlock(@Nonnull Rarity rarity) {
        this.rarity = rarity;
    }

    @Override
    public List<? extends Component> getAvailableComponents() {

        return ImmutableList.of(Itemtips.RARITY_LINE.locName(rarity.locName().withStyle(rarity.textFormatting())).withStyle(rarity.textFormatting()));
    }

    @Override
    public ExileTooltips.BlockCategories getCategory() {
        return ExileTooltips.BlockCategories.RARITY;
    }
}
