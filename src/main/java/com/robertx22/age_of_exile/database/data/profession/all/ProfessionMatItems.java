package com.robertx22.age_of_exile.database.data.profession.all;

import com.robertx22.age_of_exile.database.data.profession.CraftedItemPower;
import com.robertx22.age_of_exile.database.data.profession.items.MaterialItem;
import com.robertx22.age_of_exile.database.data.profession.items.RareMaterialItem;
import com.robertx22.age_of_exile.mmorpg.registers.deferred_wrapper.Def;
import com.robertx22.age_of_exile.mmorpg.registers.deferred_wrapper.RegObj;
import com.robertx22.temp.SkillItemTier;
import net.minecraft.world.item.Item;

import java.util.HashMap;

public class ProfessionMatItems {

    public static HashMap<String, HashMap<SkillItemTier, RegObj<Item>>> TIERED_MAIN_MATS = new HashMap<>();

    // prof > power > item
    public static HashMap<String, HashMap<CraftedItemPower, RegObj<Item>>> POWERED_RARE_MATS = new HashMap<>();


    public static Item getRare(String prof, CraftedItemPower power) {
        return POWERED_RARE_MATS.get(prof).get(power).get();
    }

    public static void init() {


        for (String prof : Professions.ALL) {
            TIERED_MAIN_MATS.put(prof, new HashMap<>());
            POWERED_RARE_MATS.put(prof, new HashMap<>());
        }

        for (SkillItemTier tier : SkillItemTier.values()) {

            TIERED_MAIN_MATS.get(Professions.MINING).put(tier, Def.item("material/mining/" + tier.tier, () -> new MaterialItem(tier, "Ore")));
            TIERED_MAIN_MATS.get(Professions.FARMING).put(tier, Def.item("material/farming/" + tier.tier, () -> new MaterialItem(tier, "Produce")));
            TIERED_MAIN_MATS.get(Professions.HUSBANDRY).put(tier, Def.item("material/meat/" + tier.tier, () -> new MaterialItem(tier, "Raw Meat")));
            TIERED_MAIN_MATS.get(Professions.FISHING).put(tier, Def.item("material/fishing/" + tier.tier, () -> new MaterialItem(tier, "Raw Fish")));
        }


        for (CraftedItemPower power : CraftedItemPower.values()) {
            POWERED_RARE_MATS.get(Professions.FISHING).put(power, Def.item("rare_mats/" + Professions.FISHING + "/" + power.id, () -> new RareMaterialItem(power, "Fish")));
            POWERED_RARE_MATS.get(Professions.MINING).put(power, Def.item("rare_mats/" + Professions.MINING + "/" + power.id, () -> new RareMaterialItem(power, "Crystal")));
            POWERED_RARE_MATS.get(Professions.HUSBANDRY).put(power, Def.item("rare_mats/" + Professions.HUSBANDRY + "/" + power.id, () -> new RareMaterialItem(power, "Beef")));
            POWERED_RARE_MATS.get(Professions.FARMING).put(power, Def.item("rare_mats/" + Professions.FARMING + "/" + power.id, () -> new RareMaterialItem(power, "Produce")));
            POWERED_RARE_MATS.get(Professions.SALVAGING).put(power, Def.item("rare_mats/" + Professions.SALVAGING + "/" + power.id, () -> new RareMaterialItem(power, "Essence")));
        }


    }
}
