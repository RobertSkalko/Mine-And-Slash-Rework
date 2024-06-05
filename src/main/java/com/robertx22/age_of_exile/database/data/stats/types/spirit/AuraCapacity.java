package com.robertx22.age_of_exile.database.data.stats.types.spirit;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import net.minecraft.ChatFormatting;

// turn this into aura capacity, flat stat
// todo this might need an event or a method to calc total spirit use
public class AuraCapacity extends Stat {

    private AuraCapacity() {
        this.icon = "\u2663";
        this.format = ChatFormatting.AQUA.getName();

        this.base = 100;
        this.min = 0;
        this.max = 250;
    }

    public static AuraCapacity getInstance() {
        return AuraCapacity.SingletonHolder.INSTANCE;
    }

    @Override
    public boolean IsPercent() {
        return false;
    }

    @Override
    public Elements getElement() {
        return Elements.ALL;
    }

    @Override
    public String locDescForLangFile() {
        return "The more you have the more auras you can equip.";
    }

    @Override
    public String GUID() {
        return "spirit_cost";
    }

    @Override
    public String locNameForLangFile() {
        return "Augment Capacity";
    }

    private static class SingletonHolder {
        private static final AuraCapacity INSTANCE = new AuraCapacity();
    }
}
