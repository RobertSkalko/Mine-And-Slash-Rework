package com.robertx22.mine_and_slash.tags.imp;

import com.robertx22.mine_and_slash.tags.ModTag;
import com.robertx22.mine_and_slash.tags.NormalModTag;
import com.robertx22.mine_and_slash.tags.TagType;

import java.util.List;
import java.util.stream.Collectors;

public class EffectTag extends NormalModTag {

    public static EffectTag SERIALIZER = new EffectTag("");

    public static EffectTag of(String id) {
        return (EffectTag) register(TagType.Effect, new EffectTag(id));
    }

    public static List<EffectTag> getAll() {
        return ModTag.MAP.get(TagType.Effect).stream().map(x -> (EffectTag) x).collect(Collectors.toList());
    }

    public EffectTag(String id) {
        super(id);
    }

    @Override
    public ModTag fromString(String s) {
        return new EffectTag(s);
    }

    @Override
    public TagType getTagType() {
        return TagType.Effect;
    }
}
