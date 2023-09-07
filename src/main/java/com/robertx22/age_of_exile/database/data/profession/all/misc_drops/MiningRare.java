package com.robertx22.age_of_exile.database.data.profession.all.misc_drops;

import com.robertx22.age_of_exile.database.data.profession.items.ProfessionDropItem;
import com.robertx22.age_of_exile.mmorpg.registers.deferred_wrapper.RegObj;
import net.minecraft.world.item.Item;

public class MiningRare extends ProfessionDropItems {

    public MiningRare() {
        super("mining_drops");
    }

    public RegObj<Item> AMETHYST = drop("amethyst", () -> new ProfessionDropItem("Amethyst", true));
    public RegObj<Item> EMERALD = drop("emerald", () -> new ProfessionDropItem("Emerald", true));
    public RegObj<Item> RUBY = drop("ruby", () -> new ProfessionDropItem("Ruby", true));
    public RegObj<Item> TOPAZ = drop("topaz", () -> new ProfessionDropItem("Topaz", true));
    public RegObj<Item> TOURMALINE = drop("tourmaline", () -> new ProfessionDropItem("Tourmaline", true));
    public RegObj<Item> SAPPHIRE = drop("sapphire", () -> new ProfessionDropItem("Sapphire", true));

}
