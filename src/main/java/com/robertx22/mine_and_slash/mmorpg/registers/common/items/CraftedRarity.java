package com.robertx22.mine_and_slash.mmorpg.registers.common.items;


import com.robertx22.mine_and_slash.database.data.rarities.GearRarity;
import com.robertx22.mine_and_slash.database.registry.ExileDB;
import com.robertx22.mine_and_slash.uncommon.interfaces.data_items.IRarity;

public enum CraftedRarity {

    COMMON(IRarity.COMMON_ID),
    UNCOMMON(IRarity.UNCOMMON),
    RARE(IRarity.RARE_ID),
    EPIC(IRarity.EPIC_ID),
    LEGENDARY(IRarity.LEGENDARY_ID),
    MYTHIC(IRarity.MYTHIC_ID);

    public String id;


    public GearRarity getRarity() {
        return ExileDB.GearRarities().get(id);
    }

    public int getPercent() {
        return getRarity().stat_percents.getMiddle();
    }

    CraftedRarity(String id) {
        this.id = id;
    }
}
