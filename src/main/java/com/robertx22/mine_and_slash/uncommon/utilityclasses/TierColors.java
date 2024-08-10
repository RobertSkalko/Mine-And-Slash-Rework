package com.robertx22.mine_and_slash.uncommon.utilityclasses;

import net.minecraft.ChatFormatting;

public class TierColors {

    public static ChatFormatting get(int tier) {

        if (tier == 0) {
            return ChatFormatting.GREEN;
        }
        if (tier == 1) {
            return ChatFormatting.BLUE;
        }
        if (tier == 2) {
            return ChatFormatting.LIGHT_PURPLE;
        }
        if (tier == 3) {
            return ChatFormatting.DARK_PURPLE;
        }
        if (tier > 3) {
            return ChatFormatting.DARK_PURPLE;
        }

        return ChatFormatting.GRAY;
    }

}
