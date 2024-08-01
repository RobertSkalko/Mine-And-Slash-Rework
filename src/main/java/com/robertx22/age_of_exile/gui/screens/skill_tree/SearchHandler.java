package com.robertx22.age_of_exile.gui.screens.skill_tree;

import com.robertx22.age_of_exile.gui.screens.skill_tree.buttons.PerkButton;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

// we don't want to run the String contain check on each perk button, handle it by using this class instead, because it will bring us some performance benefits
public class SearchHandler {

    private final ArrayList<PerkButton> allButtons = new ArrayList<>(1500);
    private final ConcurrentHashMap<String, HashSet<PerkButton>> searchHistory = new ConcurrentHashMap<>(30);
    private final int maxCounter = 8;
    private String lastTimeSearch = "";
    private String thisTimeSearch = "";
    private HashSet<PerkButton> qualifiedButtons = new HashSet<>(30);
    private int counter = maxCounter;

    private String tickCachedString = "";

    public boolean isUpdating = false;

    public SearchHandler(SkillTreeScreen screen) {
        allButtons.addAll(screen.pointPerkButtonMap.values());
    }

    public void tickThis() {
        String value = SkillTreeScreen.SEARCH.getValue();
        if (value.isEmpty()) return;
        if (tickCachedString.isEmpty() || tickCachedString.equals(value)) {
            if (counter > 0) {
                counter--;
            } else {
                counter = maxCounter;
                updateSearchResult();
                this.isUpdating = false;
            }
        } else {
            counter = maxCounter;
        }
        tickCachedString = value;
    }

    public void updateSearchResult() {
        if (this.isUpdating) return;
        this.isUpdating = true;
        String value = SkillTreeScreen.SEARCH.getValue();
        if (value.isEmpty()) return;

        thisTimeSearch = new String(value);
        if (searchHistory.containsKey(thisTimeSearch)) {
            //System.out.println("can find in history");
            this.qualifiedButtons = searchHistory.get(thisTimeSearch);
            return;
        }
        if (lastTimeSearch.equals(thisTimeSearch)) return;
        //it means that this is the first time search
        if (lastTimeSearch.isEmpty()) {
            //System.out.println("last is empty");
            Iterator<PerkButton> iterator = allButtons.iterator();
            while (iterator.hasNext()) {
                PerkButton next = iterator.next();
                if (next.matchStrings.stream().anyMatch(x -> x.contains(thisTimeSearch)) || next.perk.locName().getString().toLowerCase().contains(thisTimeSearch))
                    qualifiedButtons.add(next);
            }
            //System.out.println("add all!");
        } else {
            //check if new search is inherited from the last time search.
            //System.out.println("is not empty");
            if (thisTimeSearch.contains(lastTimeSearch)) {
                //System.out.println("contain");
                //System.out.println("last is: " + lastTimeSearch);
                //System.out.println("this is: " + thisTimeSearch);
                Iterator<PerkButton> iterator = qualifiedButtons.iterator();
                while (iterator.hasNext()) {
                    PerkButton next = iterator.next();
                    if (next.matchStrings.stream().noneMatch(x -> x.contains(thisTimeSearch)) && !next.perk.locName().getString().toLowerCase().contains(thisTimeSearch))
                        iterator.remove();
                }
            } else {
                //System.out.println("not contain");
                qualifiedButtons.clear();
                Iterator<PerkButton> iterator = allButtons.iterator();
                while (iterator.hasNext()) {
                    PerkButton next = iterator.next();
                    if (next.matchStrings.stream().anyMatch(x -> x.contains(thisTimeSearch)) || next.perk.locName().getString().toLowerCase().contains(thisTimeSearch))
                        qualifiedButtons.add(next);
                }
            }
        }
        //cuz this method is so long, value may sometimes change in this handle process.
        //but really? I think this is the cause of that bug but I can't confirm.
        if (!value.equals(thisTimeSearch)) return;
        lastTimeSearch = thisTimeSearch;
        searchHistory.put(thisTimeSearch, new HashSet<>(qualifiedButtons));

    }

    public boolean checkThisButtonIsSearchResult(PerkButton button) {
        return qualifiedButtons.contains(button);
    }
}
