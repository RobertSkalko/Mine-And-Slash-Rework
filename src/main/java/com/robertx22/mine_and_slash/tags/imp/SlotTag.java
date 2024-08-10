package com.robertx22.mine_and_slash.tags.imp;

import com.robertx22.mine_and_slash.tags.ModTag;
import com.robertx22.mine_and_slash.tags.NormalModTag;
import com.robertx22.mine_and_slash.tags.TagType;

import java.util.List;
import java.util.stream.Collectors;

// purely for syntax
public class SlotTag extends NormalModTag {

    public static SlotTag SERIALIZER = new SlotTag("");


    public static SlotTag of(String id) {
        return (SlotTag) register(TagType.GearSlot, new SlotTag(id));
    }

    public static List<SlotTag> getAll() {
        return ModTag.MAP.get(TagType.GearSlot).stream().map(x -> (SlotTag) x).collect(Collectors.toList());
    }
    
    public SlotTag(String id) {
        super(id);
    }

    @Override
    public ModTag fromString(String s) {
        return new SlotTag(s);
    }

    @Override
    public TagType getTagType() {
        return TagType.GearSlot;
    }
}
