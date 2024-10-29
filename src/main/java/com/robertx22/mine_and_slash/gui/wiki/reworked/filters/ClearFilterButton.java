package com.robertx22.mine_and_slash.gui.wiki.reworked.filters;

import com.robertx22.mine_and_slash.gui.wiki.reworked.NewWikiScreen;
import com.robertx22.mine_and_slash.mmorpg.SlashRef;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class ClearFilterButton extends AbstractButton {

    public static int HEIGHT = 30;
    static ResourceLocation tex = SlashRef.guiId("bestiary/cancel");

    NewWikiScreen screen;
    GroupFilterType filter;

    public ClearFilterButton(NewWikiScreen screen, GroupFilterType filter, int pX, int pY) {
        super(pX, pY, HEIGHT, HEIGHT, Component.literal(""));
        this.filter = filter;
        this.screen = screen;
    }

    @Override
    public void renderWidget(GuiGraphics gui, int mouseX, int mouseY, float delta) {
        super.renderWidget(gui, mouseX, mouseY, delta);
        ResourceLocation tex = SlashRef.guiId("bestiary/cancel");


        gui.setColor(1.0F, 1.0F, 1.0F, 1.0F);

        int off = (HEIGHT - 16) / 2;
        gui.blit(tex, getX() + off, getY() + off, 16, 16, 16, 16, 16, 16);

    }


    @Override
    public void onPress() {
        screen.setFilterGroup(filter, GroupFilterEntry.NONE);
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput pNarrationElementOutput) {

    }
}
