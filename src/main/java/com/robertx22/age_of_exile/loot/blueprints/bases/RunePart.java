package com.robertx22.age_of_exile.loot.blueprints.bases;

import com.robertx22.age_of_exile.database.data.runes.Rune;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.loot.blueprints.RuneBlueprint;

public class RunePart extends BlueprintPart<Rune, RuneBlueprint> {

    public RunePart(RuneBlueprint blueprint) {
        super(blueprint);
    }

    @Override
    protected Rune generateIfNull() {
        return ExileDB.Runes().getFilterWrapped(x -> this.blueprint.level.get() >= x.getReqLevelToDrop()).random();
    }
}