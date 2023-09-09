package com.robertx22.age_of_exile.database.data.profession.all;

import com.robertx22.age_of_exile.database.data.currency.gear.SharpeningStone;
import com.robertx22.age_of_exile.database.data.gear_types.bases.SlotFamily;
import com.robertx22.age_of_exile.database.data.profession.CraftedItemPower;
import com.robertx22.age_of_exile.database.data.profession.ProfessionRecipe;
import com.robertx22.age_of_exile.database.data.profession.buffs.StatBuffs;
import com.robertx22.age_of_exile.mmorpg.registers.common.items.RarityItems;
import com.robertx22.age_of_exile.uncommon.interfaces.data_items.IRarity;
import com.robertx22.temp.SkillItemTier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class ProfessionRecipes {

    public static void init() {

        foods();
        potions();
        gearCrafting();
        enchanting();
        seafoods();
    }

    private static void enchanting() {


        for (SlotFamily fam : SlotFamily.values()) {
            if (fam != SlotFamily.NONE) {

                float rarnumMulti = 1;
                int rarnum = 1;

                for (String rar : IRarity.NORMAL_GEAR_RARITIES) {
                    float finalRarnumMulti = rarnumMulti;

                    var b = ProfessionRecipe.TierBuilder.of(x -> ProfessionProductItems.CRAFTED_ENCHANTS.get(fam).get(rar).get(), Professions.ENCHANTING, 3)
                            .onlyOnTier(x -> new ItemStack(ProfessionMatItems.TIERED_MAIN_MATS.get(Professions.MINING).get(x).get(), (int) ((x.tier + 1) * finalRarnumMulti)))
                            .onTierOrAbove(SkillItemTier.TIER0, RarityItems.RARITY_STONE.get(rar).get(), 2 + rarnum)
                            .onTierOrAbove(SkillItemTier.TIER0, Items.PAPER, 1)
                            .onTierOrAbove(SkillItemTier.TIER0, fam.craftItem.get(), 1);

                    if (rarnum > 0) {
                        b.onTierOrAbove(SkillItemTier.TIER0, ProfessionMatItems.POWERED_RARE_MATS.get(Professions.MINING).get(CraftedItemPower.LESSER).get(), 1);
                    }
                    if (rarnum > 2) {
                        b.onTierOrAbove(SkillItemTier.TIER0, ProfessionMatItems.POWERED_RARE_MATS.get(Professions.MINING).get(CraftedItemPower.MEDIUM).get(), 1);

                    }
                    if (rarnum > 4) {
                        b.onTierOrAbove(SkillItemTier.TIER0, ProfessionMatItems.POWERED_RARE_MATS.get(Professions.MINING).get(CraftedItemPower.GREATER).get(), 1);

                    }

                    b.buildEachTier();

                    rarnumMulti += 0.2F;
                    rarnum++;
                }
            }

        }

    }

    private static void potions() {

        int potions = 6;


        for (String rar : IRarity.NORMAL_GEAR_RARITIES) {

            ProfessionRecipe.TierBuilder.of(x -> RarityItems.HEALTH_POTIONS.get(rar).get(), Professions.ALCHEMY, 16)
                    .onlyOnTier(x -> new ItemStack(ProfessionMatItems.TIERED_MAIN_MATS.get(Professions.FARMING).get(x).get(), 5))
                    .onTierOrAbove(SkillItemTier.TIER0, RarityItems.RARITY_STONE.get(rar).get(), 1)
                    .onTierOrAbove(SkillItemTier.TIER0, Items.MELON_SLICE, 1)
                    .onTierOrAbove(SkillItemTier.TIER1, Items.NETHER_WART, 1)
                    .forRarityPower(rar, ProfessionMatItems.POWERED_RARE_MATS.get(Professions.FARMING))
                    .exp(100)
                    .buildEachTier();

            ProfessionRecipe.TierBuilder.of(x -> RarityItems.RESOURCE_POTIONS.get(rar).get(), Professions.ALCHEMY, 16)
                    .onlyOnTier(x -> new ItemStack(ProfessionMatItems.TIERED_MAIN_MATS.get(Professions.FARMING).get(x).get(), 5))
                    .onTierOrAbove(SkillItemTier.TIER0, RarityItems.RARITY_STONE.get(rar).get(), 1)
                    .onTierOrAbove(SkillItemTier.TIER0, Items.CARROT, 1)
                    .onTierOrAbove(SkillItemTier.TIER1, Items.BEETROOT, 1)
                    .forRarityPower(rar, ProfessionMatItems.POWERED_RARE_MATS.get(Professions.FARMING))
                    .exp(100)
                    .buildEachTier();

        }

        ProfessionRecipe.TierPowerBuilder.of(StatBuffs.INT.getHolder(), SkillItemTier.TIER0, Professions.ALCHEMY, potions)
                .coreMaterials(Professions.FARMING) // general farming produce
                .lesser(Items.NETHER_WART, 3) // Vanilla material
                .forPowers(ProfessionMatItems.POWERED_RARE_MATS.get(Professions.SALVAGING), 1)
                .buildEachTierAndPower();

        ProfessionRecipe.TierPowerBuilder.of(StatBuffs.DEX.getHolder(), SkillItemTier.TIER0, Professions.ALCHEMY, potions)
                .coreMaterials(Professions.FARMING)
                .lesser(Items.CARROT, 3)
                .forPowers(ProfessionMatItems.POWERED_RARE_MATS.get(Professions.SALVAGING), 1)
                .buildEachTierAndPower();

        ProfessionRecipe.TierPowerBuilder.of(StatBuffs.STR.getHolder(), SkillItemTier.TIER0, Professions.ALCHEMY, potions)
                .coreMaterials(Professions.FARMING)
                .lesser(Items.POTATO, 3)
                .forPowers(ProfessionMatItems.POWERED_RARE_MATS.get(Professions.SALVAGING), 1)
                .buildEachTierAndPower();

        ProfessionRecipe.TierPowerBuilder.of(StatBuffs.CRIT.getHolder(), SkillItemTier.TIER0, Professions.ALCHEMY, potions)
                .coreMaterials(Professions.FARMING)
                .lesser(Items.GOLDEN_CARROT, 1)
                .forPowers(ProfessionMatItems.POWERED_RARE_MATS.get(Professions.SALVAGING), 1)
                .buildEachTierAndPower();

        ProfessionRecipe.TierPowerBuilder.of(StatBuffs.ARCANE.getHolder(), SkillItemTier.TIER0, Professions.ALCHEMY, potions)
                .coreMaterials(Professions.FARMING)
                .lesser(Items.BEETROOT, 5)
                .forPowers(ProfessionMatItems.POWERED_RARE_MATS.get(Professions.SALVAGING), 1)
                .buildEachTierAndPower();

        ProfessionRecipe.TierPowerBuilder.of(StatBuffs.MIGHT.getHolder(), SkillItemTier.TIER0, Professions.ALCHEMY, potions)
                .coreMaterials(Professions.FARMING)
                .lesser(Items.APPLE, 1)
                .forPowers(ProfessionMatItems.POWERED_RARE_MATS.get(Professions.SALVAGING), 1)
                .buildEachTierAndPower();

    }


    private static void gearCrafting() {


        for (SlotFamily fam : SlotFamily.values()) {
            if (fam != SlotFamily.NONE) {

                float rarnumMulti = 1;
                int rarnum = 1;
                for (String rar : IRarity.NORMAL_GEAR_RARITIES) {
                    float finalRarnumMulti = rarnumMulti;

                    var b = ProfessionRecipe.TierBuilder.of(x -> ProfessionProductItems.CRAFTED_SOULS.get(fam).get(rar).get(), Professions.GEAR_CRAFTING, 1)
                            .onlyOnTier(x -> new ItemStack(ProfessionMatItems.TIERED_MAIN_MATS.get(Professions.MINING).get(x).get(), (int) ((x.tier + 1) * finalRarnumMulti)))
                            .onTierOrAbove(SkillItemTier.TIER0, RarityItems.RARITY_STONE.get(rar).get(), 3 + rarnum)
                            .onTierOrAbove(SkillItemTier.TIER0, fam.craftItem.get(), 1);

                    b.forRarityPower(rar, ProfessionMatItems.POWERED_RARE_MATS.get(Professions.MINING));

                    b.buildEachTier();

                    rarnumMulti += 0.4F;
                    rarnum++;
                }
            }

        }


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

    // todo add husbandry drops here

    private static void foods() {
        int foods = 2;

        ProfessionRecipe.TierPowerBuilder.of(StatBuffs.HEALTH.getHolder(), SkillItemTier.TIER0, Professions.COOKING, foods)
                .coreMaterials(Professions.HUSBANDRY)
                .lesser(Items.APPLE, 1)
                .forPowers(ProfessionMatItems.POWERED_RARE_MATS.get(Professions.FARMING), 1)
                .buildEachTierAndPower();

        ProfessionRecipe.TierPowerBuilder.of(StatBuffs.MANA.getHolder(), SkillItemTier.TIER0, Professions.COOKING, foods)
                .coreMaterials(Professions.HUSBANDRY)
                .lesser(Items.BEETROOT, 1)
                .forPowers(ProfessionMatItems.POWERED_RARE_MATS.get(Professions.FARMING), 1)
                .buildEachTierAndPower();

        ProfessionRecipe.TierPowerBuilder.of(StatBuffs.ENERGY.getHolder(), SkillItemTier.TIER0, Professions.COOKING, foods)
                .coreMaterials(Professions.HUSBANDRY)
                .lesser(Items.CARROT, 1)
                .forPowers(ProfessionMatItems.POWERED_RARE_MATS.get(Professions.FARMING), 1)
                .buildEachTierAndPower();

        ProfessionRecipe.TierPowerBuilder.of(StatBuffs.MAGIC.getHolder(), SkillItemTier.TIER0, Professions.COOKING, foods)
                .coreMaterials(Professions.HUSBANDRY)
                .lesser(Items.POTATO, 1)
                .forPowers(ProfessionMatItems.POWERED_RARE_MATS.get(Professions.FARMING), 1)
                .buildEachTierAndPower();

    }

    private static void seafoods() {
        int foods = 3;

        ProfessionRecipe.TierPowerBuilder.of(StatBuffs.EXP.getHolder(), SkillItemTier.TIER0, Professions.COOKING, foods)
                .coreMaterials(Professions.FISHING)
                .lesser(Items.TROPICAL_FISH, 1)
                .forPowers(ProfessionMatItems.POWERED_RARE_MATS.get(Professions.FISHING), 1)
                .buildEachTierAndPower();

        ProfessionRecipe.TierPowerBuilder.of(StatBuffs.LOOT.getHolder(), SkillItemTier.TIER0, Professions.COOKING, foods)
                .coreMaterials(Professions.FISHING)
                .lesser(Items.PUFFERFISH, 1)
                .forPowers(ProfessionMatItems.POWERED_RARE_MATS.get(Professions.FISHING), 1)
                .buildEachTierAndPower();


    }
}
