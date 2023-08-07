package com.robertx22.age_of_exile.database.data.aura;

import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.age_of_exile.database.data.support_gem.SupportGem;
import com.robertx22.age_of_exile.database.registry.ExileRegistryTypes;
import com.robertx22.age_of_exile.uncommon.enumclasses.PlayStyle;
import com.robertx22.library_of_exile.registry.ExileRegistry;
import com.robertx22.library_of_exile.registry.ExileRegistryType;

import java.util.ArrayList;
import java.util.List;

public class AuraGem implements ExileRegistry<SupportGem> {


    public String id = "";

    public PlayStyle style = PlayStyle.INT;

    public float reservation = 0.25F;

    public List<StatModifier> stats = new ArrayList<>();

    public AuraGem(String id, PlayStyle style, float reservation, List<StatModifier> stats) {
        this.id = id;
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
}
