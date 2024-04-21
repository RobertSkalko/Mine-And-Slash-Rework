package com.robertx22.age_of_exile.database.data.unique_items;

import com.robertx22.age_of_exile.database.data.StatMod;
import com.robertx22.age_of_exile.database.data.gear_slots.GearSlot;
import com.robertx22.age_of_exile.database.data.gear_types.bases.BaseGearType;
import com.robertx22.age_of_exile.database.data.league.LeagueMechanic;
import com.robertx22.age_of_exile.database.data.rarities.GearRarity;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.database.registry.ExileRegistryTypes;
import com.robertx22.age_of_exile.mmorpg.SlashRef;
import com.robertx22.age_of_exile.uncommon.interfaces.IAutoLocDesc;
import com.robertx22.age_of_exile.uncommon.interfaces.IAutoLocName;
import com.robertx22.age_of_exile.uncommon.interfaces.data_items.IRarity;
import com.robertx22.library_of_exile.registry.ExileRegistryType;
import com.robertx22.library_of_exile.registry.IAutoGson;
import com.robertx22.library_of_exile.registry.JsonExileRegistry;
import com.robertx22.library_of_exile.registry.serialization.ISerializable;
import net.minecraft.ChatFormatting;

import java.util.ArrayList;
import java.util.List;

public class UniqueGear implements JsonExileRegistry<UniqueGear>, IAutoLocName, IAutoLocDesc, IAutoGson<UniqueGear>, ISerializable<UniqueGear> {

    public static UniqueGear SERIALIZER = new UniqueGear();

    public List<StatMod> unique_stats = new ArrayList<>();
    public int weight = 1000;
    public int min_tier = 0;
    public String guid;
    public String force_item_id = "";
    public String rarity = IRarity.UNIQUE_ID;
    public boolean replaces_name = true;
    public String flavor_text = "";
    public String base_gear = "";
    public String league = "";
    public boolean runable = false;

    public transient String langName;


    public boolean canSpawnInLeague(LeagueMechanic league) {

        if (league.isEmpty() && this.league.isEmpty()) {
            return true;
        }

        return this.league.equals(league.GUID());
    }

    @Override
    public int Weight() {
        return weight;
    }

    @Override
    public AutoLocGroup locNameGroup() {
        return AutoLocGroup.Unique_Items;
    }

    @Override
    public ExileRegistryType getExileRegistryType() {
        return ExileRegistryTypes.UNIQUE_GEAR;
    }

    public GearRarity getUniqueRarity() {
        return ExileDB.GearRarities().get(rarity);
    }

    public List<StatMod> uniqueStats() {
        return this.unique_stats;
    }

    @Override
    public String locNameForLangFile() {
        return this.langName;
    }

    @Override
    public String locNameLangFileGUID() {
        return SlashRef.MODID + ".unique_gear." + this.GUID() + ".name";
    }

    @Override
    public String GUID() {
        return guid;
    }

    public GearSlot getSlot() {
        return ExileDB.GearSlots()
                .get(getBaseGear().gear_slot);
    }

    public BaseGearType getBaseGear() {
        return ExileDB.GearTypes()
                .get(base_gear);
    }

    @Override
    public AutoLocGroup locDescGroup() {
        return locNameGroup();
    }

    @Override
    public String locDescLangFileGUID() {
        return SlashRef.MODID + ".unique_gear." + this.GUID() + ".flavor_text";
    }

    @Override
    public String locDescForLangFile() {
        if (flavor_text.isEmpty()) {
            return flavor_text;
        }
        return ChatFormatting.ITALIC + "" + ChatFormatting.GRAY + this.flavor_text;
    }

    @Override
    public Class<UniqueGear> getClassForSerialization() {
        return UniqueGear.class;
    }
}