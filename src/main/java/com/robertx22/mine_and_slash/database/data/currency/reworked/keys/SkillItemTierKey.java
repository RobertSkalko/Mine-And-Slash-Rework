package com.robertx22.mine_and_slash.database.data.currency.reworked.keys;

import com.robertx22.temp.SkillItemTier;

public class SkillItemTierKey extends KeyInfo {

    public SkillItemTier tier;

    public SkillItemTierKey(SkillItemTier tier) {
        this.tier = tier;
    }

    @Override
    public String GUID() {
        return tier.tier + "";
    }
}
