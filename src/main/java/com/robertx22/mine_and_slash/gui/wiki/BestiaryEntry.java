package com.robertx22.mine_and_slash.gui.wiki;

import com.robertx22.library_of_exile.utils.CLOC;
import com.robertx22.mine_and_slash.uncommon.utilityclasses.ClientOnly;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;


public abstract class BestiaryEntry<T> {


    public T obj;


    public ItemStack stack;

    public ResourceLocation icon = null;

    public BestiaryEntry(T obj) {
        this.obj = obj;
    }

    public List<Component> getTooltip() {
        var list = stack.getTooltipLines(ClientOnly.getPlayer(), TooltipFlag.NORMAL);
        return list;
    }

    public BestiaryEntry setIcon(ResourceLocation i) {
        this.icon = i;
        return this;
    }


    public abstract String getName();

    public String getCutName() {
        String t = "";
        int max = 30;

        t += getName().substring(0, Math.min(max, getName().length()));

        if (getName().length() > max) {
            t += "...";
        }
        return t;
    }

    public boolean isItem() {
        return stack != null && !stack.isEmpty();
    }


    public static class Item<T> extends BestiaryEntry<T> {
        public Item(T obj, ItemStack stack) {
            super(obj);
            this.stack = stack;
            this.name = CLOC.translate(stack.getHoverName());
        }

        String name;

        @Override
        public String getName() {
            return name;
        }
    }

    public static class NamedItem<T> extends BestiaryEntry<T> {
        public NamedItem(T obj, ItemStack stack, String name) {
            super(obj);

            this.stack = stack;
            this.name = name;
        }

        String name;

        @Override
        public String getName() {
            return name;
        }
    }

    public static class Tooltip<T> extends BestiaryEntry<T> {
        public Tooltip(T obj, ItemStack stack, String name, List<Component> tooltip) {
            super(obj);

            this.stack = stack;
            this.name = name;
            this.tooltip = tooltip;
        }

        String name;
        List<Component> tooltip;


        @Override
        public String getName() {
            return name;
        }

        @Override
        public List<Component> getTooltip() {
            return tooltip;
        }
    }

}
