package com.robertx22.age_of_exile.database.data.stats.types.offense;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.StatScaling;
import com.robertx22.age_of_exile.mmorpg.UNICODE;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
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

        this.icon = UNICODE.ATTACK;
        //this.icon = "\u2748";
        this.format = ChatFormatting.RED.getName();

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
