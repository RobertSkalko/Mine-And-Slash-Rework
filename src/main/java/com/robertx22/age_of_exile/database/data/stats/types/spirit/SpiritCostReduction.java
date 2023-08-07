package com.robertx22.age_of_exile.database.data.stats.types.spirit;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import net.minecraft.ChatFormatting;

// todo this might need an event or a method to calc total spirit use
public class SpiritCostReduction extends Stat {

    private SpiritCostReduction() {
        this.icon = "\u2663";
        this.format = ChatFormatting.AQUA.getName();

        this.max = 75;
    }

    public static SpiritCostReduction getInstance() {
        return SpiritCostReduction.SingletonHolder.INSTANCE;
    }

    @Override
    public boolean IsPercent() {
        return true;
    }

    @Override
    public Elements getElement() {
        return Elements.All;
    }

    @Override
    public String locDescForLangFile() {
        return "Reduces spirit cost needed to socket aura gems.";
    }

    @Override
    public String GUID() {
        return "spirit_cost";
    }

    @Override
    public String locNameForLangFile() {
        return "Spirit Cost Reduction";
    }

    private static class SingletonHolder {
        private static final SpiritCostReduction INSTANCE = new SpiritCostReduction();
    }
}
