package com.robertx22.age_of_exile.database.data.stats.name_regex;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;
import com.robertx22.age_of_exile.uncommon.localization.Formatter;
import com.robertx22.age_of_exile.uncommon.localization.Words;
import net.minecraft.ChatFormatting;

public class BasicStatRegex extends StatNameRegex {

    @Override
    public String getStatNameRegex(ChatFormatting format, ModType type, Stat stat, float v1) {

        if (stat.is_long) {
            return NAME;
        }

        if (type.isFlat()) {


            return VALUE + NAME;

        }
        if (type.isPercent()) {
            String s = v1 > 0 && (stat.IsPercent() && type != ModType.MORE) ? Words.PERCENT_INCREASED_STAT.locName().getString() : "";
            return VALUE + s + NAME;
        }
        if (type == ModType.MORE) {
            if (v1 > 0) {
                return VALUE + " " + Formatter.SECOND_SPECIAL_CALC_STAT.locName(stat.getMultiUseType().prefixWord.locName(), NAME);
            } else {
                return VALUE + " " + Formatter.SECOND_SPECIAL_CALC_STAT.locName(stat.getMultiUseType().prefixLessWord.locName(), NAME);

            }
        }
     

        return null;

    }
}
