package com.robertx22.age_of_exile.database.data.profession.all.misc_drops;

import com.robertx22.age_of_exile.database.data.profession.items.EssenceItem;
import com.robertx22.age_of_exile.mmorpg.registers.deferred_wrapper.RegObj;
import net.minecraft.world.item.Item;

public class RareEssences extends ProfessionDropItems {

    public RareEssences() {
        super("essence");
    }

    public RegObj<Item> INSANITY = drop("insanity", () -> new EssenceItem("Insanity", true));
    public RegObj<Item> HORROR = drop("horror", () -> new EssenceItem("Horror", true));
    public RegObj<Item> DELIRIUM = drop("delirium", () -> new EssenceItem("Delirium", true));
    public RegObj<Item> HYSTERIA = drop("hysteria", () -> new EssenceItem("Hysteria", true));

}
