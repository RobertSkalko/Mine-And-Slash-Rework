package com.robertx22.mine_and_slash.saveclasses.gearitem.gear_bases;

import com.robertx22.mine_and_slash.database.data.MinMax;

public class ModRange {

    private ModRange(MinMax minmax) {
        this.minmax = minmax;
    }

    public static ModRange of(MinMax minmax) {
        var b = new ModRange(minmax);
        return b;
    }

    public static ModRange alwaysMax() {
        var b = new ModRange(new MinMax(100, 100));
        b.hasRange = false;
        return b;
    }

    public static ModRange always(int p) {
        var b = new ModRange(new MinMax(p, p));
        b.hasRange = false;
        return b;
    }

    public static ModRange hide() {
        var b = new ModRange(new MinMax(0, 0));
        b.hasRange = false;
        b.hide = true;
        return b;
    }

    public boolean hide = false;
    public boolean hasRange = true;
    public MinMax minmax;
}
