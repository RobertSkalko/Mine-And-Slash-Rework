package com.robertx22.age_of_exile.loot.generators.util;

import com.google.common.base.Preconditions;
import com.robertx22.age_of_exile.database.data.rarities.GearRarity;
import com.robertx22.age_of_exile.database.data.unique_items.UniqueGear;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.loot.blueprints.GearBlueprint;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_parts.BaseStatsData;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_parts.UniqueStatsData;
import com.robertx22.age_of_exile.saveclasses.item_classes.GearItemData;
import com.robertx22.age_of_exile.uncommon.datasaving.StackSaving;
import com.robertx22.age_of_exile.uncommon.interfaces.data_items.IRarity;
import com.robertx22.library_of_exile.utils.RandomUtils;
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

        data.setPotential(rarity.potential);

        if (RandomUtils.roll(rarity.socket_chance)) {
            data.sockets.addSocket();
        }

        if (rarity.is_unique_item && blueprint.uniquePart.get() != null) {

            UniqueGear unique = blueprint.uniquePart.get();

            Preconditions.checkNotNull(unique);

            data.rar = ExileDB.GearRarities()
                    .get(unique.uniqueRarity)
                    .GUID();

            data.gtype = unique.base_gear;
            data.uniq = unique.GUID();
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
