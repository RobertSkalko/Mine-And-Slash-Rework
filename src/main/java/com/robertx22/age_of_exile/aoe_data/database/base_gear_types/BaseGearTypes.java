package com.robertx22.age_of_exile.aoe_data.database.base_gear_types;

import com.robertx22.age_of_exile.database.data.gear_types.bases.BaseGearType;
import com.robertx22.library_of_exile.registry.DataGenKey;

public class BaseGearTypes {

    public final static DataGenKey<BaseGearType> RING = of("ring");
    public final static DataGenKey<BaseGearType> NECKLACE = of("necklace");
    public final static DataGenKey<BaseGearType> SHIELD = of("shield");
    public final static DataGenKey<BaseGearType> SWORD = of("sword");
    public final static DataGenKey<BaseGearType> AXE = of("axe");
    public final static DataGenKey<BaseGearType> SCEPTER = of("scepter");
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

    static DataGenKey<BaseGearType> of(String id) {
        return new DataGenKey<>(id);
    }
}
