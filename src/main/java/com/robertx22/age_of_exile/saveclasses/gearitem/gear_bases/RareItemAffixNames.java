package com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases;

import com.robertx22.age_of_exile.database.data.gear_types.bases.BaseGearType;
import com.robertx22.age_of_exile.saveclasses.item_classes.GearItemData;
import com.robertx22.age_of_exile.uncommon.localization.Specialaffixs;
import com.robertx22.age_of_exile.uncommon.localization.Specialaffixs;

import java.util.HashMap;

public class RareItemAffixNames {

    public static Specialaffixs getPrefix(GearItemData gear) {

        if (prefixAny.containsKey(gear.rp)) {
            return prefixAny.get(gear.rp);
        }

        return null;
    }

    public static Specialaffixs getSuffix(GearItemData gear) {

        if (getSuffixMap(gear.GetBaseGearType())
                .containsKey(gear.rs)) {
            return getSuffixMap(gear.GetBaseGearType()).get(gear.rs);
        }

        return null;
    }

    public static HashMap<Integer, Specialaffixs> prefixAny = new HashMap<Integer, Specialaffixs>() {{
        put(0, Specialaffixs.Miracle);
        put(1, Specialaffixs.Oblivion);
        put(2, Specialaffixs.Golem);
        put(3, Specialaffixs.Beast);
        put(4, Specialaffixs.Spirit);
        put(5, Specialaffixs.Rage);
    }};

    private static HashMap<Integer, Specialaffixs> ring = new HashMap<Integer, Specialaffixs>() {{
        put(0, Specialaffixs.Band);
        put(1, Specialaffixs.Eye);
        put(2, Specialaffixs.Loop);
    }};
    private static HashMap<Integer, Specialaffixs> necklace = new HashMap<Integer, Specialaffixs>() {{
        put(0, Specialaffixs.Beads);
        put(1, Specialaffixs.Charm);
        put(2, Specialaffixs.Locket);
    }};
    private static HashMap<Integer, Specialaffixs> helmet = new HashMap<Integer, Specialaffixs>() {{
        put(0, Specialaffixs.Crown);
        put(1, Specialaffixs.Circlet);
        put(2, Specialaffixs.Horn);
    }};
    private static HashMap<Integer, Specialaffixs> chest = new HashMap<Integer, Specialaffixs>() {{
        put(0, Specialaffixs.Cloak);
        put(1, Specialaffixs.Coat);
        put(2, Specialaffixs.Mantle);
        put(3, Specialaffixs.Shell);
    }};

    private static HashMap<Integer, Specialaffixs> shield = new HashMap<Integer, Specialaffixs>() {{
        put(0, Specialaffixs.Aegis);
        put(1, Specialaffixs.Barrier);
        put(2, Specialaffixs.Guard);
        put(3, Specialaffixs.Tower);
    }};
    private static HashMap<Integer, Specialaffixs> boots = new HashMap<Integer, Specialaffixs>() {{
        put(0, Specialaffixs.Road);
        put(1, Specialaffixs.Dash);
        put(2, Specialaffixs.Hoof);
    }};
    private static HashMap<Integer, Specialaffixs> weapons = new HashMap<Integer, Specialaffixs>() {{
        put(0, Specialaffixs.Bane);
        put(1, Specialaffixs.Bite);
        put(2, Specialaffixs.Wind);
        put(3, Specialaffixs.Star);
        put(4, Specialaffixs.Splitter);
    }};
    
    private static HashMap<Integer, Specialaffixs> pants = new HashMap<Integer, Specialaffixs>() {{
        put(0, Specialaffixs.Leggings);
        put(1, Specialaffixs.Legguards);
        put(2, Specialaffixs.Legwraps);
        put(3, Specialaffixs.Leggings);
        put(4, Specialaffixs.Britches);
    }};

    private static HashMap<Integer, Specialaffixs> defaults = new HashMap<Integer, Specialaffixs>() {
        {
            put(0, Specialaffixs.Creation);
            put(1, Specialaffixs.Crest);
            put(2, Specialaffixs.Keep);
            put(3, Specialaffixs.Ward);
            put(4, Specialaffixs.Refuge);
        }
    };

    public static HashMap<Integer, Specialaffixs> getSuffixMap(BaseGearType slot) {

        if (slot.isWeapon()) {
            return weapons;
        }
        if (slot.getTags()
                .contains(BaseGearType.SlotTag.boots)) {
            return boots;
        }
        if (slot.getTags()
                .contains(BaseGearType.SlotTag.chest)) {
            return chest;
        }
        if (slot.getTags()
                .contains(BaseGearType.SlotTag.helmet)) {
            return helmet;
        }
        if (slot.getTags()
                .contains(BaseGearType.SlotTag.ring)) {
            return ring;
        }
        if (slot.getTags()
                .contains(BaseGearType.SlotTag.necklace)) {
            return necklace;
        }
        if (slot.getTags()
                .contains(BaseGearType.SlotTag.shield)) {
            return shield;
        }
        if (slot.getTags()
                .contains(BaseGearType.SlotTag.pants)) {
            return pants;
        }

        return defaults;

    }

}
