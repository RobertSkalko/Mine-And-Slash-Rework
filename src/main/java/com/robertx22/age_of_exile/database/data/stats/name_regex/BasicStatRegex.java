package com.robertx22.age_of_exile.database.data.stats.name_regex;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;
import net.minecraft.ChatFormatting;

public class BasicStatRegex extends StatNameRegex {

    @Override
    public String getStatNameRegex(ChatFormatting format, ModType type, Stat stat, float v1) {

        if (stat.is_long) {
            return NAME;
        }

        if (type.isFlat()) {

            String adds = "";

            String to = " ";

            return adds + VALUE + to + NAME;

        }
        if (type.isPercent()) {
            String s = v1 > 0 && (stat.IsPercent() && type != ModType.MORE) ? " Extra" : " ";
            return VALUE + s + NAME;
        }
        if (type == ModType.MORE) {
            if (v1 > 0) {
                return VALUE + " " + stat.getMultiUseType().tooltipPrefix + " " + NAME;
            } else {
                return VALUE + " " + stat.getMultiUseType().tooltipPrefixLess + " " + NAME;

            }
        }
        if (type.isItemLocal()) {
            return VALUE + " Gear " + NAME;
        }


        return null;

    }
}
