package com.robertx22.age_of_exile.aoe_data.database.runes;

import com.robertx22.age_of_exile.database.data.runes.Rune;
import com.robertx22.age_of_exile.mmorpg.registers.common.items.RuneItems;
import com.robertx22.age_of_exile.vanilla_mc.items.gemrunes.RuneItem;
import com.robertx22.library_of_exile.registry.ExileRegistryInit;
import com.robertx22.library_of_exile.vanilla_util.main.VanillaUTIL;

public class Runes implements ExileRegistryInit {
    @Override
    public void registerAll() {
        RuneItems.ALL.forEach(obj -> {
            RuneItem x = obj.get();

            var type = x.type;

            Rune rune = new Rune();
            rune.item_id = VanillaUTIL.REGISTRY.items().getKey(x)
                    .toString();
            rune.id = x.type.id;

            rune.tier = x.type.tier;

            rune.min_lvl_multi = x.type.lvlmin;

            rune.weight = x.type.weight;

            rune.on_jewelry_stats.addAll(type.stats.get().jewerly);
            rune.on_weapons_stats.addAll(type.stats.get().weapon);
            rune.on_armor_stats.addAll(type.stats.get().armor);

            rune.addToSerializables();
        });
    }
}
