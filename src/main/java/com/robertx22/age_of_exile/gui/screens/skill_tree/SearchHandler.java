package com.robertx22.age_of_exile.gui.screens.skill_tree;

import com.robertx22.age_of_exile.gui.screens.skill_tree.buttons.PerkButton;
import com.robertx22.age_of_exile.gui.screens.skill_tree.buttons.drawer.AllPerkButtonPainter;
import net.minecraftforge.fml.ISystemReportExtender;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

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
    private HandlerState state;
    private WaitingState waitingState;
    private SearchingState searchingState;

    public SearchHandler(SkillTreeScreen screen) {
        this.waitingState = new WaitingState(this);
        this.searchingState = new SearchingState(this);
        this.state = waitingState;
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
        if (value.isEmpty()) {
            this.isUpdating = false;
            return;
        }

        thisTimeSearch = new String(value);
        if (searchHistory.containsKey(thisTimeSearch)) {
            //System.out.println("can find in history");
            this.qualifiedButtons = searchHistory.get(thisTimeSearch);
            this.isUpdating = false;
            return;
        }
        if (lastTimeSearch.equals(thisTimeSearch)) {
            this.isUpdating = false;
            return;
        }
        //it means that this is the first time search
        System.out.println("last is: " + lastTimeSearch);
        System.out.println("this is: " + thisTimeSearch);
        if (lastTimeSearch.isEmpty()) {
            //System.out.println("last is empty");
            Iterator<PerkButton> iterator = allButtons.iterator();
            while (iterator.hasNext()) {
                PerkButton next = iterator.next();
                if (next.getOptimizedState().matchStrings.stream().anyMatch(x -> x.contains(thisTimeSearch)) || next.perk.locName().getString().toLowerCase().contains(thisTimeSearch))
                    qualifiedButtons.add(next);
            }
            //System.out.println("add all!");
        } else {
            //check if new search is inherited from the last time search.
            //System.out.println("is not empty");
            if (lastTimeSearch.indexOf(thisTimeSearch) == 0) {
                System.out.println("contain");
                Iterator<PerkButton> iterator = qualifiedButtons.iterator();
                while (iterator.hasNext()) {
                    PerkButton next = iterator.next();
                    if (next.getOptimizedState().matchStrings.stream().noneMatch(x -> x.contains(thisTimeSearch)) && !next.perk.locName().getString().toLowerCase().contains(thisTimeSearch))
                        iterator.remove();
                }
            } else {
                System.out.println("not contain");
                qualifiedButtons.clear();
                Iterator<PerkButton> iterator = allButtons.iterator();
                while (iterator.hasNext()) {
                    PerkButton next = iterator.next();
                    if (next.getOptimizedState().matchStrings.stream().anyMatch(x -> x.contains(thisTimeSearch)) || next.perk.locName().getString().toLowerCase().contains(thisTimeSearch))
                        qualifiedButtons.add(next);
                }
            }
        }
        //cuz this method is so long, value may sometimes change in this handle process.
        //but really? I think this is the cause of that bug but I can't confirm.
        Iterator<PerkButton> iterator = qualifiedButtons.iterator();
        while (iterator.hasNext()) {
            PerkButton next = iterator.next();
            if (next.getOptimizedState().matchStrings.stream().noneMatch(x -> x.contains(thisTimeSearch)) && !next.perk.locName().getString().toLowerCase().contains(thisTimeSearch)) {
                System.out.println("found one not right, is " + next);
                System.out.println("matchStrings is " + next.getOptimizedState().matchStrings);
                System.out.println("name is " + next.perk.locName().getString().toLowerCase());
                this.isUpdating = false;
                updateSearchResult();
                System.out.println("re-invoke this method!");
                return;

            }

        }
        lastTimeSearch = thisTimeSearch;
        System.out.println("success! add to history");
        System.out.println("key is " + thisTimeSearch);
        searchHistory.put(thisTimeSearch, new HashSet<>(qualifiedButtons));

    }

    @SuppressWarnings("all")
    private <T> T changeState(T state) {
        this.state = (HandlerState) state;
        return state;
    }

    abstract class HandlerState{
        public SearchHandler handler;

        public HandlerState(SearchHandler handler) {
            this.handler = handler;
        }

        public abstract void onRequest(String string);
        public abstract void onSearching(String string);
    }

    class WaitingState extends HandlerState {

        private final ConcurrentLinkedQueue<String> waitingQueue = new ConcurrentLinkedQueue<>();
        public WaitingState(SearchHandler handler) {
            super(handler);
        }

        @Override
        public void onRequest(String string) {
            this.handler.changeState(handler.searchingState).onSearching(string);
        }

        @Override
        public void onSearching(String string) {

        }

        public void addToWait(String string){
            waitingQueue.add(string);
        }
    }

    class SearchingState extends HandlerState {
        public SearchingState(SearchHandler handler) {
            super(handler);
        }

        @Override
        public void onRequest(String string) {
            handler.waitingState.addToWait(string);
        }

        @Override
        public void onSearching(String string) {
        }
    }

    public boolean checkThisButtonIsSearchResult(PerkButton button) {
        return qualifiedButtons.contains(button);
    }
}
