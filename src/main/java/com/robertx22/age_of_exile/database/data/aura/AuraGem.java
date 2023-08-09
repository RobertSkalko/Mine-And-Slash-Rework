package com.robertx22.age_of_exile.database.data.aura;

import com.robertx22.age_of_exile.capability.entity.EntityData;
import com.robertx22.age_of_exile.database.data.StatMod;
import com.robertx22.age_of_exile.database.data.support_gem.SupportGem;
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
import java.util.stream.Collectors;

public class AuraGem implements ExileRegistry<SupportGem>, ISkillGem {


    public String id = "";

    public PlayStyle style = PlayStyle.INT;

    public float reservation = 0.25F;

    public List<StatMod> stats = new ArrayList<>();

    String name;

    public AuraGem(String id, String name, PlayStyle style, float reservation, List<StatMod> stats) {
        this.id = id;
        this.name = name + " Aura";
        this.style = style;
        this.reservation = reservation;
        this.stats = stats;
    }

    @Override
    public ExileRegistryType getExileRegistryType() {
        return ExileRegistryTypes.AURA;
    }

    @Override
    public String GUID() {
        return id;
    }

    @Override
    public int Weight() {
        return 1000;
    }

    @Override
    public PlayStyle getStyle() {
        return style;
    }

    @Override
    public AutoLocGroup locNameGroup() {
        return AutoLocGroup.Spells;
    }

    @Override
    public String locNameLangFileGUID() {
        return SlashRef.MODID + ".aura." + GUID();
    }

    @Override
    public String locNameForLangFile() {
        return name;
    }

    public List<ExactStatData> GetAllStats(EntityData en, SkillGemData data) {
        return this.stats
                .stream()
                .map(x -> x.ToExactStat(data.getStatPercent(), en.getLevel()))
                .collect(Collectors.toList());

    }

}
