package com.robertx22.mine_and_slash.event_hooks.player;

import com.robertx22.library_of_exile.main.Packets;
import com.robertx22.mine_and_slash.config.forge.ClientConfigs;
import com.robertx22.mine_and_slash.gui.screens.character_screen.MainHubScreen;
import com.robertx22.mine_and_slash.mmorpg.registers.client.KeybindsRegister;
import com.robertx22.mine_and_slash.mmorpg.registers.client.SpellKeybind;
import com.robertx22.mine_and_slash.uncommon.utilityclasses.ChatUtils;
import com.robertx22.mine_and_slash.vanilla_mc.packets.UnsummonPacket;
import com.robertx22.mine_and_slash.vanilla_mc.packets.spells.TellServerToCastSpellPacket;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.settings.KeyModifier;

public class OnKeyPress {

    public static int cooldown = 0;


    public static void onEndTick(Minecraft mc) {

        if (cooldown > 0) {
            cooldown--;
            return;
        }

        if (mc.player == null) {
            return;
        }

        if (ChatUtils.wasChatOpenRecently()) {
            return;
        }


        if (KeybindsRegister.UNSUMMON.isDown()) {
            Packets.sendToServer(new UnsummonPacket());
            cooldown = 3;
        } else if (KeybindsRegister.HUB_SCREEN_KEY.isDown()) {
            mc.setScreen(new MainHubScreen());
            cooldown = 10;
        } else if (KeybindsRegister.HOTBAR_SWAP.isDown()) {
            SpellKeybind.IS_ON_SECONd_HOTBAR = !SpellKeybind.IS_ON_SECONd_HOTBAR;
            cooldown = 5;
        } else {

            int number = -1;

            var keys = SpellKeybind.ALL;

            if (ClientConfigs.getConfig().HOTBAR_SWAPPING.get()) {
                keys = SpellKeybind.FIRST_HOTBAR_KEYS;
            }

            for (SpellKeybind key : keys) {
                if (key.key.isDown()) {
                    number = key.getIndex();
                }
            }
            // we always use the key modifier when both are pressed but use same keybind
            for (SpellKeybind key : keys) {
                if (key.key.getKeyModifier() != KeyModifier.NONE && key.key.isDown()) {
                    number = key.getIndex();
                }
            }

            if (ClientConfigs.getConfig().HOTBAR_SWAPPING.get()) {
                if (SpellKeybind.IS_ON_SECONd_HOTBAR) {
                    if (number > -1) {
                        number += 4;
                    }
                }
            }

            if (number > -1) {
                // todo make sure its not lagging servers
                Packets.sendToServer(new TellServerToCastSpellPacket(number));
                cooldown = 2;
            }
        }
    }
}
