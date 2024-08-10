package com.robertx22.mine_and_slash.saveclasses.skill_gem;

public class MaxLinks {

    public int links;
    public boolean cappedByLevel = false;
    public boolean cappedBySpellLevel = false;

    public MaxLinks(int links, boolean cappedByLevel, boolean cappedBySpellLevel) {
        this.links = links;
        this.cappedByLevel = cappedByLevel;
        this.cappedBySpellLevel = cappedBySpellLevel;
    }
}
