package com.robertx22.mine_and_slash.gui.wiki.reworked.filters;

import com.robertx22.mine_and_slash.gui.wiki.reworked.NewWikiScreen;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class FilterSelectScreen extends Screen {


    private FilterEntryList list;
    public NewWikiScreen last;

    public FilterGroupButton button;

    public FilterSelectScreen(FilterGroupButton filterGroupButton, NewWikiScreen screen, GroupFilterType type) {
        super(Component.literal("Choose A Filter"));
        this.type = type;
        this.last = screen;
        this.button = filterGroupButton;
    }


    public void tick() {
    }

    public GroupFilterType type;

    protected void init() {
        this.list = new FilterEntryList(this, this.minecraft, this.width, this.height, 48, this.height - 64, 36);
        this.addWidget(this.list);
    }

    public boolean keyPressed(int pKeyCode, int pScanCode, int pModifiers) {
        return super.keyPressed(pKeyCode, pScanCode, pModifiers);
    }

    public void onClose() {
        this.minecraft.setScreen(this.last);
    }


    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        this.list.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
        pGuiGraphics.drawCenteredString(this.font, this.title, this.width / 2, 8, 16777215);
        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
    }


    public void removed() {


    }

}