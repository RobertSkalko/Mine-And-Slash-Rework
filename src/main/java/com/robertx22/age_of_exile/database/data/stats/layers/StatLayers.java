package com.robertx22.age_of_exile.database.data.stats.layers;

public class StatLayers {


    public static class Defensive {

        // todo does this work its still confusing and probably buggy!!!
        public static StatLayer PHYS_MITIGATION = new StatLayer("physical_mitigation", "Physical Mitigation", StatLayer.LayerAction.MULTIPLY, 100, 0.1F, Integer.MAX_VALUE);
        public static StatLayer ELEMENTAL_MITIGATION = new StatLayer("elemental_mitigation", "Elemental Mitigation", StatLayer.LayerAction.MULTIPLY, 101, 0.1F, Integer.MAX_VALUE);
        public static StatLayer DAMAGE_REDUCTION = new StatLayer("damage_reduction", "Damage Reduction", StatLayer.LayerAction.MULTIPLY, 102, 0.25F, Integer.MAX_VALUE);

        public static void init() {


        }

    }

    public static class Offensive {
        public static StatLayer FLAT_DAMAGE = new StatLayer("flat_damage", "Flat Damage", StatLayer.LayerAction.ADD, 0, -Integer.MAX_VALUE, Integer.MAX_VALUE);
        public static StatLayer ADDITIVE_DMG = new StatLayer("additive_damage", "Additive Damage", StatLayer.LayerAction.MULTIPLY, 1, -1, Integer.MAX_VALUE);
        public static StatLayer CRIT_DAMAGE = new StatLayer("crit_damage", "Crit Damage", StatLayer.LayerAction.MULTIPLY, 2, -1, Integer.MAX_VALUE);
        public static StatLayer DOUBLE_DAMAGE = new StatLayer("double_damage", "Double Damage", StatLayer.LayerAction.MULTIPLY, 3, 2, 2);

        public static void init() {


        }
    }

    public static class Misc {

        public static void init() {


        }
    }

    public static void init() {

        Defensive.init();
        Offensive.init();
        Misc.init();

    }

    public static void register() {

        for (StatLayer a : StatLayer.ALL) {
            a.addToSerializables();
        }

    }
}
