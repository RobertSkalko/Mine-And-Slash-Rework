package com.robertx22.age_of_exile.uncommon.utilityclasses;

import net.minecraft.network.chat.Component;

import java.util.ArrayList;
import java.util.List;

public class TooltipStatsAligner {
    List<Component> list = new ArrayList<>();
    Boolean addEmptyLine = true;
    List<Component> original = new ArrayList<>();

    public TooltipStatsAligner(List<Component> listInput) {
        list.addAll(listInput);

        this.original = new ArrayList<>(listInput);
    }

    public TooltipStatsAligner(List<Component> listInput, Boolean addEmptyLine) {
        list.addAll(listInput);

        this.original = new ArrayList<>(listInput);

        this.addEmptyLine = addEmptyLine;
    }


    // deleted
    public List<Component> buildNewTooltipsStats() {
        return original;
    }
}
