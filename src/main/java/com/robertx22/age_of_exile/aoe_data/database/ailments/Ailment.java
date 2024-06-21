package com.robertx22.age_of_exile.aoe_data.database.ailments;

import com.robertx22.age_of_exile.database.registry.ExileRegistryTypes;
import com.robertx22.age_of_exile.mmorpg.SlashRef;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.interfaces.IAutoLocDesc;
import com.robertx22.age_of_exile.uncommon.interfaces.IAutoLocName;
import com.robertx22.age_of_exile.uncommon.utilityclasses.StringUTIL;
import com.robertx22.library_of_exile.registry.ExileRegistry;
import com.robertx22.library_of_exile.registry.ExileRegistryType;

import java.util.function.Function;

public class Ailment implements ExileRegistry<Ailment>, IAutoLocName, IAutoLocDesc {

    String id;

    public Elements element;

    public boolean isDot;

    public boolean isStrengthEffect;

    public float damageEffectivenessMulti;

    //public int lostOccursEverySeconds = 3;

    public float percentLostEveryXSeconds;

    public int durationTicks;

    public float percentHealthRequiredForFullStrength = 0.25F;

    Function<Ailment, String> desc;

    public int getDurationSeconds() {
        return durationTicks / 20;
    }

    public int getPercentDamage() {
        return (int) (damageEffectivenessMulti * 100F);
    }

    public Ailment(String id, Elements element, boolean isDot, boolean isStrengthEffect, float damageEffectivenessMulti, float percentLostEveryXSeconds, int durationTicks, Function<Ailment, String> desc) {
        this.id = id;
        this.isStrengthEffect = isStrengthEffect;
        this.element = element;
        this.isDot = isDot;
        this.desc = desc;
        this.damageEffectivenessMulti = damageEffectivenessMulti;
        this.percentLostEveryXSeconds = percentLostEveryXSeconds;
        this.durationTicks = durationTicks;

        Ailments.ALL.add(this);
    }

    public int getSlowTier(float multi) {

        if (multi == 0) {
            return -1;
        }

        int tier = (int) (multi * 10D);

        if (multi >= 1) {
            tier = 50;
        }
        return tier;
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
        return StringUTIL.capitalise(id);
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
