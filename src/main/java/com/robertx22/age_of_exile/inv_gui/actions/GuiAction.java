package com.robertx22.age_of_exile.inv_gui.actions;

import com.robertx22.library_of_exile.registry.IGUID;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public abstract class GuiAction implements IGUID {


    static HashMap<String, GuiAction> map = new HashMap<>();

    public static GuiAction get(String id) {
        return map.getOrDefault(id, new GuiAction() {
            @Override
            public List<Component> getTooltip(Player p) {
                return Arrays.asList();
            }

            @Override
            public String GUID() {
                return "empty";
            }
        });
    }

    static {


    }

    static void of(GuiAction a) {
        map.put(a.GUID(), a);
    }

    public abstract List<Component> getTooltip(Player p);
}
