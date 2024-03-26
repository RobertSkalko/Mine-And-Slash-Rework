package com.robertx22.age_of_exile.tags;

public enum TagType {
    Effect("effect"),
    Spell("spell"),
    Element("element"),
    Mob("mob"),
    GearSlot("gear_slot");

    public String id;

    TagType(String id) {
        this.id = id;
    }
}
