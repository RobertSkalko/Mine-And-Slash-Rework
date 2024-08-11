package com.robertx22.mine_and_slash.gui.texts.textblocks.dropblocks;

import com.robertx22.mine_and_slash.gui.texts.ExileTooltips;
import com.robertx22.mine_and_slash.gui.texts.textblocks.AbstractTextBlock;
import com.robertx22.mine_and_slash.uncommon.localization.Itemtips;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;

import java.util.Arrays;
import java.util.List;

public class DropLevelBlock extends AbstractTextBlock {

    public int minlvl;
    public int maxlvl;

    public DropLevelBlock(int minlvl, int maxlvl) {
        this.minlvl = minlvl;
        this.maxlvl = maxlvl;
    }

    @Override
    public List<? extends Component> getAvailableComponents() {
        return Arrays.asList(
                Itemtips.DROP_LEVEL_RANGE.locName(minlvl + " - " + maxlvl).withStyle(ChatFormatting.GREEN)
        );
    }

    @Override
    public ExileTooltips.BlockCategories getCategory() {
        return ExileTooltips.BlockCategories.ADDITIONAL;
    }
}
