package com.robertx22.mine_and_slash.vanilla_mc.new_commands.wrapper;

public enum PermWrapper {
    OP(3),
    ANY_PLAYER(0);

    public int perm = 0;

    PermWrapper(int perm) {
        this.perm = perm;
    }
}


