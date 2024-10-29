package com.robertx22.mine_and_slash.database.data.currency.reworked;

import com.robertx22.mine_and_slash.database.data.currency.base.CodeCurrency;
import com.robertx22.mine_and_slash.database.data.currency.base.IShapedRecipe;
import com.robertx22.mine_and_slash.database.data.currency.reworked.item.ExileCurrencyItem;
import com.robertx22.mine_and_slash.database.data.currency.reworked.item_mod.ItemMods;
import com.robertx22.mine_and_slash.database.data.currency.reworked.item_req.ItemReqs;
import com.robertx22.mine_and_slash.database.data.currency.reworked.keys.*;
import com.robertx22.mine_and_slash.database.registry.ExileRegistryTypes;
import com.robertx22.mine_and_slash.gui.texts.textblocks.WorksOnBlock;
import com.robertx22.mine_and_slash.mmorpg.SlashRef;
import com.robertx22.mine_and_slash.uncommon.interfaces.data_items.IRarity;
import net.minecraft.world.item.Items;


public class ExileCurrencies extends ExileKeyHolder<ExileCurrency> {

    public static ExileCurrencies INSTANCE = (ExileCurrencies) new ExileCurrencies()
            // todo is this a good way to check? I'm thinking note 1 layer of dep
            .itemIds(new ItemIdProvider(x -> SlashRef.id("currency/" + x)))
            .createItems(new ItemCreator<ExileCurrency>(x -> new ExileCurrencyItem(x.get())))
            .dependsOn(() -> ItemMods.INSTANCE)
            .dependsOn(() -> ItemReqs.INSTANCE);


    public HarvestCurrencies HARVEST = new HarvestCurrencies(this);
    public JewelCurrencies JEWEL = new JewelCurrencies(this);


    public ExileKeyMap<ExileCurrency, SkillItemTierKey> SHARPEN_STONE_QUALITY = new ExileKeyMap<ExileCurrency, SkillItemTierKey>(this, "sharpening_stone")
            .ofSkillItemTiers()
            .build((id, info) -> {
                return ExileCurrency.Builder.of(id, info.tier.word + " Sharpening Stone", WorksOnBlock.ItemType.GEAR)
                        .rarity(info.tier.rar)
                        .addRequirement(ItemReqs.INSTANCE.IS_NOT_CORRUPTED)
                        .addModification(ItemMods.INSTANCE.SHARPEN_STONE_QUALITY.get(info), 100)
                        .maxUses(new MaxUsesKey(ItemReqs.Datas.MAX_SHARPENING_STONE_USES))
                        .potentialCost(0)
                        .weight(0)
                        .buildCurrency(this);
            });


    public ExileKey<ExileCurrency, IdKey> CORRUPT_GEAR = ExileCurrency.Builder.of("chaos_orb", "Orb of Chaos", WorksOnBlock.ItemType.GEAR)
            .addRequirement(ItemReqs.INSTANCE.IS_NOT_CORRUPTED)
            .rarity(IRarity.UNIQUE_ID)
            .addModification(ItemMods.INSTANCE.CORRUPT_GEAR, 75)
            .addModification(ItemMods.INSTANCE.DESTROY_ITEM, 25)
            .potentialCost(0)
            .weight(1000)
            .build(this);

    public ExileKey<ExileCurrency, IdKey> LEVEL_GEAR = ExileCurrency.Builder.of("level_up_orb", "Orb of Infinity", WorksOnBlock.ItemType.GEAR)
            .addRequirement(ItemReqs.INSTANCE.IS_NOT_CORRUPTED)
            .rarity(IRarity.UNCOMMON)
            .addRequirement(ItemReqs.INSTANCE.LEVEL_NOT_MAX)
            .addModification(ItemMods.INSTANCE.ADD_GEAR_LEVEL, 1)
            .maxUses(new MaxUsesKey(ItemReqs.Datas.MAX_LEVEL_USES))
            .potentialCost(5)
            .weight(1000)
            .build(this);

