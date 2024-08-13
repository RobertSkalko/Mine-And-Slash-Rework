package com.robertx22.mine_and_slash.database.data.profession.all;

import com.robertx22.mine_and_slash.database.data.currency.gear.SharpeningStone;
import com.robertx22.mine_and_slash.database.data.gear_types.bases.SlotFamily;
import com.robertx22.mine_and_slash.database.data.profession.ProfessionRecipe;
import com.robertx22.mine_and_slash.database.data.profession.buffs.StatBuffs;
import com.robertx22.mine_and_slash.database.data.profession.items.ProfDropTierPickerCurrency;
import com.robertx22.mine_and_slash.mmorpg.registers.common.items.RarityItemHolder;
import com.robertx22.mine_and_slash.mmorpg.registers.common.items.RarityItems;
import com.robertx22.mine_and_slash.uncommon.interfaces.data_items.IRarity;
import com.robertx22.temp.SkillItemTier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class ProfessionRecipes {

    public static void init() {

        buffConsumes();
        gearCrafting();
        enchanting();
    }

    private static void enchanting() {


        for (SlotFamily fam : SlotFamily.values()) {
            if (fam != SlotFamily.NONE) {

                float rarnumMulti = 1;
                int rarnum = 1;

                for (String rar : IRarity.NORMAL_GEAR_RARITIES) {
                    float finalRarnumMulti = rarnumMulti;

                    var b = ProfessionRecipe.TierBuilder.of(x -> ProfessionProductItems.CRAFTED_ENCHANTS.get(fam).get(rar).get(), Professions.INFUSING, 3)
                            .onlyOnTier(x -> new ItemStack(ProfessionMatItems.TIERED_MAIN_MATS.get(Professions.MINING).get(x).get(), (int) ((x.tier + 1) * finalRarnumMulti)))
                            .onTierOrAbove(SkillItemTier.TIER0, RarityItems.RARITY_STONE.get(rar).get(), 2 + rarnum)
                            .onTierOrAbove(SkillItemTier.TIER0, Items.PAPER, 1)
                            .onTierOrAbove(SkillItemTier.TIER0, fam.craftItem.get(), 1);

                    b.buildEachTier();

                    rarnumMulti += 0.2F;
                    rarnum++;
                }
            }

        }


    }


    private static void buffPotion(String prof, String matprof, RarityItemHolder holder, Item mat) {
        for (String rar : IRarity.NORMAL_GEAR_RARITIES) {
            ProfessionRecipe.TierBuilder.of(x -> holder.get(rar), prof, 1)
                    .onlyOnTier(x -> new ItemStack(ProfessionMatItems.TIERED_MAIN_MATS.get(matprof).get(x).get(), 5))
                    .onTierOrAbove(SkillItemTier.TIER0, RarityItems.RARITY_STONE.get(rar).get(), 10)
                    .onTierOrAbove(SkillItemTier.TIER0, mat, 1)
                    .exp(100)
                    .buildEachTier();
        }
    }


    private static void buffConsumes() {


        // buff pots
        buffPotion(Professions.ALCHEMY, Professions.FARMING, StatBuffs.INT.getHolder(), Items.GOLDEN_APPLE);
        buffPotion(Professions.ALCHEMY, Professions.FARMING, StatBuffs.DEX.getHolder(), Items.GOLDEN_CARROT);
        buffPotion(Professions.ALCHEMY, Professions.FARMING, StatBuffs.STR.getHolder(), Items.GOLD_INGOT);
        buffPotion(Professions.ALCHEMY, Professions.FARMING, StatBuffs.CRIT.getHolder(), Items.ENCHANTED_GOLDEN_APPLE);
        buffPotion(Professions.ALCHEMY, Professions.FARMING, StatBuffs.ARCANE.getHolder(), Items.BEETROOT);
        buffPotion(Professions.ALCHEMY, Professions.FARMING, StatBuffs.MIGHT.getHolder(), Items.APPLE);
   

        // buff meat
        buffPotion(Professions.COOKING, Professions.HUSBANDRY, StatBuffs.HEALTH.getHolder(), Items.COOKED_BEEF);
        buffPotion(Professions.COOKING, Professions.HUSBANDRY, StatBuffs.MANA.getHolder(), Items.COOKED_CHICKEN);
        buffPotion(Professions.COOKING, Professions.HUSBANDRY, StatBuffs.ENERGY.getHolder(), Items.COOKED_RABBIT);
        buffPotion(Professions.COOKING, Professions.HUSBANDRY, StatBuffs.MAGIC.getHolder(), Items.COOKED_MUTTON);

        // buff seafood
        buffPotion(Professions.COOKING, Professions.FISHING, StatBuffs.EXP.getHolder(), Items.TROPICAL_FISH);
        buffPotion(Professions.COOKING, Professions.FISHING, StatBuffs.LOOT.getHolder(), Items.PUFFERFISH);


        for (String rar : IRarity.NORMAL_GEAR_RARITIES) {

            ProfessionRecipe.TierBuilder.of(x -> RarityItems.HEALTH_POTIONS.get(rar).get(), Professions.ALCHEMY, 16)
                    .onlyOnTier(x -> new ItemStack(ProfessionMatItems.TIERED_MAIN_MATS.get(Professions.FARMING).get(x).get(), 5))
                    .onTierOrAbove(SkillItemTier.TIER0, RarityItems.RARITY_STONE.get(rar).get(), 1)
                    .onTierOrAbove(SkillItemTier.TIER0, Items.MELON_SLICE, 1)
                    .onTierOrAbove(SkillItemTier.TIER1, Items.NETHER_WART, 1)
                    .exp(100)
                    .buildEachTier();

            ProfessionRecipe.TierBuilder.of(x -> RarityItems.RESOURCE_POTIONS.get(rar).get(), Professions.ALCHEMY, 16)
                    .onlyOnTier(x -> new ItemStack(ProfessionMatItems.TIERED_MAIN_MATS.get(Professions.FARMING).get(x).get(), 5))
                    .onTierOrAbove(SkillItemTier.TIER0, RarityItems.RARITY_STONE.get(rar).get(), 1)
                    .onTierOrAbove(SkillItemTier.TIER0, Items.CARROT, 1)
                    .onTierOrAbove(SkillItemTier.TIER1, Items.BEETROOT, 1)
                    .exp(100)
                    .buildEachTier();


        }


    }


    private static void gearCrafting() {


        for (SlotFamily fam : SlotFamily.values()) {
            if (fam != SlotFamily.NONE) {

                float rarnumMulti = 1;
                int rarnum = 1;
                for (String rar : IRarity.NORMAL_GEAR_RARITIES) {
                    float finalRarnumMulti = rarnumMulti;

                    float famMulti = 1;
                    if (fam == SlotFamily.Weapon || fam == SlotFamily.Jewelry) {
                        famMulti = 2;
                    }

                    var b = ProfessionRecipe.TierBuilder.of(x -> ProfessionProductItems.CRAFTED_SOULS.get(fam).get(rar).get(), Professions.GEAR_CRAFTING, 1)
                            .onlyOnTier(x -> new ItemStack(ProfessionMatItems.TIERED_MAIN_MATS.get(Professions.MINING).get(x).get(), (int) ((x.tier + 1) * finalRarnumMulti)))
                            .onTierOrAbove(SkillItemTier.TIER0, RarityItems.RARITY_STONE.get(rar).get(), (int) (3 + (rarnum * 1.5F) * famMulti))
                            .onTierOrAbove(SkillItemTier.TIER0, fam.craftItem.get(), 1);

                    b.exp(250);
                    b.buildEachTier();

                    rarnumMulti += 0.4F;
                    rarnum++;
                }
            }

        }

        ProfessionRecipe.TierBuilder.of(x -> new ProfDropTierPickerCurrency(x).getCurrencyItem(), Professions.GEAR_CRAFTING, 1)
                .onlyOnTier(x -> new ItemStack(RarityItems.RARITY_STONE.get(x.rar).get(), 1 * (x.tier + 1)))
                .onTierOrAbove(SkillItemTier.TIER0, Items.PAPER, 1)
                .onTierOrAbove(SkillItemTier.TIER0, Items.INK_SAC, 1)
                .exp(5)
                .custom(x -> x.recipe.tier = SkillItemTier.TIER0.tier)
                .buildEachTier();

        ProfessionRecipe.TierBuilder.of(x -> new SharpeningStone(x).getCurrencyItem(), Professions.GEAR_CRAFTING, 1)
                .onlyOnTier(x -> new ItemStack(ProfessionMatItems.TIERED_MAIN_MATS.get(Professions.MINING).get(x).get(), 3 * (x.tier + 1)))
                .onTierOrAbove(SkillItemTier.TIER0, Items.COAL, 1)
                .onTierOrAbove(SkillItemTier.TIER1, Items.COPPER_INGOT, 1)
                .onTierOrAbove(SkillItemTier.TIER2, Items.IRON_INGOT, 1)
                .onTierOrAbove(SkillItemTier.TIER3, Items.GOLD_INGOT, 1)
                .onTierOrAbove(SkillItemTier.TIER4, Items.DIAMOND, 1)
                .onTierOrAbove(SkillItemTier.TIER5, Items.NETHERITE_INGOT, 1)
                .exp(100)
                .buildEachTier();

    }


}
