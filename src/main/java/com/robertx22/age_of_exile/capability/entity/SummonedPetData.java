package com.robertx22.age_of_exile.capability.entity;

import com.robertx22.age_of_exile.database.data.spells.components.Spell;

public class SummonedPetData {

    public String spell = "";
    public int ticks = 0;

    public void setup(Spell spell, int ticks) {
        this.spell = spell.GUID();
        this.ticks = ticks;
    }

}
