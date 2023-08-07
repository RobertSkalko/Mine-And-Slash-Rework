package com.robertx22.age_of_exile.database.data.support_gem;

import com.robertx22.age_of_exile.capability.entity.EntityData;
import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.age_of_exile.database.registry.ExileRegistryTypes;
import com.robertx22.age_of_exile.mmorpg.SlashRef;
import com.robertx22.age_of_exile.saveclasses.ExactStatData;
import com.robertx22.age_of_exile.saveclasses.skill_gem.ISkillGem;
import com.robertx22.age_of_exile.saveclasses.skill_gem.SkillGemData;
import com.robertx22.age_of_exile.uncommon.enumclasses.PlayStyle;
import com.robertx22.library_of_exile.registry.ExileRegistry;
import com.robertx22.library_of_exile.registry.ExileRegistryType;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class SupportGem implements ISkillGem, ExileRegistry<SupportGem> {


    public String id = "";
    public String locname = "";

    public PlayStyle style = PlayStyle.DEX;

    public float manaMulti = 0.25F;

    public List<StatModifier> stats = new ArrayList<>();

    public int weight = 1000;

    public SupportGem(String id, String name, PlayStyle style, float manaMulti, List<StatModifier> stats) {
        this.id = id;
        this.locname = name + " Support Gem";
        this.style = style;
        this.manaMulti = manaMulti;
        this.stats = stats;
    }

    public SupportGem edit(Consumer<SupportGem> s) {
        s.accept(this);
        return this;
    }

    @Override
    public ExileRegistryType getExileRegistryType() {
        return ExileRegistryTypes.SUPPORT_GEM;
    }

    @Override
    public String GUID() {
        return id;
    }

    @Override
    public int Weight() {
        return weight;
    }

    @Override
    public AutoLocGroup locNameGroup() {
        return AutoLocGroup.Spells;
    }

    @Override
    public String locNameLangFileGUID() {
        return locname;
    }

    @Override
    public String locNameForLangFile() {
        return SlashRef.MODID + ".support_gem." + GUID();
    }

    public List<ExactStatData> GetAllStats(EntityData en, SkillGemData data) {
        return this.stats
                .stream()
                .map(x -> x.ToExactStat(data.getStatPercent(), en.getLevel()))
                .collect(Collectors.toList());

    }

    @Override
    public PlayStyle getStyle() {
        return style;
    }
}
