package com.robertx22.age_of_exile.database.data.profession.all.misc_drops;

import com.robertx22.age_of_exile.database.data.profession.items.ProfessionDropItem;
import com.robertx22.age_of_exile.mmorpg.registers.deferred_wrapper.RegObj;
import net.minecraft.world.item.Item;

public class RareFarming extends ProfessionDropItems {

    public RareFarming() {
        super("farming_drops");
    }

    public RegObj<Item> DRAGONFRUIT = drop("dragonfruit", () -> new ProfessionDropItem("Dragonfruit", true));
    public RegObj<Item> BRYONY_ROOT = drop("bryony_root", () -> new ProfessionDropItem("Bryony Root", true));
    public RegObj<Item> RED_CORAL = drop("red_coral", () -> new ProfessionDropItem("Red Coral", true));
    public RegObj<Item> MAGIC_LOG = drop("magic_log", () -> new ProfessionDropItem("Magic Log", true));

}
