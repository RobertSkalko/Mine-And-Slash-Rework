package com.robertx22.mine_and_slash.tags.imp;

import com.robertx22.mine_and_slash.tags.ModTag;
import com.robertx22.mine_and_slash.tags.NormalModTag;
import com.robertx22.mine_and_slash.tags.TagType;

import java.util.List;
import java.util.stream.Collectors;

public class ElementTag extends NormalModTag {

    public static ElementTag of(String id) {
        return (ElementTag) register(TagType.Element, new ElementTag(id));
    }

    public static List<ElementTag> getAll() {
        return ModTag.MAP.get(TagType.Element).stream().map(x -> (ElementTag) x).collect(Collectors.toList());
    }

    public ElementTag(String id) {
        super(id);
    }

    @Override
    public ModTag fromString(String s) {
        return new ElementTag(s);
    }

    @Override
    public TagType getTagType() {
        return TagType.Element;
    }
}
