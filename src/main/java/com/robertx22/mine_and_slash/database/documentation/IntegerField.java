package com.robertx22.mine_and_slash.database.documentation;

import com.robertx22.library_of_exile.registry.ExileRegistry;

public class IntegerField extends JsonField<Integer, Integer> {
    public IntegerField(Integer jsonField, String documentationString) {
        super(jsonField, documentationString);
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
