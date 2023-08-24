package com.robertx22.age_of_exile.aoe_data.database.base_gear_types;

import com.robertx22.age_of_exile.database.data.gear_types.bases.BaseGearType;
import com.robertx22.library_of_exile.registry.DataGenKey;

public class BaseGearTypes {

    public final static DataGenKey<BaseGearType> RING = of("ring");
    public final static DataGenKey<BaseGearType> NECKLACE = of("necklace");
    public final static DataGenKey<BaseGearType> ARMOR_SHIELD = of("shield");
    public final static DataGenKey<BaseGearType> TOME = of("tome");
    public final static DataGenKey<BaseGearType> FLUTE = of("flute");
    public final static DataGenKey<BaseGearType> SWORD = of("sword");
    public final static DataGenKey<BaseGearType> BOW = of("bow");
    public final static DataGenKey<BaseGearType> CROSSBOW = of("crossbow");
    public final static DataGenKey<BaseGearType> STAFF = of("staff");

    public final static DataGenKey<BaseGearType> CLOTH_BOOTS = of("cloth_boots");
    public final static DataGenKey<BaseGearType> CLOTH_PANTS = of("cloth_pants");
    public final static DataGenKey<BaseGearType> CLOTH_CHEST = of("cloth_chest");
    public final static DataGenKey<BaseGearType> CLOTH_HELMET = of("cloth_helmet");

    public final static DataGenKey<BaseGearType> LEATHER_BOOTS = of("leather_boots");
    public final static DataGenKey<BaseGearType> LEATHER_PANTS = of("leather_pants");
    public final static DataGenKey<BaseGearType> LEATHER_CHEST = of("leather_chest");
    public final static DataGenKey<BaseGearType> LEATHER_HELMET = of("leather_helmet");

    public final static DataGenKey<BaseGearType> PLATE_BOOTS = of("plate_boots");
    public final static DataGenKey<BaseGearType> PLATE_PANTS = of("plate_pants");
    public final static DataGenKey<BaseGearType> PLATE_CHEST = of("plate_chest");
    public final static DataGenKey<BaseGearType> PLATE_HELMET = of("plate_helmet");

    /*
    public final static DataGenKey<BaseGearType> DODGE_ARMOR_BOOTS = of("dodge_armor_boots");
    public final static DataGenKey<BaseGearType> DODGE_ARMOR_PANTS = of("dodge_armor_pants");
    public final static DataGenKey<BaseGearType> DODGE_ARMOR_CHEST = of("dodge_armor_chest");
    public final static DataGenKey<BaseGearType> DODGE_ARMOR_HELMET = of("dodge_armor_helmet");

    public final static DataGenKey<BaseGearType> DODGE_MS_BOOTS = of("dodge_ms_boots");
    public final static DataGenKey<BaseGearType> DODGE_MS_PANTS = of("dodge_ms_pants");
    public final static DataGenKey<BaseGearType> DODGE_MS_CHEST = of("dodge_ms_chest");
    public final static DataGenKey<BaseGearType> DODGE_MS_HELMET = of("dodge_ms_helmet");

    public final static DataGenKey<BaseGearType> ARMOR_MS_BOOTS = of("armor_ms_boots");
    public final static DataGenKey<BaseGearType> ARMOR_MS_PANTS = of("armor_ms_pants");
    public final static DataGenKey<BaseGearType> ARMOR_MS_CHEST = of("armor_ms_chest");
    public final static DataGenKey<BaseGearType> ARMOR_MS_HELMET = of("armor_ms_helmet");


     */

    static DataGenKey<BaseGearType> of(String id) {
        return new DataGenKey<>(id);
    }
}
