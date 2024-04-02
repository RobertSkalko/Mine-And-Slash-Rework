package com.robertx22.age_of_exile.mmorpg.registers.client;

import net.minecraft.client.KeyMapping;

import java.util.ArrayList;
import java.util.List;

public class SpellKeybind {

    public static List<SpellKeybind> ALL = new ArrayList<>();

    public KeyMapping key;
    public int num = 0;

    public SpellKeybind(int num, int key) {
        this.key = new KeyMapping("spell_" + num, key, KeybindsRegister.CATEGORY);
        this.num = num;

        ALL.add(this);
    }

    public int getIndex() {
        return num - 1;
    }

}
