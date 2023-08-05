package com.robertx22.age_of_exile.database.data.unique_items.drop_filters;

import com.robertx22.age_of_exile.loot.LootInfo;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

public class SpecificMobFilter extends DropFilter {

    public static String ID = "specific_mob";

    @Override
    public boolean canDrop(DropFilterData data, LootInfo info) {
        return info.mobKilled != null && BuiltInRegistries.ENTITY_TYPE.getKey(info.mobKilled.getType())
                .toString()
                .equals(data.id);
    }

    @Override
    public String GUID() {
        return ID;
    }

    public List<Component> getTooltip(DropFilterData data) {

        List<Component> list = new ArrayList<>();
        list.add(Component.literal("This mobs can drop:"));
        list.add(BuiltInRegistries.ENTITY_TYPE.get(new ResourceLocation(data.id))
                .getDescription());
        return list;

    }
}
