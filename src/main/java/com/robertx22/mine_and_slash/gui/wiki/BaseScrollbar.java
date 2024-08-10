package com.robertx22.mine_and_slash.gui.wiki;


import com.robertx22.mine_and_slash.mmorpg.SlashRef;
import com.robertx22.mine_and_slash.uncommon.MathHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public abstract class BaseScrollbar extends AbstractWidget {
    static ResourceLocation SCROLLBAR_TEXTURE = new ResourceLocation(SlashRef.MODID, "textures/gui/scrollbar.png");

    static int sizeY = 27;
    static int sizeX = 6;
    protected double value = 0; // 0-1

    int totalHeight;

    protected BaseScrollbar(int xpos, int ypos, int scrollbarTotalHeight) {
        super(xpos, ypos, sizeX, scrollbarTotalHeight, Component.empty());

        this.totalHeight = scrollbarTotalHeight;
    }

    @Override
    public void renderWidget(GuiGraphics gui, int mouseX, int mouseY, float delta) {
        int xadd = (this.isHovered() ? 1 : 0) * sizeX;
        int yPos = getScrollBarY();

        gui.setColor(1.0F, 1.0F, 1.0F, 1.0F);
        gui.blit(SCROLLBAR_TEXTURE, getX(), getY(), 50, 0, sizeX, totalHeight); // draw grey background
        gui.blit(SCROLLBAR_TEXTURE, getX(), yPos, xadd, 0, sizeX, sizeY); // draw the scrollbar

    }


    public int getScrollBarY() {

        int val = (int) ((float) this.getY() + (value * (float) totalHeight));

        return MathHelper.clamp(val - sizeY / 2, this.getY(), this.getY() + totalHeight - sizeY);
    }


    @Override
    public void onClick(double x, double y) {
        this.setValueFromMouse(y);
    }

    private void setValueFromMouse(double y) {
        this.setValue(((y - (double) (this.getY())) / (double) (this.totalHeight)));
    }

    private void setValue(double y) {
        double val = this.value;
        this.value = MathHelper.clamp((float) y, 0.0F, 1.0F);
        if (val != this.value) {
            this.applyValue();
        }
    }

    public void setValueFromElement(int element, int max) {
        this.value = (float) element / (float) max;
    }

    @Override
    protected void onDrag(double x, double y, double f3, double f4) {
        this.setValueFromMouse(y);
        super.onDrag(x, y, f3, f4);
    }

    @Override
    public void onRelease(double x, double y) {
        super.playDownSound(Minecraft.getInstance().getSoundManager());
    }

    protected abstract void applyValue();
}

