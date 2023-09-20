package com.robertx22.age_of_exile.maps;

import com.robertx22.age_of_exile.uncommon.interfaces.INeedsNBT;
import net.minecraft.world.item.Item;


public class MapItem extends Item implements INeedsNBT {
    public MapItem() {
        super(new Properties().stacksTo(1));
    }
}
