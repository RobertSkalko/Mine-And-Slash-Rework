package com.robertx22.age_of_exile.gui.overlays.bar_overlays.types;

import com.robertx22.age_of_exile.config.GuiPartConfig;
import com.robertx22.age_of_exile.gui.overlays.BarGuiType;
import com.robertx22.age_of_exile.mmorpg.SlashRef;
import com.robertx22.age_of_exile.saveclasses.PointData;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;

public class MineAndSlashBars {
    static ResourceLocation BASETEX = new ResourceLocation(SlashRef.MODID, "textures/gui/overlay/base.png");

    static int BAR_HEIGHT = 11;
    static int BAR_WIDTH = 107;
    public static int INNER_BAR_WIDTH = 101;
    static int INNER_BAR_HEIGHT = 5;

    static Minecraft mc = Minecraft.getInstance();

    public static void renderMineAndSlashBar(GuiPartConfig config, BarGuiType type, GuiGraphics gui, PointData point, String text, boolean drawText) {

        if (!drawText) {
            gui.blit(BASETEX, point.x, point.y, BAR_WIDTH, BAR_HEIGHT, 0, 0, BAR_WIDTH, BAR_HEIGHT, BAR_WIDTH, BAR_HEIGHT);
            float multi = type.getMulti(Load.Unit(mc.player), mc.player);
            int bar = (int) (multi * INNER_BAR_WIDTH);
            gui.blit(type.getTexture(Load.Unit(mc.player), mc.player), point.x + 2, point.y + 2, bar, INNER_BAR_HEIGHT, 0, 0, bar, INNER_BAR_HEIGHT, INNER_BAR_WIDTH, INNER_BAR_HEIGHT);

            if (config.icon_renderer == GuiPartConfig.IconRenderer.LEFT) {
                gui.blit(type.getIcon(Load.Unit(mc.player), mc.player), point.x - 10, point.y, 9, 9, 0, 0, 9, 9, 9, 9); // draw icon
            } else if (config.icon_renderer == GuiPartConfig.IconRenderer.RIGHT) {
                gui.blit(type.getIcon(Load.Unit(mc.player), mc.player), point.x + BAR_WIDTH + 1, point.y, 9, 9, 0, 0, 9, 9, 9, 9); // draw icon
            }

        }

        if (drawText) {

            float scale = 0.9F;

            int width = mc.font.width(text);
            int textX = (int) (point.x - width / 2F + BAR_WIDTH / 2F);
            int textY = point.y + 2 + 3;

            float antiScale = 1.0F / scale;

            gui.pose().scale(scale, scale, scale);
            double textWidthMinus = (width * antiScale / 2) - width / 2F;// fixed the centering with this!!!
            double textHeightMinus = 9.0D * scale / 2.0D;
            float xp = (float) ((double) textX + textWidthMinus);
            float yp = (float) ((double) textY - textHeightMinus);
            float xf = (float) ((double) xp * antiScale);
            float yf = (float) ((double) yp * antiScale);


            gui.drawString(mc.font, text, (int) xf, (int) yf, ChatFormatting.WHITE.getColor());

            gui.pose().scale(antiScale, antiScale, antiScale);
        }
    }
}
