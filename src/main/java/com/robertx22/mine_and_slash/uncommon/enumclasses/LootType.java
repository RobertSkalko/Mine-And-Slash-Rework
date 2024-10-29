package com.robertx22.mine_and_slash.uncommon.enumclasses;

import com.robertx22.library_of_exile.registry.IGUID;
import com.robertx22.mine_and_slash.uncommon.localization.Words;

public enum LootType implements IGUID {

    Gear("Gear", "gear", Words.Gear, 0),
    Gem("Gem", "gem", Words.Gem, 1),
    DungeonKey("Dungeon Key", "dungeon_keys", Words.DungeonKey, 0),
    LevelingRewards("Leveling Rewards", "lvl_rewards", Words.LevelRewards, 0),
    Rune("Rune", "rune", Words.Rune, 2),
    Currency("Currency", "currency", Words.Currency, 3),
    Backpack("Backpack", "backpack", Words.Backpack, 3),
    SkillGem("Skill Gem", "skill_gem", Words.SkillGem, 5),
    Map("Map", "map", Words.Map, 6),
    Jewel("Jewel", "jewel", Words.Jewel, 7),
    LootChest("Loot Chest", "loot_chest", Words.Loot, 8),
    UberFrag("Uber Fragment", "uber_frag", Words.UBER_FRAG, 9),
    WatcherEye("Watcher Eye", "watcher_eye", Words.None, 10),
    ProphecyCoin("Prophecy Coin", "prophecy_coin", Words.None, 11),
    Omen("Omen", "omen", Words.None, 12),
    All("All", "all", Words.All, 0);

    public int custommodeldata;

    private LootType(String name, String id, Words word, int custommodeldata) {
        this.thename = name;
        this.id = id;
        this.word = word;
        this.custommodeldata = custommodeldata;
    }

    public static LootType of(String str) {
        for (LootType type : values()) {
            if (type.id.equals(str)) {
                return type;
            }
        }
        return null;
    }

    public Words word;

    String id;
    private String thename;

    public String getName() {
        return thename;
    }

    @Override
    public String GUID() {
        return id;
    }
}
