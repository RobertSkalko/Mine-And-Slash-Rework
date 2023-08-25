package com.robertx22.age_of_exile.gui.buttons;

import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.library_of_exile.utils.TextUTIL;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.resources.ResourceLocation;

public class FavorButton extends ImageButton {

    public static int FAVOR_BUTTON_SIZE_X = 34;
    public static int FAVOR_BUTTON_SIZE_Y = 34;

    Minecraft mc = Minecraft.getInstance();

    public FavorButton(int xPos, int yPos) {
        super(xPos, yPos, FAVOR_BUTTON_SIZE_X, FAVOR_BUTTON_SIZE_Y, 0, 0, FAVOR_BUTTON_SIZE_Y, new ResourceLocation("empty"), (button) -> {
        });

    }

    @Override
    public void render(GuiGraphics gui, int mouseX, int mouseY, float delta) {
        setModTooltip();
        super.render(gui, mouseX, mouseY, delta);
    }

    @Override
    public void renderWidget(GuiGraphics gui, int mouseX, int mouseY, float delta) {
        ResourceLocation tex = Load.player(mc.player).favor.getTexture();
        gui.setColor(1.0F, 1.0F, 1.0F, 1.0F);
        gui.blit(tex, getX(), getY(), FAVOR_BUTTON_SIZE_X, FAVOR_BUTTON_SIZE_X, FAVOR_BUTTON_SIZE_X, FAVOR_BUTTON_SIZE_X, FAVOR_BUTTON_SIZE_X, FAVOR_BUTTON_SIZE_X);

    }

    public void setModTooltip() {
        this.setTooltip(Tooltip.create(TextUTIL.mergeList(Load.player(mc.player).favor.getTooltip())));

    }


}