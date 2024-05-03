package com.robertx22.age_of_exile.database.data.custom_item;

import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.database.registry.ExileRegistryTypes;
import com.robertx22.age_of_exile.loot.LootInfo;
import com.robertx22.age_of_exile.loot.blueprints.GearBlueprint;
import com.robertx22.age_of_exile.saveclasses.item_classes.GearItemData;
import com.robertx22.age_of_exile.uncommon.MathHelper;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.interfaces.data_items.IRarity;
import com.robertx22.library_of_exile.registry.ExileRegistryType;
import com.robertx22.library_of_exile.registry.IAutoGson;
import com.robertx22.library_of_exile.registry.JsonExileRegistry;
import com.robertx22.library_of_exile.utils.RandomUtils;
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

    public GearItemData create(Player p) {

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
            data.data.set(GearItemData.KEYS.SALVAGING_DISABLED, true);
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
