package com.robertx22.mine_and_slash.aoe_data.database.boss_spell;

public class BossSpells {

    public static void init() {

        new BossDealCloseAoe().registerToExileRegistry();
        new SummonExplodyMobs().registerToExileRegistry();

    }
}
