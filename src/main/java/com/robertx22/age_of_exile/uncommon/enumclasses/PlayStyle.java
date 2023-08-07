package com.robertx22.age_of_exile.uncommon.enumclasses;

public enum PlayStyle {

    STR("str", "Melee") {
        @Override
        public AttackType getAttackType() {
            return AttackType.attack;
        }
    },
    DEX("dex", "Ranged") {
        @Override
        public AttackType getAttackType() {
            return AttackType.attack;
        }
    },
    INT("int", "Spell") {
        @Override
        public AttackType getAttackType() {
            return AttackType.spell;
        }
    };


    public String id;
    public String name;

    PlayStyle(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public abstract AttackType getAttackType();
}
