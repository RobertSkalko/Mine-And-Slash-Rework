package com.robertx22.age_of_exile.database.data.profession.all;

import com.robertx22.age_of_exile.database.data.profession.items.EssenceItem;
import com.robertx22.age_of_exile.database.data.profession.items.MaterialItem;
import com.robertx22.age_of_exile.mmorpg.registers.deferred_wrapper.Def;
import com.robertx22.age_of_exile.mmorpg.registers.deferred_wrapper.RegObj;
import com.robertx22.temp.SkillItemTier;
import net.minecraft.world.item.Item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Supplier;

public class ProfessionMatItems {

    public static HashMap<String, HashMap<SkillItemTier, RegObj<Item>>> TIERED_MAIN_MATS = new HashMap<>();

    public static class Essence {

        public static List<RegObj<Item>> ALL_NORMAL = new ArrayList<>();

        private static RegObj<Item> essence(String id, Supplier<Item> object) {
            var o = Def.item("essence/" + id, object);
            Essence.ALL_NORMAL.add(o);
            return o;
        }

        public static RegObj<Item> INT = essence("int", () -> new EssenceItem("Intelligence"));
        public static RegObj<Item> STR = essence("str", () -> new EssenceItem("Strength"));
        public static RegObj<Item> DEX = essence("dex", () -> new EssenceItem("Dexterity"));

        public static RegObj<Item> FIRE = essence("fire", () -> new EssenceItem("Fire"));
        public static RegObj<Item> WATER = essence("water", () -> new EssenceItem("Water"));
        public static RegObj<Item> LIGHTNING = essence("lightning", () -> new EssenceItem("Lightning"));
        public static RegObj<Item> CHAOS = essence("chaos", () -> new EssenceItem("Chaos"));
        public static RegObj<Item> PHYSICAL = essence("might", () -> new EssenceItem("Might"));

        public static RegObj<Item> LIFE = essence("life", () -> new EssenceItem("Life"));
        public static RegObj<Item> ENERGY = essence("energy", () -> new EssenceItem("Energy"));
        public static RegObj<Item> MANA = essence("mana", () -> new EssenceItem("Mana"));
        public static RegObj<Item> MAGIC = essence("magic", () -> new EssenceItem("Magic"));

        public static RegObj<Item> CRIT = essence("crit", () -> new EssenceItem("Criticals"));


        static void init() {
            Rare.init();
        }

        public static class Rare {
            public static List<RegObj<Item>> ALL_RARES = new ArrayList<>();

            public static RegObj<Item> INSANITY = rareEssence("insanity", () -> new EssenceItem("Insanity", true));
            public static RegObj<Item> HORROR = rareEssence("horror", () -> new EssenceItem("Horror", true));
            public static RegObj<Item> DELIRIUM = rareEssence("delirium", () -> new EssenceItem("Delirium", true));
            public static RegObj<Item> HYSTERIA = rareEssence("hysteria", () -> new EssenceItem("Hysteria", true));

            private static RegObj<Item> rareEssence(String id, Supplier<Item> object) {
                var o = Def.item("essence/" + id, object);
                Rare.ALL_RARES.add(o);
                return o;
            }

            static void init() {

            }
        }
    }


    public static void init() {
        Essence.init();

        for (String prof : Professions.ALL) {
            TIERED_MAIN_MATS.put(prof, new HashMap<>());
        }

        for (SkillItemTier tier : SkillItemTier.values()) {

            TIERED_MAIN_MATS.get(Professions.MINING).put(tier, Def.item("material/mining/" + tier.tier, () -> new MaterialItem(tier, "Ore")));
            TIERED_MAIN_MATS.get(Professions.FARMING).put(tier, Def.item("material/farming/" + tier.tier, () -> new MaterialItem(tier, "Produce")));
            TIERED_MAIN_MATS.get(Professions.HUSBANDRY).put(tier, Def.item("material/meat/" + tier.tier, () -> new MaterialItem(tier, "Raw Meat")));
        }

    }
}
