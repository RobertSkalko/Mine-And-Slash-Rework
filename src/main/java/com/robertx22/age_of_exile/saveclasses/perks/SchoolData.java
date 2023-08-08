package com.robertx22.age_of_exile.saveclasses.perks;

import com.robertx22.age_of_exile.database.data.talent_tree.TalentTree;
import com.robertx22.age_of_exile.saveclasses.PointData;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class SchoolData {


    private List<PointData> list = new ArrayList<>();

    
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
    //HashMap<PointData, Boolean> map = new HashMap<>();

    /*
    public boolean isAllocated(PointData point) {
        return map.getOrDefault(point, false);
    }

    public List<PointData> getAllocatedPoints(TalentTree school) {
        return map.entrySet()
                .stream()
                .filter(x -> {
                    return x.getValue() && school.calcData.getPerk(x.getKey()) != null;
                })
                .map(x -> x.getKey())
                .collect(Collectors.toList());
    }

     */

    public int getAllocatedPointsInSchool() {

        return list.size();
        /*

        int points = 0;

        for (Map.Entry<PointData, Boolean> x : map.entrySet()) {
            if (x.getValue()) {
                points++;
            }
        }

        return points;


         */
    }
}
