package com.robertx22.mine_and_slash.gui.wiki.reworked.filters;

import com.robertx22.mine_and_slash.gui.wiki.BestiaryEntry;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

public abstract class GroupFilterEntry {


    public static GroupFilterEntry NONE = new GroupFilterEntry() {
        @Override
        public boolean isValid(BestiaryEntry e) {
            return true;
        }

        @Override
        public MutableComponent getName() {
            return Component.literal("none");
        }
    };

    public abstract boolean isValid(BestiaryEntry e);

    public abstract MutableComponent getName();
    

}