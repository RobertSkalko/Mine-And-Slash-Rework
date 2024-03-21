package com.robertx22.age_of_exile.uncommon.enumclasses;

import com.robertx22.age_of_exile.uncommon.utilityclasses.StringUTIL;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class WeaponTypes {


    private static List<WeaponTypes> ALL = new ArrayList<>();


    public static WeaponTypes none = new WeaponTypes("none", PlayStyle.STR, WeaponRange.MELEE, false);
    public static WeaponTypes axe = new WeaponTypes("axe", PlayStyle.STR, WeaponRange.MELEE, false);
    public static WeaponTypes staff = new WeaponTypes("staff", PlayStyle.INT, WeaponRange.MELEE, false);
    public static WeaponTypes trident = new WeaponTypes("trident", PlayStyle.STR, WeaponRange.OPTIONALLY_RANGED, false);
    public static WeaponTypes sword = new WeaponTypes("sword", PlayStyle.STR, WeaponRange.MELEE, false).setCanDualWield();
    public static WeaponTypes bow = new WeaponTypes("bow", PlayStyle.DEX, WeaponRange.RANGED, true);
    public static WeaponTypes crossbow = new WeaponTypes("crossbow", PlayStyle.DEX, WeaponRange.RANGED, true);

    static {

        init();
    }

    static void init() {

    }

    WeaponTypes(String id, PlayStyle style, WeaponRange range, boolean isProjectile) {

        this.id = id;
        this.style = style;
        this.range = range;
        this.isProjectile = isProjectile;

        ALL.add(this);
        // ErrorUtils.ifFalse(this.id.equals(this.name()));
    }

    public boolean can_dual_wield = false;
    public PlayStyle style;
    WeaponRange range;
    public String id;
    public boolean isProjectile;
    public List<ResourceLocation> valid_proj_dmg_id = Arrays.asList(new ResourceLocation("testmodid", "name"));
    public List<String> valid_proj_dmg_name = Arrays.asList("arrow", "bolt", "ammo", "bullet", "dart", "missile");


    public WeaponTypes setCanDualWield() {
        this.can_dual_wield = true;
        return this;
    }

    public String locName() {
        return StringUTIL.capitalise(id);
    }

    public boolean isMelee() {
        return this.range == WeaponRange.MELEE;
    }

    public static List<WeaponTypes> getAll() {
        return ALL.stream().filter(x -> x != none).collect(Collectors.toList());

    }
}
