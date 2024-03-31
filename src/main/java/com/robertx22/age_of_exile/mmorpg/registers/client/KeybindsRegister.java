package com.robertx22.age_of_exile.mmorpg.registers.client;

import com.robertx22.age_of_exile.mmorpg.SlashRef;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import org.lwjgl.glfw.GLFW;

public class KeybindsRegister {

    static String CATEGORY = "key." + SlashRef.MODID;

    public static KeyMapping HUB_SCREEN_KEY = new KeyMapping("hub_screen", GLFW.GLFW_KEY_H, CATEGORY);


    public static KeyMapping SPELL_HOTBAR_1 = new KeyMapping("spell_1", GLFW.GLFW_KEY_R, CATEGORY);
    public static KeyMapping SPELL_HOTBAR_2 = new KeyMapping("spell_2", GLFW.GLFW_KEY_V, CATEGORY);
    public static KeyMapping SPELL_HOTBAR_3 = new KeyMapping("spell_3", GLFW.GLFW_KEY_C, CATEGORY);
    public static KeyMapping SPELL_HOTBAR_4 = new KeyMapping("spell_4", GLFW.GLFW_KEY_G, CATEGORY);
    public static KeyMapping SPELL_HOTBAR_5 = new KeyMapping("spell_5", GLFW.GLFW_KEY_M, CATEGORY);
    public static KeyMapping SPELL_HOTBAR_6 = new KeyMapping("spell_6", GLFW.GLFW_KEY_J, CATEGORY);
    public static KeyMapping SPELL_HOTBAR_7 = new KeyMapping("spell_7", GLFW.GLFW_KEY_K, CATEGORY);
    public static KeyMapping SPELL_HOTBAR_8 = new KeyMapping("spell_8", GLFW.GLFW_KEY_I, CATEGORY);

    public static KeyMapping getSpellHotbar(int num) {


        int n = num;

        if (n == 0) {
            return SPELL_HOTBAR_1;
        }
        if (n == 1) {
            return SPELL_HOTBAR_2;
        }
        if (n == 2) {
            return SPELL_HOTBAR_3;
        }
        if (n == 3) {
            return SPELL_HOTBAR_4;
        }
        if (n == 4) {
            return SPELL_HOTBAR_5;
        }
        if (n == 5) {
            return SPELL_HOTBAR_6;
        }
        if (n == 6) {
            return SPELL_HOTBAR_7;
        }
        if (n == 7) {
            return SPELL_HOTBAR_8;
        }

        return SPELL_HOTBAR_8;
    }

    public static void register(RegisterKeyMappingsEvent x) {

        x.register(HUB_SCREEN_KEY);

        x.register(SPELL_HOTBAR_1);
        x.register(SPELL_HOTBAR_2);
        x.register(SPELL_HOTBAR_3);
        x.register(SPELL_HOTBAR_4);
        x.register(SPELL_HOTBAR_5);
        x.register(SPELL_HOTBAR_6);
        x.register(SPELL_HOTBAR_7);
        x.register(SPELL_HOTBAR_8);

    }

}
