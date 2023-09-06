package com.robertx22.age_of_exile.database.data.profession.all;

import com.robertx22.age_of_exile.database.data.currency.gear.SharpeningStone;
import com.robertx22.age_of_exile.database.data.profession.ProfessionRecipe;
import com.robertx22.age_of_exile.database.data.profession.buffs.StatBuffs;
import com.robertx22.temp.SkillItemTier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class ProfessionRecipes {

    public static void init() {

        foods();
        potions();
        gearCrafting();

    }

    private static void potions() {

        int potions = 6;

        ProfessionRecipe.TierPowerBuilder.of(StatBuffs.INT.getHolder(), SkillItemTier.TIER0, Professions.ALCHEMY, potions)
                .coreMaterials(Professions.FARMING) // general farming produce
                .lesser(Items.NETHER_WART, 3) // Vanilla material
                .medium(ProfessionMatItems.Essence.INT.get(), 1) // common misc drop from prof
                .greater(ProfessionMatItems.Essence.Rare.DELIRIUM.get(), 1) // rare prof drop like - Essence of Delirium
                .buildEachTierAndPower();

        ProfessionRecipe.TierPowerBuilder.of(StatBuffs.DEX.getHolder(), SkillItemTier.TIER0, Professions.ALCHEMY, potions)
                .coreMaterials(Professions.FARMING)
                .lesser(Items.CARROT, 3)
                .medium(ProfessionMatItems.Essence.DEX.get(), 1)
                .greater(ProfessionMatItems.Essence.Rare.DELIRIUM.get(), 1)
                .buildEachTierAndPower();

        ProfessionRecipe.TierPowerBuilder.of(StatBuffs.STR.getHolder(), SkillItemTier.TIER0, Professions.ALCHEMY, potions)
                .coreMaterials(Professions.FARMING)
                .lesser(Items.POTATO, 3)
                .medium(ProfessionMatItems.Essence.STR.get(), 1)
                .greater(ProfessionMatItems.Essence.Rare.DELIRIUM.get(), 1)
                .buildEachTierAndPower();

        ProfessionRecipe.TierPowerBuilder.of(StatBuffs.CRIT.getHolder(), SkillItemTier.TIER0, Professions.ALCHEMY, potions)
                .coreMaterials(Professions.FARMING)
                .lesser(Items.GOLDEN_CARROT, 1)
                .medium(ProfessionMatItems.Essence.CRIT.get(), 1)
                .greater(ProfessionMatItems.Essence.Rare.INSANITY.get(), 1)
                .buildEachTierAndPower();

        ProfessionRecipe.TierPowerBuilder.of(StatBuffs.ARCANE.getHolder(), SkillItemTier.TIER0, Professions.ALCHEMY, potions)
                .coreMaterials(Professions.FARMING)
                .lesser(Items.BEETROOT, 5)
                .medium(ProfessionMatItems.Essence.MAGIC.get(), 1)
                .greater(ProfessionMatItems.Essence.Rare.INSANITY.get(), 1)
                .buildEachTierAndPower();

        ProfessionRecipe.TierPowerBuilder.of(StatBuffs.MIGHT.getHolder(), SkillItemTier.TIER0, Professions.ALCHEMY, potions)
                .coreMaterials(Professions.FARMING)
                .lesser(Items.APPLE, 1)
                .medium(ProfessionMatItems.Essence.LIFE.get(), 1)
                .greater(ProfessionMatItems.Essence.Rare.INSANITY.get(), 1)
                .buildEachTierAndPower();

    }


    // todo
    private static void gearCrafting() {

        ProfessionRecipe.TierBuilder.of(x -> new SharpeningStone(x).getCurrencyItem(), Professions.GEAR_CRAFTING, 1)
                .onlyOnTier(x -> new ItemStack(ProfessionMatItems.TIERED_MAIN_MATS.get(Professions.MINING).get(x).get(), 3 * (x.tier + 1)))
                .onTierOrAbove(SkillItemTier.TIER1, Items.COPPER_INGOT, 1)
                .onTierOrAbove(SkillItemTier.TIER2, Items.IRON_INGOT, 1)
                .onTierOrAbove(SkillItemTier.TIER3, Items.GOLD_INGOT, 1)
                .onTierOrAbove(SkillItemTier.TIER4, Items.DIAMOND, 1)
                .onTierOrAbove(SkillItemTier.TIER5, Items.NETHERITE_INGOT, 1)
                .exp(100)
                .buildEachTier();

    }

    private static void foods() {
        int foods = 2;

        ProfessionRecipe.TierPowerBuilder.of(StatBuffs.HEALTH.getHolder(), SkillItemTier.TIER0, Professions.COOKING, foods)
                .coreMaterials(Professions.HUSBANDRY)
                .lesser(Items.APPLE, 1)
                .medium(ProfessionMatItems.Essence.LIFE.get(), 1)
                .greater(ProfessionMatItems.Essence.Rare.HYSTERIA.get(), 1)
                .buildEachTierAndPower();

        ProfessionRecipe.TierPowerBuilder.of(StatBuffs.MANA.getHolder(), SkillItemTier.TIER0, Professions.COOKING, foods)
                .coreMaterials(Professions.HUSBANDRY)
                .lesser(Items.BEETROOT, 1)
                .medium(ProfessionMatItems.Essence.MANA.get(), 1)
                .greater(ProfessionMatItems.Essence.Rare.HYSTERIA.get(), 1)
                .buildEachTierAndPower();

        ProfessionRecipe.TierPowerBuilder.of(StatBuffs.ENERGY.getHolder(), SkillItemTier.TIER0, Professions.COOKING, foods)
                .coreMaterials(Professions.HUSBANDRY)
                .lesser(Items.CARROT, 1)
                .medium(ProfessionMatItems.Essence.ENERGY.get(), 1)
                .greater(ProfessionMatItems.Essence.Rare.HYSTERIA.get(), 1)
                .buildEachTierAndPower();

        ProfessionRecipe.TierPowerBuilder.of(StatBuffs.MAGIC.getHolder(), SkillItemTier.TIER0, Professions.COOKING, foods)
                .coreMaterials(Professions.HUSBANDRY)
                .lesser(Items.POTATO, 1)
                .medium(ProfessionMatItems.Essence.MAGIC.get(), 1)
                .greater(ProfessionMatItems.Essence.Rare.HYSTERIA.get(), 1)
                .buildEachTierAndPower();

    }
}