    public ExileKey<ExileCurrency, IdKey> ADD_SOCKET = ExileCurrency.Builder.of("socket_adder", "Orb of Digging", WorksOnBlock.ItemType.GEAR)
            .addRequirement(ItemReqs.INSTANCE.IS_NOT_CORRUPTED)
            .addRequirement(ItemReqs.INSTANCE.CAN_ADD_SOCKETS)
            .rarity(IRarity.RARE_ID)
            .addModification(ItemMods.INSTANCE.ADD_SOCKET, 50)
            .addModification(ItemMods.INSTANCE.DO_NOTHING, 50)
            .potentialCost(10)
            .weight(1000)
            .build(this);

    public ExileKey<ExileCurrency, IdKey> UNIQUE_STAT_REROLL = ExileCurrency.Builder.of("unique_reroll", "Orb of Imperfection", WorksOnBlock.ItemType.GEAR)
            .addRequirement(ItemReqs.INSTANCE.IS_NOT_CORRUPTED)
            .rarity(IRarity.RARE_ID)
            .addRequirement(ItemReqs.INSTANCE.IS_RARITY.map.get(new RarityKeyInfo(IRarity.UNIQUE_ID)))
            .addModification(ItemMods.INSTANCE.ADD_5_PERCENT_UNIQUE_STATS, 60)
            .addModification(ItemMods.INSTANCE.REDUCE_5_PERCENT_UNIQUE_STATS, 40)
            .potentialCost(10)
            .weight(CodeCurrency.Weights.UBER)
            .build(this);

    public ExileKey<ExileCurrency, IdKey> REROLL_RANDOM_AFFIX = ExileCurrency.Builder.of("affix_common_reroll", "Orb of New Beginnings", WorksOnBlock.ItemType.GEAR)
            .addRequirement(ItemReqs.INSTANCE.IS_NOT_CORRUPTED)
            .rarity(IRarity.RARE_ID)
            .addRequirement(ItemReqs.INSTANCE.HAS_AFFIXES)
            .addModification(ItemMods.INSTANCE.REROLL_RANDOM_AFFIX, 100)
            .potentialCost(10)
            .weight(CodeCurrency.Weights.COMMON)
            .build(this);

    public ExileKey<ExileCurrency, IdKey> REROLL_RANDOM_AFFIX_TO_MYTHIC = ExileCurrency.Builder.of("affix_random_mythic_reroll", "Orb of Divine Benevolence", WorksOnBlock.ItemType.GEAR)
            .rarity(IRarity.MYTHIC_ID)
            .addRequirement(ItemReqs.INSTANCE.IS_NOT_CORRUPTED)
            .addRequirement(ItemReqs.INSTANCE.HAS_AFFIXES)
            .addRequirement(ItemReqs.INSTANCE.NOT_CRAFTED)
            .addRequirement(ItemReqs.INSTANCE.IS_RARITY.get(new RarityKeyInfo(IRarity.MYTHIC_ID)))
            .addModification(ItemMods.INSTANCE.REROLL_RANDOM_AFFIX_INTO_MYTHIC, 100)
            .maxUses(new MaxUsesKey(ItemReqs.Datas.RANDOM_MYTHIC_AFFIX))
            .potentialCost(25)
            .weight(CodeCurrency.Weights.MEGA_UBER)
            .build(this);

    public ExileKey<ExileCurrency, IdKey> UPGRADE_OR_DOWNGRADE_RANDOM_AFFIX = ExileCurrency.Builder.of("affix_tier_up_down", "Orb of Imbalance", WorksOnBlock.ItemType.GEAR)
            .addRequirement(ItemReqs.INSTANCE.IS_NOT_CORRUPTED)
            .rarity(IRarity.RARE_ID)
            .addRequirement(ItemReqs.INSTANCE.HAS_AFFIXES)
            .addModification(ItemMods.INSTANCE.UPGRADE_RANDOM_AFFIX, 60)
            .addModification(ItemMods.INSTANCE.DOWNGRADE_RANDOM_AFFIX, 40)
            .potentialCost(5)
            .weight(CodeCurrency.Weights.COMMON)
            .build(this);

    public ExileKey<ExileCurrency, IdKey> UPGRADE_QUALITY = ExileCurrency.Builder.of("orb_of_quality", "Orb of Quality", WorksOnBlock.ItemType.GEAR)
            .addRequirement(ItemReqs.INSTANCE.IS_NOT_CORRUPTED)
            .addRequirement(ItemReqs.INSTANCE.UNDER_20_QUALITY)
            .rarity(IRarity.UNCOMMON)
            .addModification(ItemMods.INSTANCE.ADD_GEAR_QUALITY, 100)
            .potentialCost(0)
            .weight(CodeCurrency.Weights.COMMON)
            .build(this);

