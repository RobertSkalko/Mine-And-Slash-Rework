package com.robertx22.mine_and_slash.saveclasses.unit;

import com.robertx22.mine_and_slash.capability.entity.EntityData;
import com.robertx22.library_of_exile.registry.IGUID;

import java.util.Arrays;
import java.util.List;

public enum ResourceType implements IGUID {
    health("health", "Health"),
    magic_shield("magic_shield", "Magic Shield"),
    mana("mana", "Mana"),
    energy("energy", "Energy"),
    blood("blood", "Blood");

    public String id;

    ResourceType(String id, String locname) {
        this.id = id;
        this.locname = locname;
    }


    public boolean isFull(EntityData data) {
        return data.getResources().get(data.getEntity(), this) >= data.getResources().getMax(data.getEntity(), this);
    }

    public static List<ResourceType> getUsed() {
        return Arrays.asList(health, magic_shield, mana, energy);
    }

    public static ResourceType ofId(String id) {

        for (ResourceType type : ResourceType.values()) {
            if (type.id.equals(id)) {
                return type;
            }
        }

        return null;
    }

    public String locname;

    @Override
    public String GUID() {
        return this.id;
    }
}
