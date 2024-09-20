package com.robertx22.mine_and_slash.database.data.omen;

import com.robertx22.library_of_exile.registry.ExileRegistryType;
import com.robertx22.library_of_exile.registry.IAutoGson;
import com.robertx22.library_of_exile.registry.JsonExileRegistry;
import com.robertx22.mine_and_slash.database.data.StatMod;
import com.robertx22.mine_and_slash.database.data.affixes.Affix;
import com.robertx22.mine_and_slash.database.data.gear_slots.GearSlot;
import com.robertx22.mine_and_slash.database.data.rarities.GearRarityType;
import com.robertx22.mine_and_slash.database.registry.ExileDB;
import com.robertx22.mine_and_slash.database.registry.ExileRegistryTypes;
import com.robertx22.mine_and_slash.mmorpg.SlashRef;
import com.robertx22.mine_and_slash.uncommon.interfaces.IAutoLocName;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class Omen implements JsonExileRegistry<Omen>, IAutoGson<Omen>, IAutoLocName {

    public static Omen SERIALIZER = new Omen("ser", "ser", 0, Arrays.asList());

    public String id = "";

    public int weight = 1000;

    public transient String locname = "";

    public float lvl_req = 0;

    public List<StatMod> mods = new ArrayList<>();

    public List<Affix.Type> affix_types = new ArrayList<>(Arrays.asList(Affix.Type.chaos_stat, Affix.Type.jewel_corruption));

    public Omen(String id, String locname, float lvl_req, List<StatMod> mods) {
        this.id = id;
        this.locname = locname;
        this.lvl_req = lvl_req;
        this.mods = mods;
    }

    public Omen addCorruptionAffixes() {
        this.affix_types.add(Affix.Type.chaos_stat);
        return this;
    }


    public GearSlot getRandomSlotReq() {
        // exclude weapons from omens req at least, they're a lot of times swapped
        return ExileDB.GearSlots().getFilterWrapped(x -> !x.fam.isWeapon()).random();
    }

    public GearRarityType getRandomSlotReqRarity(OmenData data) {
        return Arrays.stream(GearRarityType.values())
                .max(Comparator.comparingInt(x -> -(int) data.slot_req.stream()
                        .filter(s -> s.rtype == x).count()))
                .orElse(GearRarityType.NORMAL);
    }

    @Override
    public ExileRegistryType getExileRegistryType() {
        return ExileRegistryTypes.OMEN;
    }

    @Override
    public Class<Omen> getClassForSerialization() {
        return Omen.class;
    }

    @Override
    public String GUID() {
        return id;
    }

    @Override
    public int Weight() {
        return weight;
    }

    @Override
    public AutoLocGroup locNameGroup() {
        return AutoLocGroup.Omens;
    }

    @Override
    public String locNameLangFileGUID() {
        return SlashRef.MODID + ".omen." + GUID();
    }

    @Override
    public String locNameForLangFile() {
        return locname;
    }
}
