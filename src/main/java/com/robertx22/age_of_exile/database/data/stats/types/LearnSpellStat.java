package com.robertx22.age_of_exile.database.data.stats.types;

import com.robertx22.age_of_exile.database.data.spells.components.Spell;
import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;

public class LearnSpellStat extends Stat {
    public Spell spell;

    public LearnSpellStat(Spell spell) {
        this.spell = spell;
        this.show_in_gui = false;
    }

    @Override
    public Elements getElement() {
        return Elements.Physical;
    }

    @Override
    public String locDescForLangFile() {
        return "";
    }

    @Override
    public String locNameForLangFile() {
        return "Learn " + spell.locNameForLangFile();
    }

    @Override
    public String GUID() {
        return "learn_" + spell.GUID();
    }
}
