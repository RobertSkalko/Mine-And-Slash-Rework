package com.robertx22.mine_and_slash.mmorpg.registers.common.items;

import com.robertx22.mine_and_slash.database.data.loot_chest.base.LootChestItem;
import com.robertx22.mine_and_slash.database.data.profession.items.RarityKeyItem;
import com.robertx22.mine_and_slash.mmorpg.registers.deferred_wrapper.Def;
import com.robertx22.mine_and_slash.mmorpg.registers.deferred_wrapper.RegObj;
import com.robertx22.mine_and_slash.uncommon.interfaces.data_items.IRarity;
import com.robertx22.mine_and_slash.vanilla_mc.items.SlashPotionItem;
import com.robertx22.mine_and_slash.vanilla_mc.items.SoulMakerItem;
import com.robertx22.mine_and_slash.vanilla_mc.items.misc.RarityStoneItem;

import java.util.HashMap;

public class RarityItems {

    public static HashMap<String, RegObj<RarityStoneItem>> RARITY_STONE = new HashMap<>();
    public static HashMap<String, RegObj<LootChestItem>> GEAR_CHESTS = new HashMap<>();
    public static HashMap<String, RegObj<SoulMakerItem>> SOUL_EXTRACTORS = new HashMap<>();
    public static HashMap<String, RegObj<SlashPotionItem>> HEALTH_POTIONS = new HashMap<>();
    public static HashMap<String, RegObj<SlashPotionItem>> RESOURCE_POTIONS = new HashMap<>();
    public static HashMap<String, RegObj<RarityKeyItem>> RARITY_KEYS = new HashMap<>();

    public static void init() {
        
        int tier = 0;
        for (String rar : IRarity.NORMAL_GEAR_RARITIES) {

            int finalTier = tier;

            RARITY_STONE.put(rar, Def.item(() -> new RarityStoneItem("Stone", finalTier), "stone/" + tier));
            GEAR_CHESTS.put(rar, Def.item(() -> new LootChestItem("Gear"), "chest/" + rar + "_gear"));
            SOUL_EXTRACTORS.put(rar, Def.item(() -> new SoulMakerItem(rar), "soul_extractor/" + rar));

            HEALTH_POTIONS.put(rar, Def.item(() -> new SlashPotionItem(rar, SlashPotionItem.Type.HP), "potion/health/" + finalTier));
            RESOURCE_POTIONS.put(rar, Def.item(() -> new SlashPotionItem(rar, SlashPotionItem.Type.MANA), "potion/resource/" + finalTier));
            RARITY_KEYS.put(rar, Def.item(() -> new RarityKeyItem(rar, "Key"), "keys/" + finalTier));

            tier++;
        }


    }
}
