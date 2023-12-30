package com.robertx22.age_of_exile.characters.gui;

import com.robertx22.age_of_exile.characters.CreateCharPacket;
import com.robertx22.age_of_exile.mmorpg.SlashRef;
import com.robertx22.library_of_exile.main.Packets;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.ImageButton;

public class NewCharButton extends ImageButton {

    public static int BUTTON_SIZE_X = 18;
    public static int BUTTON_SIZE_Y = 18;

    public NewCharButton(int xPos, int yPos) {
        super(xPos, yPos, BUTTON_SIZE_X, BUTTON_SIZE_Y, 0, 0, BUTTON_SIZE_X, SlashRef.guiId("plus_button"), (button) -> {
            Packets.sendToServer(new CreateCharPacket(CharacterSelectScreen.SEARCH.getValue()));
            Minecraft.getInstance().setScreen(null);
        });
    }
}
