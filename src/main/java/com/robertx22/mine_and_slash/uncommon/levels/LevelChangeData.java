package com.robertx22.mine_and_slash.uncommon.levels;

public class LevelChangeData {
    public int levelAfterChange;
    public LevelInfo.ChangeType change;
    public LevelInfo.LevelSource source;

    public LevelChangeData(int levelAfterChange, LevelInfo.ChangeType change, LevelInfo.LevelSource source) {
        this.levelAfterChange = levelAfterChange;
        this.change = change;
        this.source = source;
    }
}
