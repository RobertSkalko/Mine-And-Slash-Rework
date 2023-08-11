package com.robertx22.age_of_exile.aoe_data.database.spells;

public enum SummonType {
    GOLEM("golem", "Golem", 3),
    ZOMBIE("zombie", "Zombie", 3),
    SKELETON("skeleton", "Skeleton", 3),
    SPIRIT_WOLF("spirit_wolf", "Spirit Wolf", 2),
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
