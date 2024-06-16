package com.robertx22.age_of_exile.gui.screens.stat_gui;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.mmorpg.SlashRef;
import com.robertx22.age_of_exile.saveclasses.unit.StatData;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.localization.Words;
import com.robertx22.age_of_exile.uncommon.utilityclasses.ClientOnly;
import com.robertx22.library_of_exile.utils.RenderUtils;
import com.robertx22.library_of_exile.utils.TextUTIL;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

import java.util.ArrayList;
import java.util.List;

public class StatPanelButton extends ImageButton {

    public static int xSize = 163;
    public static int ySize = 20;


    StatData stat;


    public StatPanelButton(StatScreen screen, StatData stat, int xPos, int yPos) {
        super(xPos, yPos, stat.GetStat().gui_group.isValid() ? xSize : xSize - StatIconAndNumberButton.xSize, ySize, 0, 0, 0, stat.GetStat().gui_group.isValid() ? SlashRef.guiId("stat_gui/group_stat_panel") : SlashRef.guiId("stat_gui/single_stat_panel"), xSize, ySize, (button) -> {
            screen.setInfo(stat);
        });

        var data = Load.Unit(ClientOnly.getPlayer());


        if (stat.GetStat().gui_group.isValid()) {
            int i = 0;
            for (Stat st : stat.GetStat().gui_group.getSameGroupStats()) {
                var statdata = data.getUnit().getCalculatedStat(st);
                screen.publicAddButton(new StatIconAndNumberButton(screen, statdata, getX() + (i * (StatIconAndNumberButton.xSize + 15)), getY() + ySize));
                i++;
            }
        } else {
            screen.publicAddButton(new StatIconAndNumberButton(screen, stat, getX() + 145, getY()));

        }

        this.stat = stat;
    }

    @Override
    public void render(GuiGraphics gui, int x, int y, float ticks) {
        super.render(gui, x, y, ticks);

        if (this.isHoveredOrFocused()) {
            List<MutableComponent> tooltip = new ArrayList<>();
            tooltip.add(Words.PressForMoreInfo.locName());
            this.setTooltip(Tooltip.create(TextUTIL.mergeList(tooltip)));
        }

        if (stat == null || stat.GetStat() == null) {
            return;
        }
        //gui.blit(this.resourceLocation, getX(), getY(), 0, 0, this.width, height, width, height);


        int iconX = 2;
        int iconY = 2;

        int numX = 23;
        int numY = 6;

        String stattext = stat.GetStat().gui_group.isValid() ? stat.GetStat().gui_group.locName().getString() : stat.GetStat().locName().getString();

        int maxlength = 20;
        if (stattext.length() >= maxlength) {
            stattext = stattext.substring(0, maxlength) + "...";
        }

        RenderUtils.render16Icon(gui, stat.GetStat().getIconForRenderingWithDefault(), getX() + iconX, getY() + iconY);

        gui.drawString(Minecraft.getInstance().font, Component.literal(stattext), getX() + numX, getY() + numY, ChatFormatting.AQUA.getColor());

//        GuiUtils.renderScaledText(gui, getX() + numX, getY() + numY, 1F, stattext, ChatFormatting.YELLOW);


    }

}
