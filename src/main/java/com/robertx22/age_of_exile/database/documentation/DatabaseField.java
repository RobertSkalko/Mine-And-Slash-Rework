package com.robertx22.age_of_exile.database.documentation;


import com.robertx22.age_of_exile.mmorpg.SlashRef;
import com.robertx22.library_of_exile.registry.Database;
import com.robertx22.library_of_exile.registry.ExileRegistry;
import com.robertx22.library_of_exile.registry.ExileRegistryType;

import java.util.logging.Logger;

public class DatabaseField<FinalClassType extends ExileRegistry> extends JsonField<FinalClassType, String> {

    public ExileRegistryType registryType;

    public DatabaseField(String jsonField, ExileRegistryType type) {
        super(jsonField, "A database id field of MNS registry " + type.id);
        this.registryType = type;
    }


    @Override
    public FinalClassType getInternal() {
        return (FinalClassType) Database.getRegistry(registryType).get(this.getBaseType());
    }

    @Override
    public boolean isEmpty() {
        return getBaseType().isEmpty();
    }

    @Override
    public void runTestInternal(ExileRegistry containingClass) {
        Logger log = Logger.getLogger(SlashRef.MOD_NAME);

        if (canBeEmpty && isEmpty()) {
            return;
        }
        getInternal(); // this already errors if wrong
    }
}
