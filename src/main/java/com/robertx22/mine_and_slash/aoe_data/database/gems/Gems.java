package com.robertx22.mine_and_slash.aoe_data.database.gems;

import com.robertx22.library_of_exile.registry.ExileRegistryInit;
import com.robertx22.library_of_exile.vanilla_util.main.VanillaUTIL;
import com.robertx22.mine_and_slash.database.data.gear_types.bases.SlotFamily;
import com.robertx22.mine_and_slash.database.data.gems.Gem;
import com.robertx22.mine_and_slash.mmorpg.registers.common.items.GemItems;
import com.robertx22.mine_and_slash.vanilla_mc.items.gemrunes.GemItem;

public class Gems implements ExileRegistryInit {

    @Override
    public void registerAll() {
        GemItems.ALL.forEach(g -> {
            GemItem x = g.get();


            Gem gem = new Gem();
            gem.item_id = VanillaUTIL.REGISTRY.items().getKey(x).toString();
            gem.identifier = x.gemType.id + x.gemRank.tier;

            gem.min_lvl_multi = x.gemRank.lvlToDropmulti;

            gem.on_armor_stats = x.getStatsForSerialization(SlotFamily.Armor);
            gem.on_weapons_stats = x.getStatsForSerialization(SlotFamily.Weapon);
            gem.on_jewelry_stats = x.getStatsForSerialization(SlotFamily.Jewelry);

            gem.on_armor_stats.forEach(e -> e.scale_to_lvl = true);
            gem.on_weapons_stats.forEach(e -> e.scale_to_lvl = true);
            gem.on_jewelry_stats.forEach(e -> e.scale_to_lvl = true);

            gem.tier = x.gemRank.tier;
            gem.gem_type = x.gemType.id;
            gem.text_format = x.gemType.format.name();

            gem.rar = x.gemRank.rar;
            gem.perc_upgrade_chance = x.gemRank.upgradeChance;

            gem.weight = x.weight;

            gem.addToSerializables();
        });
    }
}
