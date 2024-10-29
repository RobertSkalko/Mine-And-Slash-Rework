package com.robertx22.mine_and_slash.database.data.custom_item;

import com.robertx22.library_of_exile.registry.ExileRegistryType;
import com.robertx22.library_of_exile.registry.IAutoGson;
import com.robertx22.library_of_exile.registry.JsonExileRegistry;
import com.robertx22.library_of_exile.utils.RandomUtils;
import com.robertx22.mine_and_slash.database.registry.ExileDB;
import com.robertx22.mine_and_slash.database.registry.ExileRegistryTypes;
import com.robertx22.mine_and_slash.itemstack.CustomItemData;
import com.robertx22.mine_and_slash.itemstack.ExileStacklessData;
import com.robertx22.mine_and_slash.itemstack.StackKeys;
import com.robertx22.mine_and_slash.loot.LootInfo;
import com.robertx22.mine_and_slash.loot.blueprints.GearBlueprint;
import com.robertx22.mine_and_slash.uncommon.MathHelper;
import com.robertx22.mine_and_slash.uncommon.datasaving.Load;
import com.robertx22.mine_and_slash.uncommon.interfaces.data_items.IRarity;
import net.minecraft.world.entity.player.Player;

import java.util.List;
import java.util.stream.Collectors;

public class CustomItem implements JsonExileRegistry<CustomItem>, IAutoGson<CustomItem> {
    public static CustomItem SERIALIZER = new CustomItem();

    public String id = "";

    public int min_lvl = 1;
    public int max_lvl = 100;

    public List<String> possible_rar = IRarity.NORMAL_GEAR_RARITIES;

    public String uniq_id = "";

    public String gear_type = "";

    public boolean disable_salvaging = false;

    public ExileStacklessData create(Player p) {

        int lvl = Load.Unit(p).getLevel();

        int itemlvl = MathHelper.clamp(lvl, min_lvl, max_lvl);

        GearBlueprint b = new GearBlueprint(LootInfo.ofPlayer(p));
        b.level.set(itemlvl);

        var rar = RandomUtils.weightedRandom(possible_rar.stream().map(x -> ExileDB.GearRarities().get(x)).collect(Collectors.toList()));

        b.rarity.set(rar);

        if (!uniq_id.isEmpty()) {
            b.uniquePart.set(ExileDB.UniqueGears().get(uniq_id));
        }

        if (!gear_type.isEmpty()) {
            b.setType(gear_type);
        }

        var data = b.createData();

        if (disable_salvaging) {
            data.getOrCreate(StackKeys.CUSTOM).data.set(CustomItemData.KEYS.SALVAGING_DISABLED, true);
        }

        return data;

    }

    @Override
    public ExileRegistryType getExileRegistryType() {
        return ExileRegistryTypes.CUSTOM_ITEM;
    }

    @Override
    public Class<CustomItem> getClassForSerialization() {
        return CustomItem.class;
    }

    @Override
    public String GUID() {
        return id;
    }

    @Override
    public int Weight() {
        return 1000;
    }
}
