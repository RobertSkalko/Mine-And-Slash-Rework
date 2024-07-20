package com.robertx22.age_of_exile.saveclasses.perks;

import com.google.common.collect.ImmutableSet;
import com.robertx22.age_of_exile.database.data.talent_tree.TalentTree;
import com.robertx22.age_of_exile.saveclasses.PointData;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


public class SchoolData {


    private Set<PointData> list = new HashSet<>();


    public boolean allocate(PointData point) {
        return list.add(point);
    }

    public boolean unAllocate(PointData point) {
        return list.remove(point);
    }

    public boolean isAllocated(PointData point) {
        return list.contains(point);
    }

    public List<PointData> getAllocatedPoints(TalentTree school) {
        return list.stream().filter(x -> school.calcData.getPerk(x) != null).collect(Collectors.toList());

    }

    public Set<PointData> getAllocatedPoints() {
        return ImmutableSet.copyOf(list);
    }

    public int getAllocatedPointsInSchool() {
        return list.size();

    }
}
