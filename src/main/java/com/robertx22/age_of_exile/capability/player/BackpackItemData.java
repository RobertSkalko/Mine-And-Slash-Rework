package com.robertx22.age_of_exile.capability.player;

import com.robertx22.age_of_exile.capability.player.data.Backpacks;
import com.robertx22.age_of_exile.database.data.rarities.GearRarity;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.uncommon.interfaces.data_items.IRarity;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BackpackItemData {

    public HashMap<Backpacks.BackpackType, Integer> map = new HashMap<>();

    public String rar = IRarity.COMMON_ID;

    public GearRarity getRarity() {
        return ExileDB.GearRarities().get(rar);
    }

    public int getSlots(Backpacks.BackpackType type) {

        return map.getOrDefault(type, 0);
    }

    public List<Component> getTooltip(ItemStack stack) {

        List<Component> list = new ArrayList<>();

        list.add(getRarity().locName().append(" Backpack").withStyle(getRarity().textFormatting()));

        for (Backpacks.BackpackType type : Backpacks.BackpackType.values()) {
            int slots = getSlots(type);
            list.add(type.name.locName().append(" Slots: " + slots).withStyle(ChatFormatting.GREEN));
        }

        list.add(Component.literal(""));
        list.add(Component.literal("Equip to Curio Slot"));

        return list;

    }
}
