package com.robertx22.age_of_exile.uncommon.utilityclasses;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;

import java.util.ArrayList;
import java.util.List;

public class MutableComponentUtils {


    public static List<MutableComponent> splitLongText(MutableComponent comp) {
        List<MutableComponent> componentList = new ArrayList<>();
        Style format = comp.getStyle();
        String[] originalList = comp.getString().split("\n");

        for (String comp1 : originalList) {
            componentList.add(Component.literal(comp1).withStyle(format));
        }
        return componentList;
    }
}
