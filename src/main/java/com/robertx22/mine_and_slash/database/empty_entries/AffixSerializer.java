package com.robertx22.mine_and_slash.database.empty_entries;

import com.robertx22.mine_and_slash.database.data.affixes.Affix;

public class AffixSerializer extends Affix {

    private AffixSerializer() {
    }

    public static AffixSerializer getInstance() {
        return SingletonHolder.INSTANCE;
    }

    @Override
    public boolean isRegistryEntryValid() {
        return true;
    }

    @Override
    public String GUID() {
        return "unknown_affix";
    }

    @Override
    public String locNameForLangFile() {
        return "Unknown Affix";
    }

    private static class SingletonHolder {
        private static final AffixSerializer INSTANCE = new AffixSerializer();
    }
}
