package com.robertx22.mine_and_slash.database.data.profession.all;

import com.robertx22.mine_and_slash.database.data.profession.CraftedItemPower;
import com.robertx22.mine_and_slash.database.data.profession.items.MaterialItem;
import com.robertx22.mine_and_slash.database.data.profession.items.RareMaterialItem;
import com.robertx22.mine_and_slash.mmorpg.registers.deferred_wrapper.Def;
import com.robertx22.mine_and_slash.mmorpg.registers.deferred_wrapper.RegObj;
import com.robertx22.temp.SkillItemTier;
import net.minecraft.advancements.critereon.EnchantedItemTrigger;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.world.item.Item;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

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

            TIERED_MAIN_MATS.get(Professions.MINING).put(tier, Def.item("material/mining/" + tier.tier, () -> new MaterialItem(Professions.MINING, tier, "Ore")));
            TIERED_MAIN_MATS.get(Professions.FARMING).put(tier, Def.item("material/farming/" + tier.tier, () -> new MaterialItem(Professions.FARMING, tier, "Produce")));
            TIERED_MAIN_MATS.get(Professions.HUSBANDRY).put(tier, Def.item("material/meat/" + tier.tier, () -> new MaterialItem(Professions.HUSBANDRY, tier, "Raw Meat")));
            TIERED_MAIN_MATS.get(Professions.FISHING).put(tier, Def.item("material/fishing/" + tier.tier, () -> new MaterialItem(Professions.FISHING, tier, "Raw Fish")));
        }


        for (CraftedItemPower power : CraftedItemPower.values()) {
            POWERED_RARE_MATS.get(Professions.FISHING).put(power, Def.item("rare_mats/" + Professions.FISHING + "/" + power.id, () -> new RareMaterialItem(Professions.FISHING, power, "Fish")));
            POWERED_RARE_MATS.get(Professions.MINING).put(power, Def.item("rare_mats/" + Professions.MINING + "/" + power.id, () -> new RareMaterialItem(Professions.MINING, power, "Crystal")));
            POWERED_RARE_MATS.get(Professions.HUSBANDRY).put(power, Def.item("rare_mats/" + Professions.HUSBANDRY + "/" + power.id, () -> new RareMaterialItem(Professions.HUSBANDRY, power, "Beef")));
            POWERED_RARE_MATS.get(Professions.FARMING).put(power, Def.item("rare_mats/" + Professions.FARMING + "/" + power.id, () -> new RareMaterialItem(Professions.FARMING, power, "Produce")));
            POWERED_RARE_MATS.get(Professions.SALVAGING).put(power, Def.item("rare_mats/" + Professions.SALVAGING + "/" + power.id, () -> new RareMaterialItem(Professions.SALVAGING, power, "Essence")));
        }


    }

    public static void addDownRankRecipes(Consumer<FinishedRecipe> con) {
        for (Map.Entry<String, HashMap<SkillItemTier, RegObj<Item>>> en : TIERED_MAIN_MATS.entrySet()) {
            for (Map.Entry<SkillItemTier, RegObj<Item>> e : en.getValue().entrySet()) {

                if (e.getKey().tier != SkillItemTier.TIER0.tier) {

                    var lower = e.getKey().lowerTier();

                    var loweritem = en.getValue().get(lower).get();

                    ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, loweritem, 1)
                            .unlockedBy("player_level", EnchantedItemTrigger.TriggerInstance.enchantedItem())
                            .requires(e.getValue().get(), 1)
                            .save(con);
                }
            }
        }

    }
}
