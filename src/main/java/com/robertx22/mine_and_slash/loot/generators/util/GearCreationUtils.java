package com.robertx22.mine_and_slash.loot.generators.util;

import com.google.common.base.Preconditions;
import com.robertx22.mine_and_slash.database.data.rarities.GearRarity;
import com.robertx22.mine_and_slash.database.data.unique_items.UniqueGear;
import com.robertx22.mine_and_slash.database.registry.ExileDB;
import com.robertx22.mine_and_slash.loot.blueprints.GearBlueprint;
import com.robertx22.mine_and_slash.saveclasses.gearitem.gear_parts.BaseStatsData;
import com.robertx22.mine_and_slash.saveclasses.gearitem.gear_parts.UniqueStatsData;
import com.robertx22.mine_and_slash.saveclasses.item_classes.GearItemData;
import com.robertx22.mine_and_slash.uncommon.datasaving.StackSaving;
import com.robertx22.mine_and_slash.uncommon.interfaces.data_items.IRarity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class GearCreationUtils {

    public static ItemStack CreateStack(GearItemData data, Item item) {

        ItemStack stack = new ItemStack(item);

        StackSaving.GEARS.saveTo(stack, data);


        return stack;

    }


    public static GearItemData CreateData(GearBlueprint blueprint) {


        GearRarity rarity = blueprint.rarity.get();
        GearItemData data = new GearItemData();

        data.gtype = blueprint.gearItemSlot.get().GUID();
        data.lvl = blueprint.level.get();
        data.rar = rarity.GUID();

        data.setPotential(rarity.pot.total);

        int sockets = rarity.sockets.random();

        for (int i = 0; i < sockets; i++) {
            data.sockets.addSocket();
        }

        if (rarity.is_unique_item && blueprint.uniquePart.get() != null) {

            UniqueGear unique = blueprint.uniquePart.get();

            Preconditions.checkNotNull(unique);

            data.rar = ExileDB.GearRarities().get(unique.rarity).GUID();

            data.gtype = unique.base_gear;
            data.data.set(GearItemData.KEYS.UNIQUE_ID, unique.GUID());
            data.uniqueStats = new UniqueStatsData();
            data.uniqueStats.RerollFully(data);

        } else {
            if (rarity.is_unique_item) {
                data.rar = IRarity.COMMON_ID;
            }
        }

        data.baseStats = new BaseStatsData();
        data.baseStats.RerollFully(data);
        data.imp.RerollFully(data);
        data.affixes.randomize(data);


        return data;
    }
}
