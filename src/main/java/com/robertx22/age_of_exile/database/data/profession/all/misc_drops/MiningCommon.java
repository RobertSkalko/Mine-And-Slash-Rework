package com.robertx22.age_of_exile.database.data.profession.all.misc_drops;

import com.robertx22.age_of_exile.database.data.profession.items.ProfessionDropItem;
import com.robertx22.age_of_exile.mmorpg.registers.deferred_wrapper.RegObj;
import net.minecraft.world.item.Item;

public class MiningCommon extends ProfessionDropItems {

    public MiningCommon() {
        super("mining_drops");
    }

    public RegObj<Item> CORESTONE = drop("corestone_ingot", () -> new ProfessionDropItem("Corestone Ingot"));
    public RegObj<Item> ADAMANTINE = drop("adamantite_ingot", () -> new ProfessionDropItem("Adamantine Ingot"));
    public RegObj<Item> ANCIENT = drop("ancient_ingot", () -> new ProfessionDropItem("Ancient Ingot"));
    public RegObj<Item> MYTHRIL = drop("mythril_ingot", () -> new ProfessionDropItem("Mythril Ingot"));
    public RegObj<Item> ENDERYLL = drop("enderyll_ingot", () -> new ProfessionDropItem("Enderyll Ingot"));
    public RegObj<Item> MAGMACYTE = drop("magmacyte_ingot", () -> new ProfessionDropItem("Magmacyte Ingot"));

}
