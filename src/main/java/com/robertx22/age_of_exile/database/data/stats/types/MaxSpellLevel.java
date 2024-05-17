package com.robertx22.age_of_exile.database.data.stats.types;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.tags.imp.SpellTag;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.interfaces.IGenerated;

import java.util.List;
import java.util.stream.Collectors;

public class MaxSpellLevel extends Stat implements IGenerated<MaxSpellLevel> {

    public SpellTag tag;

    public MaxSpellLevel(SpellTag tag) {
        this.tag = tag;
        this.is_perc = false;
    }

    @Override
    public Elements getElement() {
        return Elements.Physical;
    }

    @Override
    public String locDescForLangFile() {
        return MaxAllSpellLevels.DESC;
    }

    @Override
    public String locNameForLangFile() {
        return "To " + tag.locNameForLangFile() + " Spells";
    }

    @Override
    public String GUID() {
        return "plus_lvl_" + tag.GUID() + "_spells";
    }

    @Override
    public List<MaxSpellLevel> generateAllPossibleStatVariations() {
        return SpellTag.getAll().stream().map(x -> new MaxSpellLevel(x)).collect(Collectors.toList());
    }
}
