package com.robertx22.age_of_exile.database.data.unique_items.drop_filters;

import com.robertx22.age_of_exile.loot.LootInfo;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;

import java.util.ArrayList;
import java.util.List;

public class SpecificMobFilter extends DropFilter {

    public static String ID = "specific_mob";

    @Override
    public boolean canDrop(DropFilterData data, LootInfo info) {
        return info.mobKilled != null && Registry.ENTITY_TYPE.getKey(info.mobKilled.getType())
            .toString()
            .equals(data.id);
    }

    @Override
    public String GUID() {
        return ID;
    }

    public List<Component> getTooltip(DropFilterData data) {

        List<Component> list = new ArrayList<>();
        list.add(new TextComponent("This mobs can drop:"));
        list.add(Registry.ENTITY_TYPE.get(new ResourceLocation(data.id))
            .getDescription());
        return list;

    }
}
