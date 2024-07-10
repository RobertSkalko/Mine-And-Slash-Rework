package com.robertx22.age_of_exile.vanilla_mc.commands.wrapper;

public enum PermWrapper {
    OP(3),
    ANY_PLAYER(0);

    public int perm = 0;

    PermWrapper(int perm) {
        this.perm = perm;
    }
}


