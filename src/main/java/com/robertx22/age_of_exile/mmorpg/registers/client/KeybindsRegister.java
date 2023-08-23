package com.robertx22.age_of_exile.mmorpg.registers.client;

import com.robertx22.age_of_exile.mmorpg.SlashRef;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import org.lwjgl.glfw.GLFW;

public class KeybindsRegister {

    static String CATEGORY = "key." + SlashRef.MODID;

    public static KeyMapping HUB_SCREEN_KEY = new KeyMapping("hub_screen", GLFW.GLFW_KEY_H, CATEGORY);

    public static KeyMapping TOGGLE_HOTBAR = new KeyMapping("hotbar_toggle", GLFW.GLFW_KEY_CAPS_LOCK, CATEGORY);


    public static KeyMapping SPELL_HOTBAR_1 = new KeyMapping("spell_1", GLFW.GLFW_KEY_R, CATEGORY);
    public static KeyMapping SPELL_HOTBAR_2 = new KeyMapping("spell_2", GLFW.GLFW_KEY_V, CATEGORY);
    public static KeyMapping SPELL_HOTBAR_3 = new KeyMapping("spell_3", GLFW.GLFW_KEY_C, CATEGORY);
    public static KeyMapping SPELL_HOTBAR_4 = new KeyMapping("spell_4", GLFW.GLFW_KEY_G, CATEGORY);

    public static KeyMapping getSpellHotbar(int num) {


        int n = num;
        if (num > 3) {
            n -= 4;
        }
        if (n == 0) {
            return SPELL_HOTBAR_1;
        }
        if (n == 1) {
            return SPELL_HOTBAR_2;
        }
        if (n == 2) {
            return SPELL_HOTBAR_3;
        } else {
            return SPELL_HOTBAR_4;
        }

    }

    public static void register(RegisterKeyMappingsEvent x) {

        x.register(HUB_SCREEN_KEY);
        x.register(TOGGLE_HOTBAR);

        x.register(SPELL_HOTBAR_1);
        x.register(SPELL_HOTBAR_2);
        x.register(SPELL_HOTBAR_3);
        x.register(SPELL_HOTBAR_4);

    }

}
