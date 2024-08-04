package com.robertx22.age_of_exile.database.documentation;

import com.robertx22.library_of_exile.registry.ExileRegistry;

public class BooleanField extends JsonField<Boolean, Boolean> {
    public BooleanField(Boolean jsonField, String documentationString) {
        super(jsonField, documentationString);
    }

    @Override
    public Boolean getInternal() {
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
