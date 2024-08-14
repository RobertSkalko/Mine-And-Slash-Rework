package com.robertx22.mine_and_slash.tags.imp;

import com.robertx22.mine_and_slash.tags.ModTag;
import com.robertx22.mine_and_slash.tags.NormalModTag;
import com.robertx22.mine_and_slash.tags.TagType;

import java.util.List;
import java.util.stream.Collectors;

public class MapAffixTag extends NormalModTag {
    public static MapAffixTag SERIALIZER = new MapAffixTag("");

    public static MapAffixTag of(String id) {
        return (MapAffixTag) register(TagType.MapAffix, new MapAffixTag(id));
    }

    public static List<MapAffixTag> getAll() {
        return ModTag.MAP.get(TagType.MapAffix).stream().map(x -> (MapAffixTag) x).collect(Collectors.toList());
    }

    public MapAffixTag(String id) {
        super(id);
    }

    @Override
    public ModTag fromString(String s) {
        return new MapAffixTag(s);
    }

    @Override
    public TagType getTagType() {
        return TagType.MapAffix;
    }
}
