package com.robertx22.age_of_exile.prophecy.gui;

import com.robertx22.age_of_exile.gui.bases.BaseScreen;
import com.robertx22.age_of_exile.gui.bases.INamedScreen;
import com.robertx22.age_of_exile.mmorpg.SlashRef;
import com.robertx22.age_of_exile.prophecy.ProphecyData;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.localization.Words;
import com.robertx22.library_of_exile.utils.GuiUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;

public class ProphecyScreen extends BaseScreen implements INamedScreen {
    private static final ResourceLocation BACKGROUND = new ResourceLocation(SlashRef.MODID, "textures/gui/prophecy/prophecy.png");

    static int sizeX = 177;
    static int sizeY = 180;

    Minecraft mc = Minecraft.getInstance();


    public ProphecyScreen() {
        super(sizeX, sizeY);
    }

    @Override
    public ResourceLocation iconLocation() {
        return new ResourceLocation(SlashRef.MODID, "textures/gui/main_hub/icons/prophecy.png");
    }

    @Override
    public Words screenName() {
        return Words.PROPHECIES;
    }

    static int SLOT_SPACING = 18;

    @Override
    public void init() {
        super.init();
        this.clearWidgets();

        try {

            var data = Load.player(mc.player).prophecy;

            int i = 0;
            int yc = 0;

            for (ProphecyData offer : data.offers) {

                int x = this.guiLeft + 9 + (i * SLOT_SPACING);
                int y = this.guiTop + 52 + (yc * SLOT_SPACING);

                this.addRenderableWidget(new ProphecyButton(offer, true, x, y));

                i++;

                if (i > 8) {
                    i = 0;
                    yc++;
                }
            }

            i = 0;

            for (ProphecyData offer : data.taken) {

                int x = this.guiLeft + 9 + (i * SLOT_SPACING);
                int y = this.guiTop + 125;

                this.addRenderableWidget(new ProphecyButton(offer, false, x, y));

                i++;
            }

            this.addRenderableWidget(new MainProphecyButton(guiLeft + sizeX / 2 - MainProphecyButton.FAVOR_BUTTON_SIZE_X / 2, guiTop + 7));

            if (data.canClaim()) {
                this.addRenderableWidget(new ClaimProphecyRewardsButton(guiLeft + 65, guiTop + 186));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    @Override
    public void render(GuiGraphics gui, int x, int y, float ticks) {

        try {
            gui.setColor(1.0F, 1.0F, 1.0F, 1.0F);
            gui.blit(BACKGROUND, mc.getWindow()
                            .getGuiScaledWidth() / 2 - sizeX / 2,
                    mc.getWindow()
                            .getGuiScaledHeight() / 2 - sizeY / 2, 0, 0, sizeX, sizeY
            );

            int xpos = guiLeft + 10;
            int ypos = guiTop + 157;

            int width = 159;
            int height = 11;
            var progress = Load.player(mc.player).prophecy.progress;
            gui.blit(SlashRef.guiId("prophecy/base"), xpos, ypos, 0, 0, width, height, width, height);
            gui.blit(SlashRef.guiId("prophecy/fill"), xpos, ypos, 0, 0, (int) (width * progress / 100F), height, width, height);


            super.render(gui, x, y, ticks);

            String txt = Words.PROPHECIES.locName().getString();
            //  GuiUtils.renderScaledText(gui, guiLeft + 88, guiTop + 22, 1, txt, ChatFormatting.DARK_PURPLE);

            String tx2 = Words.CURRENTLY_SELECTED.locName().getString();
            GuiUtils.renderScaledText(gui, guiLeft + 88, guiTop + 116, 1, tx2, ChatFormatting.YELLOW);

            //buttons.forEach(b -> b.renderToolTip(matrix, x, y));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}