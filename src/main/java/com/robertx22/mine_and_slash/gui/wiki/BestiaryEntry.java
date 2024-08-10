package com.robertx22.mine_and_slash.gui.wiki;

import com.robertx22.mine_and_slash.uncommon.utilityclasses.ClientOnly;
import com.robertx22.library_of_exile.utils.CLOC;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;


public abstract class BestiaryEntry {

    public ItemStack stack;


    public List<Component> getTooltip() {
        var list = stack.getTooltipLines(ClientOnly.getPlayer(), TooltipFlag.NORMAL);
        return list;
    }

    public abstract String getName();

    public boolean isItem() {
        return stack != null && !stack.isEmpty();
    }


    public static class Item extends BestiaryEntry {
        public Item(ItemStack stack) {
            this.stack = stack;
            this.name = CLOC.translate(stack.getHoverName());
        }

        String name;

        @Override
        public String getName() {
            return name;
        }
    }

    public static class NamedItem extends BestiaryEntry {
        public NamedItem(ItemStack stack, String name) {
            this.stack = stack;
            this.name = name;
        }

        String name;

        @Override
        public String getName() {
            return name;
        }
    }

    public static class Tooltip extends BestiaryEntry {
        public Tooltip(ItemStack stack, String name, List<Component> tooltip) {
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
