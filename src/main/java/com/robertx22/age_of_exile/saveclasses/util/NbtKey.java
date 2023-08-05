package com.robertx22.age_of_exile.saveclasses.util;

import net.minecraft.nbt.CompoundTag;

public class NbtKey<T> {

    public static class Stringkey extends NbtKey<String> {

        public Stringkey(String defaultValue, String key) {
            super(defaultValue, new Savings.StringSaving(), key);
        }
    }

    T defaultValue;
    INbtSaving<T> saving;
    String key;

    public NbtKey(T defaultValue, INbtSaving<T> saving, String key) {
        this.defaultValue = defaultValue;
        this.saving = saving;
        this.key = key;
    }

    public void set(CompoundTag nbt, T obj) {
        saving.set(nbt, key, obj);
    }

    public T get(CompoundTag nbt) {
        return saving.get(nbt, key);
    }


    interface INbtSaving<T> {
        public abstract void set(CompoundTag nbt, String key, T obj);

        public abstract T get(CompoundTag nbt, String key);
    }

    private static class Savings {
        public static class StringSaving implements INbtSaving<String> {


            @Override
            public void set(CompoundTag nbt, String key, String obj) {
                nbt.putString(key, obj);
            }

            @Override
            public String get(CompoundTag nbt, String key) {
                return nbt.getString(key);
            }
        }
    }
}
