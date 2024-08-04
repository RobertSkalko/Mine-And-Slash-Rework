package com.robertx22.age_of_exile.database.documentation;

import com.robertx22.age_of_exile.mmorpg.SlashRef;
import com.robertx22.library_of_exile.registry.ExileRegistry;

import java.util.logging.Logger;

public abstract class JsonField<FinalClassType, JsonFieldType> {

    protected String documentationString = "";
    protected boolean canBeEmpty = false;
    JsonFieldType jsonField;

    public JsonField(JsonFieldType jsonField, String documentationString) {
        this.documentationString = documentationString;
        this.jsonField = jsonField;
    }

    public void setCanBeEmpty() {
        canBeEmpty = true;
    }

    public String createDocumentation(String fieldname) {
        String doc = "Field Name: " + fieldname;
        doc += "\n Info: " + documentationString + "\n";
        return doc;
    }

    protected abstract FinalClassType getInternal();

    private FinalClassType getResult;

    public final FinalClassType get() {
        if (getResult == null) {
            getResult = getInternal();
        }
        return getResult;
    }

    public JsonFieldType getBaseType() {
        return jsonField;
    }

    public abstract boolean isEmpty();

    public abstract void runTestInternal(ExileRegistry containingClass);

    public void runTest(ExileRegistry containingClass) {
        Logger log = Logger.getLogger(SlashRef.MOD_NAME);

        if (!canBeEmpty) {
            if (isEmpty()) {
                log.warning(containingClass.GUID() + " is empty when it's not allowed to be. For example, a string set to \"\" is empty.");
            }
        }
        runTestInternal(containingClass);

    }
}
