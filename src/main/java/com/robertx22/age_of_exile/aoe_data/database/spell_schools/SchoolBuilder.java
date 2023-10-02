package com.robertx22.age_of_exile.aoe_data.database.spell_schools;

import com.google.common.base.Preconditions;
import com.robertx22.age_of_exile.database.data.spell_school.AscendancyClass;
import com.robertx22.age_of_exile.saveclasses.PointData;

public class SchoolBuilder {

    AscendancyClass school = new AscendancyClass();

    public static SchoolBuilder of(String id, String name) {

        SchoolBuilder b = new SchoolBuilder();
        b.school.id = id;
        b.school.locname = name;
        return b;
    }


    public SchoolBuilder add(String id, PointData point) {

        Preconditions.checkArgument(AscendancyClass.MAX_X_ROWS >= point.x && point.x > -1);
        Preconditions.checkArgument(AscendancyClass.MAX_Y_ROWS >= point.y && point.y > -1);
        Preconditions.checkArgument(school.perks.values().stream().noneMatch(x -> x.equals(point)));

        school.perks.put(id, point);
        return this;
    }

    public AscendancyClass build() {
        school.addToSerializables();
        return school;
    }
}
