package com.robertx22.mine_and_slash.database.data.stats.types.gear_base;

import com.robertx22.mine_and_slash.database.data.stats.Stat;
import com.robertx22.mine_and_slash.database.data.stats.StatScaling;
import com.robertx22.mine_and_slash.database.data.stats.types.defense.Armor;
import com.robertx22.mine_and_slash.database.data.stats.types.defense.DodgeRating;
import com.robertx22.mine_and_slash.database.data.stats.types.resources.magic_shield.MagicShield;
import com.robertx22.mine_and_slash.uncommon.enumclasses.Elements;

public class GearDefense extends Stat implements IBaseStatModifier {

    public static GearDefense getInstance() {
        return GearDefense.SingletonHolder.INSTANCE;
    }

    @Override
    public String locDescForLangFile() {
        return "Modifies the gear's base defense value, whether it's armor, magic shield or dodge.";
    }

    public static String GUID = "gear_defense";

    private GearDefense() {

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
        return "Gear Defense";
    }

    @Override
    public boolean canModifyBaseStat(Stat stat) {
        return stat == Armor.getInstance() || stat == DodgeRating.getInstance() || stat == MagicShield.getInstance();
    }

    private static class SingletonHolder {
        private static final GearDefense INSTANCE = new GearDefense();
    }
}
