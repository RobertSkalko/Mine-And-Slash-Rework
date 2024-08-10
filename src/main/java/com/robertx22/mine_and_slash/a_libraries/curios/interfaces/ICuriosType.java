package com.robertx22.mine_and_slash.a_libraries.curios.interfaces;

public interface ICuriosType {

    String curioTypeName();

    default boolean rightClickEquip() {
        return true;
    }

}
