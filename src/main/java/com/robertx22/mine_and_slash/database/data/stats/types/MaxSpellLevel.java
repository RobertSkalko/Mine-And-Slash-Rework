package com.robertx22.mine_and_slash.database.data.stats.types;

import com.robertx22.mine_and_slash.database.data.stats.Stat;
import com.robertx22.mine_and_slash.mmorpg.SlashRef;
import com.robertx22.mine_and_slash.tags.imp.SpellTag;
import com.robertx22.mine_and_slash.uncommon.enumclasses.Elements;
import com.robertx22.mine_and_slash.uncommon.interfaces.IGenerated;

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
    public String locDescLangFileGUID() {
        return SlashRef.MODID + ".stat_desc." + "plus_spell_lvls_desc";
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
