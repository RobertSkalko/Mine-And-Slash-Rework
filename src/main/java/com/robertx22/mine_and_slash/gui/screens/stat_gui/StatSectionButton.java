package com.robertx22.mine_and_slash.gui.screens.stat_gui;

import com.robertx22.mine_and_slash.uncommon.utilityclasses.ClientOnly;
import com.robertx22.library_of_exile.utils.TextUTIL;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.network.chat.Component;

import java.util.ArrayList;
import java.util.List;

public class StatSectionButton extends ImageButton {

    public static int xSize = 16;
    public static int ySize = 16;


    StatGuiGroupSection sec;

    public StatSectionButton(StatScreen screen, StatGuiGroupSection sec, int xPos, int yPos) {
        super(xPos, yPos, xSize, ySize, 0, 0, 0, sec.getIcon(), xSize, ySize, (button) -> {
            screen.showStats(sec.getStats(ClientOnly.getPlayer()), true);
        });

        this.sec = sec;
    }

    @Override
    public void render(GuiGraphics gui, int x, int y, float ticks) {
        super.render(gui, x, y, ticks);

        if (this.isHoveredOrFocused()) {
            List<Component> tooltip = new ArrayList<>();
            tooltip.add(sec.locName().withStyle(ChatFormatting.GOLD));
            this.setTooltip(Tooltip.create(TextUTIL.mergeList(tooltip)));
        }

    }

}
