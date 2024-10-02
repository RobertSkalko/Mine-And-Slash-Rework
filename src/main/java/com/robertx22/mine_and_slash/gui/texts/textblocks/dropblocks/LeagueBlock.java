package com.robertx22.mine_and_slash.gui.texts.textblocks.dropblocks;

import com.robertx22.mine_and_slash.database.data.league.LeagueMechanic;
import com.robertx22.mine_and_slash.gui.texts.ExileTooltips;
import com.robertx22.mine_and_slash.gui.texts.textblocks.AbstractTextBlock;
import com.robertx22.mine_and_slash.mmorpg.UNICODE;
import com.robertx22.mine_and_slash.uncommon.localization.Chats;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

import java.util.ArrayList;
import java.util.List;

public class LeagueBlock extends AbstractTextBlock {

    LeagueMechanic league;

    public LeagueBlock(LeagueMechanic l) {
        this.league = l;
    }

    @Override
    public List<? extends Component> getAvailableComponents() {
        var list = new ArrayList<MutableComponent>();

        list.add(Component.literal(UNICODE.SKULL + " ").withStyle(league.getTextColor()).append(league.locName().withStyle(league.getTextColor())));

        if (Screen.hasShiftDown()) {
            list.add(Chats.ITEM_AQUIRED_THROUGH_LEAGUE.locName().withStyle(league.getTextColor()));
        }
        return list;
    }

    @Override
    public ExileTooltips.BlockCategories getCategory() {
        return ExileTooltips.BlockCategories.ADDITIONAL;
    }
}
