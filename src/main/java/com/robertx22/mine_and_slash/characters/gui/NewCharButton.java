package com.robertx22.mine_and_slash.characters.gui;

import com.robertx22.mine_and_slash.characters.CreateCharPacket;
import com.robertx22.mine_and_slash.mmorpg.SlashRef;
import com.robertx22.mine_and_slash.uncommon.localization.Chats;
import com.robertx22.library_of_exile.main.Packets;
import com.robertx22.library_of_exile.utils.TextUTIL;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.components.Tooltip;

import static com.robertx22.mine_and_slash.uncommon.utilityclasses.TooltipUtils.splitLongText;

public class NewCharButton extends ImageButton {

    public static int BUTTON_SIZE_X = 18;
    public static int BUTTON_SIZE_Y = 18;

    public NewCharButton(int xPos, int yPos) {
        super(xPos, yPos, BUTTON_SIZE_X, BUTTON_SIZE_Y, 0, 0, BUTTON_SIZE_X, SlashRef.guiId("plus_button"), (button) -> {
            Packets.sendToServer(new CreateCharPacket(CharacterSelectScreen.SEARCH.getValue()));
            Minecraft.getInstance().setScreen(null);
        });
    }

    @Override
    public void renderWidget(GuiGraphics gui, int mouseX, int mouseY, float delta) {
        if (isHovered()) {
            this.setTooltip(Tooltip.create(TextUTIL.mergeList(splitLongText(Chats.CREATE_NEW_CHARACTER.locName()))));
        }
        super.renderWidget(gui, mouseX, mouseY, delta);
    }
}
