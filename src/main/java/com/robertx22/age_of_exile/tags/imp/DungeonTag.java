package com.robertx22.age_of_exile.tags.imp;

import com.robertx22.age_of_exile.tags.ModTag;
import com.robertx22.age_of_exile.tags.NormalModTag;
import com.robertx22.age_of_exile.tags.TagType;

import java.util.List;
import java.util.stream.Collectors;

public class DungeonTag extends NormalModTag {
    public static DungeonTag SERIALIZER = new DungeonTag("");

    public static DungeonTag of(String id) {
        return (DungeonTag) register(TagType.Dungeon, new DungeonTag(id));
    }

    public static List<DungeonTag> getAll() {
        return ModTag.MAP.get(TagType.Dungeon).stream().map(x -> (DungeonTag) x).collect(Collectors.toList());
    }

    public DungeonTag(String id) {
        super(id);
    }

    @Override
    public DungeonTag fromString(String s) {
        return new DungeonTag(s);
    }

    @Override
    public TagType getTagType() {
        return TagType.Dungeon;
    }
}
