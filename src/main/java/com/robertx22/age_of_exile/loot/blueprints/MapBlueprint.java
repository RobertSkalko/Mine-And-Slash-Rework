package com.robertx22.age_of_exile.loot.blueprints;


import com.robertx22.age_of_exile.database.data.map_affix.MapAffix;
import com.robertx22.age_of_exile.database.data.rarities.GearRarity;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.loot.blueprints.bases.GearRarityPart;
import com.robertx22.age_of_exile.maps.MapAffixData;
import com.robertx22.age_of_exile.maps.MapItemData;
import com.robertx22.age_of_exile.mmorpg.registers.common.items.SlashItems;
import com.robertx22.age_of_exile.uncommon.datasaving.StackSaving;
import com.robertx22.library_of_exile.utils.RandomUtils;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class MapBlueprint extends ItemBlueprint {


    public MapBlueprint(int level) {
        super(level);
        this.rarity.chanceForHigherRarity = 50;
    }

    public GearRarityPart rarity = new GearRarityPart(this);

    @Override
    public ItemStack generate() {


        MapItemData data = createData();

        ItemStack stack = new ItemStack(SlashItems.MAP.get());

        StackSaving.MAP.saveTo(stack, data);

        return stack;

    }

    public MapItemData createData() {
        MapItemData data = new MapItemData();
        GearRarity rarity = (GearRarity) this.rarity.get();

        data.rar = rarity.GUID();

        data.tier = RandomUtils.RandomRange(0, MapItemData.MAX_TIER);

        data.lvl = level.get();

        genAffixes(data, rarity);


        return data;
    }

    private MapItemData genAffixes(MapItemData map, GearRarity rarity) {

        int amount = rarity.getAffixAmount() + 2;

        List<String> affixes = new ArrayList<String>();

        for (int i = 0; i < amount; i++) {

            MapAffix affix = ExileDB.MapAffixes().random();

            while (affixes.contains(affix.GUID()) /*|| affix.isBeneficial()*/) {
                affix = ExileDB.MapAffixes().random();
            }
            int percent = RandomUtils.RandomRange(0, 100);
            map.affixes.add(new MapAffixData(affix, percent));
            affixes.add(affix.GUID());

        }

        return map;
    }

}
