package com.robertx22.age_of_exile.database.documentation;

import com.robertx22.library_of_exile.registry.ExileRegistry;

public class WeightField extends JsonField<Integer, Integer> {
    public WeightField(Integer jsonField) {
        super(jsonField, "Weight is a number used to represent chance, it's compared to weights of other things. " +
                "For example, if a sword has weight 500, and all other gear types have weight 1000, it means it's half as likely to appear in loot." +
                "You can set this to 0 if you don't want it to be included in any random results. For example, a unique item that is only attainable with a command.");
    }

    @Override
    protected Integer getInternal() {
        return getBaseType();
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public void runTestInternal(ExileRegistry containingClass) {

    }
}
