package com.robertx22.age_of_exile.inv_gui;

import com.robertx22.age_of_exile.gui.bases.BaseScreen;
import com.robertx22.age_of_exile.mmorpg.SlashRef;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;

public class InvGuiScreen extends BaseScreen {
    static ResourceLocation TEX = SlashRef.guiId("inv_gui/background");

    InvGuiGrid grid;

    public InvGuiScreen(InvGuiGrid grid) {
        super(177, 136);

        this.grid = grid;
    }


    @Override
    public void render(GuiGraphics gui, int x, int y, float ticks) {


        gui.setColor(1.0F, 1.0F, 1.0F, 1.0F);

        gui.blit(TEX, mc.getWindow()
                        .getGuiScaledWidth() / 2 - sizeX / 2,
                mc.getWindow()
                        .getGuiScaledHeight() / 2 - sizeY / 2, 0, 0, sizeX, sizeY
        );

        super.render(gui, x, y, ticks);


    }

    @Override
    protected void init() {
        super.init();
        int i = 0;
        for (int y = 0; y < InvGuiGrid.Y_MAX; y++) {
            for (int x = 0; x < InvGuiGrid.X_MAX; x++) {

                int xpos = guiLeft + 8 + (x * InvGuiButton.BUTTON_SIZE_X);
                int ypos = guiTop + 17 + (y * InvGuiButton.BUTTON_SIZE_Y);

                this.publicAddButton(new InvGuiButton(grid.list.get(i), xpos, ypos));

                i++;
            }
        }

    }
}
