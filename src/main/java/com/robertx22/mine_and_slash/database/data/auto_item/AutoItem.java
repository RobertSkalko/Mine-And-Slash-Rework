package com.robertx22.mine_and_slash.database.data.auto_item;

import com.robertx22.mine_and_slash.database.registry.ExileDB;
import com.robertx22.mine_and_slash.database.registry.ExileRegistryTypes;
import com.robertx22.mine_and_slash.saveclasses.item_classes.GearItemData;
import com.robertx22.library_of_exile.registry.ExileRegistryType;
import com.robertx22.library_of_exile.registry.IAutoGson;
import com.robertx22.library_of_exile.registry.JsonExileRegistry;
import com.robertx22.library_of_exile.utils.RandomUtils;
import com.robertx22.library_of_exile.vanilla_util.main.VanillaUTIL;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;

import java.util.HashMap;
import java.util.List;

public class AutoItem implements JsonExileRegistry<AutoItem>, IAutoGson<AutoItem> {
    public static AutoItem SERIALIZER = new AutoItem();

    public String id = "";
    public int weight = 1000;

    public String item_id = "";

    public String custom_item_generation = "";


    public static void of(String id, String itemid, String gen) {
        AutoItem b = new AutoItem();
        b.item_id = itemid;
        b.id = id;
        b.custom_item_generation = gen;

        b.addToSerializables();
    }

    public GearItemData create(Player p) {
        return ExileDB.CustomItemGenerations().get(custom_item_generation).create(p);
    }

    static HashMap<Item, List<AutoItem>> cached = new HashMap<>();

    public static AutoItem get(Item item) {
        if (!cached.containsKey(item)) {
            var list = ExileDB.AutoItems().getFilterWrapped(x -> x.item_id.equals(VanillaUTIL.REGISTRY.items().getKey(item).toString())).list;
            cached.put(item, list);
        }

        var list = cached.get(item);
        if (!list.isEmpty()) {
            var result = RandomUtils.weightedRandom(list);
            return result;
        }
        return null;
    }

    @Override
    public ExileRegistryType getExileRegistryType() {
        return ExileRegistryTypes.AUTO_ITEM;
    }

    @Override
    public Class<AutoItem> getClassForSerialization() {
        return AutoItem.class;
    }

    @Override
    public String GUID() {
        return id;
    }

    @Override
    public int Weight() {
        return weight;
    }
}
