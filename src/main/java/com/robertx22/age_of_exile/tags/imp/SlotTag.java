package com.robertx22.age_of_exile.tags.imp;

import com.robertx22.age_of_exile.tags.ModTag;
import com.robertx22.age_of_exile.tags.NormalModTag;
import com.robertx22.age_of_exile.tags.TagType;

import java.util.List;
import java.util.stream.Collectors;

// purely for syntax
public class SlotTag extends NormalModTag {

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
}
