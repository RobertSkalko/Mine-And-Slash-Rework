package com.robertx22.mine_and_slash.maps;

import com.robertx22.mine_and_slash.uncommon.interfaces.INeedsNBT;
import net.minecraft.world.item.Item;


public class MapItem extends Item implements INeedsNBT {
    public MapItem() {
        super(new Properties().stacksTo(1));
    }
}
