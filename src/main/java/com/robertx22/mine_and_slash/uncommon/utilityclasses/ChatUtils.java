package com.robertx22.mine_and_slash.uncommon.utilityclasses;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.ChatScreen;

public class ChatUtils {

    public static boolean isChatOpen() {
        return Minecraft.getInstance().screen instanceof ChatScreen;
    }

    public static boolean wasChatOpenRecently() {
        return ClientOnly.ticksSinceChatWasOpened > -5;
    }

}
