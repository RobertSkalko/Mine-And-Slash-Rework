package com.robertx22.mine_and_slash.database.data.prophecy.starts;

import com.robertx22.mine_and_slash.database.data.prophecy.ProphecyModifierType;
import com.robertx22.mine_and_slash.database.data.prophecy.ProphecyStart;
import com.robertx22.mine_and_slash.loot.LootInfo;
import com.robertx22.mine_and_slash.loot.blueprints.ItemBlueprint;
import com.robertx22.mine_and_slash.loot.blueprints.SkillGemBlueprint;
import com.robertx22.mine_and_slash.saveclasses.skill_gem.SkillGemData;

public class SupportGemProphecy extends ProphecyStart {
    @Override
    public ItemBlueprint create(int lvl, int tier) {
        var info = LootInfo.ofLevel(lvl);
        info.map_tier = tier;
        return new SkillGemBlueprint(info, SkillGemData.SkillGemType.SUPPORT);
    }

    @Override
    public boolean acceptsModifier(ProphecyModifierType type) {
        return true;
    }

    @Override
    public String GUID() {
        return "support_gem";
    }

    @Override
    public int Weight() {
        return 300;
    }

    @Override
    public String locNameForLangFile() {
        return "Support Gem Prophecy";
    }
}
