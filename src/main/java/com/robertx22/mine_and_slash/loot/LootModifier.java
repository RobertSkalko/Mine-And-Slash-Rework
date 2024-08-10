package com.robertx22.mine_and_slash.loot;

public class LootModifier {

    public float multi = 1;
    public LootModifierEnum name;

    public LootModifier(LootModifierEnum name, float multi) {
        this.multi = multi;
        this.name = name;
    }

    public boolean cancelsLoot() {
        return multi <= 0f;
    }
}
