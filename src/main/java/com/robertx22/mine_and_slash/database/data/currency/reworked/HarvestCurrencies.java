package com.robertx22.mine_and_slash.database.data.currency.reworked;

import com.robertx22.mine_and_slash.database.data.currency.base.CodeCurrency;
import com.robertx22.mine_and_slash.database.data.currency.base.IShapedRecipe;
import com.robertx22.mine_and_slash.database.data.currency.reworked.item_mod.ItemMods;
import com.robertx22.mine_and_slash.database.data.currency.reworked.item_req.ItemReqs;
import com.robertx22.mine_and_slash.database.data.currency.reworked.keys.*;
import com.robertx22.mine_and_slash.database.data.league.LeagueMechanics;
import com.robertx22.mine_and_slash.database.registry.ExileRegistryTypes;
import com.robertx22.mine_and_slash.gui.texts.textblocks.WorksOnBlock;
import com.robertx22.mine_and_slash.loot.req.DropRequirement;
import com.robertx22.mine_and_slash.mechanics.harvest.HarvestItems;
import com.robertx22.mine_and_slash.mmorpg.registers.common.items.RarityItems;
import com.robertx22.mine_and_slash.uncommon.interfaces.data_items.IRarity;
import com.robertx22.temp.SkillItemTier;
import net.minecraft.world.item.Items;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;


public class HarvestCurrencies extends ExileKeyHolderSection<ExileCurrencies> {

    public HarvestCurrencies(ExileCurrencies holder) {
        super(holder);
    }

    public ExileKeyMap<ExileCurrency, SkillItemTierKey> HARVEST_ESSENCE = new ExileKeyMap<ExileCurrency, SkillItemTierKey>(get(), "harvest_essence")
            .ofList(Arrays.stream(SkillItemTier.values()).toList().stream().filter(x -> x != SkillItemTier.TIER5).map(x -> new SkillItemTierKey(x)).collect(Collectors.toList()))
            .build((id, info) -> {
                return ExileCurrency.Builder.of(id, "Harvested " + info.tier.word + " Essence", WorksOnBlock.ItemType.GEAR)
                        .rarity(info.tier.rar)
                        .addRequirement(ItemReqs.INSTANCE.IS_NOT_CORRUPTED)
                        .addRequirement(ItemReqs.INSTANCE.NOT_CRAFTED)
                        .addRequirement(ItemReqs.INSTANCE.HAS_AFFIX_OF_RARITY.get(new RarityKeyInfo(info.tier.rar)))
                        .addModification(ItemMods.INSTANCE.DESTROY_ITEM, 15)
                        .addModification(ItemMods.INSTANCE.UPGRADE_SPECIFIC_AFFIX_RARITY.get(new RarityKeyInfo(info.tier.rar)), 85)
                        .potentialCost(1)
                        .weight(5)
                        .dropsOnlyIn(DropRequirement.Builder.of().setOnlyDropsInLeague(LeagueMechanics.HARVEST_ID).build())
                        .buildCurrency(get());
            });

    public ExileKey<ExileCurrency, IdKey> HARVEST_AFFIX_UPGRADE = ExileCurrency.Builder.of("entangled_affix_upgrade", "Entangled Orb of Upgrade", WorksOnBlock.ItemType.GEAR)
            .rarity(IRarity.LEGENDARY_ID)
            .addRequirement(ItemReqs.INSTANCE.IS_NOT_CORRUPTED)
            .addRequirement(ItemReqs.INSTANCE.HAS_AFFIXES)
            .addModification(ItemMods.INSTANCE.UPGRADE_LOWEST_AFFIX, 50)
            .addModification(ItemMods.INSTANCE.CORRUPT_GEAR_NO_AFFIXES, 50)
            .potentialCost(1)
            .weight(CodeCurrency.Weights.UBER)
            .dropsOnlyIn(DropRequirement.Builder.of().setOnlyDropsInLeague(LeagueMechanics.HARVEST_ID).build())
            .build(get());


    public ExileKey<ExileCurrency, IdKey> HARVEST_POTENTIAL_UPGRADE = ExileCurrency.Builder.of("entangled_potential", "Entangled Orb of Potential", WorksOnBlock.ItemType.GEAR)
            .rarity(IRarity.LEGENDARY_ID)
            .addRequirement(ItemReqs.INSTANCE.IS_NOT_CORRUPTED)
            .addModification(ItemMods.INSTANCE.ADD_25_POTENTIAL, 60)
            .addModification(ItemMods.INSTANCE.CORRUPT_GEAR_NO_AFFIXES, 40)
            .potentialCost(0)
            .weight(CodeCurrency.Weights.UBER)
            .dropsOnlyIn(DropRequirement.Builder.of().setOnlyDropsInLeague(LeagueMechanics.HARVEST_ID).build())
            .build(get());


