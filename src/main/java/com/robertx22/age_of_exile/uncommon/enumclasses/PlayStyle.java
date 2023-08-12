package com.robertx22.age_of_exile.uncommon.enumclasses;

import com.robertx22.age_of_exile.aoe_data.database.stats.old.DatapackStats;
import com.robertx22.age_of_exile.database.data.stats.Stat;

public enum PlayStyle {

    STR("str", "Melee") {
        @Override
        public Stat getStat() {
            return DatapackStats.STR;
        }
    },
    DEX("dex", "Ranged") {
        @Override
        public Stat getStat() {
            return DatapackStats.DEX;
        }
    },
    INT("int", "Spell") {
        @Override
        public Stat getStat() {
            return DatapackStats.INT;
        }
    };


    public String id;
    public String name;

    public abstract Stat getStat();

    PlayStyle(String id, String name) {
        this.id = id;
        this.name = name;
    }

}
