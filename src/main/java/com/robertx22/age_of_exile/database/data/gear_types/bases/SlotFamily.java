package com.robertx22.age_of_exile.database.data.gear_types.bases;

import com.robertx22.age_of_exile.database.data.profession.all.ProfessionMatItems;
import com.robertx22.age_of_exile.mmorpg.registers.common.items.SlashItems;
import com.robertx22.age_of_exile.uncommon.utilityclasses.StringUTIL;
import com.robertx22.age_of_exile.vanilla_mc.items.gearitems.VanillaMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

import java.util.function.Supplier;

public enum SlotFamily {
    Weapon("weapon", () -> Items.IRON_SWORD, () -> ProfessionMatItems.ESSENCES.PHYSICAL.get()),
    Armor("armor", () -> Items.IRON_CHESTPLATE, () -> ProfessionMatItems.ESSENCES.LIFE.get()),
    Jewelry("jewelry", () -> SlashItems.GearItems.RINGS.get(VanillaMaterial.IRON).get(), () -> ProfessionMatItems.ESSENCES.MAGIC.get()),
    OffHand("offhand", () -> Items.SHIELD, () -> ProfessionMatItems.ESSENCES.ENERGY.get()),
    NONE("none", () -> Items.AIR, () -> Items.AIR);


    public String id;
    public Supplier<Item> craftItem;
    public Supplier<Item> enchantItem;

    public String locname() {
        return StringUTIL.capitalise(id);
    }

    SlotFamily(String id, Supplier<Item> craftItem, Supplier<Item> enchantItem) {
        this.id = id;
        this.craftItem = craftItem;
        this.enchantItem = enchantItem;
    }


    public boolean isJewelry() {
        return this == Jewelry;
    }

    public boolean isArmor() {
        return this == Armor;
    }

    public boolean isWeapon() {
        return this == Weapon;
    }

    public boolean isOffhand() {
        return this == OffHand;
    }

}
