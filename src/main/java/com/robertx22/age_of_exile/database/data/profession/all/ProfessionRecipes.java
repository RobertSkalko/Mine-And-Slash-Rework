package com.robertx22.age_of_exile.database.data.profession.all;

import com.robertx22.age_of_exile.database.data.currency.gear.SharpeningStone;
import com.robertx22.age_of_exile.database.data.gear_types.bases.SlotFamily;
import com.robertx22.age_of_exile.database.data.profession.ProfessionRecipe;
import com.robertx22.age_of_exile.database.data.profession.buffs.StatBuffs;
import com.robertx22.age_of_exile.mmorpg.registers.common.items.RarityItems;
import com.robertx22.age_of_exile.mmorpg.registers.deferred_wrapper.RegObj;
import com.robertx22.age_of_exile.uncommon.interfaces.data_items.IRarity;
import com.robertx22.temp.SkillItemTier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.List;

public class ProfessionRecipes {

    public static void init() {

        foods();
        potions();
        gearCrafting();

    }

    private static void potions() {

        int potions = 6;


        for (String rar : IRarity.NORMAL_GEAR_RARITIES) {

            ProfessionRecipe.TierBuilder.of(x -> RarityItems.HEALTH_POTIONS.get(rar).get(), Professions.ALCHEMY, 16)
                    .onlyOnTier(x -> new ItemStack(ProfessionMatItems.TIERED_MAIN_MATS.get(Professions.FARMING).get(x).get(), 5))
                    .onTierOrAbove(SkillItemTier.TIER0, RarityItems.RARITY_STONE.get(rar).get(), 1)
                    .onTierOrAbove(SkillItemTier.TIER0, Items.MELON_SLICE, 1)
                    .onTierOrAbove(SkillItemTier.TIER1, Items.NETHER_WART, 1)
                    .onTierOrAbove(SkillItemTier.TIER2, ProfessionMatItems.COMMON_FARMING.RED_PEPPER.get(), 1)
                    .onTierOrAbove(SkillItemTier.TIER3, ProfessionMatItems.COMMON_FARMING.BLOOT_ROOT.get(), 1)
                    .onTierOrAbove(SkillItemTier.TIER4, ProfessionMatItems.RARE_FARMING.RED_CORAL.get(), 1)
                    .onTierOrAbove(SkillItemTier.TIER5, ProfessionMatItems.RARE_FARMING.BRYONY_ROOT.get(), 1)
                    .exp(100)
                    .buildEachTier();

            ProfessionRecipe.TierBuilder.of(x -> RarityItems.RESOURCE_POTIONS.get(rar).get(), Professions.ALCHEMY, 16)
                    .onlyOnTier(x -> new ItemStack(ProfessionMatItems.TIERED_MAIN_MATS.get(Professions.FARMING).get(x).get(), 5))
                    .onTierOrAbove(SkillItemTier.TIER0, RarityItems.RARITY_STONE.get(rar).get(), 1)
                    .onTierOrAbove(SkillItemTier.TIER0, Items.CARROT, 1)
                    .onTierOrAbove(SkillItemTier.TIER1, Items.BEETROOT, 1)
                    .onTierOrAbove(SkillItemTier.TIER2, ProfessionMatItems.COMMON_FARMING.GREEN_PEPPER.get(), 1)
                    .onTierOrAbove(SkillItemTier.TIER3, ProfessionMatItems.COMMON_FARMING.BOLETE_MUSHROOM.get(), 1)
                    .onTierOrAbove(SkillItemTier.TIER4, ProfessionMatItems.RARE_FARMING.DRAGONFRUIT.get(), 1)
                    .onTierOrAbove(SkillItemTier.TIER5, ProfessionMatItems.RARE_FARMING.MAGIC_LOG.get(), 1)
                    .exp(100)
                    .buildEachTier();

        }

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
                int rarnum = 1;
                for (String rar : IRarity.NORMAL_GEAR_RARITIES) {
                    float finalRarnumMulti = rarnumMulti;

                    var b = ProfessionRecipe.TierBuilder.of(x -> ProfessionProductItems.CRAFTED_SOULS.get(fam).get(rar).get(), Professions.GEAR_CRAFTING, 1)
                            .onlyOnTier(x -> new ItemStack(ProfessionMatItems.TIERED_MAIN_MATS.get(Professions.MINING).get(x).get(), (int) ((x.tier + 1) * finalRarnumMulti)))
                            .onTierOrAbove(SkillItemTier.TIER0, RarityItems.RARITY_STONE.get(rar).get(), 3 + rarnum)
                            .onTierOrAbove(SkillItemTier.TIER0, fam.craftItem.get(), 1);

                    List<RegObj<Item>> list = null;

                    if (fam == SlotFamily.Jewelry) {
                        list = ProfessionMatItems.RARE_MINING.ALL;
                    } else {
                        list = ProfessionMatItems.COMMON_MINING.ALL;
                    }

                    for (int i = 0; i < rarnum; i++) {
                        if (list.size() > i) {
                            b.onTierOrAbove(SkillItemTier.TIER0, list.get(i).get(), 1);
                        }
                    }

                    b.buildEachTier();

                    rarnumMulti += 0.4F;
                    rarnum++;
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
                .greater(ProfessionMatItems.RARE_FARMING.BRYONY_ROOT.get(), 1)
                .buildEachTierAndPower();

        ProfessionRecipe.TierPowerBuilder.of(StatBuffs.MANA.getHolder(), SkillItemTier.TIER0, Professions.COOKING, foods)
                .coreMaterials(Professions.HUSBANDRY)
                .lesser(Items.BEETROOT, 1)
                .medium(ProfessionMatItems.ESSENCES.MANA.get(), 1)
                .greater(ProfessionMatItems.RARE_FARMING.RED_CORAL.get(), 1)
                .buildEachTierAndPower();

        ProfessionRecipe.TierPowerBuilder.of(StatBuffs.ENERGY.getHolder(), SkillItemTier.TIER0, Professions.COOKING, foods)
                .coreMaterials(Professions.HUSBANDRY)
                .lesser(Items.CARROT, 1)
                .medium(ProfessionMatItems.ESSENCES.ENERGY.get(), 1)
                .greater(ProfessionMatItems.RARE_FARMING.DRAGONFRUIT.get(), 1)
                .buildEachTierAndPower();

        ProfessionRecipe.TierPowerBuilder.of(StatBuffs.MAGIC.getHolder(), SkillItemTier.TIER0, Professions.COOKING, foods)
                .coreMaterials(Professions.HUSBANDRY)
                .lesser(Items.POTATO, 1)
                .medium(ProfessionMatItems.ESSENCES.MAGIC.get(), 1)
                .greater(ProfessionMatItems.RARE_FARMING.MAGIC_LOG.get(), 1)
                .buildEachTierAndPower();

    }
}
