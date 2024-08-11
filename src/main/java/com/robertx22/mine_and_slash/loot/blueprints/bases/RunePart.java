package com.robertx22.mine_and_slash.loot.blueprints.bases;

import com.robertx22.library_of_exile.registry.FilterListWrap;
import com.robertx22.mine_and_slash.database.data.runes.Rune;
import com.robertx22.mine_and_slash.database.registry.ExileDB;
import com.robertx22.mine_and_slash.loot.blueprints.RuneBlueprint;

public class RunePart extends BlueprintPart<Rune, RuneBlueprint> {

    public RunePart(RuneBlueprint blueprint) {
        super(blueprint);
    }

    @Override
    protected Rune generateIfNull() {
        return droppableAtLevel(this.blueprint.info.level).random();
    }

    public static FilterListWrap<Rune> droppableAtLevel(int lvl) {
        return ExileDB.Runes().getFilterWrapped(x -> lvl >= x.getReqLevelToDrop());
    }
}