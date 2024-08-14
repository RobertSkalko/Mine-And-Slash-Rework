package com.robertx22.mine_and_slash.gui.card_picker;

import com.robertx22.mine_and_slash.gui.bases.BaseScreen;

import java.util.List;

public class CardPickScreen extends BaseScreen {
    List<ICard> cards;

    static int spacing = 5;

    public CardPickScreen(List<ICard> cards) {
        super((CardPickButton.SIZE_X + spacing) * 3, CardPickButton.SIZE_Y);
        this.cards = cards;
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
}
