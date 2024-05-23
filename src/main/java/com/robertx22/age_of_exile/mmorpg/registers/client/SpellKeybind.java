package com.robertx22.age_of_exile.mmorpg.registers.client;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.settings.KeyModifier;

import java.util.ArrayList;
import java.util.List;

public class SpellKeybind {

    public static List<SpellKeybind> ALL = new ArrayList<>();

    public KeyMapping key;
    public int num = 0;

    public SpellKeybind(int num, int key, KeyModifier mod) {
        this.key = new KeyMapping("spell_" + num, key, KeybindsRegister.CATEGORY);

        if (mod != null) {
            // todo will this work
            this.key.setKeyModifierAndCode(mod, InputConstants.UNKNOWN);
        }
        this.num = num;

        ALL.add(this);
    }

    public int getIndex() {
        return num - 1;
    }

}
