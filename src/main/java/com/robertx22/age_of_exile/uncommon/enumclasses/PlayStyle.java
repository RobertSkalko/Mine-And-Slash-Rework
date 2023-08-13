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

    public static PlayStyle fromID(String id) {
        for (PlayStyle value : values()) {
            if (value.id.equals(id)) {
                return value;
            }
        }
        return PlayStyle.STR;
    }

    PlayStyle(String id, String name) {
        this.id = id;
        this.name = name;
    }

}
