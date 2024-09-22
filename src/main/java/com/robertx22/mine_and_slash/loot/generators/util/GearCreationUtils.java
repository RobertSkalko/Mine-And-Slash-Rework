package com.robertx22.mine_and_slash.loot.generators.util;

import com.google.common.base.Preconditions;
import com.robertx22.mine_and_slash.database.data.rarities.GearRarity;
import com.robertx22.mine_and_slash.database.data.unique_items.UniqueGear;
import com.robertx22.mine_and_slash.database.registry.ExileDB;
import com.robertx22.mine_and_slash.itemstack.CustomItemData;
import com.robertx22.mine_and_slash.itemstack.ExileStack;
import com.robertx22.mine_and_slash.itemstack.ExileStacklessData;
import com.robertx22.mine_and_slash.loot.blueprints.GearBlueprint;
import com.robertx22.mine_and_slash.saveclasses.gearitem.gear_parts.BaseStatsData;
import com.robertx22.mine_and_slash.saveclasses.gearitem.gear_parts.UniqueStatsData;
import com.robertx22.mine_and_slash.saveclasses.item_classes.GearItemData;
import com.robertx22.mine_and_slash.uncommon.interfaces.data_items.IRarity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class GearCreationUtils {

    public static ItemStack CreateStack(ExileStacklessData data, Item item) {

        ItemStack stack = new ItemStack(item);

        var ex = ExileStack.of(stack);
        data.apply(ex);

        return ex.getStack();

    }


    public static ExileStacklessData CreateData(GearBlueprint blueprint) {
        ExileStacklessData data = new ExileStacklessData();

        GearRarity rarity = blueprint.rarity.get();
        GearItemData gear = new GearItemData();

        gear.gtype = blueprint.gearItemSlot.get().GUID();
        gear.lvl = blueprint.level.get();
        gear.rar = rarity.GUID();


        int sockets = rarity.sockets.random();

        for (int i = 0; i < sockets; i++) {
            gear.sockets.addSocket();
        }

        if (rarity.is_unique_item && blueprint.uniquePart.get() != null) {

            UniqueGear unique = blueprint.uniquePart.get();

            Preconditions.checkNotNull(unique);

            gear.rar = ExileDB.GearRarities().get(unique.rarity).GUID();

            gear.gtype = unique.base_gear;
            data.getOrCreate(x -> x.CUSTOM).data.set(CustomItemData.KEYS.UNIQUE_ID, unique.GUID());
            gear.uniqueStats = new UniqueStatsData();
            gear.uniqueStats.RerollFully(gear);

        } else {
            if (rarity.is_unique_item) {
                gear.rar = IRarity.COMMON_ID;
            }
        }

        gear.baseStats = new BaseStatsData();
        gear.baseStats.RerollFully(gear);
        gear.imp.RerollFully(gear);
        gear.affixes.randomize(gear);


        data.set(x -> x.GEAR, gear);

        return data;
    }
}
