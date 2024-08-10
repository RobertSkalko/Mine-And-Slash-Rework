package com.robertx22.mine_and_slash.database.data.stats.types.resources.mana;

import com.robertx22.mine_and_slash.database.data.stats.StatScaling;
import com.robertx22.mine_and_slash.database.data.stats.types.resources.BaseRegenClass;
import com.robertx22.mine_and_slash.saveclasses.unit.ResourceType;

public class ManaRegen extends BaseRegenClass {
    public static String GUID = "mana_regen";

    public static ManaRegen getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private ManaRegen() {
        this.min = 0;
        this.scaling = StatScaling.NORMAL;
        this.group = StatGroup.MAIN;
    }

    @Override
    public ResourceType getResourceType() {
        return ResourceType.mana;
    }

    @Override
    public String GUID() {
        return GUID;
    }

    @Override
    public String locNameForLangFile() {
        return "Mana Regen";
    }

    private static class SingletonHolder {
        private static final ManaRegen INSTANCE = new ManaRegen();
    }
}
