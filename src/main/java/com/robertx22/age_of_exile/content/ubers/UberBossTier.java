package com.robertx22.age_of_exile.content.ubers;

import java.util.Comparator;
import java.util.HashMap;

public class UberBossTier {

    public static HashMap<Integer, UberBossTier> map = new HashMap<>();

    public static UberBossTier T1 = new UberBossTier(40, 50, 0, 1);
    public static UberBossTier T2 = new UberBossTier(60, 75, 1, 2);
    public static UberBossTier T3 = new UberBossTier(90, 100, 2, 3);

    public static UberBossTier getTierForFragmentDrop(int lvl) {
        return map.values().stream().filter(x -> lvl >= x.frag_drop_lvl).sorted(Comparator.comparingInt(x -> x.tier)).findFirst().get();
    }

    public int frag_drop_lvl = 1;
    public int boss_lvl = 1;
    public int tier = 0;
    public int watcherEyeAffixes = 1;

    public UberBossTier(int frag_drop_lvl, int boss_lvl, int tier, int watcherEyeAffixes) {
        this.frag_drop_lvl = frag_drop_lvl;
        this.boss_lvl = boss_lvl;
        this.tier = tier;
        this.watcherEyeAffixes = watcherEyeAffixes;

        map.put(tier, this);
    }


}
