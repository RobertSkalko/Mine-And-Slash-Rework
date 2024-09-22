package com.robertx22.mine_and_slash.database.data.requirements;

import com.robertx22.mine_and_slash.database.data.requirements.bases.BaseRequirement;
import com.robertx22.mine_and_slash.database.data.requirements.bases.GearRequestedFor;
import com.robertx22.mine_and_slash.tags.TagList;
import com.robertx22.mine_and_slash.tags.TagType;
import net.minecraft.network.chat.Component;
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

    public boolean isEmpty() {
        return included.isEmpty() && excluded.isEmpty();
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

        List<MutableComponent> all = new ArrayList<>();

        all.add(Component.literal("Tag Requirements"));
        if (!included.isEmpty()) {
            all.add(Component.literal("For:").append(String.join(", ", this.included)));
        }
        if (!excluded.isEmpty()) {
            all.add(Component.literal("Not For:").append(String.join(", ", this.excluded)));
        }
        return all;
    }
}
