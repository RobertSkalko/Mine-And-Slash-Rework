package com.robertx22.age_of_exile.aoe_data.database.boss_spell;

public class BossSpells {

    public static void init() {

        new SummonThornMobs().registerToExileRegistry();
        new BossDealCloseAoe().registerToExileRegistry();
        new SummonExplodyMobs().registerToExileRegistry();

    }
}
