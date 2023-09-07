package com.robertx22.age_of_exile.database.data.profession.all.misc_drops;

import com.robertx22.age_of_exile.database.data.profession.items.ProfessionDropItem;
import com.robertx22.age_of_exile.mmorpg.registers.deferred_wrapper.RegObj;
import net.minecraft.world.item.Item;

public class FarmingCommon extends ProfessionDropItems {

    public FarmingCommon() {
        super("farming_drops");
    }

    public RegObj<Item> GREEN_PEPPER = drop("green_pepper", () -> new ProfessionDropItem("Green Pepper"));
    public RegObj<Item> RED_PEPPER = drop("red_pepper", () -> new ProfessionDropItem("Red Pepper"));
    public RegObj<Item> BOLETE_MUSHROOM = drop("bolete_mushroom", () -> new ProfessionDropItem("Bolete Mushroom"));
    public RegObj<Item> BLOOT_ROOT = drop("blootroot", () -> new ProfessionDropItem("Bloot Root"));
    public RegObj<Item> WORMED_APPLE = drop("wormed_apple", () -> new ProfessionDropItem("Wormed Apple"));

}
