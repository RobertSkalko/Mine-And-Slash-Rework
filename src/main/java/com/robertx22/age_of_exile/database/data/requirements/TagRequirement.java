package com.robertx22.age_of_exile.database.data.requirements;

import com.robertx22.age_of_exile.database.data.requirements.bases.BaseRequirement;
import com.robertx22.age_of_exile.database.data.requirements.bases.GearRequestedFor;
import com.robertx22.age_of_exile.tags.TagList;
import com.robertx22.age_of_exile.tags.TagType;
import net.minecraft.network.chat.MutableComponent;

import java.util.ArrayList;
import java.util.List;

public class TagRequirement extends BaseRequirement<TagRequirement> {

    public enum ReqType {
        INCLUDES_ANY, HAS_ALL
    }

    public TagType type = TagType.GearSlot;

    public List<String> included = new ArrayList<>();
    public List<String> excluded = new ArrayList<>();

    public ReqType req_type = ReqType.INCLUDES_ANY;

    public TagRequirement(TagType type, List<String> included, List<String> excluded) {
        this.type = type;
        this.included = included;
        this.excluded = excluded;
    }

    private TagRequirement() {
    }

    @Override
    public boolean meetsRequierment(GearRequestedFor requested) {
        TagList list = requested.forSlot.getTags();

        if (excluded.stream().anyMatch(y -> list.contains(y))) {
            return false;
        }

        if (req_type == ReqType.HAS_ALL) {
            return included.stream().allMatch(x -> list.contains(x));
        } else {
            return included.stream().anyMatch(x -> list.contains(x));
        }

    }


    @Override
    public List<MutableComponent> GetTooltipString() {
        return new ArrayList<>();
    }
}
