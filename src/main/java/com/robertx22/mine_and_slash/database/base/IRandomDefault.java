package com.robertx22.mine_and_slash.database.base;

import com.robertx22.library_of_exile.registry.IWeighted;
import com.robertx22.library_of_exile.utils.RandomUtils;
import com.robertx22.mine_and_slash.database.data.requirements.bases.GearRequestedFor;
import com.robertx22.mine_and_slash.mmorpg.MMORPG;

import java.util.List;
import java.util.stream.Collectors;

public interface IRandomDefault<T extends IhasRequirements & IWeighted> extends IRandom<T, GearRequestedFor> {

    public default T random(GearRequestedFor gearRequestedFor) {

        if (gearRequestedFor.gearData != null && gearRequestedFor.gearData.GetBaseGearType() == null) {
            MMORPG.LOGGER.log("Gear slot doesn't exist, or was renamed.");
            return null;
        }

        List<T> allThatMeetReq = allThatMeetRequirement(All(), gearRequestedFor);

        if (allThatMeetReq.size() == 0) {
            MMORPG.LOGGER.log("No Matching item found for the gear requested, returning null.");
            return null;
        }

        return RandomUtils.weightedRandom(allThatMeetReq);
    }

    public default T random() {
        return RandomUtils.weightedRandom(All());
    }

    public default List<T> allThatMeetRequirement(List<T> list, GearRequestedFor gearRequestedFor) {
        return list.stream()
                .filter(x -> {
                    return x.requirements()
                            .satisfiesAllRequirements(gearRequestedFor);
                })
                .collect(Collectors.toList());
    }

}
