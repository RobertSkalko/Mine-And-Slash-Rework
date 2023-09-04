package com.robertx22.age_of_exile.database.data.profession.all;

import com.robertx22.age_of_exile.database.data.profession.EssenceItem;
import com.robertx22.age_of_exile.database.data.profession.MaterialItem;
import com.robertx22.age_of_exile.mmorpg.registers.deferred_wrapper.Def;
import com.robertx22.age_of_exile.mmorpg.registers.deferred_wrapper.RegObj;
import com.robertx22.temp.SkillItemTier;
import net.minecraft.world.item.Item;

import java.util.HashMap;

public class ProfessionMatItems {

    public static HashMap<String, HashMap<SkillItemTier, RegObj<Item>>> TIERED_MAIN_MATS = new HashMap<>();

    public static class Essence {
        public static RegObj<Item> INT = Def.item("essence/int", () -> new EssenceItem("Intelligence"));
        public static RegObj<Item> STR = Def.item("essence/str", () -> new EssenceItem("Strength"));
        public static RegObj<Item> DEX = Def.item("essence/dex", () -> new EssenceItem("Dexterity"));

        public static RegObj<Item> FIRE = Def.item("essence/fire", () -> new EssenceItem("Fire"));
        public static RegObj<Item> WATER = Def.item("essence/water", () -> new EssenceItem("Water"));
        public static RegObj<Item> LIGHTNING = Def.item("essence/lightning", () -> new EssenceItem("Lightning"));
        public static RegObj<Item> CHAOS = Def.item("essence/chaos", () -> new EssenceItem("Chaos"));
        public static RegObj<Item> PHYSICAL = Def.item("essence/might", () -> new EssenceItem("Might"));

        public static RegObj<Item> LIFE = Def.item("essence/life", () -> new EssenceItem("Life"));
        public static RegObj<Item> ENERGY = Def.item("essence/energy", () -> new EssenceItem("Energy"));
        public static RegObj<Item> MANA = Def.item("essence/mana", () -> new EssenceItem("Mana"));
        public static RegObj<Item> MAGIC = Def.item("essence/magic", () -> new EssenceItem("Magic"));

        public static RegObj<Item> CRIT = Def.item("essence/crit", () -> new EssenceItem("Criticals"));


        static void init() {
            Rare.init();
        }

        public static class Rare {
            public static RegObj<Item> INSANITY = Def.item("essence/insanity", () -> new EssenceItem("Insanity", true));
            public static RegObj<Item> HORROR = Def.item("essence/horror", () -> new EssenceItem("Horror", true));
            public static RegObj<Item> DELIRIUM = Def.item("essence/delirium", () -> new EssenceItem("Delirium", true));
            public static RegObj<Item> HYSTERIA = Def.item("essence/hysteria", () -> new EssenceItem("Hysteria", true));

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
            TIERED_MAIN_MATS.get(Professions.DISENCHANTING).put(tier, Def.item("material/disenchant/" + tier.tier, () -> new MaterialItem(tier, "Dust")));
            TIERED_MAIN_MATS.get(Professions.HUSBANDRY).put(tier, Def.item("material/meat/" + tier.tier, () -> new MaterialItem(tier, "Raw Meat")));
        }

    }
}
