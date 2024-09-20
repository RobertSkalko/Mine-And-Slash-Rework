package com.robertx22.mine_and_slash.database.data.requirements;

import com.robertx22.library_of_exile.wrappers.ExileText;
import com.robertx22.mine_and_slash.database.data.requirements.bases.GearRequestedFor;
import com.robertx22.mine_and_slash.saveclasses.gearitem.gear_bases.ITooltipList;
import net.minecraft.network.chat.MutableComponent;

import java.util.ArrayList;
import java.util.List;

public class Requirements implements ITooltipList {


    public List<TagRequirement> tag_requirements = new ArrayList<>();

    public Requirements(TagRequirement req) {
        this.tag_requirements.add(req);
    }


    public boolean satisfiesAllRequirements(GearRequestedFor requested) {
        for (TagRequirement req : tag_requirements) {
            if (req.meetsRequierment(requested) == false) {
                return false;
            }
        }

        return true;
    }


    @Override
    public List<MutableComponent> GetTooltipString() {
        List<MutableComponent> list = new ArrayList<>();
        this.tag_requirements.forEach(x -> {
            list.add(ExileText.ofText("").get());
            list.addAll(x.GetTooltipString());
        });
        return list;
    }
}
