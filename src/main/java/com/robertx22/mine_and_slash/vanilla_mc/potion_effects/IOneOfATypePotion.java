package com.robertx22.mine_and_slash.vanilla_mc.potion_effects;

public interface IOneOfATypePotion {

    default boolean isOneOfAKind() {
        return !getOneOfATypeType().isEmpty();
    }

    public String getOneOfATypeType();
}
