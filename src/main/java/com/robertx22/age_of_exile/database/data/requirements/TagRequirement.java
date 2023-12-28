package com.robertx22.age_of_exile.database.data.requirements;

import com.robertx22.age_of_exile.database.data.requirements.bases.BaseRequirement;
import com.robertx22.age_of_exile.database.data.requirements.bases.GearRequestedFor;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.TooltipInfo;
import com.robertx22.age_of_exile.tags.TagList;
import com.robertx22.age_of_exile.tags.TagType;
import net.minecraft.network.chat.MutableComponent;

import java.util.ArrayList;
import java.util.List;

public class TagRequirement extends BaseRequirement<TagRequirement> {

    public TagType type = TagType.GearSlot;

    public List<String> included = new ArrayList<>();
    public List<String> excluded = new ArrayList<>();

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

        if (included.stream()
                .anyMatch(x -> list.contains(x))) {
            if (excluded.stream()
                    .noneMatch(y -> list.contains(y))) {
                return true;
            }
        }

        return false;
    }


    @Override
    public List<MutableComponent> GetTooltipString(TooltipInfo info) {
        return new ArrayList<>();
    }
}
