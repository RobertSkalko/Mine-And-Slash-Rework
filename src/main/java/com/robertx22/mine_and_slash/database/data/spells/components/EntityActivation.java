package com.robertx22.mine_and_slash.database.data.spells.components;

public enum EntityActivation {
    ON_CAST(), ON_TICK(), ON_HIT(), ON_EXPIRE(), PER_ENTITY_HIT(), ENTITY_BASIC_ATTACKED();

    EntityActivation() {

    }
}
