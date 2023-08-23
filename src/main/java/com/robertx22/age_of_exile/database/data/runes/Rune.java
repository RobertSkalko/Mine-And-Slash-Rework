package com.robertx22.age_of_exile.database.data.runes;

import com.robertx22.age_of_exile.database.data.StatMod;
import com.robertx22.age_of_exile.database.data.game_balance_config.GameBalanceConfig;
import com.robertx22.age_of_exile.database.data.gear_types.bases.SlotFamily;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.database.registry.ExileRegistryTypes;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_parts.SocketData;
import com.robertx22.library_of_exile.registry.ExileRegistryType;
import com.robertx22.library_of_exile.registry.IAutoGson;
import com.robertx22.library_of_exile.registry.JsonExileRegistry;
import com.robertx22.library_of_exile.vanilla_util.main.VanillaUTIL;
import net.minecraft.ChatFormatting;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

import java.util.ArrayList;
import java.util.List;

public class Rune implements IAutoGson<Rune>, JsonExileRegistry<Rune> {

    public static Rune SERIALIZER = new Rune();

    public Item getItem() {
        return VanillaUTIL.REGISTRY.items().get(new ResourceLocation(item_id));
    }

    public List<StatMod> on_armor_stats = new ArrayList<>();
    public List<StatMod> on_jewelry_stats = new ArrayList<>();
    public List<StatMod> on_weapons_stats = new ArrayList<>();

    public String item_id = "";

    public String id = "";

    public int tier = 1;
    public int weight = 1000;

    public float min_lvl_multi = 0;

    public int getReqLevelToDrop() {
        return (int) (GameBalanceConfig.get().MAX_LEVEL * min_lvl_multi);
    }

    @Override
    public Class<Rune> getClassForSerialization() {
        return Rune.class;
    }

    public ChatFormatting getFormat(SocketData data) {
        try {
            // todo not the best heh, i should probably create a general stat perc or just rename it
            return ExileDB.GearRarities().getFilterWrapped(x -> x.stat_percents.isInRange(data.p)).list.get(0).textFormatting();

        } catch (IllegalArgumentException e) {
            //  e.printStackTrace();
        }
        return ChatFormatting.GRAY;
    }

    @Override
    public ExileRegistryType getExileRegistryType() {
        return ExileRegistryTypes.RUNE;
    }

    public final List<StatMod> getFor(SlotFamily sfor) {
        if (sfor == SlotFamily.Armor) {
            return on_armor_stats;
        }
        if (sfor == SlotFamily.Jewelry) {
            return on_jewelry_stats;
        }
        if (sfor == SlotFamily.Weapon) {
            return on_weapons_stats;
        }

        return on_armor_stats;

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
