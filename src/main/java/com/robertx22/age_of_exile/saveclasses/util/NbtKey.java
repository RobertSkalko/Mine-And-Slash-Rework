package com.robertx22.age_of_exile.saveclasses.util;

import net.minecraft.nbt.CompoundNBT;

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

    public void set(CompoundNBT nbt, T obj) {
        saving.set(nbt, key, obj);
    }

    public T get(CompoundNBT nbt) {
        return saving.get(nbt, key);
    }


    interface INbtSaving<T> {
        public abstract void set(CompoundNBT nbt, String key, T obj);

        public abstract T get(CompoundNBT nbt, String key);
    }

    private static class Savings {
        public static class StringSaving implements INbtSaving<String> {


            @Override
            public void set(CompoundNBT nbt, String key, String obj) {
                nbt.putString(key, obj);
            }

            @Override
            public String get(CompoundNBT nbt, String key) {
                return nbt.getString(key);
            }
        }
    }
}
