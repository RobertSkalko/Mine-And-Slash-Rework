package com.robertx22.age_of_exile.mixin_methods;

import net.minecraft.network.chat.Component;

public class TooltipWidth {


    // todo make this configurable and check if works good
    public static int getMax(Component msg) {
        // for easy hotswapping

        int lines = msg.getSiblings().size();

        int maxlines = 10;
        int extraWidthPerLine = 5;

        int extralines = lines - maxlines;

        if (extralines > 0) {
            int total = 170 + (extralines * extraWidthPerLine); // default
            return total;
        } else {
            return 170;
        }
    }
}
