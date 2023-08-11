package com.robertx22.age_of_exile.inv_gui.actions;

import com.robertx22.age_of_exile.database.data.rarities.GearRarity;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.inv_gui.actions.auto_salvage.ToggleAutoSalvageRarity;
import com.robertx22.age_of_exile.mmorpg.SlashRef;
import com.robertx22.library_of_exile.registry.IGUID;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
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
            public void doAction(Player p) {

            }

            @Override
            public String GUID() {
                return "empty";
            }
        });
    }

    // do every time before constructing the gui, because we want to use datapack stuff and not care about when its loaded
    public static void regenActionMap() {

        for (ToggleAutoSalvageRarity.SalvageType type : ToggleAutoSalvageRarity.SalvageType.values()) {
            for (GearRarity rar : ExileDB.GearRarities().getList()) {
                of(new ToggleAutoSalvageRarity(type, rar));
            }
        }
    }


    public ResourceLocation getIcon() {
        return SlashRef.id("textures/gui/inv_gui/icons/" + GUID() + ".png");
    }

    public ResourceLocation getBackGroundIcon() {
        return null;
    }


    static void of(GuiAction a) {
        map.put(a.GUID(), a);
    }

    public abstract List<Component> getTooltip(Player p);

    public abstract void doAction(Player p);


}
