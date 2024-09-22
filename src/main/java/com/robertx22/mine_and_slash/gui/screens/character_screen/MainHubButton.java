package com.robertx22.mine_and_slash.gui.screens.character_screen;

import com.robertx22.library_of_exile.utils.RenderUtils;
import com.robertx22.mine_and_slash.gui.bases.IAlertScreen;
import com.robertx22.mine_and_slash.gui.bases.IContainerNamedScreen;
import com.robertx22.mine_and_slash.gui.bases.INamedScreen;
import com.robertx22.mine_and_slash.mmorpg.SlashRef;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipPositioner;
import net.minecraft.client.gui.screens.inventory.tooltip.DefaultTooltipPositioner;
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
        super(xPos, yPos, xSize, ySize, 0, 0, ySize, loc, (button) -> {
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
    protected ClientTooltipPositioner createTooltipPositioner() {
        return DefaultTooltipPositioner.INSTANCE;
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
            var mc = Minecraft.getInstance();
            // float color = MathHelper.clamp((mc.player.tickCount % 25 + mc.getPartialTick()) * 0.3F, 0, 3);
            gui.setColor(1, 1, 1, 1);
            RenderUtils.render16Icon(gui, EXLAMATION_MARK_TEX, this.getX() + 5, this.getY() + 6);
            gui.setColor(1.0F, 1.0F, 1.0F, 1.0F);

        }

        String str = screen.screenName().translate();


        if (isHovered()) {
            if (right) {
                gui.drawCenteredString(Minecraft.getInstance().font, str, this.getX() + 65, this.getY() + 10, ChatFormatting.YELLOW.getColor());
            } else {
                gui.drawCenteredString(Minecraft.getInstance().font, str, this.getX() + 40, this.getY() + 10, ChatFormatting.YELLOW.getColor());

            }
        }
    }

}
