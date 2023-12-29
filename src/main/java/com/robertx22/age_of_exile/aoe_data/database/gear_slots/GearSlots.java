package com.robertx22.age_of_exile.aoe_data.database.gear_slots;

import com.robertx22.age_of_exile.database.data.gear_slots.GearSlot;
import com.robertx22.age_of_exile.database.data.gear_types.bases.SlotFamily;
import com.robertx22.library_of_exile.registry.ExileRegistryInit;

import java.util.Arrays;

public class GearSlots implements ExileRegistryInit {

    public static String SWORD = new String("sword");
    public static String BOW = new String("bow");
    public static String CROSBOW = new String("crossbow");
    public static String STAFF = new String("staff");

    public static String BOOTS = new String("boots");
    public static String PANTS = new String("pants");
    public static String CHEST = new String("chest");
    public static String HELMET = new String("helmet");

    public static String SHIELD = new String("shield");

    public static String RING = new String("ring");
    public static String NECKLACE = new String("necklace");

    public static String TOME = new String("tome");
    public static String TOTEM = new String("totem");

    public static String[] allWeapons() {
        return Arrays.asList(SWORD, BOW, CROSBOW, STAFF).toArray(new String[]{});
    }

    @Override
    public void registerAll() {

        new GearSlot(SWORD, "Sword", SlotFamily.Weapon, new GearSlot.WeaponData(4, 2, 0.8F), 1, 1000).addToSerializables();
        new GearSlot(STAFF, "Staff", SlotFamily.Weapon, new GearSlot.WeaponData(4, 2, 0.8F), 3, 1500).addToSerializables();
        new GearSlot(BOW, "Bow", SlotFamily.Weapon, new GearSlot.WeaponData(0, 8, 2), 5, 1000).addToSerializables();
        new GearSlot(CROSBOW, "Crossbow", SlotFamily.Weapon, new GearSlot.WeaponData(0, 8, 2), 6, 500).addToSerializables();

        new GearSlot(BOOTS, "Boots", SlotFamily.Armor, new GearSlot.WeaponData(0, 0, 0), 7, 1000).addToSerializables();
        new GearSlot(PANTS, "Pants", SlotFamily.Armor, new GearSlot.WeaponData(0, 0, 0), 8, 1000).addToSerializables();
        new GearSlot(CHEST, "Chest", SlotFamily.Armor, new GearSlot.WeaponData(0, 0, 0), 9, 1000).addToSerializables();
        new GearSlot(HELMET, "Helmet", SlotFamily.Armor, new GearSlot.WeaponData(0, 0, 0), 10, 1000).addToSerializables();

        new GearSlot(SHIELD, "Shield", SlotFamily.OffHand, new GearSlot.WeaponData(0, 0, 0), 11, 500).addToSerializables(); // todo model numbers
        new GearSlot(TOME, "Tome", SlotFamily.OffHand, new GearSlot.WeaponData(0, 0, 0), 11, 500).addToSerializables();
        new GearSlot(TOTEM, "Totem", SlotFamily.OffHand, new GearSlot.WeaponData(0, 0, 0), 11, 500).addToSerializables();

        new GearSlot(RING, "Ring", SlotFamily.Jewelry, new GearSlot.WeaponData(0, 0, 0), 12, 500).addToSerializables();
        new GearSlot(NECKLACE, "Necklace", SlotFamily.Jewelry, new GearSlot.WeaponData(0, 0, 0), 13, 500).addToSerializables();


    }
}
