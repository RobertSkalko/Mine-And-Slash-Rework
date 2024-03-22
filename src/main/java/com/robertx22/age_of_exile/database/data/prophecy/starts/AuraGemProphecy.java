package com.robertx22.age_of_exile.database.data.prophecy.starts;

import com.robertx22.age_of_exile.database.data.prophecy.ProphecyStart;
import com.robertx22.age_of_exile.loot.LootInfo;
import com.robertx22.age_of_exile.loot.blueprints.ItemBlueprint;
import com.robertx22.age_of_exile.loot.blueprints.SkillGemBlueprint;
import com.robertx22.age_of_exile.saveclasses.skill_gem.SkillGemData;

public class AuraGemProphecy extends ProphecyStart {
    @Override
    public ItemBlueprint create(int lvl, int tier) {
        var info = LootInfo.ofLevel(lvl);
        info.map_tier = tier;
        return new SkillGemBlueprint(info, SkillGemData.SkillGemType.AURA);
    }


    @Override
    public String GUID() {
        return "aura_gem";
    }

    @Override
    public int Weight() {
        return 100;
    }

    @Override
    public String locNameForLangFile() {
        return "Aura Gem Prophecy";
    }
}
