package com.robertx22.age_of_exile.database.data.requirements;

import com.robertx22.age_of_exile.database.data.requirements.bases.GearRequestedFor;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.ITooltipList;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.TooltipInfo;
import com.robertx22.library_of_exile.registry.serialization.ISerializablePart;
import com.robertx22.library_of_exile.wrappers.ExileText;
import net.minecraft.network.chat.MutableComponent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Requirements implements ITooltipList {

    public static Requirements EMPTY = new Requirements((TagRequirement) null);

    public List<TagRequirement> tag_requirements = new ArrayList<>();

    public Requirements(TagRequirement req) {
        this.tag_requirements.add(req);
    }

    public Requirements(TagRequirement... req1) {
        for (TagRequirement req : req1) {
            this.tag_requirements.add(req);
        }
    }

    public Requirements(List<TagRequirement> reqs) {
        this.tag_requirements.addAll(reqs);
    }

    public boolean satisfiesAllRequirements(GearRequestedFor requested) {

        for (TagRequirement req : tag_requirements) {
            if (req.meetsRequierment(requested) == false) {
                return false;
            }
        }

        return true;
    }

    public static List<ISerializablePart> possible = Arrays.asList(new TagRequirement());

    @Override
    public List<MutableComponent> GetTooltipString(TooltipInfo info) {
        List<MutableComponent> list = new ArrayList<>();
        this.tag_requirements.forEach(x -> {
            list.add(ExileText.ofText("").get());
            list.addAll(x.GetTooltipString(info));
        });
        return list;
    }
}
