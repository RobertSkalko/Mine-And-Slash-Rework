package com.robertx22.age_of_exile.gui.screens.character_screen;

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
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

public class HubStatButton extends ImageButton {

    public static int xSize = 41;
    public static int ySize = 20;
    public static ResourceLocation LEFT = new ResourceLocation(SlashRef.MODID, "textures/gui/main_hub/hub_stat_button_left.png");
    public static ResourceLocation RIGHT = new ResourceLocation(SlashRef.MODID, "textures/gui/main_hub/hub_stat_button_right.png");


    StatData stat;
    boolean right;

    public HubStatButton(boolean isright, StatData stat, int xPos, int yPos) {
        super(xPos, yPos, xSize, ySize, 0, 0, ySize, isright ? RIGHT : LEFT, (button) -> {

        });

        this.right = isright;
        this.stat = stat;
    }

    @Override
    public void render(GuiGraphics gui, int x, int y, float ticks) {
        super.render(gui, x, y, ticks);


        if (stat == null || stat.GetStat() == null) {
            return;
        }

        if (this.isHovered()) {
            List<Component> tooltip = new ArrayList<>();
            var text = stat.GetStat().locName().append(": " + CharacterStatsButtons.getStatString(stat.GetStat(), Load.Unit(ClientOnly.getPlayer())));
            tooltip.add(text);
            this.setTooltip(Tooltip.create(TextUTIL.mergeList(tooltip)));

        }
      

        int iconX = 0;
        int iconY = 0;

        int numX = 0;
        int numY = 0;

        if (right) {
            iconX = 31;
            iconY = 10;

            numX = 10;
            numY = 11;
        } else {
            iconX = 10;
            iconY = 10;

            numX = 31;
            numY = 11;
        }

        var tex = LEFT;

        if (this.right) {
            tex = RIGHT;
        }

        gui.setColor(1.0F, 1.0F, 1.0F, 1.0F);
        gui.blit(tex, getX(), getY(), 0, 0, xSize, ySize, xSize, ySize);


        String stattext = ((int) stat.getValue()) + "";

        // gui.drawCenteredString(Minecraft.getInstance().font, stattext, getX() + numX, getY() + numY, ChatFormatting.YELLOW.getColor());


        GuiUtils.renderScaledText(gui, getX() + numX, getY() + numY, 0.75F, stattext, ChatFormatting.YELLOW);

        RenderUtils.render16Icon(gui, stat.GetStat().getIconForRendering(), getX() + iconX - 8, getY() + iconY - 8);

        //gui.blit(tex, iconX, iconY, 0, 0, 16, ySize, xSize, ySize);


    }

}
