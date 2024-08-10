package com.robertx22.mine_and_slash.prophecy;

import com.robertx22.mine_and_slash.database.data.prophecy.ProphecyModifier;
import com.robertx22.mine_and_slash.database.registry.ExileDB;

public class ProphecyModifierData {

    public String id = "";


    public ProphecyModifierData(String id) {
        this.id = id;
    }

    public ProphecyModifier get() {
        return ExileDB.ProphecyModifiers().get(id);
    }
}
