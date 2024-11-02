package com.robertx22.mine_and_slash.database.data.boss_arena;

import java.util.Arrays;

public class BossArenas {

    public static void init() {

        new BossArena("sandstone", 2, Arrays.asList("minecraft:iron_golem"), 1000).addToSerializables();

    }

}
