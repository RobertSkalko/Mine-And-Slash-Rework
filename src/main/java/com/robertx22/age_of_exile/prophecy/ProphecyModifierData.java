package com.robertx22.age_of_exile.prophecy;

import com.robertx22.age_of_exile.database.data.prophecy.ProphecyModifier;
import com.robertx22.age_of_exile.database.registry.ExileDB;

public class ProphecyModifierData {

    public String id = "";


    public ProphecyModifierData(String id) {
        this.id = id;
    }

    public ProphecyModifier get() {
        return ExileDB.ProphecyModifiers().get(id);
    }
}
