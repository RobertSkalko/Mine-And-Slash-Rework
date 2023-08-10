package com.robertx22.age_of_exile.inv_gui.actions;

import com.robertx22.library_of_exile.registry.IGUID;
import net.minecraft.world.entity.player.Player;

import java.awt.*;
import java.util.HashMap;

public abstract class GuiAction implements IGUID {


    static HashMap<String, GuiAction> map = new HashMap<>();

    static {


    }

    static void of(GuiAction a) {
        map.put(a.GUID(), a);
    }

    public abstract List<Component> getTooltip(Player p);
}
