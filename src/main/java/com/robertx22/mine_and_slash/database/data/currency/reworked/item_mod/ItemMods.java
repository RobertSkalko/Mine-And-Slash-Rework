package com.robertx22.mine_and_slash.database.data.currency.reworked.item_mod;

import com.robertx22.mine_and_slash.database.data.MinMax;
import com.robertx22.mine_and_slash.database.data.currency.reworked.item_mod.all.AddPotentialItemMod;
import com.robertx22.mine_and_slash.database.data.currency.reworked.item_mod.all.DestroyItemMod;
import com.robertx22.mine_and_slash.database.data.currency.reworked.item_mod.all.DoNothingItemMod;
import com.robertx22.mine_and_slash.database.data.currency.reworked.item_mod.all.IncrementUsesItemMod;
import com.robertx22.mine_and_slash.database.data.currency.reworked.item_mod.gear.*;
import com.robertx22.mine_and_slash.database.data.currency.reworked.item_mod.jewel.CorruptJewelItemMod;
import com.robertx22.mine_and_slash.database.data.currency.reworked.item_mod.jewel.UpgradeJewelAffixRarityMod;
import com.robertx22.mine_and_slash.database.data.currency.reworked.item_mod.map.UpgradeMapRarityItemMod;
import com.robertx22.mine_and_slash.database.data.currency.reworked.item_req.ItemReqs;
import com.robertx22.mine_and_slash.database.data.currency.reworked.keys.*;
import com.robertx22.mine_and_slash.uncommon.interfaces.data_items.IRarity;

// i think i like this way of registering. one liner, holds id and lazy supplier for when needs to register
public class ItemMods extends ExileKeyHolder<ItemModification> {

    public static ItemMods INSTANCE = new ItemMods();

    public ExileKeyMap<ItemModification, SkillItemTierKey> SHARPEN_STONE_QUALITY = new ExileKeyMap<ItemModification, SkillItemTierKey>(this, "sharpening_stone_quality")
            .ofSkillItemTiers()
            .build((id, info) -> {
                int num = (info.tier.tier + 1) * 2;
                return new AddQualityItemMod(id, new AddQualityItemMod.Data(new MinMax(num, num)));
            });

    public ExileKeyMap<ItemModification, RarityKeyInfo> UPGRADE_SPECIFIC_AFFIX_RARITY = new ExileKeyMap<ItemModification, RarityKeyInfo>(this, "upgrade_specific_rar")
            .ofGearRarities()
            .build((id, info) -> {
                return new UpgradeAffixItemMod(id, UpgradeAffixItemMod.AffixFinder.SPECIFIC_RARITY.ofSpecificRarity(info.rar));
            });

    public ExileKeyMap<ItemModification, RarityKeyInfo> SET_LOWEST_AFFIX_RARITY = new ExileKeyMap<ItemModification, RarityKeyInfo>(this, "set_lowest_affix_rar")
            .ofGearRarities()
            .build((id, info) -> {
                return new SetAffixRarityItemMod(id, new SetAffixRarityItemMod.Data(new UpgradeAffixItemMod.AffixFinderData(UpgradeAffixItemMod.AffixFinder.LOWEST_RARITY_AFFIX, ""), info.rar));
            });


    public ExileKeyMap<ItemModification, MaxUsesKey> MAXIMUM_USES = new ExileKeyMap<ItemModification, MaxUsesKey>(this, "increment_uses")
            .ofList(ItemReqs.Datas.allMaxUses(), x -> new MaxUsesKey(x))
            .build((id, info) -> new IncrementUsesItemMod(id, info.data));

