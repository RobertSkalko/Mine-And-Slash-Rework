package com.robertx22.age_of_exile.mmorpg.registers.client;

import com.robertx22.age_of_exile.mmorpg.SlashRef;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import org.lwjgl.glfw.GLFW;

public class KeybindsRegister {

    public static KeyMapping HUB_SCREEN_KEY = new KeyMapping("Main Hub Screen", GLFW.GLFW_KEY_H, SlashRef.MOD_NAME);

    public static KeyMapping SPELL_HOTBAR_1 = new KeyMapping("Use Spell 1", GLFW.GLFW_KEY_R, SlashRef.MOD_NAME);
    public static KeyMapping SPELL_HOTBAR_2 = new KeyMapping("Use Spell 2", GLFW.GLFW_KEY_V, SlashRef.MOD_NAME);
    public static KeyMapping SPELL_HOTBAR_3 = new KeyMapping("Use Spell 3", GLFW.GLFW_KEY_C, SlashRef.MOD_NAME);
    public static KeyMapping SPELL_HOTBAR_4 = new KeyMapping("Use Spell 4", GLFW.GLFW_KEY_G, SlashRef.MOD_NAME);

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

    public static void register() {

        ClientRegistry.registerKeyBinding(HUB_SCREEN_KEY);

        ClientRegistry.registerKeyBinding(SPELL_HOTBAR_1);
        ClientRegistry.registerKeyBinding(SPELL_HOTBAR_2);
        ClientRegistry.registerKeyBinding(SPELL_HOTBAR_3);
        ClientRegistry.registerKeyBinding(SPELL_HOTBAR_4);

    }

}
