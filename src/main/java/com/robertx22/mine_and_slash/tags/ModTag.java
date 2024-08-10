package com.robertx22.mine_and_slash.tags;

import com.robertx22.mine_and_slash.mmorpg.SlashRef;
import com.robertx22.mine_and_slash.uncommon.interfaces.IAutoLocName;
import com.robertx22.mine_and_slash.uncommon.utilityclasses.StringUTIL;
import com.robertx22.library_of_exile.registry.IGUID;

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

    public abstract TagType getTagType();

    @Override
    public AutoLocGroup locNameGroup() {
        return AutoLocGroup.Tags;
    }

    @Override
    public String locNameLangFileGUID() {
        return SlashRef.MODID + ".tag." + getTagType().id + "." + GUID();
    }

    @Override
    public String locNameForLangFile() {
        return StringUTIL.capitalise(GUID().replaceAll("_", " "));
    }


}
