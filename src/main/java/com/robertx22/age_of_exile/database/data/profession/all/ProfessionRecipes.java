package com.robertx22.age_of_exile.database.data.profession.all;

import com.robertx22.age_of_exile.database.data.currency.gear.SharpeningStone;
import com.robertx22.age_of_exile.database.data.gear_types.bases.SlotFamily;
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

    }

    private static void potions() {

        int potions = 6;

        // todo add farming drops here

        ProfessionRecipe.TierPowerBuilder.of(StatBuffs.INT.getHolder(), SkillItemTier.TIER0, Professions.ALCHEMY, potions)
                .coreMaterials(Professions.FARMING) // general farming produce
                .lesser(Items.NETHER_WART, 3) // Vanilla material
                .medium(ProfessionMatItems.ESSENCES.INT.get(), 1) // common misc drop from prof
                .greater(ProfessionMatItems.RARE_ESSENCES.DELIRIUM.get(), 1) // rare prof drop like - Essence of Delirium
                .buildEachTierAndPower();

        ProfessionRecipe.TierPowerBuilder.of(StatBuffs.DEX.getHolder(), SkillItemTier.TIER0, Professions.ALCHEMY, potions)
                .coreMaterials(Professions.FARMING)
                .lesser(Items.CARROT, 3)
                .medium(ProfessionMatItems.ESSENCES.DEX.get(), 1)
                .greater(ProfessionMatItems.RARE_ESSENCES.DELIRIUM.get(), 1)
                .buildEachTierAndPower();

        ProfessionRecipe.TierPowerBuilder.of(StatBuffs.STR.getHolder(), SkillItemTier.TIER0, Professions.ALCHEMY, potions)
                .coreMaterials(Professions.FARMING)
                .lesser(Items.POTATO, 3)
                .medium(ProfessionMatItems.ESSENCES.STR.get(), 1)
                .greater(ProfessionMatItems.RARE_ESSENCES.DELIRIUM.get(), 1)
                .buildEachTierAndPower();

        ProfessionRecipe.TierPowerBuilder.of(StatBuffs.CRIT.getHolder(), SkillItemTier.TIER0, Professions.ALCHEMY, potions)
                .coreMaterials(Professions.FARMING)
                .lesser(Items.GOLDEN_CARROT, 1)
                .medium(ProfessionMatItems.ESSENCES.CRIT.get(), 1)
                .greater(ProfessionMatItems.RARE_ESSENCES.INSANITY.get(), 1)
                .buildEachTierAndPower();

        ProfessionRecipe.TierPowerBuilder.of(StatBuffs.ARCANE.getHolder(), SkillItemTier.TIER0, Professions.ALCHEMY, potions)
                .coreMaterials(Professions.FARMING)
                .lesser(Items.BEETROOT, 5)
                .medium(ProfessionMatItems.ESSENCES.MAGIC.get(), 1)
                .greater(ProfessionMatItems.RARE_ESSENCES.INSANITY.get(), 1)
                .buildEachTierAndPower();

        ProfessionRecipe.TierPowerBuilder.of(StatBuffs.MIGHT.getHolder(), SkillItemTier.TIER0, Professions.ALCHEMY, potions)
                .coreMaterials(Professions.FARMING)
                .lesser(Items.APPLE, 1)
                .medium(ProfessionMatItems.ESSENCES.LIFE.get(), 1)
                .greater(ProfessionMatItems.RARE_ESSENCES.INSANITY.get(), 1)
                .buildEachTierAndPower();

    }


    private static void gearCrafting() {


        for (SlotFamily fam : SlotFamily.values()) {
            if (fam != SlotFamily.NONE) {

                float rarnumMulti = 1;
                for (String rar : IRarity.NORMAL_GEAR_RARITIES) {
                    float finalRarnumMulti = rarnumMulti;

                    ProfessionRecipe.TierBuilder.of(x -> ProfessionProductItems.CRAFTED_SOULS.get(fam).get(rar).get(), Professions.GEAR_CRAFTING, 1)
                            .onlyOnTier(x -> new ItemStack(ProfessionMatItems.TIERED_MAIN_MATS.get(Professions.MINING).get(x).get(), (int) ((x.tier + 1) * finalRarnumMulti)))
                            .onTierOrAbove(SkillItemTier.TIER0, RarityItems.RARITY_STONE.get(rar).get(), 1 + (int) finalRarnumMulti)
                            .onTierOrAbove(SkillItemTier.TIER0, fam.craftItem.get(), 1)
                            .buildEachTier();

                    rarnumMulti += 0.4F;
                }
            }

        }


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

    // todo add husbandry drops here

    private static void foods() {
        int foods = 2;

        ProfessionRecipe.TierPowerBuilder.of(StatBuffs.HEALTH.getHolder(), SkillItemTier.TIER0, Professions.COOKING, foods)
                .coreMaterials(Professions.HUSBANDRY)
                .lesser(Items.APPLE, 1)
                .medium(ProfessionMatItems.ESSENCES.LIFE.get(), 1)
                .greater(ProfessionMatItems.RARE_ESSENCES.HYSTERIA.get(), 1)
                .buildEachTierAndPower();

        ProfessionRecipe.TierPowerBuilder.of(StatBuffs.MANA.getHolder(), SkillItemTier.TIER0, Professions.COOKING, foods)
                .coreMaterials(Professions.HUSBANDRY)
                .lesser(Items.BEETROOT, 1)
                .medium(ProfessionMatItems.ESSENCES.MANA.get(), 1)
                .greater(ProfessionMatItems.RARE_ESSENCES.HYSTERIA.get(), 1)
                .buildEachTierAndPower();

        ProfessionRecipe.TierPowerBuilder.of(StatBuffs.ENERGY.getHolder(), SkillItemTier.TIER0, Professions.COOKING, foods)
                .coreMaterials(Professions.HUSBANDRY)
                .lesser(Items.CARROT, 1)
                .medium(ProfessionMatItems.ESSENCES.ENERGY.get(), 1)
                .greater(ProfessionMatItems.RARE_ESSENCES.HYSTERIA.get(), 1)
                .buildEachTierAndPower();

        ProfessionRecipe.TierPowerBuilder.of(StatBuffs.MAGIC.getHolder(), SkillItemTier.TIER0, Professions.COOKING, foods)
                .coreMaterials(Professions.HUSBANDRY)
                .lesser(Items.POTATO, 1)
                .medium(ProfessionMatItems.ESSENCES.MAGIC.get(), 1)
                .greater(ProfessionMatItems.RARE_ESSENCES.HYSTERIA.get(), 1)
                .buildEachTierAndPower();

    }
}
