package com.robertx22.mine_and_slash.aoe_data.database.spell_schools;

import com.google.common.base.Preconditions;
import com.robertx22.mine_and_slash.database.data.spell_school.SpellSchool;
import com.robertx22.mine_and_slash.saveclasses.PointData;

public class SchoolBuilder {

    SpellSchool school = new SpellSchool();

    public static SchoolBuilder of(String id, String name) {

        SchoolBuilder b = new SchoolBuilder();
        b.school.id = id;
        b.school.locname = name;
        return b;
    }


    public SchoolBuilder add(String id, PointData point) {

        Preconditions.checkArgument(SpellSchool.MAX_X_ROWS >= point.x && point.x > -1);
        Preconditions.checkArgument(SpellSchool.MAX_Y_ROWS >= point.y && point.y > -1);
        Preconditions.checkArgument(school.perks.values().stream().noneMatch(x -> x.equals(point)));

        school.perks.put(id, point);
        return this;
    }

    public SpellSchool build() {
        school.addToSerializables();
        return school;
    }
}
