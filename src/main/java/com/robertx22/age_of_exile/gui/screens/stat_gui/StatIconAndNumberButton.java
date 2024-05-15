package com.robertx22.age_of_exile.gui.screens.stat_gui;

import com.robertx22.age_of_exile.gui.buttons.CharacterStatsButtons;
import com.robertx22.age_of_exile.mmorpg.SlashRef;
import com.robertx22.age_of_exile.saveclasses.unit.StatData;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.utilityclasses.ClientOnly;
import com.robertx22.library_of_exile.utils.GuiUtils;
import com.robertx22.library_of_exile.utils.RenderUtils;
import com.robertx22.library_of_exile.utils.TextUTIL;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.network.chat.Component;

import java.util.ArrayList;
import java.util.List;

public class StatIconAndNumberButton extends ImageButton {

    public static int xSize = 19;
    public static int ySize = 19;


    StatData stat;


    public StatIconAndNumberButton(StatData stat, int xPos, int yPos) {
        super(xPos, yPos, xSize, ySize, 0, 0, 0, SlashRef.guiId("stat_gui/stat_icon"), xSize, ySize, (button) -> {

        });

        this.stat = stat;
    }

    @Override
    public void render(GuiGraphics gui, int x, int y, float ticks) {
        super.render(gui, x, y, ticks);


        if (stat == null || stat.GetStat() == null) {
            return;
        }

        if (this.isHoveredOrFocused()) {
            List<Component> tooltip = new ArrayList<>();
            var text = stat.GetStat().locName().append(": " + CharacterStatsButtons.getStatString(stat.GetStat(), Load.Unit(ClientOnly.getPlayer())));
            tooltip.add(text);
            this.setTooltip(Tooltip.create(TextUTIL.mergeList(tooltip)));
        }


        int iconX = 5;
        int iconY = 5;

        int numX = 10;
        int numY = 16;

        String stattext = ((int) stat.getValue()) + "";


        RenderUtils.render16Icon(gui, stat.GetStat().getIconForRenderingInGroup(), getX() + iconX - 4, getY() + iconY - 3);

        GuiUtils.renderScaledText(gui, getX() + numX, getY() + numY, 1F, stattext, ChatFormatting.YELLOW);


    }

}
