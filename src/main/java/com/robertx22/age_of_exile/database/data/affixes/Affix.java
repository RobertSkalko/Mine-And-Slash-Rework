package com.robertx22.age_of_exile.database.data.affixes;

import com.robertx22.age_of_exile.database.base.IhasRequirements;
import com.robertx22.age_of_exile.database.data.StatMod;
import com.robertx22.age_of_exile.database.data.requirements.Requirements;
import com.robertx22.age_of_exile.database.registry.ExileRegistryTypes;
import com.robertx22.age_of_exile.mmorpg.SlashRef;
import com.robertx22.age_of_exile.uncommon.interfaces.IAutoLocName;
import com.robertx22.library_of_exile.registry.*;

import java.util.ArrayList;
import java.util.List;

public class Affix implements IWeighted, IGUID, IAutoLocName, IhasRequirements,
        JsonExileRegistry<Affix>, IAutoGson<Affix> {

    public enum Type {
        prefix,
        suffix,
        enchant,
        tool,
        jewel,
        implicit;

        public boolean isPrefix() {
            return this == prefix;
        }

        public boolean isSuffix() {
            return this == suffix;
        }

        public Type getOpposite() {
            if (this.isPrefix()) {
                return suffix;
            }
            if (this.isSuffix()) {
                return prefix;
            }
            return null;
        }
    }

    public String guid;
    public transient String loc_name;
    public boolean only_one_per_item = true;
    public int weight = 1000;
    public Requirements requirements;
    public Type type;

    public List<StatMod> stats = new ArrayList<>();

    public List<String> getAllTagReq() {
        List<String> list = new ArrayList<>();
        requirements.tag_requirements.forEach(x -> list.addAll(x.included));
        return list;
    }

    @Override
    public boolean isRegistryEntryValid() {
        if (guid == null || stats.isEmpty() || requirements == null || type == null) {
            return false;
        }

        return true;
    }

    @Override
    public final String datapackFolder() {
        return type.name() + "/";
    }

    @Override
    public ExileRegistryType getExileRegistryType() {
        return ExileRegistryTypes.AFFIX;
    }

    public String GUID() {
        return guid;
    }

    @Override
    public String locNameLangFileGUID() {
        return SlashRef.MODID + ".affix." + GUID();
    }

    @Override
    public final AutoLocGroup locNameGroup() {
        return AutoLocGroup.Affixes;
    }

    @Override
    public final Requirements requirements() {
        return requirements;
    }

    @Override
    public int Weight() {
        return weight;
    }

    public List<StatMod> getStats() {
        return stats;
    }

    @Override
    public String locNameForLangFile() {
        return this.loc_name;
    }

    @Override
    public Class<Affix> getClassForSerialization() {
        return Affix.class;
    }
}
