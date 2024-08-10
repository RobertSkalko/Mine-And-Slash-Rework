package com.robertx22.mine_and_slash.database.documentation;

import com.robertx22.mine_and_slash.mmorpg.SlashRef;
import com.robertx22.library_of_exile.registry.ExileRegistry;

import java.util.Locale;
import java.util.logging.Logger;

public class JsonIdentifierField extends JsonField<String, String> {
    public JsonIdentifierField(String jsonField) {
        super(jsonField,
                "The Key/GUID/Identifier of the Json/Datapack entry." +
                        " For example, if you make a unique gear json for a 'Epic Sword' sword, you'd want to make the GUID into \"epic_sword\"." +
                        "This GUID must be unique, all lowercase letters.");
    }

    @Override
    protected String getInternal() {
        return getBaseType();
    }

    @Override
    public boolean isEmpty() {
        return getBaseType().isEmpty();
    }

    @Override
    public void runTestInternal(ExileRegistry containingClass) {
        Logger log = Logger.getLogger(SlashRef.MOD_NAME);

        if (!getBaseType().equals(getBaseType().toLowerCase(Locale.ROOT))) {
            log.warning(getBaseType() + " is a GUID and should be in all lowercase letters");
        }

    }
}
