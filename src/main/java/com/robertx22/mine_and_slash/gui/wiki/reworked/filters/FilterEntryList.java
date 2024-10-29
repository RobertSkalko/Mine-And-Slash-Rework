package com.robertx22.mine_and_slash.gui.wiki.reworked.filters;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.ObjectSelectionList;

import java.util.ArrayList;
import java.util.List;

public class FilterEntryList extends ObjectSelectionList<FilterEntryButton> {

    FilterSelectScreen screen;

    public FilterEntryList(FilterSelectScreen screen, Minecraft mc, int pWidth, int pHeight, int pY0, int pY1, int pItemHeight) {
        super(mc, pWidth, pHeight, pY0, pY1, pItemHeight);
        this.screen = screen;

        reloadAllEntries();
        forceFilter();
    }

    private List<GroupFilterEntry> currentlyDisplayedLevels = new ArrayList<>();


    private void reloadAllEntries() {

        this.currentlyDisplayedLevels = new ArrayList<>();
        this.currentlyDisplayedLevels.addAll(screen.type.entries.get());

    }

    public void forceFilter() {
        this.clearEntries();
        reloadAllEntries();

        for (GroupFilterEntry cur : this.currentlyDisplayedLevels) {
            this.addEntry(new FilterEntryButton(screen, cur));
        }
    }

}