package com.robertx22.age_of_exile.saveclasses.skill_gem;

import com.robertx22.age_of_exile.uncommon.enumclasses.PlayStyle;
import com.robertx22.age_of_exile.uncommon.interfaces.IAutoLocName;

public interface ISkillGem extends IAutoLocName {


    int getRequiredLevel();

    PlayStyle getStyle();

}
