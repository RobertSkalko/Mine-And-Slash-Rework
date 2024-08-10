package com.robertx22.mine_and_slash.database.data.stats.types.offense;

import com.robertx22.mine_and_slash.database.data.stats.Stat;
import com.robertx22.mine_and_slash.database.data.stats.StatScaling;
import com.robertx22.mine_and_slash.uncommon.enumclasses.Elements;
import net.minecraft.ChatFormatting;

public class WeaponDamage extends Stat {

    public static WeaponDamage getInstance() {
        return WeaponDamage.SingletonHolder.INSTANCE;
    }

    @Override
    public String locDescForLangFile() {
        return "Used for certain spells";
    }

    public static String GUID = "weapon_damage";

    private WeaponDamage() {


        this.min = 0;
        this.scaling = StatScaling.NORMAL;
        this.group = StatGroup.MAIN;

        this.icon = Elements.Cold.icon;
        this.format = ChatFormatting.GOLD.getName();

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
        return "Weapon Damage";
    }

    private static class SingletonHolder {
        private static final WeaponDamage INSTANCE = new WeaponDamage();
    }
}
