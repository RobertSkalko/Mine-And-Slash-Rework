package com.robertx22.age_of_exile.database.data.profession.all.misc_drops;

import com.robertx22.age_of_exile.database.data.profession.items.EssenceItem;
import com.robertx22.age_of_exile.mmorpg.registers.deferred_wrapper.RegObj;
import net.minecraft.world.item.Item;

public class Essences extends ProfessionDropItems {

    public Essences() {
        super("essence");
    }

    public RegObj<Item> INT = drop("int", () -> new EssenceItem("Intelligence"));
    public RegObj<Item> STR = drop("str", () -> new EssenceItem("Strength"));
    public RegObj<Item> DEX = drop("dex", () -> new EssenceItem("Dexterity"));

    public RegObj<Item> FIRE = drop("fire", () -> new EssenceItem("Fire"));
    public RegObj<Item> WATER = drop("water", () -> new EssenceItem("Water"));
    public RegObj<Item> LIGHTNING = drop("lightning", () -> new EssenceItem("Lightning"));
    public RegObj<Item> CHAOS = drop("chaos", () -> new EssenceItem("Chaos"));
    public RegObj<Item> PHYSICAL = drop("might", () -> new EssenceItem("Might"));

    public RegObj<Item> LIFE = drop("life", () -> new EssenceItem("Life"));
    public RegObj<Item> ENERGY = drop("energy", () -> new EssenceItem("Energy"));
    public RegObj<Item> MANA = drop("mana", () -> new EssenceItem("Mana"));
    public RegObj<Item> MAGIC = drop("magic", () -> new EssenceItem("Magic"));

    public RegObj<Item> CRIT = drop("crit", () -> new EssenceItem("Criticals"));


}
