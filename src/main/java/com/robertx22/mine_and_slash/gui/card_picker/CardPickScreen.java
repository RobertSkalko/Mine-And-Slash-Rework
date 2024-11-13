package com.robertx22.mine_and_slash.gui.card_picker;

import com.robertx22.mine_and_slash.gui.bases.BaseScreen;
import com.robertx22.mine_and_slash.gui.bases.INamedScreen;
import com.robertx22.mine_and_slash.mmorpg.SlashRef;
import com.robertx22.mine_and_slash.uncommon.localization.Words;
import net.minecraft.resources.ResourceLocation;

import java.util.List;

// one problem is how do i Send a packet from server for the cards? there should be a card loader or something
public class CardPickScreen extends BaseScreen implements INamedScreen {
    List<ICard> cards;

    static int spacing = 5;
    Words word;
    String icon;

    public CardPickScreen(List<ICard> cards, Words word, String icon) {
        super((CardPickButton.SIZE_X + spacing) * 3, CardPickButton.SIZE_Y);
        this.cards = cards;
        this.word = word;
        this.icon = icon;
    }

    @Override
    public void init() {
        super.init();
        this.clearWidgets();

        int x = guiLeft;
        int y = guiTop;

        for (ICard card : cards) {
            this.addRenderableWidget(new CardPickButton(card, x, y));
            x += CardPickButton.SIZE_X + spacing;
        }

    }

    @Override
    public ResourceLocation iconLocation() {
        return new ResourceLocation(SlashRef.MODID, "textures/gui/main_hub/icons/" + icon + ".png");
    }

    @Override
    public Words screenName() {
        return word;
    }

}
