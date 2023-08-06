package com.robertx22.age_of_exile.aoe_data.database.ailments;

import com.robertx22.age_of_exile.database.registry.ExileRegistryTypes;
import com.robertx22.age_of_exile.mmorpg.SlashRef;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.interfaces.IAutoLocDesc;
import com.robertx22.age_of_exile.uncommon.interfaces.IAutoLocName;
import com.robertx22.library_of_exile.registry.ExileRegistry;
import com.robertx22.library_of_exile.registry.ExileRegistryType;
import org.codehaus.plexus.util.StringUtils;

import java.util.function.Function;

public class Ailment implements ExileRegistry<Ailment>, IAutoLocName, IAutoLocDesc {

    String id;

    public Elements element;

    public boolean isDot;

    public boolean isStrengthEffect;

    public float damageEffectivenessMulti;

    public float mobHealthPercentLostPerSecond;

    public int durationTicks;

    public float percentHealthRequiredForFullStrength = 0.25F;

    Function<Ailment, String> desc;

    public int getDurationSeconds() {
        return durationTicks / 20;
    }

    public int getPercentDamage() {
        return (int) (damageEffectivenessMulti * 100F);
    }

    public Ailment(String id, Elements element, boolean isDot, boolean isStrengthEffect, float damageEffectivenessMulti, float mobHealthPercentLostPerSecond, int durationTicks, Function<Ailment, String> desc) {
        this.id = id;
        this.isStrengthEffect = isStrengthEffect;
        this.element = element;
        this.isDot = isDot;
        this.desc = desc;
        this.damageEffectivenessMulti = damageEffectivenessMulti;
        this.mobHealthPercentLostPerSecond = mobHealthPercentLostPerSecond;
        this.durationTicks = durationTicks;
    }

    @Override
    public ExileRegistryType getExileRegistryType() {
        return ExileRegistryTypes.AILMENT;
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
    public AutoLocGroup locNameGroup() {
        return AutoLocGroup.StatusEffects;
    }

    @Override
    public String locNameLangFileGUID() {
        return SlashRef.MODID + ".ailment." + GUID();
    }

    @Override
    public String locNameForLangFile() {
        return StringUtils.capitalise(id);
    }

    @Override
    public AutoLocGroup locDescGroup() {
        return AutoLocGroup.StatusEffects;
    }

    @Override
    public String locDescLangFileGUID() {
        return SlashRef.MODID + ".ailment.desc." + GUID();
    }


    @Override
    public String locDescForLangFile() {
        return desc.apply(this);
    }
}
