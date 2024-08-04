package com.robertx22.age_of_exile.database.documentation;

import com.robertx22.library_of_exile.registry.ExileRegistry;

public class GenericField<T> extends JsonField<T, T> {
    public GenericField(T jsonField, String documentationString) {
        super(jsonField, documentationString);
    }

    @Override
    protected T getInternal() {
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
