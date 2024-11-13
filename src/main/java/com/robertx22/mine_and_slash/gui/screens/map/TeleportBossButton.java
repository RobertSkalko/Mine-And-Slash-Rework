package com.robertx22.mine_and_slash.gui.screens.map;

import com.robertx22.library_of_exile.main.Packets;
import com.robertx22.mine_and_slash.uncommon.localization.Words;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.narration.NarrationElementOutput;

public class TeleportBossButton extends AbstractButton {

    public static int WIDTH = 150;
    public static int HEIGHT = 25;

    public TeleportBossButton(int pX, int pY) {
        super(pX, pY, WIDTH, HEIGHT, Words.TELEPORT_TO_BOSS.locName().withStyle(ChatFormatting.RED, ChatFormatting.BOLD));
    }

    @Override
    public void onPress() {
        Minecraft.getInstance().setScreen(null);
        Packets.sendToServer(new MapTeleportPacket(MapTeleportPacket.Type.TO_BOSS));
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput pNarrationElementOutput) {

    }
}
