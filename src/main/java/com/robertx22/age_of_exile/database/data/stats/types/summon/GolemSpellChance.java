package com.robertx22.age_of_exile.database.data.stats.types.summon;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import net.minecraft.ChatFormatting;

public class GolemSpellChance extends Stat {

    private GolemSpellChance() {
        this.format = ChatFormatting.AQUA.getName();
    }

    public static GolemSpellChance getInstance() {
        return GolemSpellChance.SingletonHolder.INSTANCE;
    }

    @Override
    public boolean IsPercent() {
        return true;
    }

    @Override
    public Elements getElement() {
        return null;
    }

    @Override
    public String locDescForLangFile() {
        return "Bonus Chance for golems to cast their AOE nova spells";
    }

    @Override
    public String GUID() {
        return "golem_spell_chance";
    }

    @Override
    public String locNameForLangFile() {
        return "Golem Spell Chance";
    }

    private static class SingletonHolder {
        private static final GolemSpellChance INSTANCE = new GolemSpellChance();
    }
}
