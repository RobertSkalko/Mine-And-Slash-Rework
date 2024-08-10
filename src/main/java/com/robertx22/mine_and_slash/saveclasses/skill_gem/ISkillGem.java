package com.robertx22.mine_and_slash.saveclasses.skill_gem;

import com.robertx22.mine_and_slash.uncommon.enumclasses.PlayStyle;
import com.robertx22.mine_and_slash.uncommon.interfaces.IAutoLocName;

public interface ISkillGem extends IAutoLocName {


    int getRequiredLevel();

    PlayStyle getStyle();

}
