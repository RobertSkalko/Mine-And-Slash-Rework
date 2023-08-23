package com.robertx22.age_of_exile.database.data.rarities;

public final class MobRarity {


    public static MobRarity of(int minlvl, float bonusstatmulti, int affixes) {

        float lootmulti = 1 + (bonusstatmulti * 0.5F);

        MobRarity r = new MobRarity();

        r.min_lvl = minlvl;
        r.stat_multi = 1 + bonusstatmulti * 1F;
        r.extra_hp_multi = 1 + bonusstatmulti * 1.5F;
        r.dmg_multi = 1 + bonusstatmulti * 0.75F;

        r.loot_multi = lootmulti;
        r.exp_multi = lootmulti;

        r.affixes = affixes;

        return r;
    }

    public int min_lvl;

    public float dmg_multi;
    public float extra_hp_multi;
    public float stat_multi;
    public float loot_multi;
    public float exp_multi;
    public int affixes = 0;


    public int minMobLevelForRandomSpawns() {
        return min_lvl;
    }

    public float DamageMultiplier() {
        return dmg_multi;
    }

    public float ExtraHealthMulti() {
        return extra_hp_multi;
    }

    public float StatMultiplier() {
        return stat_multi;
    }

    public float LootMultiplier() {
        return loot_multi;
    }

    public float expMulti() {
        return exp_multi;
    }


}
