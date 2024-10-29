package com.robertx22.mine_and_slash.gui.wiki.reworked.filters;

import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ObjectSelectionList;
import net.minecraft.network.chat.Component;

public class FilterEntryButton extends ObjectSelectionList.Entry<FilterEntryButton> {
    public static int HEIGHT = 50;
    public static int WIDTH = 200;

    GroupFilterEntry entry;
    FilterSelectScreen screen;

    int count = 0;

    public FilterEntryButton(FilterSelectScreen screen, GroupFilterEntry entry) {
        this.screen = screen;
        this.entry = entry;

        this.count = (int) screen.last.group.getAll(1).stream().filter(x -> this.entry.isValid(x)).count();
    }

    @Override
    public boolean mouseClicked(double pMouseX, double pMouseY, int pButton) {
        this.screen.last.setFilterGroup(screen.type, entry);
        Minecraft.getInstance().setScreen(screen.last);
        return true;
    }

    @Override
    public Component getNarration() {
        return Component.empty();
    }

    @Override
    public void render(GuiGraphics gui, int pIndex, int pTop, int pLeft, int pWidth, int pHeight, int pMouseX, int pMouseY, boolean pHovering, float pPartialTick) {

        var mc = Minecraft.getInstance();

        int xp = (int) (pLeft + 37);
        int yp = (int) pTop + 5;


        var text = entry.getName().append(" (" + count + ")");

        if (count < 1) {
            text.withStyle(ChatFormatting.STRIKETHROUGH);
            text.withStyle(ChatFormatting.GRAY);

        }
        gui.drawString(mc.font, text, xp, yp, ChatFormatting.YELLOW.getColor());

    }
}