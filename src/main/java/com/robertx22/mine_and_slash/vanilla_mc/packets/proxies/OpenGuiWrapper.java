package com.robertx22.mine_and_slash.vanilla_mc.packets.proxies;

import com.robertx22.mine_and_slash.gui.screens.character_screen.MainHubScreen;
import com.robertx22.mine_and_slash.gui.wiki.BestiaryGroup;
import com.robertx22.mine_and_slash.gui.wiki.BestiaryScreen;

public class OpenGuiWrapper {

    public static void openMainHub() {

        net.minecraft.client.Minecraft.getInstance()
                .setScreen(new MainHubScreen());

    }

    public static void openWikiRunewords() {

        var sc = new BestiaryScreen();
        net.minecraft.client.Minecraft.getInstance().setScreen(sc);
        sc.setGroup(BestiaryGroup.RUNEWORD);

    }
}
