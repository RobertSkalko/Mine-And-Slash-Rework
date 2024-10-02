package com.robertx22.mine_and_slash.itemstack;

import com.robertx22.mine_and_slash.uncommon.MathHelper;

public class PotentialData {

    public int potential = 0;

    public void add(int num) {
        this.potential = MathHelper.clamp(potential + num, 0, 1000000);
    }

    public void spend(int num) {
        this.potential = MathHelper.clamp(potential - num, 0, 1000000);
    }

}