    public ExileKey<ItemModification, KeyInfo> ADD_GEAR_LEVEL = ExileKey.ofId(this, "add_gear_level", x -> new AddGearLevelItemMod(x.GUID(), new AddGearLevelItemMod.Data(1)));
    public ExileKey<ItemModification, KeyInfo> ADD_GEAR_QUALITY = ExileKey.ofId(this, "add_gear_quality", x -> new AddQualityItemMod(x.GUID(), new AddQualityItemMod.Data(new MinMax(1, 1))));
    public ExileKey<ItemModification, KeyInfo> ADD_UP_TO_5_GEAR_QUALITY = ExileKey.ofId(this, "add_1_to_5_gear_quality", x -> new AddQualityItemMod(x.GUID(), new AddQualityItemMod.Data(new MinMax(1, 5))));
    public ExileKey<ItemModification, KeyInfo> ADD_SOCKET = ExileKey.ofId(this, "add_socket", x -> new AddSocketItemMod(x.GUID(), new AddSocketItemMod.Data(1)));
    public ExileKey<ItemModification, KeyInfo> UPGRADE_RANDOM_AFFIX = ExileKey.ofId(this, "upgrade_random_affix", x -> new UpgradeAffixItemMod(x.GUID(), UpgradeAffixItemMod.AffixFinder.RANDOM_AFFIX.get()));
    public ExileKey<ItemModification, KeyInfo> DOWNGRADE_RANDOM_AFFIX = ExileKey.ofId(this, "downgrade_random_affix", x -> new DowngradeAffixItemMod(x.GUID(), new DowngradeAffixItemMod.Data(UpgradeAffixItemMod.AffixFinder.RANDOM_AFFIX.get())));
    public ExileKey<ItemModification, KeyInfo> UPGRADE_LOWEST_AFFIX = ExileKey.ofId(this, "upgrade_lowest_affix", x -> new UpgradeAffixItemMod(x.GUID(), UpgradeAffixItemMod.AffixFinder.LOWEST_RARITY_AFFIX.get()));
    public ExileKey<ItemModification, KeyInfo> CORRUPT_GEAR = ExileKey.ofId(this, "corrupt_gear", x -> new CorruptGearItemMod(x.GUID(), new CorruptGearItemMod.Data(true)));
    public ExileKey<ItemModification, KeyInfo> CORRUPT_GEAR_NO_AFFIXES = ExileKey.ofId(this, "corrupt_gear_no_affix", x -> new CorruptGearItemMod(x.GUID(), new CorruptGearItemMod.Data(false)));
    public ExileKey<ItemModification, KeyInfo> DESTROY_ITEM = ExileKey.ofId(this, "destroy_item", x -> new DestroyItemMod(x.GUID()));
    public ExileKey<ItemModification, KeyInfo> DO_NOTHING = ExileKey.ofId(this, "do_nothing", x -> new DoNothingItemMod(x.GUID()));
    public ExileKey<ItemModification, KeyInfo> ADD_5_PERCENT_UNIQUE_STATS = ExileKey.ofId(this, "add_5_perc_unique_stats", x -> new ModifyUniqueStatsItemMod(x.GUID(), new ModifyUniqueStatsItemMod.Data(5)));
    public ExileKey<ItemModification, KeyInfo> ADD_10_PERCENT_UNIQUE_STATS = ExileKey.ofId(this, "add_10_perc_unique_stats", x -> new ModifyUniqueStatsItemMod(x.GUID(), new ModifyUniqueStatsItemMod.Data(10)));
    public ExileKey<ItemModification, KeyInfo> REDUCE_5_PERCENT_UNIQUE_STATS = ExileKey.ofId(this, "reduce_5_perc_unique_stats", x -> new ModifyUniqueStatsItemMod(x.GUID(), new ModifyUniqueStatsItemMod.Data(-5)));
    public ExileKey<ItemModification, KeyInfo> REROLL_RANDOM_AFFIX = ExileKey.ofId(this, "reroll_random_affix", x -> new RerollAffixItemMod(x.GUID(), new RerollAffixItemMod.Data(UpgradeAffixItemMod.AffixFinder.RANDOM_AFFIX.get(), "random"), ""));
    public ExileKey<ItemModification, KeyInfo> REROLL_LOWEST_AFFIX = ExileKey.ofId(this, "reroll_lowest_affix", x -> new RerollAffixItemMod(x.GUID(), new RerollAffixItemMod.Data(UpgradeAffixItemMod.AffixFinder.LOWEST_RARITY_AFFIX.get(), "random"), ""));
    public ExileKey<ItemModification, KeyInfo> REROLL_RANDOM_AFFIX_INTO_MYTHIC = ExileKey.ofId(this, "reroll_random_affix_to_mythic", x -> new RerollAffixItemMod(x.GUID(), new RerollAffixItemMod.Data(UpgradeAffixItemMod.AffixFinder.RANDOM_AFFIX.get(), IRarity.MYTHIC_ID), "Mythic"));
    public ExileKey<ItemModification, KeyInfo> REROLL_INFUSION = ExileKey.ofId(this, "reroll_infusion", x -> new RerollInfusionItemMod(x.GUID()));
    public ExileKey<ItemModification, KeyInfo> UPGRADE_MAP_RARITY = ExileKey.ofId(this, "map_rarity_upgrade", x -> new UpgradeMapRarityItemMod(x.GUID()));
    public ExileKey<ItemModification, KeyInfo> ADD_25_POTENTIAL = ExileKey.ofId(this, "add_potential", x -> new AddPotentialItemMod(x.GUID(), new AddPotentialItemMod.Data(25)));
    public ExileKey<ItemModification, KeyInfo> JEWEL_CORRUPTION = ExileKey.ofId(this, "jewel_corrupt", x -> new CorruptJewelItemMod(x.GUID()));
    public ExileKey<ItemModification, KeyInfo> REROLL_AFFIX_NUMBERS = ExileKey.ofId(this, "affix_number_reroll", x -> new RerollAffixNumbersItemMod(x.GUID()));

    // jewels
    public ExileKey<ItemModification, KeyInfo> UPGRADE_JEWEL_AFFIX_RARITY = ExileKey.ofId(this, "upgrade_jewel_affix_rarity", x -> new UpgradeJewelAffixRarityMod(x.GUID(), UpgradeAffixItemMod.AffixFinder.RANDOM_AFFIX.get()));


    @Override
    public void loadClass() {


    }
}
