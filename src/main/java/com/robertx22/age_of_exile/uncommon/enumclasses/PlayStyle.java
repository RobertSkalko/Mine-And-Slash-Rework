package com.robertx22.age_of_exile.uncommon.enumclasses;

import org.apache.commons.lang3.StringUtils;

public enum PlayStyle {

    STR() {
        @Override
        public AttackType getAttackType() {
            return AttackType.attack;
        }
    },
    DEX() {
        @Override
        public AttackType getAttackType() {
            return AttackType.attack;
        }
    },
    INT() {
        @Override
        public AttackType getAttackType() {
            return AttackType.spell;
        }
    };


    public String getLocName() {
        return StringUtils.capitalize(name());
    }

    PlayStyle() {

    }

    public abstract AttackType getAttackType();
}
