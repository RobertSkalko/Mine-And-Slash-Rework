package com.robertx22.mine_and_slash.database.data.omen;

import com.robertx22.library_of_exile.registry.ExileRegistryType;
import com.robertx22.library_of_exile.registry.IAutoGson;
import com.robertx22.library_of_exile.registry.JsonExileRegistry;
import com.robertx22.mine_and_slash.database.data.StatMod;
import com.robertx22.mine_and_slash.database.data.affixes.Affix;
import com.robertx22.mine_and_slash.database.data.gear_slots.GearSlot;
import com.robertx22.mine_and_slash.database.registry.ExileDB;
import com.robertx22.mine_and_slash.database.registry.ExileRegistryTypes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Omen implements JsonExileRegistry<Omen>, IAutoGson<Omen> {

    public static Omen SERIALIZER = new Omen("ser", "ser", 0, Arrays.asList());

    public String id = "";

    public int weight = 1000;

    public transient String locname = "";

    public float lvl_req = 0;

    public List<StatMod> mods = new ArrayList<>();

    public List<Affix.Type> affix_types = new ArrayList<>(Arrays.asList(Affix.Type.prefix, Affix.Type.suffix));

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
}