    public ExileKey<ExileCurrency, IdKey> REROLL_INFUSION = ExileCurrency.Builder.of("enchant_reroll", "Orb of Second Guessing", WorksOnBlock.ItemType.GEAR)
            .addRequirement(ItemReqs.INSTANCE.IS_NOT_CORRUPTED)
            .addRequirement(ItemReqs.INSTANCE.HAS_INFUSION)
            .rarity(IRarity.UNCOMMON)
            .addModification(ItemMods.INSTANCE.REROLL_INFUSION, 100)
            .potentialCost(1)
            .weight(CodeCurrency.Weights.RARE)
            .build(this);


    public ExileKey<ExileCurrency, IdKey> MAP_RARITY_UPGRADE = ExileCurrency.Builder.of("map_rarity_upgrade", "Map Rarity Upgrade Orb", WorksOnBlock.ItemType.MAP)
            .addRequirement(ItemReqs.INSTANCE.IS_NOT_CORRUPTED)
            .addRequirement(ItemReqs.INSTANCE.MAP_HAS_HIGHER_RARITY)
            .rarity(IRarity.EPIC_ID)
            .addModification(ItemMods.INSTANCE.UPGRADE_MAP_RARITY, 100)
            .potentialCost(0)
            .weight(CodeCurrency.Weights.COMMON)
            .build(this);


    public ExileKey<ExileCurrency, IdKey> UPGRADE_COMMON_AFFIX = ExileCurrency.Builder.of("upgrade_common_affix", "Orb of Fledgling's Reprieve", WorksOnBlock.ItemType.GEAR)
            .rarity(IRarity.RARE_ID)
            .addRequirement(ItemReqs.INSTANCE.IS_NOT_CORRUPTED)
            .addRequirement(ItemReqs.INSTANCE.HAS_AFFIX_OF_RARITY.get(new RarityKeyInfo(IRarity.COMMON_ID)))
            .addModification(ItemMods.INSTANCE.UPGRADE_SPECIFIC_AFFIX_RARITY.get(new RarityKeyInfo(IRarity.COMMON_ID)), 100)
            .potentialCost(3)
            .weight(CodeCurrency.Weights.COMMON)
            .build(this);

    public ExileKey<ExileCurrency, IdKey> REROLL_AFFIX_NUMBERS = ExileCurrency.Builder.of("affix_number_reroll", "Orb of Ciphers", WorksOnBlock.ItemType.GEAR)
            .rarity(IRarity.RARE_ID)
            .addRequirement(ItemReqs.INSTANCE.IS_NOT_CORRUPTED)
            .addRequirement(ItemReqs.INSTANCE.HAS_AFFIXES)
            .addModification(ItemMods.INSTANCE.REROLL_AFFIX_NUMBERS, 100)
            .potentialCost(5)
            .weight(CodeCurrency.Weights.COMMON)
            .build(this);

    public ExileKey<ExileCurrency, IdKey> UPGRADE_CORRUPTION_AFFIX = ExileCurrency.Builder.of("up_corrupt_affix", "Orb of Foolish Risk",
                    WorksOnBlock.ItemType.GEAR, WorksOnBlock.ItemType.JEWEL)
            .rarity(IRarity.EPIC_ID)
            .addRequirement(ItemReqs.INSTANCE.HAS_CORRUPTION_AFFIXES)
            .addModification(ItemMods.INSTANCE.UPGRADE_CORRUPTION_AFFIX_RARITY, 90)
            .addModification(ItemMods.INSTANCE.DESTROY_ITEM, 10)
            .potentialCost(0)
            .weight(CodeCurrency.Weights.COMMON)
            .build(this);


    @Override
    public void loadClass() {

        MAP_RARITY_UPGRADE.addRecipe(ExileRegistryTypes.CURRENCY, x -> {
            return IShapedRecipe.of(x.getItem(), 3)
                    .define('X', Items.DIAMOND)
                    .define('Y', Items.REDSTONE)
                    .pattern("YYY")
                    .pattern("YXY")
                    .pattern("YYY");
        });

    }
}
