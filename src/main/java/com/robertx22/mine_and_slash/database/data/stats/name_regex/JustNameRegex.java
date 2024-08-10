package com.robertx22.mine_and_slash.database.data.stats.name_regex;

import com.robertx22.mine_and_slash.database.data.stats.Stat;
import com.robertx22.mine_and_slash.uncommon.enumclasses.ModType;
import net.minecraft.ChatFormatting;

public class JustNameRegex extends StatNameRegex {

    @Override
    public String getStatNameRegex(ChatFormatting format, ModType type, Stat stat, float v1) {
        return NAME;
    }
}
