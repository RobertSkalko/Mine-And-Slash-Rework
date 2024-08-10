package com.robertx22.mine_and_slash.loot.generators;

import com.robertx22.mine_and_slash.config.forge.ServerContainer;
import com.robertx22.mine_and_slash.loot.LootInfo;
import com.robertx22.mine_and_slash.loot.blueprints.GearBlueprint;
import com.robertx22.mine_and_slash.loot.blueprints.SkillGemBlueprint;
import com.robertx22.mine_and_slash.saveclasses.skill_gem.SkillGemData;
import com.robertx22.mine_and_slash.uncommon.enumclasses.LootType;
import net.minecraft.world.item.ItemStack;

public class SuppGemLootGen extends BaseLootGen<GearBlueprint> {

    public SuppGemLootGen(LootInfo info) {
        super(info);

    }

    @Override
    public float baseDropChance() {
        return (float) (ServerContainer.get().SUPP_GEM_DROPRATE.get().floatValue());
    }

    @Override
    public LootType lootType() {
        return LootType.SkillGem;
    }

    @Override
    public boolean condition() {
        return info.level > 5;
    }

    @Override
    public ItemStack generateOne() {
        SkillGemBlueprint blueprint = new SkillGemBlueprint(info, SkillGemData.SkillGemType.SUPPORT);
        return blueprint.createStack();
    }

}