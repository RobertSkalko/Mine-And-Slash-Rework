package com.robertx22.mine_and_slash.database.data.spells.components;

import java.util.TreeSet;

public record SpellCastInfo(int castTime, int[] castPoint) {

    //for quick search
    public boolean castInThisTick( int target) {
        for (int n : castPoint) {
            if (n == target) return true;
        }
        return false;
    }
}
