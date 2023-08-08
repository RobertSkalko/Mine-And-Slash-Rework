package com.robertx22.age_of_exile.gui.screens.character_screen;

import com.robertx22.age_of_exile.gui.bases.IAlertScreen;
import com.robertx22.age_of_exile.gui.bases.IContainerNamedScreen;
import com.robertx22.age_of_exile.gui.bases.INamedScreen;
import com.robertx22.age_of_exile.mmorpg.SlashRef;
import com.robertx22.library_of_exile.utils.RenderUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.resources.ResourceLocation;

public class MainHubButton extends ImageButton {

    public static int xSize = 105;
    public static int ySize = 28;
    public static ResourceLocation EXLAMATION_MARK_TEX = new ResourceLocation(
            SlashRef.MODID, "textures/gui/main_hub/exclamation_mark.png");

    boolean shouldAlert = false;


    INamedScreen screen;
    boolean right;

    public MainHubButton(boolean isright, ResourceLocation loc, INamedScreen screen, int xPos, int yPos) {
        super(xPos, yPos, xSize, ySize, 0, 0, ySize + 1, loc, (button) -> {
            if (screen instanceof IContainerNamedScreen) {
                IContainerNamedScreen con = (IContainerNamedScreen) screen;
                con.openContainer();
            } else {
                Minecraft.getInstance()
                        .setScreen((Screen) screen);
            }
        });

        this.right = isright;
        this.screen = screen;

        if (screen instanceof IAlertScreen) {
            IAlertScreen alert = (IAlertScreen) screen;
            this.shouldAlert = alert.shouldAlert();
        }

    }

    @Override
    public void render(GuiGraphics gui, int x, int y, float ticks) {
        super.render(gui, x, y, ticks);


        if (right) {
            RenderUtils.render16Icon(gui, screen.iconLocation(), this.getX() + 9, this.getY() + 6);
        } else {
            RenderUtils.render16Icon(gui, screen.iconLocation(), this.getX() + 80, this.getY() + 6);
        }
        if (shouldAlert) {
            RenderUtils.render16Icon(gui, EXLAMATION_MARK_TEX, this.getX() + 5, this.getY() + 6);
        }

        String str = screen.screenName().translate();
        

        if (isHovered()) {
            if (right) {
                gui.drawString(Minecraft.getInstance().font, str, this.getX() + 32, this.getY() + 9, ChatFormatting.GREEN.getColor());
            } else {
                gui.drawString(Minecraft.getInstance().font, str, this.getX() + 6, this.getY() + 9, ChatFormatting.GREEN.getColor());

            }
        }
    }

}
