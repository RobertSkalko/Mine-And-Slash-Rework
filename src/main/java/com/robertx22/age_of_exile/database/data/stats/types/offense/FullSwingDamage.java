package com.robertx22.age_of_exile.database.data.stats.types.offense;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.StatScaling;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import net.minecraft.util.text.TextFormatting;

public class FullSwingDamage extends Stat {

    public static FullSwingDamage getInstance() {
        return FullSwingDamage.SingletonHolder.INSTANCE;
    }

    @Override
    public String locDescForLangFile() {
        return "Bonus damage when attacking with full basic attack swings";
    }

    public static String GUID = "full_swing_damage";

    private FullSwingDamage() {

        this.min = 10;
        this.scaling = StatScaling.NORMAL;
        this.group = StatGroup.MAIN;

        this.icon = "\u2748";
        this.format = TextFormatting.RED.getName();

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
        return true;
    }

    @Override
    public String locNameForLangFile() {
        return "Full Swing Damage";
    }

    private static class SingletonHolder {
        private static final FullSwingDamage INSTANCE = new FullSwingDamage();
    }
}
