package com.robertx22.age_of_exile.event_hooks.player;

import com.robertx22.age_of_exile.gui.screens.character_screen.MainHubScreen;
import com.robertx22.age_of_exile.mmorpg.registers.client.KeybindsRegister;
import com.robertx22.age_of_exile.mmorpg.registers.client.SpellKeybind;
import com.robertx22.age_of_exile.uncommon.utilityclasses.ChatUtils;
import com.robertx22.age_of_exile.vanilla_mc.packets.spells.TellServerToCastSpellPacket;
import com.robertx22.library_of_exile.main.Packets;
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


        if (KeybindsRegister.HUB_SCREEN_KEY.isDown()) {
            mc.setScreen(new MainHubScreen());
            cooldown = 10;
        } else {

            int number = -1;


            for (SpellKeybind key : SpellKeybind.ALL) {
                if (key.key.isDown()) {
                    number = key.getIndex();
                }
            }
            // we always use the key modifier when both are pressed but use same keybind
            for (SpellKeybind key : SpellKeybind.ALL) {
                if (key.key.getKeyModifier() != KeyModifier.NONE && key.key.isDown()) {
                    number = key.getIndex();
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
