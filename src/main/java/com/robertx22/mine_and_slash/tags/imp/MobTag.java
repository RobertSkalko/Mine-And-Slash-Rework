package com.robertx22.mine_and_slash.tags.imp;

import com.robertx22.mine_and_slash.tags.ModTag;
import com.robertx22.mine_and_slash.tags.NormalModTag;
import com.robertx22.mine_and_slash.tags.TagType;

import java.util.List;
import java.util.stream.Collectors;

public class MobTag extends NormalModTag {
    public static MobTag SERIALIZER = new MobTag("");

    public static MobTag of(String id) {
        return (MobTag) register(TagType.Mob, new MobTag(id));
    }

    public static List<MobTag> getAll() {
        return ModTag.MAP.get(TagType.Mob).stream().map(x -> (MobTag) x).collect(Collectors.toList());
    }

    public MobTag(String id) {
        super(id);
    }

    @Override
    public ModTag fromString(String s) {
        return new MobTag(s);
    }

    @Override
    public TagType getTagType() {
        return TagType.Mob;
    }
}
