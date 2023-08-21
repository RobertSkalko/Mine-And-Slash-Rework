package com.robertx22.age_of_exile.vanilla_mc.items.gemrunes;

import com.robertx22.age_of_exile.database.data.StatMod;
import com.robertx22.age_of_exile.database.data.gear_types.bases.SlotFamily;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StatPerType {

    public List<StatMod> armor = new ArrayList<>();
    public List<StatMod> jewerly = new ArrayList<>();
    public List<StatMod> weapon = new ArrayList<>();


    public final List<StatMod> getFor(SlotFamily sfor) {
        if (sfor == SlotFamily.Armor) {
            return armor;
        }
        if (sfor == SlotFamily.Jewelry) {
            return jewerly;
        }
        if (sfor == SlotFamily.Weapon) {
            return weapon;
        }
        return Arrays.asList();
    }

    public static StatPerType of() {
        return new StatPerType();
    }

    public StatPerType addArmor(StatMod mod) {
        armor.add(mod);
        return this;
    }

    public StatPerType addWeapon(StatMod mod) {
        weapon.add(mod);
        return this;
    }

    public StatPerType addJewerly(StatMod mod) {
        jewerly.add(mod);
        return this;
    }

}