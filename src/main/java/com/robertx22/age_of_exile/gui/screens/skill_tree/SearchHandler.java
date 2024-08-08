package com.robertx22.age_of_exile.gui.screens.skill_tree;

import com.robertx22.age_of_exile.gui.screens.skill_tree.buttons.PerkButton;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

// we don't want to run the String contain check on each perk button, handle it by using this class instead, because it will bring us some performance benefits
public class SearchHandler {

    private final ConcurrentHashMap<String, HashSet<PerkButton>> searchHistory = new ConcurrentHashMap<>(30);
    private final int maxCounter = 8;
    public boolean isUpdating = false;
    private int timer = maxCounter;
    private String tickCachedString = "";
    private HandlerState state;
    private WaitingState waitingState;
    private SearchingState searchingState;

    public SearchHandler(SkillTreeScreen screen) {
        this.waitingState = new WaitingState(this);
        this.searchingState = new SearchingState(this);
        this.state = waitingState;
        searchingState.allButtons.addAll(screen.pointPerkButtonMap.values());
    }

    public void tickThis() {
        String value = SkillTreeScreen.SEARCH.getValue();
        if (value.isEmpty()) return;
        if (tickCachedString.isEmpty() || tickCachedString.equals(value)) {
            if (timer > 0) {
                timer--;
            } else {
                timer = maxCounter;
                trySearch();
            }
        } else {
            timer = maxCounter;
        }
        tickCachedString = value;
    }

    public void trySearch() {
        System.out.println("try search!");
        String value = SkillTreeScreen.SEARCH.getValue();
        if (!isThisStringWaiting(value)) {
            this.state.onRequest(value);
        }
    }

    public boolean isThisStringWaiting(String str) {
        return this.waitingState.waitingQueue.contains(str);
    }

    public boolean isThisStringHasResult(String str) {
        return this.searchHistory.containsKey(str);
    }

    @SuppressWarnings("all")
    private <T> T changeState(T state) {
        this.state = (HandlerState) state;
        return state;
    }

    public boolean isThisButtonBeingSearched(String string, PerkButton button) {
        if (isThisStringHasResult(string)) {
            return this.searchHistory.get(string).contains(button);
        }
        return false;
    }

    abstract class HandlerState {
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
            System.out.println("waiting request!");
            this.handler.changeState(handler.searchingState).onSearching(string);
        }

        @Override
        public void onSearching(String string) {

        }

        public void addToWait(String string) {
            waitingQueue.add(string);
        }

        public boolean hasTasks() {
            return !this.waitingQueue.isEmpty();
        }

        public void Research() {
            if (waitingQueue.isEmpty()) return;
            System.out.println("poll one " + waitingQueue.peek());
            onRequest(waitingQueue.poll());
        }
    }

    class SearchingState extends HandlerState {
        private final ArrayList<PerkButton> allButtons = new ArrayList<>(1500);

        public SearchingState(SearchHandler handler) {
            super(handler);
        }

        @Override
        public void onRequest(String string) {
            System.out.println("searching request!");
            if (handler.isThisStringWaiting(string)) return;
            System.out.println("add to wait");
            handler.waitingState.addToWait(string);
        }

        @Override
        public void onSearching(String string) {
            System.out.println("searching " + string);
            if (handler.isThisStringHasResult(string)) {
                System.out.println("already searched " + string);
                handler.changeState(waitingState).Research();
                return;
            }
            System.out.println("searching!");
            ConcurrentHashMap.KeySetView<PerkButton, Boolean> qualifiedButtons = ConcurrentHashMap.newKeySet();
            allButtons.forEach(x -> {
                if (x.getOptimizedState().matchStrings.stream().anyMatch(string1 -> string1.contains(string)) || x.perk.locName().getString().toLowerCase().contains(string))
                    qualifiedButtons.add(x);
            });
            System.out.println("string is " + string);
            System.out.println("results button: " + qualifiedButtons.size());
            System.out.println("put to history " + string);
            searchHistory.put(string, new HashSet<>(qualifiedButtons));
            handler.changeState(waitingState).Research();

        }


    }
}
