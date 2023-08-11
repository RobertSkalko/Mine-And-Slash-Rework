package com.robertx22.age_of_exile.inv_gui;

import com.robertx22.age_of_exile.database.data.rarities.GearRarity;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.inv_gui.actions.GuiAction;
import com.robertx22.age_of_exile.inv_gui.actions.auto_salvage.ToggleAutoSalvageRarity;

import java.util.*;

public class GuiInventoryGrids {

    public static InvGuiGrid ofSalvageConfig() {
        GuiAction.regenActionMap(); //  todo find better way of ensuring

        HashMap<ToggleAutoSalvageRarity.SalvageType, List<GuiItemData>> map = new HashMap<>();

        var rarities = ExileDB.GearRarities().getList();
        rarities.sort(Comparator.comparingInt(x -> x.item_tier));

        for (ToggleAutoSalvageRarity.SalvageType type : ToggleAutoSalvageRarity.SalvageType.values()) {

            if (!map.containsKey(type)) {
                map.put(type, new ArrayList<>());
            }
            for (GearRarity rar : rarities) {
                map.get(type).add(new GuiItemData(new ToggleAutoSalvageRarity(type, rar)));
            }
        }
        List<List<GuiItemData>> lists = new ArrayList<>();

        for (Map.Entry<ToggleAutoSalvageRarity.SalvageType, List<GuiItemData>> en : map.entrySet()) {
            lists.add(en.getValue());
        }

        return InvGuiGrid.ofYRowLists(lists);
    }
}