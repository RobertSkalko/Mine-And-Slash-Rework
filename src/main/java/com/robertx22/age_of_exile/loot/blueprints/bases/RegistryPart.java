package com.robertx22.age_of_exile.loot.blueprints.bases;

import com.robertx22.age_of_exile.loot.blueprints.ItemBlueprint;
import com.robertx22.library_of_exile.registry.Database;
import com.robertx22.library_of_exile.registry.ExileRegistry;
import com.robertx22.library_of_exile.registry.ExileRegistryType;

import java.util.function.Predicate;

public class RegistryPart<Registry extends ExileRegistry> extends BlueprintPart<Registry, ItemBlueprint> {

    public ExileRegistryType regType;
    public Predicate<Registry> pred;

    public RegistryPart(ItemBlueprint blueprint, ExileRegistryType regType, Predicate<Registry> pred) {
        super(blueprint);
        this.regType = regType;
        this.pred = pred;
    }


    @Override
    protected Registry generateIfNull() {
        return (Registry) Database.getRegistry(regType).getFilterWrapped(x -> pred.test((Registry) x)).random();

    }

    public void set(String id) {
        super.set((Registry) Database.get(regType, id));
    }

}


