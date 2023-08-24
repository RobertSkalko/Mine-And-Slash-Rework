package com.robertx22.age_of_exile.database.data.support_gem;

import com.robertx22.age_of_exile.capability.entity.EntityData;
import com.robertx22.age_of_exile.database.data.StatMod;
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

    public List<StatMod> stats = new ArrayList<>();

    public int weight = 1000;

    public String one_of_a_kind = "";

    public SupportGem(String id, String name, PlayStyle style, float manaMulti, List<StatMod> stats) {
        this.id = id;
        this.locname = name + " Support Gem";
        this.style = style;
        this.manaMulti = manaMulti;
        this.stats = stats;
    }

    public boolean isOneOfAKind() {
        return !one_of_a_kind.isEmpty();
    }

    public SupportGem setOneOfAKind(String id) {
        this.one_of_a_kind = id;
        return this;
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
        return SlashRef.MODID + ".support_gem." + GUID();
    }

    @Override
    public String locNameForLangFile() {
        return locname;
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
