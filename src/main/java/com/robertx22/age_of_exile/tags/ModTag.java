package com.robertx22.age_of_exile.tags;

import com.robertx22.age_of_exile.mmorpg.SlashRef;
import com.robertx22.age_of_exile.uncommon.interfaces.IAutoLocName;
import com.robertx22.library_of_exile.registry.IGUID;
import org.codehaus.plexus.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class ModTag implements IAutoLocName, IGUID {

    // todo maybe have tags use resourceloc as as tagtype:tagid thin?
    public static HashMap<TagType, List<ModTag>> MAP = new HashMap<>();

    public static ModTag register(TagType type, ModTag tag) {
        if (!MAP.containsKey(type)) {
            MAP.put(type, new ArrayList<>());
        }
        MAP.get(type).add(tag);
        return tag;
    }

    
    public abstract ModTag fromString(String s);

    @Override
    public AutoLocGroup locNameGroup() {
        return AutoLocGroup.Tags;
    }

    @Override
    public String locNameLangFileGUID() {
        return SlashRef.MODID + ".tag." + GUID();
    }

    @Override
    public String locNameForLangFile() {
        return StringUtils.capitalise(GUID().replaceAll("_", " "));
    }


}
