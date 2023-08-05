package com.robertx22.age_of_exile.database.data.stats.name_regex;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;
import net.minecraft.ChatFormatting;

public class BasicLocalStatRegex extends StatNameRegex {

    @Override
    public ChatFormatting statColor(Stat stat) {
        return ChatFormatting.WHITE;
    }

    @Override
    public ChatFormatting numberColor(ChatFormatting format, Stat stat, float val) {
        return stat.getFormat();
    }

    @Override
    public String getStatNameRegex(ChatFormatting format, ModType type, Stat stat, float v1) {

        return NAME + ": " + numberColor(format, stat, v1) + VALUE;

    }
}