    public ExileKey<ExileCurrency, IdKey> HARVEST_QUALITY = ExileCurrency.Builder.of("entangled_quality", "Entangled Orb of Quality", WorksOnBlock.ItemType.GEAR)
            .rarity(IRarity.LEGENDARY_ID)
            .addRequirement(ItemReqs.INSTANCE.IS_NOT_CORRUPTED)
            .addRequirement(ItemReqs.INSTANCE.UNDER_21_QUALITY)
            .addModification(ItemMods.INSTANCE.ADD_UP_TO_5_GEAR_QUALITY, 75)
            .addModification(ItemMods.INSTANCE.CORRUPT_GEAR_NO_AFFIXES, 25)
            .potentialCost(0)
            .weight(CodeCurrency.Weights.UBER)
            .dropsOnlyIn(DropRequirement.Builder.of().setOnlyDropsInLeague(LeagueMechanics.HARVEST_ID).build())
            .build(get());

    public ExileKey<ExileCurrency, IdKey> HARVEST_UNIQUE_STATS = ExileCurrency.Builder.of("entangled_unique_reroll", "Entangled Orb of Imperfection", WorksOnBlock.ItemType.GEAR)
            .rarity(IRarity.LEGENDARY_ID)
            .addRequirement(ItemReqs.INSTANCE.IS_NOT_CORRUPTED)
            .addRequirement(ItemReqs.INSTANCE.IS_RARITY.get(new RarityKeyInfo(IRarity.UNIQUE_ID)))
            .addModification(ItemMods.INSTANCE.ADD_10_PERCENT_UNIQUE_STATS, 30)
            .addModification(ItemMods.INSTANCE.CORRUPT_GEAR_NO_AFFIXES, 70)
            .potentialCost(0)
            .weight(CodeCurrency.Weights.UBER)
            .dropsOnlyIn(DropRequirement.Builder.of().setOnlyDropsInLeague(LeagueMechanics.HARVEST_ID).build())
            .build(get());

    @Override
    public void init() {


        for (Map.Entry<SkillItemTierKey, ExileKey<ExileCurrency, SkillItemTierKey>> en : HARVEST_ESSENCE.map.entrySet()) {
            en.getValue().addRecipe(ExileRegistryTypes.CURRENCY, x -> {
                return IShapedRecipe.of(x.getItem(), 1)
                        .define('X', HARVEST_AFFIX_UPGRADE.getItem())
                        .define('Y', RarityItems.RARITY_STONE.get(x.info.tier.higherTier().rar).get())
                        .pattern("YYY")
                        .pattern("YXY")
                        .pattern("YYY");
            });

        }


        HARVEST_AFFIX_UPGRADE.addRecipe(ExileRegistryTypes.CURRENCY, x -> {
            return IShapedRecipe.of(x.getItem(), 1)
                    .define('X', Items.IRON_INGOT)
                    .define('Y', HarvestItems.BLUE_INGOT.get())
                    .pattern("YYY")
                    .pattern("YXY")
                    .pattern("YYY");
        });

        HARVEST_POTENTIAL_UPGRADE.addRecipe(ExileRegistryTypes.CURRENCY, x -> {
            return IShapedRecipe.of(x.getItem(), 1)
                    .define('X', Items.GOLD_INGOT)
                    .define('Y', HarvestItems.PURPLE_INGOT.get())
                    .pattern("YYY")
                    .pattern("YXY")
                    .pattern("YYY");
        });
        HARVEST_QUALITY.addRecipe(ExileRegistryTypes.CURRENCY, x -> {
            return IShapedRecipe.of(x.getItem(), 1)
                    .define('X', Items.DIAMOND)
                    .define('Y', HarvestItems.GREEN_INGOT.get())
                    .pattern("YYY")
                    .pattern("YXY")
                    .pattern("YYY");
        });

        HARVEST_UNIQUE_STATS.addRecipe(ExileRegistryTypes.CURRENCY, x -> {
            return IShapedRecipe.of(x.getItem(), 1)
                    .define('X', Items.DIAMOND_BLOCK)
                    .define('Y', HarvestItems.PURPLE_INGOT.get())
                    .pattern("YYY")
                    .pattern("YXY")
                    .pattern("YYY");
        });

    }
}
