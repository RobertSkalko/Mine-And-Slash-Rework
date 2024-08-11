package com.robertx22.mine_and_slash.gui.texts.textblocks.dropblocks;

import com.robertx22.mine_and_slash.gui.texts.ExileTooltips;
import com.robertx22.mine_and_slash.gui.texts.textblocks.AbstractTextBlock;
import com.robertx22.mine_and_slash.mmorpg.MMORPG;
import com.robertx22.mine_and_slash.uncommon.localization.Itemtips;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

import java.util.ArrayList;
import java.util.List;

public class DropChanceBlock extends AbstractTextBlock {

    public float chance;

    public DropChanceBlock(float chance) {
        this.chance = chance;
    }


    @Override
    public List<? extends Component> getAvailableComponents() {
        var chancetext = Component.literal(MMORPG.DECIMAL_FORMAT.format(chance) + "%").withStyle(ChatFormatting.YELLOW);

        List<MutableComponent> list = new ArrayList<>();
        list.add(Itemtips.DROP_CHANCE.locName(chancetext).withStyle(ChatFormatting.GREEN));
        if (Screen.hasShiftDown()) {
            list.add(Itemtips.DROP_CHANCE_EXTRA_INFO.locName().withStyle(ChatFormatting.BLUE));
        }
        return list;
    }

    @Override
    public ExileTooltips.BlockCategories getCategory() {
        return ExileTooltips.BlockCategories.ADDITIONAL;
    }
}
