package com.robertx22.mine_and_slash.database.data.stats.types.gear_base;

import com.robertx22.mine_and_slash.database.data.stats.Stat;
import com.robertx22.mine_and_slash.database.data.stats.StatScaling;
import com.robertx22.mine_and_slash.database.data.stats.types.offense.WeaponDamage;
import com.robertx22.mine_and_slash.uncommon.enumclasses.Elements;

public class GearDamage extends Stat implements IBaseStatModifier {

    public static GearDamage getInstance() {
        return GearDamage.SingletonHolder.INSTANCE;
    }

    @Override
    public String locDescForLangFile() {
        return "Modifies the gear's base stat";
    }

    public static String GUID = "gear_weapon_damage";

    private GearDamage() {

        this.min = 0;
        this.scaling = StatScaling.NORMAL;
        this.group = StatGroup.MAIN;

    }

    @Override
    public String GUID() {
        return GUID;
    }

    @Override
    public Elements getElement() {
        return Elements.Physical;
    }

    @Override
    public boolean IsPercent() {
        return false;
    }

    @Override
    public String locNameForLangFile() {
        return "Gear Weapon Damage";
    }

    @Override
    public boolean canModifyBaseStat(Stat stat) {
        return stat == WeaponDamage.getInstance();
    }

    private static class SingletonHolder {
        private static final GearDamage INSTANCE = new GearDamage();
    }
}
