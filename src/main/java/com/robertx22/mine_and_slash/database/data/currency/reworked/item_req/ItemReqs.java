package com.robertx22.mine_and_slash.database.data.currency.reworked.item_req;

import com.robertx22.mine_and_slash.database.data.currency.reworked.item_req.custom.IsAnyReq;
import com.robertx22.mine_and_slash.database.data.currency.reworked.item_req.custom.MaximumUsesReq;
import com.robertx22.mine_and_slash.database.data.currency.reworked.item_req.gear.*;
import com.robertx22.mine_and_slash.database.data.currency.reworked.item_req.jewel.JewelHasAffixesReq;
import com.robertx22.mine_and_slash.database.data.currency.reworked.item_req.map.MapHasHigherRarityReq;
import com.robertx22.mine_and_slash.database.data.currency.reworked.keys.*;
import com.robertx22.mine_and_slash.uncommon.interfaces.data_items.IRarity;

import java.util.Arrays;
import java.util.List;


// todo add a has x rarity affix
public class ItemReqs extends ExileKeyHolder<ItemRequirement> {

    public static class Datas {
        public static MaximumUsesReq.Data MAX_LEVEL_USES = new MaximumUsesReq.Data("level_up", 5);
        public static MaximumUsesReq.Data RANDOM_MYTHIC_AFFIX = new MaximumUsesReq.Data("random_mythic_affix", 1);
        public static MaximumUsesReq.Data MAX_SHARPENING_STONE_USES = new MaximumUsesReq.Data("sharpening_stone", 1);
        public static MaximumUsesReq.Data MAX_JEWEL_UPGRADE_USES = new MaximumUsesReq.Data("sure_jewel_up", 3);

        public static List<MaximumUsesReq.Data> allMaxUses() {
            return Arrays.asList(MAX_JEWEL_UPGRADE_USES, MAX_LEVEL_USES, RANDOM_MYTHIC_AFFIX, MAX_SHARPENING_STONE_USES);
        }
    }

    public static ItemReqs INSTANCE = new ItemReqs();

    public ExileKeyMap<ItemRequirement, RarityKeyInfo> IS_RARITY = new ExileKeyMap<ItemRequirement, RarityKeyInfo>(this, "is")
            .ofGearRarities()
            .build((id, info) -> new IsRarityReq(id, new IsRarityReq.Data(info.rar)));

    public ExileKeyMap<ItemRequirement, RarityKeyInfo> HAS_AFFIX_OF_RARITY = new ExileKeyMap<ItemRequirement, RarityKeyInfo>(this, "has_affix")
            .ofGearRarities()
            .build((id, info) -> new HasAffixOfRarity(id, new HasAffixOfRarity.Data(info.rar)));

    public ExileKeyMap<ItemRequirement, MaxUsesKey> MAXIMUM_USES = new ExileKeyMap<ItemRequirement, MaxUsesKey>(this, "max_uses")
            .ofList(Datas.allMaxUses(), x -> new MaxUsesKey(x))
            .build((id, info) -> new MaximumUsesReq(id, info.data));

    public ExileKey<ItemRequirement, KeyInfo> LEVEL_NOT_MAX = ExileKey.ofId(this, "lvl_not_max", x -> new LevelNotMaxReq(x.GUID()));
    public ExileKey<ItemRequirement, KeyInfo> HAS_AFFIXES = ExileKey.ofId(this, "has_affixes", x -> new MustHaveAffixesReq(x.GUID()));
    public ExileKey<ItemRequirement, KeyInfo> IS_NOT_CORRUPTED = ExileKey.ofId(this, "is_not_corrupted", x -> new IsNotCorruptedReq(x.GUID()));
    public ExileKey<ItemRequirement, KeyInfo> HAS_INFUSION = ExileKey.ofId(this, "has_infusion", x -> new HasInfusionReq(x.GUID()));
    public ExileKey<ItemRequirement, KeyInfo> CAN_ADD_SOCKETS = ExileKey.ofId(this, "can_add_sockets", x -> new CanAddSocketsReq(x.GUID()));
    public ExileKey<ItemRequirement, KeyInfo> UNDER_20_QUALITY = ExileKey.ofId(this, "is_under_20_quality", x -> new IsUnderQualityReq(x.GUID(), IsUnderQualityReq.UNDER_20));
    public ExileKey<ItemRequirement, KeyInfo> UNDER_21_QUALITY = ExileKey.ofId(this, "is_under_21_quality", x -> new IsUnderQualityReq(x.GUID(), IsUnderQualityReq.UNDER_21));
    public ExileKey<ItemRequirement, KeyInfo> NOT_CRAFTED = ExileKey.ofId(this, "not_crafted_gear", x -> new IsGearNotCraftedReq(x.GUID()));


    // maps
    public ExileKey<ItemRequirement, KeyInfo> MAP_HAS_HIGHER_RARITY = ExileKey.ofId(this, "map_has_higher_rar", x -> new MapHasHigherRarityReq(x.GUID()));


    // any
    public ExileKey<ItemRequirement, KeyInfo> IS_COMMON_OR_UNCOMMON = ExileKey.ofId(this, "is_common_or_uncommon",
            x -> new IsAnyReq(
                    x.GUID(),
                    new IsAnyReq.Data(Arrays.asList(
                            IS_RARITY.getId(new RarityKeyInfo(IRarity.COMMON_ID)),
                            IS_RARITY.getId(new RarityKeyInfo(IRarity.UNCOMMON))
                    )),
                    "Must be Common Or Uncommon")
    );

    // nones


    public ExileKey<ItemRequirement, KeyInfo> JEWEL_HAS_AFFIXES = ExileKey.ofId(this, "jewel_has_affixes", x -> new JewelHasAffixesReq(x.GUID()));

    @Override
    public void loadClass() {


    }
}
