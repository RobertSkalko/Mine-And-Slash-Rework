package com.robertx22.mine_and_slash.database.data.stats.types.resources.magic_shield;

import com.robertx22.mine_and_slash.database.data.stats.StatScaling;
import com.robertx22.mine_and_slash.database.data.stats.types.resources.BaseRegenClass;
import com.robertx22.mine_and_slash.saveclasses.unit.ResourceType;
import com.robertx22.mine_and_slash.uncommon.enumclasses.Elements;

public class MagicShieldRegen extends BaseRegenClass {
    public static String GUID = "magic_shield_regen";

    public static MagicShieldRegen getInstance() {
        return MagicShieldRegen.SingletonHolder.INSTANCE;

    }

    private MagicShieldRegen() {
        this.min = 0;
        this.scaling = StatScaling.NORMAL;
        this.group = StatGroup.MAIN;
    }

    @Override
    public String GUID() {
        return GUID;
    }

    @Override
    public Elements getElement() {
        return null;
    }

    @Override
    public boolean IsPercent() {
        return false;
    }

    @Override
    public ResourceType getResourceType() {
        return ResourceType.magic_shield;
    }

    @Override
    public String locNameForLangFile() {
        return "Magic Shield Regen";
    }

    private static class SingletonHolder {
        private static final MagicShieldRegen INSTANCE = new MagicShieldRegen();
    }
}
