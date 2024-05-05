package com.robertx22.age_of_exile.gui.inv_gui.actions;

import com.robertx22.age_of_exile.capability.player.data.PlayerConfigData;
import com.robertx22.age_of_exile.database.data.rarities.GearRarity;
import com.robertx22.age_of_exile.database.data.spells.components.Spell;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.gui.inv_gui.actions.auto_salvage.ToggleAutoSalvageRarity;
import com.robertx22.age_of_exile.mmorpg.SlashRef;
import com.robertx22.library_of_exile.registry.IGUID;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public abstract class GuiAction<T> implements IGUID {


    private static HashMap<String, GuiAction> map = new HashMap<>();

    public static GuiAction get(String id) {
        if (!map.containsKey(id)) {
            regenActionMap();
        }

        return map.getOrDefault(id, new GuiAction() {
            @Override
            public void saveExtraData(FriendlyByteBuf buf) {

            }

            @Override
            public Object loadExtraData(FriendlyByteBuf buf) {
                return null;
            }

            @Override
            public List<Component> getTooltip(Player p) {
                return Arrays.asList();
            }

            @Override
            public void doAction(Player p, Object d) {

            }

            @Override
            public void clientAction(Player p, Object obj) {

            }

            @Override
            public String GUID() {
                return "empty";
            }
        });
    }

    private static void of(GuiAction a) {
        if (!map.containsKey(a.GUID())) {
            map.put(a.GUID(), a);
        }
    }

    // do every time before constructing the gui, because we want to use datapack stuff and not care about when its loaded
    public static void regenActionMap() {


        for (PlayerConfigData.Config v : PlayerConfigData.Config.values()) {
            of(new GuiConfigToggle(v));

        }
  
        for (ToggleAutoSalvageRarity.SalvageType type : ToggleAutoSalvageRarity.SalvageType.values()) {
            for (GearRarity rar : ExileDB.GearRarities().getList()) {
                of(new ToggleAutoSalvageRarity(type, rar));
            }
        }
        for (Spell rw : ExileDB.Spells().getList()) {
            of(new PickSpellAction(rw));
        }

    }


    public ResourceLocation getIcon() {
        return SlashRef.id("textures/gui/inv_gui/icons/" + GUID() + ".png");
    }

    public ResourceLocation getBackGroundIcon() {
        return null;
    }

    public abstract void saveExtraData(FriendlyByteBuf buf);

    public abstract T loadExtraData(FriendlyByteBuf buf);

    public abstract List<Component> getTooltip(Player p);

    public abstract void doAction(Player p, Object obj);

    public abstract void clientAction(Player p, Object obj);


}
