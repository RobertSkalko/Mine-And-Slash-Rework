package com.robertx22.age_of_exile.prophecy.gui;

import com.robertx22.age_of_exile.mmorpg.SlashRef;
import com.robertx22.age_of_exile.prophecy.ClaimProphecyRewardsPacket;
import com.robertx22.age_of_exile.uncommon.localization.Words;
import com.robertx22.library_of_exile.main.Packets;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.network.chat.Component;

public class ClaimProphecyRewardsButton extends ImageButton {

    public static int BUTTON_SIZE_X = 45;
    public static int BUTTON_SIZE_Y = 15;


    public ClaimProphecyRewardsButton(int xPos, int yPos) {
        super(xPos, yPos, BUTTON_SIZE_X, BUTTON_SIZE_Y, 0, 0, BUTTON_SIZE_Y, SlashRef.guiId("prophecy/claim"), (button) -> {
            Packets.sendToServer(new ClaimProphecyRewardsPacket());
        });

    }

    @Override
    public void render(GuiGraphics gui, int mouseX, int mouseY, float delta) {
        super.render(gui, mouseX, mouseY, delta);
    }

    @Override
    public void renderWidget(GuiGraphics gui, int mouseX, int mouseY, float delta) {

        gui.setColor(1.0F, 1.0F, 1.0F, 1.0F);

        Minecraft mc = Minecraft.getInstance();


        gui.blit(SlashRef.guiId("prophecy/claim"), getX(), getY(), 0, 0, BUTTON_SIZE_X, BUTTON_SIZE_Y, BUTTON_SIZE_X, BUTTON_SIZE_Y);

        Component lvl = Words.CLAIM.locName().withStyle(ChatFormatting.GREEN);
        gui.drawCenteredString(mc.font, lvl, this.getX() + 7, this.getY() + 7, ChatFormatting.YELLOW.getColor());

    }

}
