package com.robertx22.mine_and_slash.database.data.gear_types.bases;

import com.robertx22.mine_and_slash.mmorpg.registers.common.items.SlashItems;
import com.robertx22.mine_and_slash.uncommon.utilityclasses.StringUTIL;
import com.robertx22.mine_and_slash.vanilla_mc.items.gearitems.VanillaMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

import java.util.function.Supplier;

public enum SlotFamily {
    Weapon("weapon", () -> Items.IRON_SWORD),
    Armor("armor", () -> Items.IRON_BOOTS),
    Jewelry("jewelry", () -> SlashItems.GearItems.RINGS.get(VanillaMaterial.IRON).get()),
    OffHand("offhand", () -> Items.SHIELD),
    NONE("none", () -> Items.AIR);


    public String id;
    public Supplier<Item> craftItem;
    public Supplier<Item> enchantItem;

    public String locname() {
        return StringUTIL.capitalise(id);
    }

    SlotFamily(String id, Supplier<Item> craftItem) {
        this.id = id;
        this.craftItem = craftItem;
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
