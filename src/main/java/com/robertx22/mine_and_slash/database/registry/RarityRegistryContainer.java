package com.robertx22.mine_and_slash.database.registry;

import com.robertx22.mine_and_slash.saveclasses.gearitem.gear_bases.Rarity;
import com.robertx22.library_of_exile.registry.ExileRegistryContainer;
import com.robertx22.library_of_exile.registry.ExileRegistryType;

public class RarityRegistryContainer<T extends Rarity> extends ExileRegistryContainer<T> {

    public RarityRegistryContainer(ExileRegistryType type, String emptyDefault) {
        super(type, emptyDefault);
    }


}
