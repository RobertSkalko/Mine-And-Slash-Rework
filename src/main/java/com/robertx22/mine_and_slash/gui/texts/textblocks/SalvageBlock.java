package com.robertx22.mine_and_slash.gui.texts.textblocks;

import com.robertx22.mine_and_slash.gui.texts.ExileTooltips;
import com.robertx22.mine_and_slash.itemstack.ExileStack;
import com.robertx22.mine_and_slash.saveclasses.gearitem.gear_bases.ModRange;
import com.robertx22.mine_and_slash.saveclasses.gearitem.gear_bases.StatRangeInfo;
import com.robertx22.mine_and_slash.uncommon.interfaces.data_items.ISalvagable;
import com.robertx22.mine_and_slash.uncommon.localization.Words;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;

import java.util.Collections;
import java.util.List;

public class SalvageBlock extends AbstractTextBlock {
    ExileStack ex;
    private final ISalvagable data;

    private final StatRangeInfo info = new StatRangeInfo(ModRange.hide());

    public SalvageBlock(ISalvagable sal, ExileStack ex) {
        this.ex = ex;
        this.data = sal;
    }

    @Override
    public List<? extends Component> getAvailableComponents() {
        if (info.useInDepthStats()) {
            return Collections.singletonList(data.isSalvagable(ex) ? Words.SALVAGEABLE.locName().withStyle(ChatFormatting.GREEN) : Words.UNSALVAGEABLE.locName().withStyle(ChatFormatting.RED));
        }
        return Collections.emptyList();
    }

    @Override
    public ExileTooltips.BlockCategories getCategory() {
        return ExileTooltips.BlockCategories.SALVAGE;
    }
}
