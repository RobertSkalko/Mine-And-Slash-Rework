package com.robertx22.age_of_exile.uncommon.enumclasses;

import com.robertx22.age_of_exile.uncommon.utilityclasses.ErrorUtils;

import java.util.Arrays;
import java.util.List;

public enum AttackType {

    hit("hit", "Hit") {},
    dot("dot", "DOT") {};
    // all("all", "Any") {};

    public static List<AttackType> getAllUsed() {
        return Arrays.asList(hit);
    }

    public String id;

    AttackType(String id, String locname) {
        this.id = id;
        this.locname = locname;

        ErrorUtils.ifFalse(id.equals(this.name()));
    }


    public boolean isHit() {
        return this == hit;
    }

    public boolean isAttack() {
        return this == hit;
    }

 
    public String locname;

}