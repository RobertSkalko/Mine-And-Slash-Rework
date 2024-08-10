package com.robertx22.mine_and_slash.gui.texts.textblocks;

import com.google.common.collect.ImmutableList;
import com.robertx22.mine_and_slash.gui.texts.ExileTooltips;
import com.robertx22.mine_and_slash.saveclasses.gearitem.gear_bases.Rarity;
import com.robertx22.mine_and_slash.uncommon.localization.Itemtips;
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

        return ImmutableList.of(Component.literal("").withStyle(rarity.textFormatting()).append(
                Itemtips.RARITY_LINE.locName(rarity.locName().withStyle(rarity.textFormatting())).withStyle(rarity.textFormatting())));
    }

    @Override
    public ExileTooltips.BlockCategories getCategory() {
        return ExileTooltips.BlockCategories.RARITY;
    }
}
