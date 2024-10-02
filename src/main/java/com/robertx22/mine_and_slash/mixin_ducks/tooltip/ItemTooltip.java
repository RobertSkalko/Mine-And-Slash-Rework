package com.robertx22.mine_and_slash.mixin_ducks.tooltip;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class ItemTooltip {

    static Set<ItemTooltip> ALL = new HashSet<>();

    public static void register(ItemTooltip t) {
        ALL.add(t);
    }

    public static void render(Player p, List<Component> tip, ItemStack stack) {
        for (ItemTooltip t : ALL) {
            if (t.shouldRender(stack)) {
                t.renderTooltip(p, tip, stack);
                if (t.stopsOtherTooltips()) {
                    break;
                }
            }
        }
    }


    public boolean stopsOtherTooltips() {
        return false;
    }

    public abstract boolean shouldRender(ItemStack stack);

   
    public abstract void renderTooltip(Player p, List<Component> tip, ItemStack stack);
}
