package com.robertx22.age_of_exile.saveclasses.unit;

import com.robertx22.age_of_exile.capability.entity.EntityData;

import java.util.Arrays;
import java.util.List;

public enum ResourceType {
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

}
