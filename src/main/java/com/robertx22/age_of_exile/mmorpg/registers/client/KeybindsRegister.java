package com.robertx22.age_of_exile.mmorpg.registers.client;

import com.robertx22.age_of_exile.mmorpg.SlashRef;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.client.settings.KeyModifier;
import org.lwjgl.glfw.GLFW;

public class KeybindsRegister {

    static String CATEGORY = "key." + SlashRef.MODID;

    public static KeyMapping HUB_SCREEN_KEY = new KeyMapping("hub_screen", GLFW.GLFW_KEY_H, CATEGORY);

    public static KeyMapping UNSUMMON = new KeyMapping("unsummon", GLFW.GLFW_KEY_MINUS, CATEGORY);

    public static SpellKeybind SPELL_HOTBAR_1 = new SpellKeybind(1, GLFW.GLFW_KEY_R, null);
    public static SpellKeybind SPELL_HOTBAR_2 = new SpellKeybind(2, GLFW.GLFW_KEY_V, null);
    public static SpellKeybind SPELL_HOTBAR_3 = new SpellKeybind(3, GLFW.GLFW_KEY_C, null);
    public static SpellKeybind SPELL_HOTBAR_4 = new SpellKeybind(4, GLFW.GLFW_KEY_G, null);
    public static SpellKeybind SPELL_HOTBAR_5 = new SpellKeybind(5, GLFW.GLFW_KEY_R, KeyModifier.SHIFT);
    public static SpellKeybind SPELL_HOTBAR_6 = new SpellKeybind(6, GLFW.GLFW_KEY_V, KeyModifier.SHIFT);
    public static SpellKeybind SPELL_HOTBAR_7 = new SpellKeybind(7, GLFW.GLFW_KEY_C, KeyModifier.SHIFT);
    public static SpellKeybind SPELL_HOTBAR_8 = new SpellKeybind(8, GLFW.GLFW_KEY_G, KeyModifier.SHIFT);

    public static SpellKeybind getSpellHotbar(int num) {
        return SpellKeybind.ALL.stream().filter(x -> x.getIndex() == num).findAny().get();
    }

    public static void register(RegisterKeyMappingsEvent x) {

        x.register(HUB_SCREEN_KEY);
        x.register(UNSUMMON);

        for (SpellKeybind k : SpellKeybind.ALL) {
            x.register(k.key);
        }

    }

}
