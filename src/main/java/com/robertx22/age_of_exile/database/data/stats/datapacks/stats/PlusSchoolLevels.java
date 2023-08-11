package com.robertx22.age_of_exile.database.data.stats.datapacks.stats;

import com.robertx22.age_of_exile.capability.entity.EntityData;
import com.robertx22.age_of_exile.database.data.spell_school.AscendancyClass;
import com.robertx22.age_of_exile.database.data.stats.StatScaling;
import com.robertx22.age_of_exile.database.data.stats.datapacks.base.BaseDatapackStat;
import com.robertx22.age_of_exile.database.data.stats.name_regex.StatNameRegex;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.saveclasses.unit.StatData;

public class PlusSchoolLevels extends BaseDatapackStat implements IAfterStatCalc {

    public static String SER_ID = "plus_school";

    public String school;

    transient String locname;

    public PlusSchoolLevels(AscendancyClass school) {
        this(school.GUID());
        this.locname = "To All " + school.locname + " Skills";
    }

    public PlusSchoolLevels(String school) {
        super(SER_ID);

        this.id = "plus_to_" + school;
        this.school = school;

        this.is_perc = true;
        this.min = 0;
        this.scaling = StatScaling.NONE;
    }

    // todo redo this

    @Override
    public void affectUnit(EntityData unitdata, StatData statdata) {

        AscendancyClass sc = ExileDB.SpellSchools()
                .get(school);

        /*
        sc.spells.keySet()
                .forEach(x -> {
                    Load.spells(unitdata.getEntity())
                            .getSpellsData()
                            .addToLevelsFromStat(x, (int) statdata.getValue());
                });

         */

    }

    @Override
    public StatNameRegex getStatNameRegex() {
        return StatNameRegex.BASIC;
    }

    @Override
    public boolean IsPercent() {
        return false;
    }

    @Override
    public String locDescForLangFile() {
        return "";
    }

    @Override
    public String locNameForLangFile() {
        return locname;
    }

}