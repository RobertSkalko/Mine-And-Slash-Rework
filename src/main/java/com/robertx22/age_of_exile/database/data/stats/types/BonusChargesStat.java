package com.robertx22.age_of_exile.database.data.stats.types;

import com.robertx22.age_of_exile.aoe_data.database.exile_effects.adders.ModEffects;
import com.robertx22.age_of_exile.aoe_data.database.stats.base.EffectCtx;
import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.interfaces.IGenerated;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class BonusChargesStat extends Stat implements IGenerated<BonusChargesStat> {
    public EffectCtx effect;

    public BonusChargesStat(EffectCtx effect) {
        this.effect = effect;
    }

    @Override
    public Elements getElement() {
        return Elements.Physical;
    }

    @Override
    public String locDescForLangFile() {
        return "Increases the amount of charges or stacks an effect can have.";
    }

    @Override
    public String locNameForLangFile() {
        return "Maximum " + effect.locname + " Charges";
    }

    @Override
    public List<BonusChargesStat> generateAllPossibleStatVariations() {
        return Arrays.asList(
                        ModEffects.ENDURANCE_CHARGE, ModEffects.POWER_CHARGE, ModEffects.FRENZY_CHARGE
                )
                .stream().map(x -> new BonusChargesStat(x)).collect(Collectors.toList());
    }

    @Override
    public String GUID() {
        return "max_" + effect.id + "_charges";
    }
}
