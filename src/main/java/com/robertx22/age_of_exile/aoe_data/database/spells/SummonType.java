package com.robertx22.age_of_exile.aoe_data.database.spells;

// todo this should be a datapack
public enum SummonType {
    TOTAL("total", "Total", 5),
    GOLEM("golem", "Golem", 3),
    UNDEAD("undead", "Undead", 3),
    SPIDER("spider", "Spider", 5),
    BEAST("beast", "Beast", 2),
    NONE("none", "NONE", 0);

    public String id;
    public String name;
    public int maxSummons;

    SummonType(String id, String name, int maxSummons) {
        this.id = id;
        this.name = name;
        this.maxSummons = maxSummons;
    }


}
