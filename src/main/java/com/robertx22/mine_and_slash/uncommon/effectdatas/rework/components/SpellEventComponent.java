package com.robertx22.mine_and_slash.uncommon.effectdatas.rework.components;

import com.robertx22.mine_and_slash.database.data.spells.components.Spell;

public class SpellEventComponent extends EventComponent {

    public Spell spell;

    public SpellEventComponent(Spell spell) {
        this.spell = spell;
    }

    @Override
    public String GUID() {
        return EventComponents.SPELL_ID;
    }
}
