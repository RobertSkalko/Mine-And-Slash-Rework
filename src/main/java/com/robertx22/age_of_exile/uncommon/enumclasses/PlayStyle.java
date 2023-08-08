package com.robertx22.age_of_exile.uncommon.enumclasses;

public enum PlayStyle {

    STR("str", "Melee") {
  
    },
    DEX("dex", "Ranged") {

    },
    INT("int", "Spell") {

    };


    public String id;
    public String name;

    PlayStyle(String id, String name) {
        this.id = id;
        this.name = name;
    }

}
